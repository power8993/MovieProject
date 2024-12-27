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

import movie.domain.CategoryVO;
import movie.domain.MovieVO;
import movie.domain.ScreenVO;
import movie.domain.ShowtimeVO;

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
			
			pstmt.setString(1, movie.getCatevo().getCategory_code());
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
				
				CategoryVO catevo = new CategoryVO();
				catevo.setCategory(rs.getString("category"));
				mvvo.setCatevo(catevo);
				
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

	
	
	// 페이징 처리를 안한 모든 등록된 영화 리스트 보여주기
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
					   + " on(m.fk_category_code = c.category_code) ";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				MovieVO mvvo = new MovieVO();
				
				mvvo.setSeq_movie_no(rs.getInt("seq_movie_no"));
				mvvo.setRegister_date(rs.getString("register_date"));
				
				CategoryVO catevo = new CategoryVO();
				catevo.setCategory(rs.getString("category"));
				mvvo.setCatevo(catevo);
				
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
	}// end of public List<MovieVO> selectMovieList(Map<String, String> paraMap) throws SQLException {}----------------
	
	
	
	// 페이징 처리를 안한 영화제목 검색 결과 보여주기(select)
	@Override
	public List<MovieVO> selectSearchMovieList(Map<String, String> paraMap) throws SQLException {
		
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
				
				CategoryVO catevo = new CategoryVO();
				catevo.setCategory(rs.getString("category"));
				mvvo.setCatevo(catevo);
				
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
	}// end of public List<MovieVO> selectSearchMovieList() throws SQLException {}-------------------


	
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
					   + "      , to_char(start_date, 'yyyy-mm-dd') as start_date "
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
				
				CategoryVO catevo = new CategoryVO();
				catevo.setCategory(rs.getString("category"));
				mvvo.setCatevo(catevo);
				
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


	
	// 영화를 수정하는 메소드(seq에 해당하는 영화를 update)
	@Override
	public int updateMovie(MovieVO movie) throws SQLException {
		
		int result = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql = " update tbl_movie set fk_category_code = (select category_code from tbl_category where category= ? ) "
					   + "                   , movie_title = ? "
					   + "                   , content = ? "
					   + "                   , director = ? "
					   + "                   , actor = ? "
					   + "                   , movie_grade = ? "
					   + "                   , running_time = ? "
					   + "                   , start_date = ? "
					   + "                   , end_date = ? "
					   + "                   , poster_file = ? "
					   + "                   , video_url = ? "
					   + " where seq_movie_no = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, movie.getCatevo().getCategory_code());
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
			pstmt.setInt(12, movie.getSeq_movie_no());
			
			result = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		return result;	
	}// end of public int updateMovie(String seq) throws SQLException {}-----------------------------



	// 영화를 삭제하는 메소드(seq에 해당하는 영화를 delete)
	@Override
	public int deleteMovie(String seq) throws SQLException {
		int result = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql = " delete from tbl_movie where seq_movie_no = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, Integer.parseInt(seq));
			
			result = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		return result;
	}// end of public int deleteMovie(String seq) throws SQLException {}-----------------------------


	
	// 상영일정을 등록해주는 메소드(tbl_showtime 테이블에 insert)
	@Override
	public int registerShowtime(MovieVO mvvo) throws SQLException {
		int result = 0;
		
		String seat_arr = "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0";
		
		try {
			conn = ds.getConnection();
			
			String sql = " insert into tbl_showtime (seq_showtime_no, fk_seq_movie_no, fk_screen_no, start_time, end_time, seat_arr, unused_seat) "
					   + " values(seq_showtime_no.nextval "
					   + "      , ? "
					   + "      , ? "
					   + "      , to_timestamp(replace(?, 'T', ' '), 'yyyy-MM-dd HH24:MI') "
			           + "      , to_timestamp(replace(?, 'T', ' '), 'yyyy-MM-dd HH24:MI') "
					   + "      , ? "
					   + "      , (select seat_cnt from tbl_screen where screen_no = ? ) ) ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, mvvo.getSeq_movie_no());
			pstmt.setInt(2, mvvo.getScvo().getScreen_no());
			pstmt.setString(3, mvvo.getShowvo().getStart_time());
			pstmt.setString(4, mvvo.getShowvo().getEnd_time());
			pstmt.setString(5, seat_arr);
			pstmt.setInt(6, mvvo.getScvo().getScreen_no());

			result = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		return result;		
	}// end of public int registerShowtime(MovieVO mvvo) throws SQLException {}----------------------



	// [상영시간 조회하기] 선택한 상영 시간과 상영관에 중첩된 상영이 있는지 확인하는 메소드 (select)
	@Override
	public List<MovieVO> selectShowtimeConflict(Map<String, String> paraMap) throws SQLException {
		List<MovieVO> movieList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			
			String sql = " with "
	                   + " m as ( "
	                   + "     select seq_movie_no, "
	                   + "            c.category, "
	                   + "            movie_title, "
	                   + "            movie_grade, "
	                   + "            running_time, "
	                   + "            to_char(start_date, 'yyyy-mm-dd') as start_date, "
	                   + "            to_char(end_date, 'yyyy-mm-dd') as end_date, "
	                   + "            poster_file "
	                   + "     from tbl_movie m "
	                   + "     join tbl_category c on m.fk_category_code = c.category_code "
	                   + " ), "
	                   + " s as ( "
	                   + "     select fk_seq_movie_no, "
	                   + "            seq_showtime_no, "
	                   + "            sc.screen_no, "
	                   + "            to_char(start_time, 'yyyy-mm-dd hh24:mi:ss') as start_time, "
	                   + "            to_char(end_time, 'yyyy-mm-dd hh24:mi:ss') as end_time "
	                   + "     from tbl_showtime s "
	                   + "     join tbl_screen sc on s.fk_screen_no = sc.screen_no "
	                   + " ) "
	                   + " select m.poster_file, "
	                   + "        m.movie_title, "
	                   + "        m.movie_grade, "
	                   + "        m.category, "
	                   + "        s.screen_no, "
	                   + "        m.running_time, "
	                   + "        s.start_time, "
	                   + "        s.end_time "
	                   + " from m "
	                   + " join s on m.seq_movie_no = s.fk_seq_movie_no "
	                   + " where s.screen_no = ? "
	                   + " and ( "
	                   + "    -- 시작 시간과 종료 시간이 입력 시간에 10분 간격을 더한 범위에 포함\r\n"
	                   + "    (s.start_time <= to_timestamp(replace(?, 'T', ' '), 'yyyy-mm-dd hh24:mi:ss') + INTERVAL '9' MINUTE "
	                   + "     and s.end_time >= to_timestamp(replace(?, 'T', ' '), 'yyyy-mm-dd hh24:mi:ss') - INTERVAL '9' MINUTE) "
	                   + "    or "
	                   + "    -- 상영이 입력된 시간 범위 내에서 시작하는지 여부\r\n"
	                   + "    (s.start_time >= to_timestamp(replace(?, 'T', ' '), 'yyyy-mm-dd hh24:mi:ss') - INTERVAL '9' MINUTE "
	                   + "     and s.start_time <= to_timestamp(replace(?, 'T', ' '), 'yyyy-mm-dd hh24:mi:ss') + INTERVAL '9' MINUTE) "
	                   + "    or "
	                   + "    -- 상영이 입력된 시간 범위 내에서 종료하는지 여부\r\n"
	                   + "    (s.end_time >= to_timestamp(replace(?, 'T', ' '), 'yyyy-mm-dd hh24:mi:ss') - INTERVAL '9' MINUTE "
	                   + "     and s.end_time <= to_timestamp(replace(?, 'T', ' '), 'yyyy-mm-dd hh24:mi:ss') + INTERVAL '9' MINUTE) "
	                   + ") ";	

	        pstmt = conn.prepareStatement(sql);
	        
	        // 시간값을 적절히 포맷
	        String start_time_str = paraMap.get("start_time").replace("T", " "); // T를 공백으로 변경
	        String end_time_str = paraMap.get("end_time").replace("T", " "); // T를 공백으로 변경
	        

			pstmt.setString(1, paraMap.get("screen_no"));
			pstmt.setString(2, start_time_str); 
			pstmt.setString(3, end_time_str);
			pstmt.setString(4, start_time_str); 
			pstmt.setString(5, end_time_str); 
			pstmt.setString(6, start_time_str); 
			pstmt.setString(7, end_time_str); 
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				MovieVO mvvo = new MovieVO();
				
				mvvo.setPoster_file(rs.getString("poster_file"));
				mvvo.setMovie_title(rs.getString("movie_title"));
				mvvo.setMovie_grade(rs.getString("movie_grade"));

				CategoryVO catevo = new CategoryVO();
				catevo.setCategory(rs.getString("category"));
				mvvo.setCatevo(catevo);
				
				ScreenVO scvo = new ScreenVO();
				scvo.setScreen_no(Integer.parseInt(rs.getString("screen_no")));
				mvvo.setScvo(scvo);
				
				mvvo.setRunning_time(rs.getString("running_time"));
				
				ShowtimeVO showvo = new ShowtimeVO();
				showvo.setStart_time(rs.getString("start_time"));
				showvo.setEnd_time(rs.getString("end_time"));
				mvvo.setShowvo(showvo);
				
				movieList.add(mvvo);
				
			}// while(rs.next()) {}------------------------------------------
					
		} finally {
			close();
		}
		
		return movieList;
	}// end of public List<MovieVO> selectShowtimeConflict(Map<String, String> paraMap) throws SQLException {}----------------------


	
	// 페이징 처리를 안한 상영일정이 등록된 모든 영화 리스트 보여주기 (select)
	@Override
	public List<MovieVO> selectShowtimeList(Map<String, String> paraMap) throws SQLException {
		
		List<MovieVO> movieList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			
			String sql = " with "
					   + " s as "
					   + " (select seq_showtime_no, fk_seq_movie_no, start_time, end_time, fk_screen_no, unused_seat "
					   + " from tbl_showtime), "
					   + " m as "
					   + " (select seq_movie_no, poster_file, movie_title "
					   + " from tbl_movie) "
					   + " select s.seq_showtime_no "
					   + "      , to_char(s.start_time, 'yyyy-mm-dd hh24:mi') as start_time "
					   + "      , to_char(s.end_time, 'yyyy-mm-dd hh24:mi') as end_time "
					   + "      , s.fk_screen_no "
					   + "      , m.poster_file "
					   + "      , m.movie_title "
					   + "      , s.unused_seat "
					   + " from s join m "
					   + " on(s.fk_seq_movie_no = m.seq_movie_no) ";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				MovieVO mvvo = new MovieVO();
				
				ShowtimeVO showvo = new ShowtimeVO();
				showvo.setStart_time(rs.getString("start_time"));
				showvo.setEnd_time(rs.getString("end_time"));
				showvo.setFk_screen_no(rs.getInt("fk_screen_no"));
				showvo.setUnused_seat(rs.getInt("unused_seat"));
				showvo.setSeq_showtime_no(rs.getInt("seq_showtime_no"));
				mvvo.setShowvo(showvo);
				
				mvvo.setPoster_file(rs.getString("poster_file"));
				mvvo.setMovie_title(rs.getString("movie_title"));
				
				movieList.add(mvvo);
				
			}// while(rs.next()) {}------------------------------------------
					
		} finally {
			close();
		}
		return movieList;
	}// end of public List<MovieVO> selectShowtimeList(Map<String, String> paraMap) throws SQLException {}-------------------------



	// 페이징 처리를 위한 검색유무와 상관 없는 회원에 대한 총 페이지 수 알아오기 
	@Override
	public int getTotalPage(Map<String, String> paraMap) throws SQLException {
		
		int total_page = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql = " select ceil(count(*)/?) "
					   + " from tbl_movie ";
			
			String category_code = paraMap.get("search_category_code");
			String search_movie_title = paraMap.get("search_movie_title");
			
			if(!category_code.isBlank()) {
				// 장르를 선택한 경우
				sql += " where fk_category_code = ? ";
			}
			
			if(!search_movie_title.isBlank()) {
				// 검색어를 입력한 경우
				sql += " and movie_title like '%'|| ? ||'%' ";
			}
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, Integer.parseInt(paraMap.get("size_per_page")));
			
			if(!category_code.isBlank() && search_movie_title.isBlank()) {
				// 장르를 선택, 검색어 입력하지 않음
				pstmt.setString(1, category_code); 
			}
			else if (category_code.isBlank() && !search_movie_title.isBlank()) {
				// 장르를 미선택, 검색어 입력
				pstmt.setString(1, search_movie_title); 
			}
			else if (!category_code.isBlank() && !search_movie_title.isBlank()){
				// 장르 선택, 검색어 입력
				pstmt.setString(1, category_code); 
				pstmt.setString(2, search_movie_title);
			}
			
			rs = pstmt.executeQuery();
			
			rs.next();
			
			total_page = rs.getInt(1);
			
		} finally {
			close();
		}
		
		return total_page;
	}// end of public int getTotalPage(Map<String, String> paraMap) throws SQLException {


	
	// 페이징 처리를 한 모든 등록된 영화 리스트 보여주기 (select)
	@Override
	public List<MovieVO> selectMovieListPaging(Map<String, String> paraMap) throws SQLException {
		
		List<MovieVO> movieList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			
			String sql = " select rno, seq_movie_no, register_date, category, poster_file, movie_title, movie_grade, start_date "
					   + " from "
					   + " (select rownum as rno "
					   + "      , seq_movie_no "
					   + "      , to_char(register_date, 'yyyy-mm-dd') as register_date "
					   + "      , c.category "
					   + "      , poster_file "
					   + "      , movie_title "
					   + "      , movie_grade "
					   + "      , to_char(start_date, 'yyyy-mm-dd') as start_date "
					   + " from tbl_movie m join tbl_category c "
					   + " on(m.fk_category_code = c.category_code) ";
					   
			
			String category_code = paraMap.get("search_category_code");
			String search_movie_title = paraMap.get("search_movie_title");
			
			if(!category_code.isBlank()) {
				// 장르를 선택한 경우
				sql += " where c.category_code = ? ";
			}
			
			if(!search_movie_title.isBlank()) {
				// 검색어를 입력한 경우
				sql += " and movie_title like '%'|| ? ||'%') ";
			}
			
			sql += " where rno between ? and ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			int current_showpage_no = Integer.parseInt(paraMap.get("current_showpage_no"));
			int size_per_page = Integer.parseInt(paraMap.get("size_per_page"));
			
			
			
			if(!category_code.isBlank() && search_movie_title.isBlank()) {
				// 장르를 선택, 검색어 입력하지 않음
				pstmt.setString(1, category_code); 
				pstmt.setInt(2, (current_showpage_no * size_per_page) - (size_per_page - 1));
				pstmt.setInt(3, (current_showpage_no * size_per_page));
			}
			else if (category_code.isBlank() && !search_movie_title.isBlank()) {
				// 장르를 미선택, 검색어 입력
				pstmt.setString(1, search_movie_title); 
				pstmt.setInt(2, (current_showpage_no * size_per_page) - (size_per_page - 1));
				pstmt.setInt(3, (current_showpage_no * size_per_page));
			}
			else if (!category_code.isBlank() && !search_movie_title.isBlank()){
				// 장르 선택, 검색어 입력
				pstmt.setString(1, category_code); 
				pstmt.setString(2, search_movie_title);
				pstmt.setInt(3, (current_showpage_no * size_per_page) - (size_per_page - 1));
				pstmt.setInt(4, (current_showpage_no * size_per_page));
			}
			else if (category_code.isBlank() && search_movie_title.isBlank()){
				// 장르 미선택, 검색어 입력하지 않음
				pstmt.setInt(1, (current_showpage_no * size_per_page) - (size_per_page - 1));
				pstmt.setInt(2, (current_showpage_no * size_per_page));
			}
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				MovieVO mvvo = new MovieVO();
				
				mvvo.setSeq_movie_no(rs.getInt("seq_movie_no"));
				mvvo.setRegister_date(rs.getString("register_date"));
				
				CategoryVO catevo = new CategoryVO();
				catevo.setCategory(rs.getString("category"));
				mvvo.setCatevo(catevo);
				
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
	}// end of public List<MovieVO> selectMovieListPaging(Map<String, String> paraMap) throws SQLException {}-------------------------


	
	// 검색이 있는 또는 검색이 없는 회원의 총개수 알아오기
	@Override
	public int getTotalMovieCount(Map<String, String> paraMap) throws SQLException {
		int total_movie_count = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql = " select count(*) "
					   + " from tbl_movie ";
			
			String category_code = paraMap.get("search_category_code");
			String search_movie_title = paraMap.get("search_movie_title");
			
			if(!category_code.isBlank()) {
				// 장르를 선택한 경우
				sql += " where fk_category_code = ? ";
			}
			
			if(!search_movie_title.isBlank()) {
				// 검색어를 입력한 경우
				sql += " and movie_title like '%'|| ? ||'%' ";
			}
			
			pstmt = conn.prepareStatement(sql);

			if(!category_code.isBlank() && search_movie_title.isBlank()) {
				// 장르를 선택, 검색어 입력하지 않음
				pstmt.setString(1, category_code); 
			}
			else if (category_code.isBlank() && !search_movie_title.isBlank()) {
				// 장르를 미선택, 검색어 입력
				pstmt.setString(1, search_movie_title); 
			}
			else if (!category_code.isBlank() && !search_movie_title.isBlank()){
				// 장르 선택, 검색어 입력
				pstmt.setString(1, category_code); 
				pstmt.setString(2, search_movie_title);
			}

			rs = pstmt.executeQuery();
			
			rs.next();
			
			total_movie_count = rs.getInt(1);
			
		} finally {
			close();
		}
		
		return total_movie_count;
	}// end of public int getTotalMovieCount(Map<String, String> paraMap) throws SQLException {}------------------------------------



	

	
}
