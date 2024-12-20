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

public class MovieDAO_imple implements MovieDAO {
	
	private DataSource ds;	// DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다.
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	// 생성자
	public MovieDAO_imple() {
		
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


	
	// 영화를 등록 페이지에서 [등록된 영화 조회]를 통해 검색한 영화들을 보여주는 페이지(select)
	@Override
	public List<MovieVO> selectMovieRegister(String movie_title) throws SQLException {
		
		List<MovieVO> movieList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			
			String sql = " select poster_file, movie_title, c.category, movie_grade, to_char(register_date, 'yyyy-mm-dd') as register_date "
					   + " from tbl_movie m join tbl_category c "
					   + " on(m.fk_category_code = c.category_code) "
					   + " where movie_title like ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			String search_title = "%" + movie_title + "%"; 
			pstmt.setString(1, search_title);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				MovieVO mvvo = new MovieVO();
				
				mvvo.setPoster_file(rs.getString("poster_file"));
				mvvo.setMovie_title(rs.getString("movie_title"));
				mvvo.setFk_category_code(rs.getString("category"));
				mvvo.setMovie_grade(rs.getString("movie_grade"));
				mvvo.setRegister_date(rs.getString("register_date"));
				
				movieList.add(mvvo);
			}// while(rs.next()) {}------------------------------------------
			
		}
		finally {
			close();
		}
		
		return movieList;
	}// end of public List<MovieVO> selectMovieRegister(String movie_title) throws SQLException {}--------------------


	
	// 페이징 처리를 안한 모든 영화 목록 보여주기(select)
	@Override
	public List<MovieVO> selectMovieList(Map<String, String> paraMap) throws SQLException {
		
		List<MovieVO> movieList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			
			String sql = " select seq_movie_no "
					   + "      , to_char(register_date, 'yyyy-mm-dd') as register_date "
					   + "      , c.category "
					   + "      , poster_file "
					   + "      , movie_title "
					   + "      , movie_grade "
					   + "      , to_char(start_date, 'yyyy-mm-dd') as start_date "
					   + " from tbl_movie m join tbl_category c "
					   + " on(m.fk_category_code = c.category_code) "
					   + " where movie_title like ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			String search_title = "%" + paraMap.get("movie_title") + "%"; 
			pstmt.setString(1, search_title);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				MovieVO mvvo = new MovieVO();
				
				mvvo.setSeq_movie_no(rs.getInt("seq_movie_no"));
				mvvo.setRegister_date(rs.getString("register_date"));
				mvvo.setFk_category_code(rs.getString("category"));
				mvvo.setPoster_file(rs.getString("poster_file"));
				mvvo.setMovie_title(rs.getString("movie_title"));		
				mvvo.setMovie_grade(rs.getString("movie_grade"));
				mvvo.setStart_date(rs.getString("start_date"));
				
				movieList.add(mvvo);
				
			}// while(rs.next()) {}------------------------------------------
					
		} finally {
			close();
		}
		return movieList;
	}// end of public List<MovieVO> selectMovieList() throws SQLException {}-------------------


	
	// 등록된 영화를 보여주는 페이지에서 영화 클릭 시, 해당 영화 상세 내용 보여주기(select)
	@Override
	public MovieVO selectMovieDetail(String seq) throws SQLException {
		
		MovieVO mvvo = null;	
		
		try {
			conn = ds.getConnection();
			
			String sql = " select seq_movie_no "
					   + "      , c.category "
					   + "      , movie_title "
					   + "      , content "
					   + "      , director "
					   + "      , actor "
					   + "      , movie_grade "
					   + "      , running_time "
					   + "      , to_char(end_date, 'yyyy-mm-dd') as start_date "
					   + "      , to_char(end_date, 'yyyy-mm-dd') as end_date "
					   + "      , poster_file"
					   + "      , video_url "
					   + "      , to_char(register_date, 'yyyy-mm-dd') as register_date "
					   + " from tbl_movie m join tbl_category c "
					   + " on(m.fk_category_code = c.category_code) "
					   + " where seq_movie_no = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, seq);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				mvvo = new MovieVO();
				mvvo.setSeq_movie_no(rs.getInt("seq_movie_no"));
				mvvo.setFk_category_code(rs.getString("category"));
				mvvo.setMovie_title(rs.getString("movie_title"));
				mvvo.setContent(rs.getString("content").replace("\r\n","<br>"));
				mvvo.setDirector(rs.getString("director"));
				mvvo.setActor(rs.getString("actor"));
				mvvo.setMovie_grade(rs.getString("movie_grade"));
				mvvo.setRunning_time(rs.getString("running_time"));
				mvvo.setStart_date(rs.getString("start_date"));
				mvvo.setEnd_date(rs.getString("end_date"));
				mvvo.setPoster_file(rs.getString("poster_file"));
				mvvo.setVideo_url(rs.getString("video_url"));
				mvvo.setRegister_date(rs.getString("register_date"));
			}
			
		} finally {
			close();
		}
		
		return mvvo;
	}// end of public MovieVO selectMovieDetail(String seq) throws SQLException {}-------------------
	

	
}
