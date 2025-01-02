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
	
		
	
	// 모든 영화 정보 가져오기 (예매율 순으로 바꾸기)
	@Override
	public List<MovieVO_yeo> select_Movies() throws SQLException {
	    List<MovieVO_yeo> movieList = new ArrayList<>();
	    
	   String sql = " with totalaudience as ( " +
	                 "    select sum(total_viewer) as overall_audience " +
	                 "    from tbl_showtime " +
	                 " ) " +
	                 " select " +
	                 "    m.seq_movie_no, m.fk_category_code, c.category as category_name, m.movie_title, " +
	                 "    m.content, m.director, m.actor, m.movie_grade, m.running_time, m.like_count, " +
	                 "    to_char(m.start_date, 'yyyy-mm-dd') as start_date, " +
	                 "    to_char(m.end_date, 'yyyy-mm-dd') as end_date, m.poster_file, m.video_url, " +
	                 "    sum(nvl(s.total_viewer, 0)) as movie_audience, " +
	                 "    (select overall_audience from totalaudience) as total_audience, " +
	                 "    case when (select overall_audience from totalaudience) > 0 then " +
	                 "        round((sum(nvl(s.total_viewer, 0)) / (select overall_audience from totalaudience)) * 100, 2) " +
	                 "    else 0 end as booking_rate " +
	                 " from tbl_movie m " +
	                 " join tbl_category c on m.fk_category_code = c.category_code " +
	                 " left join tbl_showtime s on m.seq_movie_no = s.fk_seq_movie_no " +
	                 " group by " +
	                 "    m.seq_movie_no, m.fk_category_code, c.category, m.movie_title, " +
	                 "    m.content, m.director, m.actor, m.movie_grade, m.running_time, m.like_count, " +
	                 "    m.start_date, m.end_date, m.poster_file, m.video_url " +
	                 " order by booking_rate desc ";

	    try (Connection conn = ds.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql);
	         ResultSet rs = pstmt.executeQuery()) {

	        while (rs.next()) {
	            MovieVO_yeo movie = new MovieVO_yeo();
	            CategoryVO_yeo category = new CategoryVO_yeo();

	            // Setting movie details
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

	            // Setting category
	            category.setCategory(rs.getString("category_name"));
	            movie.setCg(category);

	            // Setting booking rate
	            movie.setBookingRate(rs.getDouble("booking_rate"));

	            // Adding movie to the list
	            movieList.add(movie);
	        }
	    } catch (SQLException e) {
	        System.err.println("SQL 실행 중 오류 발생: " + e.getMessage());
	        throw e; // 예외 재발생
	    } finally {
	        close(); // 자원 해제
	    } 

	    return movieList;
	}


	// 상영중 영화 가져오기(예매율 순)
	@Override
	public List<MovieVO_yeo> select_run_Movies() throws SQLException {
	    List<MovieVO_yeo> movieList = new ArrayList<>();
	    
	    String sql = " with totalaudience as ( " +
	                 "    select sum(total_viewer) as overall_audience " +
	                 "    from tbl_showtime " +
	                 " ) " +
	                 " select " +
	                 "    m.seq_movie_no, m.fk_category_code, c.category as category_name, m.movie_title, " +
	                 "    m.content, m.director, m.actor, m.movie_grade, m.running_time, m.like_count, " +
	                 "    to_char(m.start_date, 'yyyy-mm-dd') as start_date, " +
	                 "    to_char(m.end_date, 'yyyy-mm-dd') as end_date, m.poster_file, m.video_url, " +
	                 "    sum(nvl(s.total_viewer, 0)) as movie_audience, " +
	                 "    (select overall_audience from totalaudience) as total_audience, " +
	                 "    case when (select overall_audience from totalaudience) > 0 then " +
	                 "        round((sum(nvl(s.total_viewer, 0)) / (select overall_audience from totalaudience)) * 100, 2) " +
	                 "    else 0 end as booking_rate " +
	                 " from tbl_movie m " +
	                 " join tbl_category c on m.fk_category_code = c.category_code " +
	                 " left join tbl_showtime s on m.seq_movie_no = s.fk_seq_movie_no " +
	                 " where sysdate between m.start_date and m.end_date + 1 " +
	                 " group by " +
	                 "    m.seq_movie_no, m.fk_category_code, c.category, m.movie_title, " +
	                 "    m.content, m.director, m.actor, m.movie_grade, m.running_time, m.like_count, " +
	                 "    m.start_date, m.end_date, m.poster_file, m.video_url " +
	                 " order by booking_rate desc ";

	    try (Connection conn = ds.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql);
	         ResultSet rs = pstmt.executeQuery()) {

	        while (rs.next()) {
	            MovieVO_yeo movie = new MovieVO_yeo();
	            CategoryVO_yeo category = new CategoryVO_yeo();

	            // Setting movie details
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

	            // Setting category
	            category.setCategory(rs.getString("category_name"));
	            movie.setCg(category);

	            // Setting booking rate
	            movie.setBookingRate(rs.getDouble("booking_rate"));

	            // Adding movie to the list
	            movieList.add(movie);
	        }
	    } catch (SQLException e) {
	        System.err.println("SQL Error: " + e.getMessage());
	        throw e;
	    }

	    return movieList;
	}// end of 상영중 영화 가져오기(예매율 순) ------------------------------
	
	
	// 상영예정작 가져오기(개봉날짜 순)
	@Override
	public List<MovieVO_yeo> select_Upc_Movies() throws SQLException {
	    List<MovieVO_yeo> movieList = new ArrayList<>();

	    String sql = " SELECT " +
	                 "    m.seq_movie_no, m.fk_category_code, c.category AS category_name, m.movie_title, " +
	                 "    m.content, m.director, m.actor, m.movie_grade, m.running_time, m.like_count, " +
	                 "    TO_CHAR(m.start_date, 'yyyy-MM-dd') AS start_date, " +
	                 "    m.poster_file, m.video_url " +
	                 " FROM tbl_movie m " +
	                 " JOIN tbl_category c ON m.fk_category_code = c.category_code " +
	                 " WHERE m.start_date > SYSDATE " + // 상영 예정작 조건
	                 " ORDER BY m.start_date ASC"; // 개봉 날짜 순 정렬

	    try (Connection conn = ds.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql);
	         ResultSet rs = pstmt.executeQuery()) {

	        while (rs.next()) {
	            MovieVO_yeo movie = new MovieVO_yeo();
	            CategoryVO_yeo category = new CategoryVO_yeo();

	            // Setting movie details
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
	            movie.setPoster_file(rs.getString("poster_file"));
	            movie.setVideo_url(rs.getString("video_url"));

	            // Setting category
	            category.setCategory(rs.getString("category_name"));
	            movie.setCg(category);

	            // Adding movie to the list
	            movieList.add(movie);
	        }
	    } catch (SQLException e) {
	        System.err.println("SQL Error: " + e.getMessage());
	        throw e;
	    }

	    return movieList;
	} // end of 상영예정작 가져오기(개봉날짜 순) ----------------------------
	
	
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

	    String sql = " SELECT m.seq_movie_no, m.movie_title, m.running_time, m.start_date, m.end_date, m.poster_file, c.category " +
	                 " FROM tbl_movie m " +
	                 " JOIN tbl_category c ON m.fk_category_code = c.category_code " +
	                 " WHERE c.category_code = ? " +
	                 " ORDER BY m.start_date ASC ";

	    try (Connection conn = ds.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setString(1, genreCode);

	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                MovieVO_yeo movie = new MovieVO_yeo();

	                movie.setSeq_movie_no(rs.getInt("seq_movie_no"));
	                movie.setMovie_title(rs.getString("movie_title"));
	                movie.setRunning_time(rs.getString("running_time"));
	                movie.setStart_date(rs.getString("start_date"));
	                movie.setEnd_date(rs.getString("end_date"));
	                movie.setPoster_file(rs.getString("poster_file"));

	                CategoryVO_yeo category = new CategoryVO_yeo();
	                category.setCategory(rs.getString("category"));
	                movie.setCg(category);

	                movieList.add(movie);
	            }
	        }
	    }

	    return movieList;
	}

	// 특정 장르의 영화 가져오기(상영중)
	@Override
	public List<MovieVO_yeo> getMoviesByGenre(String genreCode) throws SQLException {
	    List<MovieVO_yeo> movieList = new ArrayList<>();

	    String sql = " SELECT m.seq_movie_no, m.movie_title, m.running_time, m.start_date, m.end_date, m.poster_file, c.category " +
	                 " FROM tbl_movie m " +
	                 " JOIN tbl_category c ON m.fk_category_code = c.category_code " +
	                 " WHERE c.category_code = ? AND SYSDATE BETWEEN m.start_date AND m.end_date " +
	                 " ORDER BY m.start_date ASC ";

	    try (Connection conn = ds.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setString(1, genreCode);

	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                MovieVO_yeo movie = new MovieVO_yeo();

	                movie.setSeq_movie_no(rs.getInt("seq_movie_no"));
	                movie.setMovie_title(rs.getString("movie_title"));
	                movie.setRunning_time(rs.getString("running_time"));
	                movie.setStart_date(rs.getString("start_date"));
	                movie.setEnd_date(rs.getString("end_date"));
	                movie.setPoster_file(rs.getString("poster_file"));

	                CategoryVO_yeo category = new CategoryVO_yeo();
	                category.setCategory(rs.getString("category"));
	                movie.setCg(category);

	                movieList.add(movie);
	            }
	        }
	    }

	    return movieList;
	}

	// 특정 장르의 영화 가져오기(상영예정)
	@Override
	public List<MovieVO_yeo> ucgetMoviesByGenre(String genreCode) throws SQLException {
	    List<MovieVO_yeo> movieList = new ArrayList<>();

	    String sql = " SELECT m.seq_movie_no, m.movie_title, m.running_time, m.start_date, m.end_date, m.poster_file, c.category " +
	                 " FROM tbl_movie m " +
	                 " JOIN tbl_category c ON m.fk_category_code = c.category_code " +
	                 " WHERE c.category_code = ? AND SYSDATE < m.start_date " +
	                 " ORDER BY m.start_date ASC ";

	    try (Connection conn = ds.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setString(1, genreCode);

	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                MovieVO_yeo movie = new MovieVO_yeo();

	                movie.setSeq_movie_no(rs.getInt("seq_movie_no"));
	                movie.setMovie_title(rs.getString("movie_title"));
	                movie.setRunning_time(rs.getString("running_time"));
	                movie.setStart_date(rs.getString("start_date"));
	                movie.setEnd_date(rs.getString("end_date"));
	                movie.setPoster_file(rs.getString("poster_file"));

	                CategoryVO_yeo category = new CategoryVO_yeo();
	                category.setCategory(rs.getString("category"));
	                movie.setCg(category);

	                movieList.add(movie);
	            }
	        }
	    }

	    return movieList;
	}




	// 선택한 날짜 영화 시간표 가져오기
	@Override
	public List<MovieVO_yeo> selectMovieTimeByDate(String selectedDate) throws SQLException {
	    List<MovieVO_yeo> movieTimeList = new ArrayList<>();

	    try {
	        conn = ds.getConnection(); // DB 연결 초기화

	        // SQL 쿼리 작성
	        String sql = " SELECT m.movie_title, " +
	                     "       TO_CHAR(m.start_date, 'yyyy-mm-dd') AS start_date, " +
	                     "       m.running_time, " +
	                     "       TO_CHAR(s.start_time, 'yyyy-mm-dd hh24:mi') AS start_time, " +
	                     "       s.unused_seat, " +
	                     "       sc.screen_no " +
	                     " FROM tbl_showtime s " +
	                     " JOIN tbl_movie m ON s.fk_seq_movie_no = m.seq_movie_no " +
	                     " JOIN tbl_screen sc ON s.fk_screen_no = sc.screen_no " +
	                     " WHERE TO_CHAR(s.start_time, 'yyyy-mm-dd') = ? " +
	                     " ORDER BY s.start_time ASC ";

	        pstmt = conn.prepareStatement(sql); // PreparedStatement 생성
	        pstmt.setString(1, selectedDate); // SQL 파라미터 설정

	        rs = pstmt.executeQuery(); // SQL 실행

	        // 결과를 리스트에 추가
	        while (rs.next()) {
	            MovieVO_yeo movie = new MovieVO_yeo();     // 영화 객체 생성
	            ShowTimeVO_yeo svo = new ShowTimeVO_yeo(); // 상영 시간 객체 생성

	            // 영화 정보 설정
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
	        close(); // 자원 해제
	    }

	    return movieTimeList; // 결과 반환
	}

	// 선택한 날짜 영화 시간표 가져오기 (1관수 순)
	@Override
	public List<MovieVO_yeo> selectMovieTimeByDateNO1(String selectedDate) throws SQLException {
		List<MovieVO_yeo> movieTimeList = new ArrayList<>();

	    try {
	        conn = ds.getConnection(); // DB 연결 초기화

	     // SQL 쿼리 작성
	        String sql = " SELECT m.movie_title, "
	        		+ "       TO_CHAR(m.start_date, 'yyyy-MM-dd') AS start_date, "
	        		+ "       m.running_time, "
	        		+ "       TO_CHAR(s.start_time, 'yyyy-MM-dd HH24:mi') AS start_time, "
	        		+ "       s.unused_seat, "
	        		+ "       sc.screen_no "
	        		+ " FROM tbl_showtime s "
	        		+ " JOIN tbl_movie m ON s.fk_seq_movie_no = m.seq_movie_no "
	        		+ " JOIN tbl_screen sc ON s.fk_screen_no = sc.screen_no "
	        		+ " WHERE TO_CHAR(s.start_time, 'yyyy-MM-dd') = ?  "
	        		+ "  AND s.fk_screen_no = 1  "
	        		+ " ORDER BY s.start_time ASC ";

	        pstmt = conn.prepareStatement(sql); // PreparedStatement 생성
	        pstmt.setString(1, selectedDate); // SQL 파라미터 설정

	        rs = pstmt.executeQuery(); // SQL 실행

	        // 결과를 리스트에 추가
	        while (rs.next()) {
	            MovieVO_yeo movie = new MovieVO_yeo();     // 영화 객체 생성
	            ShowTimeVO_yeo svo = new ShowTimeVO_yeo(); // 상영 시간 객체 생성

	            // 영화 정보 설정
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
	        close(); // 자원 해제
	    }

	    return movieTimeList; // 결과 반환
	}
	
	
	// 선택한 날짜 영화 시간표 가져오기 (2관수 순)
		@Override
		public List<MovieVO_yeo> selectMovieTimeByDateNO2(String selectedDate) throws SQLException {
			List<MovieVO_yeo> movieTimeList = new ArrayList<>();

		    try {
		        conn = ds.getConnection(); // DB 연결 초기화

		        // SQL 쿼리 작성
		        String sql = " SELECT m.movie_title, "
		        		+ "       TO_CHAR(m.start_date, 'yyyy-MM-dd') AS start_date, "
		        		+ "       m.running_time, "
		        		+ "       TO_CHAR(s.start_time, 'yyyy-MM-dd HH24:mi') AS start_time, "
		        		+ "       s.unused_seat, "
		        		+ "       sc.screen_no "
		        		+ " FROM tbl_showtime s "
		        		+ " JOIN tbl_movie m ON s.fk_seq_movie_no = m.seq_movie_no "
		        		+ " JOIN tbl_screen sc ON s.fk_screen_no = sc.screen_no "
		        		+ " WHERE TO_CHAR(s.start_time, 'yyyy-MM-dd') = ?  "
		        		+ "  AND s.fk_screen_no = 2  "
		        		+ " ORDER BY s.start_time ASC ";
		        		

		        pstmt = conn.prepareStatement(sql); // PreparedStatement 생성
		        pstmt.setString(1, selectedDate); // SQL 파라미터 설정

		        rs = pstmt.executeQuery(); // SQL 실행

		        // 결과를 리스트에 추가
		        while (rs.next()) {
		            MovieVO_yeo movie = new MovieVO_yeo();     // 영화 객체 생성
		            ShowTimeVO_yeo svo = new ShowTimeVO_yeo(); // 상영 시간 객체 생성

		            // 영화 정보 설정
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
		        close(); // 자원 해제
		    }

		    return movieTimeList; // 결과 반환
		}

		@Override
		public List<MovieVO_yeo> searchMovies(String trim) throws SQLException {
		    List<MovieVO_yeo> movieList = new ArrayList<>();
		    try {
		        conn = ds.getConnection(); // DB 연결
		        String sql = " SELECT m.seq_movie_no, m.fk_category_code, m.movie_title, m.content, m.director, " +
		                     "       m.actor, m.movie_grade, m.running_time, m.like_count, " +
		                     "       TO_CHAR(m.start_date, 'yyyy-MM-dd') AS start_date, " +
		                     "       TO_CHAR(m.end_date, 'yyyy-MM-dd') AS end_date, " +
		                     "       m.poster_file, m.video_url, m.register_date, c.category_code, c.category, " +
		                     "       TO_CHAR(s.start_time, 'yyyy-MM-dd') AS start_time, s.unused_seat, sc.screen_no " +
		                     " FROM TBL_MOVIE m " +
		                     " JOIN TBL_CATEGORY c ON m.fk_category_code = c.category_code " +
		                     " LEFT JOIN TBL_SHOWTIME s ON m.seq_movie_no = s.fk_seq_movie_no " +
		                     " LEFT JOIN TBL_SCREEN sc ON s.fk_screen_no = sc.screen_no " +
		                     " WHERE m.movie_title LIKE ? OR c.category LIKE ? " +
		                     " ORDER BY m.start_date DESC ";

		        pstmt = conn.prepareStatement(sql);
		        pstmt.setString(1, "%" + trim + "%");
		        pstmt.setString(2, "%" + trim + "%");
		        rs = pstmt.executeQuery();

		        while (rs.next()) {
		            MovieVO_yeo movie = new MovieVO_yeo();
		            CategoryVO_yeo category = new CategoryVO_yeo();
		            ShowTimeVO_yeo showTime = new ShowTimeVO_yeo();

		            // 영화 정보 설정
		            movie.setSeq_movie_no(rs.getInt("seq_movie_no"));
		            movie.setFk_category_code(rs.getString("category_code"));
		            movie.setMovie_title(rs.getString("movie_title"));
		            movie.setContent(rs.getString("content"));
		            movie.setDirector(rs.getString("director"));
		            movie.setActor(rs.getString("actor"));
		            movie.setMovie_grade(rs.getString("movie_grade"));
		            movie.setRunning_time(rs.getString("running_time"));
		            movie.setLike_count(rs.getInt("like_count"));
		            movie.setStart_date(rs.getString("start_date")); // 날짜 형식으로 변환된 개봉일
		            movie.setEnd_date(rs.getString("end_date"));     // 날짜 형식으로 변환된 종료일
		            movie.setPoster_file(rs.getString("poster_file"));
		            movie.setVideo_url(rs.getString("video_url"));
		            movie.setRegister_date(rs.getString("register_date"));

		            // 카테고리 정보 설정
		            category.setCategory_code(rs.getString("category_code"));
		            category.setCategory(rs.getString("category"));
		            movie.setCg(category);

		            // 상영 정보 설정
		            showTime.setStart_time(rs.getString("start_time")); // 날짜 형식으로 변환된 상영 시작 시간
		            showTime.setUnused_seat(rs.getInt("unused_seat"));  // 남은 좌석 수
		            showTime.setFk_screenNO(rs.getInt("screen_no"));    // 상영관 번호
		            movie.setSvo(showTime);

		            movieList.add(movie);
		        }
		    } catch (SQLException e) {
		        System.err.println("SQL 실행 중 오류 발생: " + e.getMessage());
		        throw e; // 예외 재발생
		    } finally {
		        close(); // 자원 해제
		    }
		    return movieList;
		}


	

		
	
  
}
