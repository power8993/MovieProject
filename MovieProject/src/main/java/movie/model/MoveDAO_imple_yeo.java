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
import movie.domain.MovieVO_yeo;
import movie.domain.ShowTimeVO_yeo;

public class MoveDAO_imple_yeo implements MovieDAO_yeo {
    private DataSource ds;    // 아파치 톰캣의 DBCP(DataSource)
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    // 생성자
    public MoveDAO_imple_yeo() {

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

    // 모든 영화 정보 가져오기 (예매율 순으로 바꾸기)
    @Override
    public List<MovieVO_yeo> select_Movies() throws SQLException {
       
    	List<MovieVO_yeo> movieList = new ArrayList<>();
    	
        try {
            conn = ds.getConnection(); // DB 연결
        
            // SQL 쿼리 작성
            String sql = " SELECT SEQ_MOVIE_NO, FK_CATEGORY_CODE, MOVIE_TITLE, CONTENT, DIRECTOR, " +
                         " ACTOR, MOVIE_GRADE, RUNNING_TIME, LIKE_COUNT, START_DATE, END_DATE, " +
                         " POSTER_FILE, VIDEO_URL, REGISTER_DATE " +
                         " FROM TBL_MOVIE";
           
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            // ResultSet 데이터 읽기
            while (rs.next()) {
            	MovieVO_yeo movie = new MovieVO_yeo();
                movie.setSeq_movie_no(rs.getInt("SEQ_MOVIE_NO"));
                movie.setFk_category_code(rs.getString("FK_CATEGORY_CODE"));
                movie.setMovie_title(rs.getString("MOVIE_TITLE"));
                movie.setContent(rs.getString("CONTENT"));
                movie.setDirector(rs.getString("DIRECTOR"));
                movie.setActor(rs.getString("ACTOR"));
                movie.setMovie_grade(rs.getString("MOVIE_GRADE"));
                movie.setRunning_time(rs.getString("RUNNING_TIME"));
                movie.setLike_count(rs.getInt("LIKE_COUNT"));
                movie.setStart_date(rs.getString("START_DATE"));
                movie.setEnd_date(rs.getString("END_DATE"));
                movie.setPoster_file(rs.getString("POSTER_FILE"));
                movie.setVideo_url(rs.getString("VIDEO_URL"));
                movie.setRegister_date(rs.getString("REGISTER_DATE"));
                movieList.add(movie);               
            }
        } catch (SQLException e) {         
            e.printStackTrace();
        } finally {
            close(); // 자원 해제
        }
        return movieList;
    }  // 모든 영화 정보 가져오기 (예매율 순으로 바꾸기) ------------------------------------------------------------------------------------
 
    // 상영중 영화 가져오기(개봉 날짜 순)
	@Override
	public List<MovieVO_yeo> select_run_Movies() throws SQLException {		
	
		List<MovieVO_yeo> movieList = new ArrayList<>();

        try {
            conn = ds.getConnection();
            //System.out.println("DB 연결 성공");
            String sql = " select movie_title, movie_grade, start_date "
            		   + " from tbl_movie\r\n"
            		   + " where sysdate >= start_date and sysdate <= end_date + 1 "
            		   + " order by start_date ";
           // System.out.println("실행할 SQL: " + sql);

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
            	
            	MovieVO_yeo movie = new MovieVO_yeo();
            	movie.setMovie_title(rs.getString("movie_title"));
            	movie.setMovie_grade(rs.getString("movie_grade"));
				movie.setStart_date(rs.getString("start_date"));
												
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
            String sql = " select movie_title, movie_grade, start_date "
            		   + " from tbl_movie "
            		   + " where sysdate <= start_date "
            		   + " order by start_date ";
     		 
           // System.out.println("실행할 SQL: " + sql);

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
            	
            	MovieVO_yeo movie = new MovieVO_yeo();
            	movie.setMovie_title(rs.getString("movie_title"));
            	movie.setMovie_grade(rs.getString("movie_grade"));
				movie.setStart_date(rs.getString("start_date"));
												
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
            String sql = " select "
            		   + "    movie_title , "
            		   + "    running_time , "
            		   + "    movie_grade , "
            		   + "    TO_CHAR(start_date, 'YYYY-MM-DD') AS start_date , "
            		   + "    start_time , "
            		   + "    unused_seat "
            		   + " from "
            		   + "    TBL_MOVIE m "
            		   + " join "
            		   + "    TBL_SHOWTIME s "
            		   + " on "
            		   + "    m.SEQ_MOVIE_NO = s.FK_SEQ_MOVIE_NO "
            		   + " where SYSDATE >= START_DATE and SYSDATE <= END_DATE + 1 "
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
            	svo.setUnused_seat(rs.getInt("unused_seat"));//영화 남은 자리 
						
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

	
}
