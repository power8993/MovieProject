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
import movie.domain.MovieVO;
import movie.domain.MovieVO_yeo;
import movie.domain.ShowTimeVO_yeo;


public class MovieDAO_imple_yeo implements MovieDAO_yeo {
    private DataSource ds;    // 아파치 톰캣의 DBCP(DataSource)
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

    // 자원 해제 메소드
    private void close() {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 전체 목록 (예매율순)
    @Override
    public List<MovieVO_yeo> select_Movies() throws SQLException {
       
    	List<MovieVO_yeo> movieList = new ArrayList<>();
    	
        try {
        	
            conn = ds.getConnection(); // DB 연결
        
            // SQL 쿼리 작성
            String sql = " select "
            		   + "    m.seq_movie_no, m.movie_title, m.fk_category_code,  c.category, m.content, m.director, m.actor, m.movie_grade, "
            		   + "    m.running_time,  m.like_count, to_char(m.start_date, 'yyyy-mm-dd') as start_date,  m.end_date,  m.poster_file, m.video_url, "
            		   + "    m.register_date,st.seq_showtime_no,s.screen_no,s.seat_cnt, "
            		   + "    (s.seat_cnt - st.unused_seat) as reserved_seats, "
            		   + "    round((s.seat_cnt - st.unused_seat) / s.seat_cnt * 100, 2) as reservation_rate "
            		   + " from "
            		   + "    tbl_movie m" 
            		   + " join "
            		   + "    tbl_category c on m.fk_category_code = c.category_code "
            		   + " join "
            		   + "    tbl_showtime st on m.seq_movie_no = st.fk_seq_movie_no "
            		   + " join "
            		   + "    tbl_screen s on st.fk_screen_no = s.screen_no "
            		   + " order by "
            		   + "    reservation_rate desc ";
           
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            
            // ResultSet 데이터 읽기
            while (rs.next()) {
            	
            	MovieVO_yeo movie = new MovieVO_yeo();
            	CategoryVO_yeo cg = new CategoryVO_yeo();
            	            	
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
                
                cg.setCategory(rs.getString("category"));
                
                movie.setCg(cg);;
                
                movieList.add(movie);               
            }
        } catch (SQLException e) {         
            e.printStackTrace();
        } finally {
            close(); // 자원 해제
        }
        return movieList;
    }  // 전체 목록 (예매율순)  ------------------------------------------------------------------------------------
 
    
  
    // 상영중 영화 가져오기(개봉 날짜 순)
	@Override
	public List<MovieVO_yeo> select_run_Movies() throws SQLException {		
	
		List<MovieVO_yeo> movieList = new ArrayList<>();

        try {
            conn = ds.getConnection();
            //System.out.println("DB 연결 성공");
            String sql = " select  "
            	   	   + " m.seq_movie_no, m.movie_title, m.fk_category_code,  c.category, m.content, m.director, m.actor, m.movie_grade,  "
            	   	   + " m.running_time,  m.like_count, to_char(m.start_date, 'yyyy-mm-dd') as start_date,  m.end_date,  m.poster_file, m.video_url,  "
            	   	   + " m.register_date,st.seq_showtime_no,s.screen_no,s.seat_cnt,  "
            	   	   + " (s.seat_cnt - st.unused_seat) as reserved_seats,  "
            	   	   + " round((s.seat_cnt - st.unused_seat) / s.seat_cnt * 100, 2) as reservation_rate  "
            	   	   + " from  "
            	   	   + "    tbl_movie m "
            	   	   + " join  "
            	   	   + "    tbl_category c on m.fk_category_code = c.category_code "
            	   	   + " join  "
            	   	   + "    tbl_showtime st on m.seq_movie_no = st.fk_seq_movie_no "
            	   	   + " join  "
            	   	   + "    tbl_screen s on st.fk_screen_no = s.screen_no "
            	   	   + " where  "
            	   	   + "    st.start_time >= sysdate  "
            	   	   + " order by  "
            	   	   + "    reservation_rate desc ";
           // System.out.println("실행할 SQL: " + sql);

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
            	
            	MovieVO_yeo movie = new MovieVO_yeo();
            	CategoryVO_yeo cg = new CategoryVO_yeo();
            	
            	movie.setMovie_title(rs.getString("movie_title"));  // 영화제목
            	movie.setMovie_grade(rs.getString("movie_grade"));  // 연령대
				movie.setStart_date(rs.getString("start_date"));    // 개봉일
				
				// 러닝타임 좌석
												
				cg.setCategory(rs.getString("category"));
                
                movie.setCg(cg);;
                
                movieList.add(movie);    

            }
        } catch (SQLException e) {
            //System.out.println("SQL 실행 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        } finally {
            close(); // 자원 해제
        }

        return movieList;

	} // 상영중 영화 가져오기(개봉 날짜 순) 끝 -----------------------------------------------------------------------------

	
	// 상영예정작 가져오기(예매율 순)
	@Override
	public List<MovieVO_yeo> select_Upc_Movies() throws SQLException {
		
		List<MovieVO_yeo> movieList = new ArrayList<>();

        try {
            conn = ds.getConnection();
            //System.out.println("DB 연결 성공");
            String sql = " select  "
            	   	   + "    m.seq_movie_no, m.movie_title, m.fk_category_code, c.category, m.content, m.director, m.actor, m.movie_grade,  "
            	   	   + "    m.running_time, m.like_count, to_char(m.start_date, 'yyyy-mm-dd') as start_date, m.end_date, m.poster_file, m.video_url,  "
            	   	   + "    m.register_date, st.seq_showtime_no, s.screen_no, s.seat_cnt,  "
            	   	   + "    (s.seat_cnt - st.unused_seat) as reserved_seats,   "
            	   	   + "    round((s.seat_cnt - st.unused_seat) / s.seat_cnt * 100, 2) as reservation_rate  "
            	   	   + " from  "
            	   	   + "    tbl_movie m "
            	   	   + " join  "
            	   	   + "    tbl_category c on m.fk_category_code = c.category_code "
            	   	   + " join  "
            	   	   + "    tbl_showtime st on m.seq_movie_no = st.fk_seq_movie_no "
            	   	   + " join  "
            	   	   + "    tbl_screen s on st.fk_screen_no = s.screen_no "
            	   	   + " where  "
            	   	   + "    st.start_time <= sysdate  "
            	   	   + " order by  "
            	   	   + "    m.start_date ";

     		 
           // System.out.println("실행할 SQL: " + sql);

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
            	
            	MovieVO_yeo movie = new MovieVO_yeo();
            	CategoryVO_yeo cg = new CategoryVO_yeo();
            	
            	movie.setMovie_title(rs.getString("movie_title"));
            	movie.setMovie_grade(rs.getString("movie_grade"));
				movie.setStart_date(rs.getString("start_date"));
				cg.setCategory(rs.getString("category"));
                
                movie.setCg(cg);;
               
                movieList.add(movie);

            }
        } catch (SQLException e) {
            //  System.out.println("SQL 실행 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        } finally {
            close(); // 자원 해제
        }

        return movieList;
	} // 상영예정작 가져오기(예매율 순) 끝 ------------------------------------------------------------------------

	// 영화 시간표 가져오기  
	@Override
	public List<MovieVO_yeo> selectMovieTiem() throws SQLException {
		
		List<MovieVO_yeo> movieList = new ArrayList<>();

        try {
            conn = ds.getConnection();
            //System.out.println("DB 연결 성공");
            String sql = " select  "
            		+ " movie_title ,  "
            		+ " running_time ,  "
            		+ " movie_grade , "
            		+ " to_char(start_date, 'yyyy-mm-dd') as start_date ,  "
            		+ " to_char(start_time, 'yyyy-mm-dd hh24:mi') as start_time ,  "
            		+ " unused_seat "
            		+ " from  "           		
            		+ " TBL_MOVIE m  "
            		+ " join  "
            		+ " TBL_SHOWTIME s  "
            		+ " on  "
            		+ " m.SEQ_MOVIE_NO = s.FK_SEQ_MOVIE_NO  "
            		+ " where SYSDATE >= START_DATE and SYSDATE <= END_DATE + 1  "
            		+ " order by START_DATE, START_TIME ";
     		 
            
           
           // System.out.println("실행할 SQL: " + sql);

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
            	
            	MovieVO_yeo movie = new MovieVO_yeo();
            	ShowTimeVO_yeo svo = new ShowTimeVO_yeo();
            	
            	movie.setMovie_title(rs.getString("movie_title")); // 제목
            	movie.setRunning_time(rs.getString("running_time"));// 러닝타임
            	movie.setStart_date(rs.getString("start_date"));  // 개봉날짜
            	movie.setMovie_grade(rs.getString("movie_grade")); //  연령
            	           	
            	svo.setStart_time(rs.getString("start_time"));   // 영화시작시간
            	svo.setUnused_seat(rs.getInt("unused_seat")); //영화 남은 자리 
            	                           
            	movie.setSvo(svo);
            	movieList.add(movie);

            }
        } catch (SQLException e) {
             System.out.println("SQL 실행 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        } finally {
            close(); // 자원 해제
        }

        return movieList;
		
	}

	// 장르 종류 가져오기
	@Override
	public List<CategoryVO_yeo> selectcategory() throws SQLException {
		
		List<CategoryVO_yeo> cgList = new ArrayList<>();
	      
        try {
         conn = ds.getConnection();
         
         String sql = " select category_code, category "
         		    + " from tbl_category ";
         
         pstmt = conn.prepareStatement(sql);
         
         rs = pstmt.executeQuery();
         
         while(rs.next()) {
        	 
        	 CategoryVO_yeo cg = new CategoryVO_yeo();
        	 
        	 cg.setCategory_code(rs.getString(1));         
        	 cg.setCategory((rs.getString(2)));
            
        	 cgList.add(cg);
         }// end of while-----------------
         
      } finally {
         close();
      }
      
      return cgList;
		
		
	}

	
}
