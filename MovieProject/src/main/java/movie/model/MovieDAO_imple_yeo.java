package movie.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import movie.domain.MovieVO;

public class MovieDAO_imple_yeo implements MovieDAO {
	
	private DataSource ds;	// DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다.
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	// 생성자
	public MovieDAO_imple_yeo() {
		
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
	
	
	
	
	
	// 영화를 등록해주는 메소드(tbl_movie 테이블에 insert)
	@Override
	public int registerMovie(MovieVO movie) throws SQLException {
		
		int result = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql = " insert into tbl_movie (seq_movie_no, fk_category_code, movie_title, content, director, actor, movie_grade, running_time, start_date, end_date, poster_file, video_url) "
					   + " values (seq_movie_no.nextval "
					   + "       , (select category_code from tbl_category where category=?) "
					   + "       , ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, movie.getFk_category_code());
			pstmt.setString(2, movie.getMovie_title());
			pstmt.setString(3, movie.getContent());
			pstmt.setString(4, movie.getDirector());
			pstmt.setString(5, movie.getActor());
			pstmt.setString(6, movie.getMovie_grade());
			pstmt.setString(7, movie.getRunning_time());
			pstmt.setString(8, movie.getStart_date());
			pstmt.setString(9, movie.getEnd_date());
			pstmt.setString(10, movie.getPoster_file());
			pstmt.setString(11, movie.getVideo_url());
			
			result = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		return result;
	}// end of public int registerMovie(MovieVO movie) throws SQLException {}-------------------
	

	
}
