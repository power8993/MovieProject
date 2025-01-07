package movie.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import movie.domain.MovieVO;
import movie.domain.ShowTimeVO_sunghoon;
import reservation.controller.TicketVO_hoon;
import reservation.domain.TicketVO;

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
			
			String sql = " select case when length(movie_title) < 20 then movie_title else substr(movie_title, 0, 17) || '...' end as movie_title, seq_movie_no, movie_grade "
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
					   + " where FK_SEQ_MOVIE_NO = ? and to_char(start_time, 'yyyymmdd') = ? "
					   + " order by fk_screen_no asc ";
			
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
			pstmt.setString(1, paraMap.get("imp_uid"));
			pstmt.setString(2, paraMap.get("userid"));
			pstmt.setInt(3, Integer.parseInt(paraMap.get("seq_showtime_no")));
			pstmt.setInt(4, Integer.parseInt(paraMap.get("ticketPrice")));
			pstmt.setString(5, "card");
			pstmt.setString(6, paraMap.get("status"));
			
			n = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		return n;

	} // end of public int makePayment(Map<String, String> paraMap) throws SQLException---------------------


	// 티켓 생성
	@Override
	public int makeTicket(TicketVO_hoon ticket) throws SQLException {
		
		int n = 0;
		
		try {
			
			conn = ds.getConnection();
			
			String sql = " insert into tbl_ticket(SEQ_TICKET_NO, FK_IMP_UID, SEAT_NO, TICKET_PRICE, TICKET_AGE_GROUP) "
					   + " values(SEQ_TICKET_NO.nextval,?,?,?,?) ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, ticket.getFk_imp_uid());
			pstmt.setString(2, ticket.getSeat_no());
			pstmt.setInt(3, ticket.getTicket_price());
			pstmt.setString(4, ticket.getTicket_age_group());
			
			n = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		return n;
		
	} // end of public int makeTicket(TicketVO_hoon ticket) throws SQLException-----------------------------


	// 좌석 배열 가져오기
	@Override
	public String getSeatArr(int seq_showtime_no) throws SQLException {
		
		String seat_arr = "";
		
		try {
			
			conn = ds.getConnection();
			
			String sql = " select seat_arr"
					   + " from tbl_showtime"
					   + " where SEQ_SHOWTIME_NO = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, seq_showtime_no);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				seat_arr = rs.getString("seat_arr");
			}
			
		} finally {
			close();
		}
		
		return seat_arr;
		
	} // end of public String getSeatArr(int seq_showtime_no) throws SQLException


	// 상영 영화 수정
	@Override
	public int updateShowtime(String seat_arr_str, int seq_showtime_no, int selected_seat_arr_length) throws SQLException {
		
		int n = 0;
		
		try {
			
			conn = ds.getConnection();
			
			String sql = " update tbl_showtime set SEAT_ARR = ?, UNUSED_SEAT = UNUSED_SEAT - ? "
					   + " where SEQ_SHOWTIME_NO = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, seat_arr_str);
			pstmt.setInt(2, selected_seat_arr_length);
			pstmt.setInt(3, seq_showtime_no);
			
			n = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		return n;
	}


	// 보유중인 포인트 가져오기
	@Override
	public int getHavingPoint(String userid) throws SQLException {
		
		int havingPoint = 0;
		
		try {
			
			conn = ds.getConnection();
			
			String sql = " select not_used_point - used_point as havingpoint "
					   + " from "
					   + " ( "
					   + "    select NVL(SUM(p2.point), 0) as not_used_point "
					   + "    from tbl_payment p1 JOIN tbl_showtime s "
					   + "    on p1.fk_SEQ_SHOWTIME_NO = s.SEQ_SHOWTIME_NO "
					   + "    join tbl_point p2 "
					   + "    on p1.imp_uid = p2.fk_imp_uid "
					   + "    where sysdate > s.end_time and p2.fk_user_id = ? and point_type = 1 "
					   + " ) A "
					   + " , "
					   + " ( "
					   + "    select NVL(SUM(point), 0) as used_point "
					   + "    from tbl_point "
					   + "    where fk_user_id = ? and point_type = 0 "
					   + " ) B ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			pstmt.setString(2, userid);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				havingPoint = rs.getInt("havingPoint");
			}
			
		} finally {
			close();
		}
		
		return havingPoint;
		
	} // end of public int getHavingPoint(String userid) throws SQLException------------------------


	// 포인트 거래 내역 만들기
	@Override
	public int makePoint(Map<String, String> paraMap) throws SQLException {

		int n = 0;
		
		int using_point = Integer.parseInt(paraMap.get("using_point"));
		int ticketPrice = Integer.parseInt(paraMap.get("ticketPrice"));
		String userid = paraMap.get("userid");
		String imp_uid = paraMap.get("imp_uid");
		
		try {
			conn = ds.getConnection();
			
			if(using_point == 0) {
					
				String sql = " insert into tbl_point(SEQ_POINT_NO, FK_USER_ID, FK_IMP_UID, POINT_TYPE, POINT) "
						   + " values(SEQ_POINT_NO.nextval, ?, ?, 1, ?) ";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userid);
				pstmt.setString(2, imp_uid);
				pstmt.setInt(3, ticketPrice/10);
				
				n = pstmt.executeUpdate();
				
			}
			else {
				
				String sql = " insert into tbl_point(SEQ_POINT_NO, FK_USER_ID, FK_IMP_UID, POINT_TYPE, POINT) "
						   + " values(SEQ_POINT_NO.nextval, ?, ?, 1, ?) ";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userid);
				pstmt.setString(2, imp_uid);
				pstmt.setInt(3, (ticketPrice - using_point)/10);
				
				n = pstmt.executeUpdate();
				
				sql = " insert into tbl_point(SEQ_POINT_NO, FK_USER_ID, FK_IMP_UID, POINT_TYPE, POINT) "
					+ " values(SEQ_POINT_NO.nextval, ?, ?, 0, ?) ";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userid);
				pstmt.setString(2, imp_uid);
				pstmt.setInt(3, using_point);
				
				n = pstmt.executeUpdate();
				
			}
		} finally {
			close();
		}
		
		return n;
		
	} // end of public int makePoint(Map<String, String> paraMap) throws SQLException----------------------


	// 티켓 정보 가져오기
	@Override
	public List<TicketVO> getTickets(String userid, String imp_uid) throws SQLException {
		
		List<TicketVO> ticketlist = new ArrayList<>();
		
		try {
			
			conn = ds.getConnection();
			
			String str = " select ticket_price, ticket_age_group, seat_no "
					   + " from tbl_ticket "
					   + " where fk_imp_uid = ? "
					   + " order by substr(seat_no, 1, 1), to_number(substr(seat_no, 2)) ";
			
			pstmt = conn.prepareStatement(str);
			pstmt.setString(1, imp_uid);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				TicketVO ticket = new TicketVO();
				
				ticket.setTicket_price(rs.getInt("ticket_price"));
				ticket.setTicket_age_group(rs.getString("ticket_age_group"));
				ticket.setSeat_no(rs.getString("seat_no"));
				
				ticketlist.add(ticket);
			}
			
		} finally {
			close();
		}
		
		return ticketlist;
		
	} // end of public Map<String, String> getTickets() throws SQLException----------------


	
	// 결제 번호로 영화 이름 가져오기
	@Override
	public Map<String, String> getMovieTitle(String imp_uid) throws SQLException {
		
		Map<String, String> map = new HashMap<>();
		
		try {
			conn = ds.getConnection();
			
			String sql = " select movie_title, to_char(start_time, 'yyyy.mm.dd hh24:mi') as start_time, to_char(fk_screen_no) as fk_screen_no, movie_grade, poster_file "
					   + " from tbl_payment p join tbl_showtime s "
					   + " on s.seq_showtime_no = p.fk_seq_showtime_no "
					   + " join tbl_movie m "
					   + " on m.seq_movie_no = s.fk_seq_movie_no "
					   + " where p.imp_uid = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, imp_uid);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				map.put("movie_title", rs.getString("movie_title"));
				map.put("start_time", rs.getString("start_time"));
				map.put("fk_screen_no", rs.getString("fk_screen_no"));
				map.put("movie_grade", rs.getString("movie_grade"));
				map.put("poster_file", rs.getString("poster_file"));
			}
			
		} finally {
			close();
		}
		
		return map;
	}
	

	
}
