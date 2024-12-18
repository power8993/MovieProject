package movie.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import movie.domain.MovieVO;

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
	

	
}
