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

    // 모든 영화 정보 가져오기
    @Override
    public List<MovieVO> select_Movies() throws SQLException {
        List<MovieVO> movieList = new ArrayList<>();
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
                MovieVO movie = new MovieVO();
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
    }  // 모든 영화 정보 가져오기 끝 ------------------------------------------------------------------------------------
 
    // 영화 시간별 가져오기 
	@Override
	public List<MovieVO> selectMovieTiem() throws SQLException {		
	 List<MovieVO> movieList = new ArrayList<>();

        try {
            conn = ds.getConnection();
            //System.out.println("DB 연결 성공");
            String sql = " SELECT SEQ_MOVIE_NO, FK_CATEGORY_CODE, MOVIE_TITLE, CONTENT, DIRECTOR, ACTOR, " +
                         " MOVIE_GRADE, RUNNING_TIME, LIKE_COUNT, START_DATE, END_DATE, POSTER_FILE, " +
                         " VIDEO_URL, REGISTER_DATE " +
                         " FROM TBL_MOVIE ORDER BY START_DATE DESC ";
           // System.out.println("실행할 SQL: " + sql);

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                MovieVO movie = new MovieVO();
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
                movie.setStart_date(rs.getString("START_DATE"));
                movie.setEnd_date(rs.getString("END_DATE"));
                movie.setPoster_file(rs.getString("POSTER_FILE"));
                movie.setVideo_url(rs.getString("VIDEO_URL"));
                movie.setRegister_date(rs.getString("REGISTER_DATE"));
                
                movieList.add(movie);

            }
        } catch (SQLException e) {
            //System.out.println("SQL 실행 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        } finally {
            close(); // 자원 해제
        }

        return movieList;

	} // // 영화 시간별 가져오기 끝-----------------------------------------------------------------------------
}
