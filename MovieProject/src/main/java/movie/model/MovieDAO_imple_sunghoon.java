package movie.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import movie.domain.MovieVO;
import movie.domain.ShowTimeVO_sunghoon;

public class MovieDAO_imple_sunghoon implements MovieDAO_sunghoon {
	
	private DataSource ds;	// DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다.
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	// 생성자
	public MovieDAO_imple_sunghoon() {
		
		try {
		    Context initContext = new InitialContext();
		    Context envContext  = (Context)initContext.lookup("java:/comp/env");
		    ds = (DataSource)envContext.lookup("jdbc/semioracle");		
		    					    
		} catch(NamingException e) {
			e.printStackTrace();
		} 
		
	}
	
	
	// === Method === //
	// 사용한 자원을 반납하는 close() 메소드 생성하기
	private void close() {
		
		try {
			if(rs    != null) {rs.close();    rs=null;}
			if(pstmt != null) {pstmt.close(); pstmt=null;}
			if(conn  != null) {conn.close();  conn=null;}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}// end of private void close() {}-------------------
	

	// 영화 예약 페이지에서 보여줄 영화 리스트
	@Override
	public List<MovieVO> reservationMovieList() throws SQLException {
		
		List<MovieVO> movieList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			
			String sql = " select movie_title, movie_grade, seq_movie_no "
					   + " from tbl_movie "
					   + " where sysdate >= start_date and sysdate <= end_date + 1 ";
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MovieVO movie = new MovieVO();
				movie.setSeq_movie_no(rs.getInt("seq_movie_no"));
				movie.setMovie_title(rs.getString("movie_title"));
				movie.setMovie_grade(rs.getString("movie_grade"));
				
				movieList.add(movie);
				
			}
			
		} finally {
			close();
		}
		
		return movieList;
		
	} // end of public List<MovieVO> reservationMovieList() throws SQLException----------------------------------


	
	// 영화와 날짜를 선택했을 때 보여줄 상영하는 영화 시간 리스트
	@Override
	public List<ShowTimeVO_sunghoon> getScreenTime(Map<String, String> paraMap) throws SQLException {
		
		List<ShowTimeVO_sunghoon> showTimeList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			
			String sql = " select to_char(start_time, 'hh24mi') as start_time, to_char(end_time, 'hh24mi') as end_time, "
					   + " seat_arr, seq_showtime_no, fk_seq_movie_no, total_viewer, unused_seat, fk_screen_no "
					   + " from tbl_showtime "
					   + " where FK_SEQ_MOVIE_NO = ? and to_char(start_time, 'yyyymmdd') = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, paraMap.get("seq_movie_no"));
			pstmt.setString(2, paraMap.get("input_date"));
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ShowTimeVO_sunghoon showTime = new ShowTimeVO_sunghoon();
				showTime.setStart_time(rs.getString("start_time"));
				showTime.setEnd_time(rs.getString("end_time"));
				showTime.setSeat_arr(rs.getString("seat_arr"));
				showTime.setSeq_showtime_no(rs.getInt("seq_showtime_no"));
				showTime.setFk_seq_movie_no(rs.getInt("fk_seq_movie_no"));
				showTime.setTotal_viewer(rs.getInt("total_viewer"));
				showTime.setUnused_seat(rs.getInt("unused_seat"));
				showTime.setFk_screen_no(rs.getInt("fk_screen_no"));
				
				showTimeList.add(showTime);
			}
			
		} finally {
			close();
		}
		
		return showTimeList;
		
	} // end of public List<ShowTimeVO_sunghoon> getScreenTime(Map<String, String> paraMap) throws SQLException--------------------


	
	// 영화 티켓 가격 가져오기
	@Override
	public List<Integer> getTicketPrice() throws SQLException {
		
		List<Integer> ticketPriceList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			
			String sql = " select price "
					   + " from tbl_movie_price "
					   + " order by price desc ";
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ticketPriceList.add(rs.getInt("price"));
			}
			
		} finally {
			close();
		}
		
		return ticketPriceList;
		
	} // end of public List<Integer> getTicketPrice() throws SQLException--------------------------------------


	
	// 결제 내역 생성
	@Override
	public int makePayment(Map<String, String> paraMap) throws SQLException {
		
		int n = 0;
	
		try {
			conn = ds.getConnection();
			
			String sql = " insert into tbl_payment(IMP_UID, FK_USER_ID, FK_SEQ_SHOWTIME_NO, PAY_amount, PAY_TYPE, PAY_STATUS, PAY_SUCCESS_DATE) "
					   + " values(?,?,?,?,?,?,sysdate) ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(paraMap.get("imp_uid")));
			pstmt.setString(2, paraMap.get("userid"));
			pstmt.setInt(3, Integer.parseInt(paraMap.get("seq_movie_no")));
			pstmt.setInt(4, Integer.parseInt(paraMap.get("ticketPrice")));
			pstmt.setString(5, "card");
			pstmt.setString(6, paraMap.get("status"));
			
			n = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		return n;

	} // end of public int makePayment(Map<String, String> paraMap) throws SQLException---------------------
	

	
}
