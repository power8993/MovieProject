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

import movie.domain.CategoryVO_yeo;
import movie.domain.MovieVO_yeo;
import movie.domain.ShowTimeVO_yeo;

public class MovieDAO_imple_yeo implements MovieDAO_yeo {

	private DataSource ds; // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다.
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
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (pstmt != null) {
				pstmt.close();
				pstmt = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// end of private void close() {}-------------------
	
		
	
	// 모든 영화 정보 가져오기 (예매율 순)
	@Override
	public List<MovieVO_yeo> select_Movies() throws SQLException {
	    List<MovieVO_yeo> movieList = new ArrayList<>();

	    String sql = " WITH movie_payment AS ( " +
	                 "    SELECT st.FK_SEQ_MOVIE_NO, COUNT(p.IMP_UID) AS movie_payment_count " +
	                 "    FROM TBL_PAYMENT p " +
	                 "    JOIN TBL_SHOWTIME st ON p.FK_SEQ_SHOWTIME_NO = st.SEQ_SHOWTIME_NO " +
	                 "    GROUP BY st.FK_SEQ_MOVIE_NO " +
	                 " ), " +
	                 " total_payment AS ( " +
	                 "    SELECT COUNT(p.IMP_UID) AS total_payment_count " +
	                 "    FROM TBL_PAYMENT p " +
	                 " ) " +
	                 " SELECT m.SEQ_MOVIE_NO, m.FK_CATEGORY_CODE, c.CATEGORY AS category_name, " +
	                 "       case when length(movie_title) < 10 then movie_title else substr(movie_title, 0, 10) || '...' end as movie_title, m.CONTENT, m.DIRECTOR, m.ACTOR, m.MOVIE_GRADE, m.RUNNING_TIME, m.LIKE_COUNT, " +
	                 "       TO_CHAR(m.START_DATE, 'yyyy-MM-dd') AS start_date, TO_CHAR(m.END_DATE, 'yyyy-MM-dd') AS end_date, " +
	                 "       m.POSTER_FILE, m.VIDEO_URL, " +
	                 "       COALESCE(mp.movie_payment_count, 0) AS movie_payment_count, " +
	                 "       COALESCE(tp.total_payment_count, 1) AS total_payment_count, " +
	                 "       CASE WHEN COALESCE(tp.total_payment_count, 0) > 0 THEN " +
	                 "            ROUND((COALESCE(mp.movie_payment_count, 0) * 1.0 / COALESCE(tp.total_payment_count, 1)) * 100, 2) " +
	                 "       ELSE 0 END AS booking_rate " +
	                 " FROM TBL_MOVIE m " +
	                 " LEFT JOIN TBL_CATEGORY c ON m.FK_CATEGORY_CODE = c.CATEGORY_CODE " +
	                 " LEFT JOIN movie_payment mp ON m.SEQ_MOVIE_NO = mp.FK_SEQ_MOVIE_NO " +
	                 " CROSS JOIN total_payment tp " +
	                 " WHERE end_date >= SYSDATE " + // 상영 예정작 조건
	                 " ORDER BY booking_rate DESC";

	    try {
	        conn = ds.getConnection();
	        pstmt = conn.prepareStatement(sql);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            MovieVO_yeo movie = new MovieVO_yeo();
	            CategoryVO_yeo category = new CategoryVO_yeo();

	            // 영화 정보 매핑
	            movie.setSeq_movie_no(rs.getInt("SEQ_MOVIE_NO"));
	            movie.setFk_category_code(rs.getString("FK_CATEGORY_CODE"));
	            movie.setMovie_title(rs.getString("MOVIE_TITLE"));
	            movie.setContent(rs.getString("CONTENT"));
	            movie.setDirector(rs.getString("DIRECTOR"));
	            movie.setActor(rs.getString("ACTOR"));
	            movie.setMovie_grade(rs.getString("MOVIE_GRADE"));
	            movie.setRunning_time(rs.getString("RUNNING_TIME"));
	            movie.setLike_count(rs.getInt("LIKE_COUNT"));
	            movie.setStart_date(rs.getString("start_date"));
	            movie.setEnd_date(rs.getString("end_date"));
	            movie.setPoster_file(rs.getString("POSTER_FILE"));
	            movie.setVideo_url(rs.getString("VIDEO_URL"));

	            // 카테고리 매핑
	            category.setCategory(rs.getString("category_name"));
	            movie.setCg(category);

	            // 예매율 매핑
	            movie.setBookingRate(rs.getDouble("booking_rate"));

	            movieList.add(movie);
	        }
	    } finally {
	        close();
	    }

	    return movieList;
	}// end of 모든 영화 정보 가져오기 (예매율 순) ---------------------------------------------------------


 // 상영중 영화 가져오기 (예매율 순)
	@Override
	public List<MovieVO_yeo> select_run_Movies() throws SQLException {
	    List<MovieVO_yeo> movieList = new ArrayList<>();

	    String sql = "WITH movie_payment AS ( " +
	                 "    SELECT st.FK_SEQ_MOVIE_NO, COUNT(p.IMP_UID) AS movie_payment_count " +
	                 "    FROM TBL_PAYMENT p " +
	                 "    JOIN TBL_SHOWTIME st ON p.FK_SEQ_SHOWTIME_NO = st.SEQ_SHOWTIME_NO " +
	                 "    GROUP BY st.FK_SEQ_MOVIE_NO " +
	                 "), " +
	                 "total_payment AS ( " +
	                 "    SELECT COUNT(p.IMP_UID) AS total_payment_count " +
	                 "    FROM TBL_PAYMENT p " +
	                 ") " +
	                 "SELECT m.SEQ_MOVIE_NO, m.FK_CATEGORY_CODE, c.CATEGORY AS category_name, " +
	                 "       case when length(movie_title) < 10 then movie_title else substr(movie_title, 0, 10) || '...' end as movie_title, m.CONTENT, m.DIRECTOR, m.ACTOR, m.MOVIE_GRADE, m.RUNNING_TIME, m.LIKE_COUNT, " +
	                 "       TO_CHAR(m.START_DATE, 'yyyy-MM-dd') AS start_date, TO_CHAR(m.END_DATE, 'yyyy-MM-dd') AS end_date, " +
	                 "       m.POSTER_FILE, m.VIDEO_URL, " +
	                 "       COALESCE(mp.movie_payment_count, 0) AS movie_payment_count, " +
	                 "       COALESCE(tp.total_payment_count, 1) AS total_payment_count, " +
	                 "       CASE WHEN COALESCE(tp.total_payment_count, 0) > 0 THEN " +
	                 "            ROUND((COALESCE(mp.movie_payment_count, 0) * 1.0 / COALESCE(tp.total_payment_count, 1)) * 100, 2) " +
	                 "       ELSE 0 END AS booking_rate " +
	                 "FROM TBL_MOVIE m " +
	                 "LEFT JOIN TBL_CATEGORY c ON m.FK_CATEGORY_CODE = c.CATEGORY_CODE " +
	                 "LEFT JOIN movie_payment mp ON m.SEQ_MOVIE_NO = mp.FK_SEQ_MOVIE_NO " +
	                 "CROSS JOIN total_payment tp " +
	                 "WHERE SYSDATE BETWEEN m.START_DATE AND m.END_DATE " +
	                 "ORDER BY booking_rate DESC";

	    try {
	        conn = ds.getConnection();
	        pstmt = conn.prepareStatement(sql);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            MovieVO_yeo movie = new MovieVO_yeo();
	            CategoryVO_yeo category = new CategoryVO_yeo();

	            // 영화 정보 매핑
	            movie.setSeq_movie_no(rs.getInt("SEQ_MOVIE_NO"));
	            movie.setFk_category_code(rs.getString("FK_CATEGORY_CODE"));
	            movie.setMovie_title(rs.getString("MOVIE_TITLE"));
	            movie.setContent(rs.getString("CONTENT"));
	            movie.setDirector(rs.getString("DIRECTOR"));
	            movie.setActor(rs.getString("ACTOR"));
	            movie.setMovie_grade(rs.getString("MOVIE_GRADE"));
	            movie.setRunning_time(rs.getString("RUNNING_TIME"));
	            movie.setLike_count(rs.getInt("LIKE_COUNT"));
	            movie.setStart_date(rs.getString("start_date"));
	            movie.setEnd_date(rs.getString("end_date"));
	            movie.setPoster_file(rs.getString("POSTER_FILE"));
	            movie.setVideo_url(rs.getString("VIDEO_URL"));

	            // 카테고리 매핑
	            category.setCategory(rs.getString("category_name"));
	            movie.setCg(category);

	            // 예매율 매핑
	            movie.setBookingRate(rs.getDouble("booking_rate"));

	            movieList.add(movie);
	        }
	    } finally {
	        close();
	    }

	    return movieList;
	} // end of 상영중 영화 가져오기 (예매율 순) ----------------------------------------------

    	
	// 상영예정작 가져오기(개봉날짜 순)
	@Override
	public List<MovieVO_yeo> select_Upc_Movies() throws SQLException {
	    List<MovieVO_yeo> movieList = new ArrayList<>();

	    String sql = "WITH movie_payment AS ( " +
	                 "    SELECT st.FK_SEQ_MOVIE_NO, COUNT(p.IMP_UID) AS movie_payment_count " +
	                 "    FROM TBL_PAYMENT p " +
	                 "    JOIN TBL_SHOWTIME st ON p.FK_SEQ_SHOWTIME_NO = st.SEQ_SHOWTIME_NO " +
	                 "    GROUP BY st.FK_SEQ_MOVIE_NO " +
	                 "), " +
	                 "total_payment AS ( " +
	                 "    SELECT COUNT(p.IMP_UID) AS total_payment_count " +
	                 "    FROM TBL_PAYMENT p " +
	                 ") " +
	                 "SELECT m.SEQ_MOVIE_NO, m.FK_CATEGORY_CODE, c.CATEGORY AS category_name, " +
	                 "       m.MOVIE_TITLE, m.CONTENT, m.DIRECTOR, m.ACTOR, m.MOVIE_GRADE, m.RUNNING_TIME, m.LIKE_COUNT, " +
	                 "       TO_CHAR(m.START_DATE, 'yyyy-MM-dd') AS start_date, m.POSTER_FILE, m.VIDEO_URL, " +
	                 "       COALESCE(mp.movie_payment_count, 0) AS movie_payment_count, " +
	                 "       COALESCE(tp.total_payment_count, 1) AS total_payment_count, " +
	                 "       CASE WHEN COALESCE(tp.total_payment_count, 0) > 0 THEN " +
	                 "            ROUND((COALESCE(mp.movie_payment_count, 0) * 1.0 / COALESCE(tp.total_payment_count, 1)) * 100, 2) " +
	                 "       ELSE 0 END AS booking_rate " +
	                 "FROM TBL_MOVIE m " +
	                 "LEFT JOIN TBL_CATEGORY c ON m.FK_CATEGORY_CODE = c.CATEGORY_CODE " +
	                 "LEFT JOIN movie_payment mp ON m.SEQ_MOVIE_NO = mp.FK_SEQ_MOVIE_NO " +
	                 "CROSS JOIN total_payment tp " +
	                 "WHERE m.START_DATE > SYSDATE " + // 상영 예정작 조건
	                 "ORDER BY m.START_DATE ASC"; // 개봉 날짜 순 정렬

	    try (Connection conn = ds.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql);
	         ResultSet rs = pstmt.executeQuery()) {

	        while (rs.next()) {
	            MovieVO_yeo movie = new MovieVO_yeo();
	            CategoryVO_yeo category = new CategoryVO_yeo();

	            // 영화 정보 매핑
	            movie.setSeq_movie_no(rs.getInt("SEQ_MOVIE_NO"));
	            movie.setFk_category_code(rs.getString("FK_CATEGORY_CODE"));
	            movie.setMovie_title(rs.getString("MOVIE_TITLE"));
	            movie.setContent(rs.getString("CONTENT"));
	            movie.setDirector(rs.getString("DIRECTOR"));
	            movie.setActor(rs.getString("ACTOR"));
	            movie.setMovie_grade(rs.getString("MOVIE_GRADE"));
	            movie.setRunning_time(rs.getString("RUNNING_TIME"));
	            movie.setLike_count(rs.getInt("LIKE_COUNT"));
	            movie.setStart_date(rs.getString("start_date"));
	            movie.setPoster_file(rs.getString("POSTER_FILE"));
	            movie.setVideo_url(rs.getString("VIDEO_URL"));

	            // 카테고리 매핑
	            category.setCategory(rs.getString("category_name"));
	            movie.setCg(category);

	            // 예매율 매핑
	            movie.setBookingRate(rs.getDouble("booking_rate"));

	            movieList.add(movie);
	        }
	    } catch (SQLException e) {
	        System.err.println("SQL Error: " + e.getMessage());
	        throw e;
	    }

	    return movieList;
	}
 // end of 상영예정작 가져오기(개봉날짜 순) ----------------------------
	
	
	// 장르 종류 가져오기
	@Override
	public List<CategoryVO_yeo> selectcategory() throws SQLException {
	    List<CategoryVO_yeo> categoryList = new ArrayList<>();

	    String sql = " SELECT category_code, category " +
	                 " FROM tbl_category " +
	                 " ORDER BY category_code ASC "; // 카테고리 코드 순으로 정렬

	    try (Connection conn = ds.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql);
	         ResultSet rs = pstmt.executeQuery()) {

	        while (rs.next()) {
	            CategoryVO_yeo category = new CategoryVO_yeo();

	            // ResultSet 데이터를 CategoryVO_yeo 객체에 설정
	            category.setCategory_code(rs.getString("category_code")); // 장르 코드
	            category.setCategory(rs.getString("category"));           // 장르명

	            // 리스트에 추가
	            categoryList.add(category);
	        }
	    } catch (SQLException e) {
	        System.err.println("SQL 실행 중 오류 발생: " + e.getMessage());
	        throw e;
	    }

	    return categoryList; // 결과 리스트 반환
	} // end of 장르 종류 가져오기 ----------------------------------------	

	
	// 특정 장르의 영화 가져오기(전체)
	@Override
	public List<MovieVO_yeo> allgetMoviesByGenre(String genreCode) throws SQLException {
	    List<MovieVO_yeo> movieList = new ArrayList<>();

	    String sql = "WITH movie_payment AS ( " +
	                 "    SELECT st.FK_SEQ_MOVIE_NO, COUNT(p.IMP_UID) AS payment_count " +
	                 "    FROM TBL_PAYMENT p " +
	                 "    JOIN TBL_SHOWTIME st ON p.FK_SEQ_SHOWTIME_NO = st.SEQ_SHOWTIME_NO " +
	                 "    GROUP BY st.FK_SEQ_MOVIE_NO " +
	                 "), " +
	                 "total_payment AS ( " +
	                 "    SELECT COUNT(p.IMP_UID) AS total_payment_count " +
	                 "    FROM TBL_PAYMENT p " +
	                 ") " +
	                 "SELECT m.SEQ_MOVIE_NO, m.MOVIE_TITLE, m.RUNNING_TIME, " +
	                 "       TO_CHAR(m.START_DATE, 'yyyy-MM-dd') AS start_date, " +
	                 "       TO_CHAR(m.END_DATE, 'yyyy-MM-dd') AS end_date, m.POSTER_FILE, " +
	                 "       c.CATEGORY, m.MOVIE_GRADE, " +
	                 "       COALESCE(mp.payment_count, 0) AS movie_payment_count, " +
	                 "       COALESCE(tp.total_payment_count, 1) AS total_payment_count, " +
	                 "       CASE WHEN COALESCE(tp.total_payment_count, 0) > 0 THEN " +
	                 "            ROUND((COALESCE(mp.payment_count, 0) * 1.0 / COALESCE(tp.total_payment_count, 1)) * 100, 2) " +
	                 "       ELSE 0 END AS booking_rate " +
	                 "FROM TBL_MOVIE m " +
	                 "JOIN TBL_CATEGORY c ON m.FK_CATEGORY_CODE = c.CATEGORY_CODE " +
	                 "LEFT JOIN movie_payment mp ON m.SEQ_MOVIE_NO = mp.FK_SEQ_MOVIE_NO " +
	                 "CROSS JOIN total_payment tp " +
	                 "WHERE c.CATEGORY_CODE = ? " +
	                 "ORDER BY m.START_DATE ASC";

	    try (Connection conn = ds.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setString(1, genreCode);

	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                MovieVO_yeo movie = new MovieVO_yeo();
	                CategoryVO_yeo category = new CategoryVO_yeo();

	                // 영화 정보 매핑
	                movie.setSeq_movie_no(rs.getInt("SEQ_MOVIE_NO"));
	                movie.setMovie_title(rs.getString("MOVIE_TITLE"));
	                movie.setRunning_time(rs.getString("RUNNING_TIME"));
	                movie.setStart_date(rs.getString("start_date"));
	                movie.setEnd_date(rs.getString("end_date"));
	                movie.setPoster_file(rs.getString("POSTER_FILE"));
	                movie.setMovie_grade(rs.getString("MOVIE_GRADE")); // 영화 등급 추가

	                // 카테고리 매핑
	                category.setCategory(rs.getString("CATEGORY"));
	                movie.setCg(category);

	                // 예매율 매핑
	                movie.setBookingRate(rs.getDouble("booking_rate"));

	                movieList.add(movie);
	            }
	        }
	    }

	    return movieList;
	} // end of 특정 장르의 영화 가져오기(전체)

	
	
	

	// 특정 장르의 영화 가져오기(상영중)
	@Override
	public List<MovieVO_yeo> getMoviesByGenre(String genreCode) throws SQLException {
	    List<MovieVO_yeo> movieList = new ArrayList<>();

	    String sql = "WITH movie_payment AS ( " +
	                 "    SELECT st.FK_SEQ_MOVIE_NO, COUNT(p.IMP_UID) AS movie_payment_count " +
	                 "    FROM TBL_PAYMENT p " +
	                 "    JOIN TBL_SHOWTIME st ON p.FK_SEQ_SHOWTIME_NO = st.SEQ_SHOWTIME_NO " +
	                 "    GROUP BY st.FK_SEQ_MOVIE_NO " +
	                 "), " +
	                 "total_payment AS ( " +
	                 "    SELECT COUNT(p.IMP_UID) AS total_payment_count " +
	                 "    FROM TBL_PAYMENT p " +
	                 ") " +
	                 "SELECT m.SEQ_MOVIE_NO, m.MOVIE_TITLE, m.RUNNING_TIME, " +
	                 "       TO_CHAR(m.START_DATE, 'yyyy-MM-dd') AS start_date, " +
	                 "       TO_CHAR(m.END_DATE, 'yyyy-MM-dd') AS end_date, m.POSTER_FILE, " +
	                 "       c.CATEGORY, m.MOVIE_GRADE, " +
	                 "       COALESCE(mp.movie_payment_count, 0) AS movie_payment_count, " +
	                 "       COALESCE(tp.total_payment_count, 1) AS total_payment_count, " +
	                 "       CASE WHEN COALESCE(tp.total_payment_count, 0) > 0 THEN " +
	                 "            ROUND((COALESCE(mp.movie_payment_count, 0) * 1.0 / COALESCE(tp.total_payment_count, 1)) * 100, 2) " +
	                 "       ELSE 0 END AS booking_rate " +
	                 "FROM TBL_MOVIE m " +
	                 "JOIN TBL_CATEGORY c ON m.FK_CATEGORY_CODE = c.CATEGORY_CODE " +
	                 "LEFT JOIN movie_payment mp ON m.SEQ_MOVIE_NO = mp.FK_SEQ_MOVIE_NO " +
	                 "CROSS JOIN total_payment tp " +
	                 "WHERE c.CATEGORY_CODE = ? AND SYSDATE BETWEEN m.START_DATE AND m.END_DATE " +
	                 "ORDER BY booking_rate DESC";

	    try (Connection conn = ds.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setString(1, genreCode);

	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                MovieVO_yeo movie = new MovieVO_yeo();
	                CategoryVO_yeo category = new CategoryVO_yeo();

	                // 영화 정보 매핑
	                movie.setSeq_movie_no(rs.getInt("SEQ_MOVIE_NO"));
	                movie.setMovie_title(rs.getString("MOVIE_TITLE"));
	                movie.setRunning_time(rs.getString("RUNNING_TIME"));
	                movie.setStart_date(rs.getString("start_date"));
	                movie.setEnd_date(rs.getString("end_date"));
	                movie.setPoster_file(rs.getString("POSTER_FILE"));
	                movie.setMovie_grade(rs.getString("MOVIE_GRADE")); // 영화 등급 추가

	                // 카테고리 매핑
	                category.setCategory(rs.getString("CATEGORY"));
	                movie.setCg(category);

	                // 예매율 매핑
	                movie.setBookingRate(rs.getDouble("booking_rate"));

	                movieList.add(movie);
	            }
	        }
	    }

	    return movieList;
	} // end of 특정 장르의 영화 가져오기(상영중) ------------------------------------------------------


	// 특정 장르의 영화 가져오기(상영예정)
	@Override
	public List<MovieVO_yeo> ucgetMoviesByGenre(String genreCode) throws SQLException {
	    List<MovieVO_yeo> movieList = new ArrayList<>();

	    String sql = "WITH movie_payment AS ( " +
	                 "    SELECT st.FK_SEQ_MOVIE_NO, COUNT(p.IMP_UID) AS payment_count " +
	                 "    FROM TBL_PAYMENT p " +
	                 "    JOIN TBL_SHOWTIME st ON p.FK_SEQ_SHOWTIME_NO = st.SEQ_SHOWTIME_NO " +
	                 "    GROUP BY st.FK_SEQ_MOVIE_NO " +
	                 "), " +
	                 "total_payment AS ( " +
	                 "    SELECT COUNT(p.IMP_UID) AS total_payment_count " +
	                 "    FROM TBL_PAYMENT p " +
	                 ") " +
	                 "SELECT m.SEQ_MOVIE_NO, m.MOVIE_TITLE, m.RUNNING_TIME, " +
	                 "       TO_CHAR(m.START_DATE, 'yyyy-MM-dd') AS start_date, " +
	                 "       TO_CHAR(m.END_DATE, 'yyyy-MM-dd') AS end_date, m.POSTER_FILE, " +
	                 "       c.CATEGORY, m.MOVIE_GRADE, " +
	                 "       COALESCE(mp.payment_count, 0) AS movie_payment_count, " +
	                 "       COALESCE(tp.total_payment_count, 1) AS total_payment_count, " +
	                 "       CASE WHEN COALESCE(tp.total_payment_count, 0) > 0 THEN " +
	                 "            ROUND((COALESCE(mp.payment_count, 0) * 1.0 / COALESCE(tp.total_payment_count, 1)) * 100, 2) " +
	                 "       ELSE 0 END AS booking_rate " +
	                 "FROM TBL_MOVIE m " +
	                 "JOIN TBL_CATEGORY c ON m.FK_CATEGORY_CODE = c.CATEGORY_CODE " +
	                 "LEFT JOIN movie_payment mp ON m.SEQ_MOVIE_NO = mp.FK_SEQ_MOVIE_NO " +
	                 "CROSS JOIN total_payment tp " +
	                 "WHERE c.CATEGORY_CODE = ? AND SYSDATE < m.START_DATE " +
	                 "ORDER BY m.START_DATE ASC";

	    try (Connection conn = ds.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setString(1, genreCode);

	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                MovieVO_yeo movie = new MovieVO_yeo();
	                CategoryVO_yeo category = new CategoryVO_yeo();

	                // 영화 정보 매핑
	                movie.setSeq_movie_no(rs.getInt("SEQ_MOVIE_NO"));
	                movie.setMovie_title(rs.getString("MOVIE_TITLE"));
	                movie.setRunning_time(rs.getString("RUNNING_TIME"));
	                movie.setStart_date(rs.getString("start_date"));
	                movie.setEnd_date(rs.getString("end_date"));
	                movie.setPoster_file(rs.getString("POSTER_FILE"));
	                movie.setMovie_grade(rs.getString("MOVIE_GRADE")); // 영화 등급 추가

	                // 카테고리 매핑
	                category.setCategory(rs.getString("CATEGORY"));
	                movie.setCg(category);

	                // 예매율 매핑
	                movie.setBookingRate(rs.getDouble("booking_rate"));

	                movieList.add(movie);
	            }
	        }
	    }

	    return movieList;
	} //end of 특정 장르의 영화 가져오기(상영예정) ----------------------------------------------------

	
	// 선택한 날짜 영화 시간표 가져오기
	@Override
	public List<MovieVO_yeo> selectMovieTimeByDate(String selectedDate) throws SQLException {
	    List<MovieVO_yeo> movieTimeList = new ArrayList<>();

	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        conn = ds.getConnection(); // DB 연결 초기화

	        // SQL 쿼리 작성
	        String sql = "SELECT m.seq_movie_no, " +  // 쉼표 추가
	                     "m.movie_title, " +
	                     "TO_CHAR(m.start_date, 'yyyy-MM-dd') AS start_date, " + // 날짜 형식 수정
	                     "m.running_time, " +
	                     "TO_CHAR(s.start_time, 'HH24:mi') AS start_time, " + // 시간 형식 수정
	                     "s.unused_seat, " +
	                     "sc.screen_no " +
	                     "FROM tbl_showtime s " +
	                     "JOIN tbl_movie m ON s.fk_seq_movie_no = m.seq_movie_no " +
	                     "JOIN tbl_screen sc ON s.fk_screen_no = sc.screen_no " +
	                     "WHERE TO_CHAR(s.start_time, 'yyyy-MM-dd') = ? " + // WHERE 절 날짜 형식 수정
	                     "ORDER BY s.start_time ASC";

	        pstmt = conn.prepareStatement(sql); // PreparedStatement 생성
	        pstmt.setString(1, selectedDate); // SQL 파라미터 설정
	        rs = pstmt.executeQuery(); // SQL 실행

	        // 결과를 리스트에 추가
	        while (rs.next()) {
	            MovieVO_yeo movie = new MovieVO_yeo();     // 영화 객체 생성
	            ShowTimeVO_yeo svo = new ShowTimeVO_yeo(); // 상영 시간 객체 생성

	            // 영화 정보 설정
	            movie.setSeq_movie_no(rs.getInt("seq_movie_no"));     // 영화 번호  
	            movie.setMovie_title(rs.getString("movie_title"));    // 영화 제목
	            movie.setStart_date(rs.getString("start_date"));      // 개봉 날짜
	            movie.setRunning_time(rs.getString("running_time"));  // 러닝 타임

	            // 상영 정보 설정
	            svo.setStart_time(rs.getString("start_time"));        // 상영 시작 시간
	            svo.setUnused_seat(rs.getInt("unused_seat"));         // 남은 좌석 수
	            svo.setFk_screenNO(rs.getInt("screen_no"));           // 상영관 번호

	            movie.setSvo(svo);
	            movieTimeList.add(movie);
	        }
	    } catch (SQLException e) {
	        System.err.println("SQL 실행 중 오류 발생: " + e.getMessage());
	        throw e; // 예외 재발생
	    } finally {
	        if (rs != null) rs.close();
	        if (pstmt != null) pstmt.close();
	        if (conn != null) conn.close(); // 자원 해제
	    }

	    return movieTimeList; // 결과 반환
	} // end of 선택한 날짜 영화 시간표 가져오기 ---------------------------------------------------------


	// 선택한 날짜 영화 시간표 가져오기 (1관수 순)	
	@Override
	public List<MovieVO_yeo> selectMovieTimeByDateNO1(String selectedDate) throws SQLException {
	  
		List<MovieVO_yeo> movieTimeList = new ArrayList<>();

	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        conn = ds.getConnection(); // DB 연결 초기화

	        // SQL 쿼리 작성
	        String sql = " SELECT m.seq_movie_no, " +
	                     " m.movie_title, " +
	                    // " TO_CHAR(m.start_date, 'yyyy-MM-dd') AS start_date, " +
	                     " m.running_time, " +
	                     " TO_CHAR(s.start_time, 'HH24:mi') AS start_time, " +
	                     " s.unused_seat, " +
	                     " to_char(start_time, 'yyyy-MM-dd') as start_date," + 
	                     " fk_screen_no " +
	                     " FROM tbl_showtime s " +
	                     " JOIN tbl_movie m ON s.fk_seq_movie_no = m.seq_movie_no " +
	                     
	                     " WHERE TO_CHAR(s.start_time, 'yyyy-MM-dd') = ? AND s.fk_screen_no = 1 " +
	                     " ORDER BY s.start_time ASC ";

	        pstmt = conn.prepareStatement(sql); // PreparedStatement 생성
	        pstmt.setString(1, selectedDate); // SQL 파라미터 설정
	        rs = pstmt.executeQuery(); // SQL 실행

	        // 결과를 리스트에 추가
	        while (rs.next()) {
	            MovieVO_yeo movie = new MovieVO_yeo();     // 영화 객체 생성
	            ShowTimeVO_yeo svo = new ShowTimeVO_yeo(); // 상영 시간 객체 생성

	            // 영화 정보 설정
	            movie.setSeq_movie_no(rs.getInt("seq_movie_no"));    // 영화 번호
	            movie.setMovie_title(rs.getString("movie_title"));   // 영화 제목
	            movie.setStart_date(rs.getString("start_date"));     // 개봉 날짜
	            movie.setRunning_time(rs.getString("running_time")); // 러닝 타임

	            // 상영 정보 설정
	            svo.setStart_time(rs.getString("start_time"));       // 상영 시작 시간
	            svo.setUnused_seat(rs.getInt("unused_seat"));        // 남은 좌석 수
	            svo.setFk_screenNO(rs.getInt("fk_screen_no"));       // 상영관 번호

	            movie.setSvo(svo); 
	            movieTimeList.add(movie); 
	        }
	    } catch (SQLException e) {
	        System.err.println("SQL 실행 중 오류 발생: " + e.getMessage());
	        throw e; // 예외 재발생
	    } finally {
	       close();
	    }

	    return movieTimeList; // 결과 반환
	} // end of 선택한 날짜 영화 시간표 가져오기 (1관수 순) -----------------------------------------------

	
	// 선택한 날짜 영화 시간표 가져오기 (2관수 순)		
	@Override
	public List<MovieVO_yeo> selectMovieTimeByDateNO2(String selectedDate) throws SQLException {
		
		List<MovieVO_yeo> movieTimeList = new ArrayList<>();

		    Connection conn = null;
		    PreparedStatement pstmt = null;
		    ResultSet rs = null;

		    try {
		        conn = ds.getConnection(); // DB 연결 초기화

		        // SQL 쿼리 작성
		        String sql = " SELECT m.seq_movie_no, " +
		                     " m.movie_title, " +
		                    // " TO_CHAR(m.start_date, 'yyyy-MM-dd') AS start_date, " +
		                     " m.running_time, " +
		                     " TO_CHAR(s.start_time, 'HH24:mi') AS start_time, " +
		                     " s.unused_seat, " +
		                     " to_char(start_time, 'yyyy-MM-dd') as start_date," + 
		                     " fk_screen_no " +
		                     " FROM tbl_showtime s " +
		                     " JOIN tbl_movie m ON s.fk_seq_movie_no = m.seq_movie_no " +
		                     
		                     " WHERE TO_CHAR(s.start_time, 'yyyy-MM-dd') = ? AND s.fk_screen_no = 2 " +
		                     " ORDER BY s.start_time ASC ";

		        pstmt = conn.prepareStatement(sql); // PreparedStatement 생성
		        pstmt.setString(1, selectedDate); // SQL 파라미터 설정
		        rs = pstmt.executeQuery(); // SQL 실행

		        // 결과를 리스트에 추가
		        while (rs.next()) {
		            MovieVO_yeo movie = new MovieVO_yeo();     // 영화 객체 생성
		            ShowTimeVO_yeo svo = new ShowTimeVO_yeo(); // 상영 시간 객체 생성

		            // 영화 정보 설정
		            movie.setSeq_movie_no(rs.getInt("seq_movie_no"));    // 영화 번호
		            movie.setMovie_title(rs.getString("movie_title"));   // 영화 제목
		            movie.setStart_date(rs.getString("start_date"));     // 개봉 날짜
		            movie.setRunning_time(rs.getString("running_time")); // 러닝 타임

		            // 상영 정보 설정
		            svo.setStart_time(rs.getString("start_time"));       // 상영 시작 시간
		            svo.setUnused_seat(rs.getInt("unused_seat"));        // 남은 좌석 수
		            svo.setFk_screenNO(rs.getInt("fk_screen_no"));       // 상영관 번호

		            movie.setSvo(svo); 
		            movieTimeList.add(movie); 
		        }
		    } catch (SQLException e) {
		        System.err.println("SQL 실행 중 오류 발생: " + e.getMessage());
		        throw e; // 예외 재발생
		    } finally {
		       close();
		    }

		    return movieTimeList; // 결과 반환
	} // end of 선택한 날짜 영화 시간표 가져오기 (2관수 순) --------------------------------------------

	
	// 영화 검색해서 가져오기   
	@Override
	public List<MovieVO_yeo> searchMovies(String trim) throws SQLException {

	    List<MovieVO_yeo> movieList = new ArrayList<>();

	    try {
	        conn = ds.getConnection(); // DB 연결
	        String sql = "WITH movie_payment AS ( " +
	                     "    SELECT st.FK_SEQ_MOVIE_NO, COUNT(p.IMP_UID) AS payment_count " +
	                     "    FROM TBL_PAYMENT p " +
	                     "    JOIN TBL_SHOWTIME st ON p.FK_SEQ_SHOWTIME_NO = st.SEQ_SHOWTIME_NO " +
	                     "    GROUP BY st.FK_SEQ_MOVIE_NO " +
	                     "), " +
	                     "total_payment AS ( " +
	                     "    SELECT COUNT(p.IMP_UID) AS total_payment_count " +
	                     "    FROM TBL_PAYMENT p " +
	                     ") " +
	                     "SELECT " +
	                     "    m.seq_movie_no, " +
	                     "    m.fk_category_code, " +
	                     "    CASE WHEN LENGTH(movie_title) < 10 THEN movie_title ELSE SUBSTR(movie_title, 0, 10) || '...' END AS movie_title, " +
	                     "    m.content, " +
	                     "    m.director, " +
	                     "    m.actor, " +
	                     "    m.movie_grade, " +
	                     "    m.running_time, " +
	                     "    m.like_count, " +
	                     "    TO_CHAR(m.start_date, 'yyyy-MM-dd') AS start_date, " +
	                     "    TO_CHAR(m.end_date, 'yyyy-MM-dd') AS end_date, " +
	                     "    m.poster_file, " +
	                     "    m.video_url, " +
	                     "    m.register_date, " +
	                     "    c.category_code, " +
	                     "    c.category, " +
	                     "    COALESCE(mp.payment_count, 0) AS movie_payment_count, " +
	                     "    COALESCE(tp.total_payment_count, 1) AS total_payment_count, " +
	                     "    CASE WHEN COALESCE(tp.total_payment_count, 0) > 0 THEN " +
	                     "         ROUND((COALESCE(mp.payment_count, 0) * 1.0 / COALESCE(tp.total_payment_count, 1)) * 100, 2) " +
	                     "    ELSE 0 END AS booking_rate " +
	                     "FROM " +
	                     "    TBL_MOVIE m " +
	                     "JOIN " +
	                     "    TBL_CATEGORY c ON m.fk_category_code = c.category_code " +
	                     "LEFT JOIN " +
	                     "    movie_payment mp ON m.seq_movie_no = mp.FK_SEQ_MOVIE_NO " +
	                     "CROSS JOIN " +
	                     "    total_payment tp " +
	                     "WHERE " +
	                     "    LOWER(m.movie_title) LIKE '%' || ? || '%' OR LOWER(c.category) LIKE '%' || ? || '%' " +
	                     "ORDER BY " +
	                     "    booking_rate DESC";

	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, "%" + trim.trim().toLowerCase() + "%"); // 입력값에서 공백 제거 및 소문자 변환
	        pstmt.setString(2, "%" + trim.trim().toLowerCase() + "%");
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            MovieVO_yeo movie = new MovieVO_yeo();
	            CategoryVO_yeo category = new CategoryVO_yeo();

	            // 영화 정보 설정
	            movie.setSeq_movie_no(rs.getInt("seq_movie_no"));
	            movie.setFk_category_code(rs.getString("fk_category_code"));
	            movie.setMovie_title(rs.getString("movie_title"));
	            movie.setContent(rs.getString("content"));
	            movie.setDirector(rs.getString("director"));
	            movie.setActor(rs.getString("actor"));
	            movie.setMovie_grade(rs.getString("movie_grade"));
	            movie.setRunning_time(rs.getString("running_time"));
	            movie.setLike_count(rs.getInt("like_count"));
	            movie.setStart_date(rs.getString("start_date"));
	            movie.setEnd_date(rs.getString("end_date"));
	            movie.setPoster_file(rs.getString("poster_file"));
	            movie.setVideo_url(rs.getString("video_url"));
	            movie.setRegister_date(rs.getString("register_date"));

	            // 카테고리 정보 설정
	            category.setCategory_code(rs.getString("category_code"));
	            category.setCategory(rs.getString("category"));
	            movie.setCg(category);

	            // 예매율 설정
	            movie.setBookingRate(rs.getDouble("booking_rate"));

	            movieList.add(movie);
	        }
	    } catch (SQLException e) {
	        System.err.println("SQL 실행 중 오류 발생: " + e.getMessage());
	        throw e; // 예외 재발생
	    } finally {
	        if (rs != null) rs.close();
	        if (pstmt != null) pstmt.close();
	        if (conn != null) conn.close(); // 자원 해제
	    }
	    return movieList;
	} // end of 영화 검색해서 가져오기

		
			
		
	
  
}
