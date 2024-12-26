package movie.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import member.domain.MemberVO;
import movie.domain.CategoryVO;
import movie.domain.MovieVO_wonjae;

public class MovieDAO_imple_wonjae implements MovieDAO_wonjae {

	private DataSource ds; // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다.
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	// 생성자
	public MovieDAO_imple_wonjae() {
		
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

	// 영화상세보기
	@Override
	public MovieVO_wonjae movieDetail(int seq_movie_no) throws SQLException {
		MovieVO_wonjae mvo = null;
		
		try {
			conn = ds.getConnection();

			// 공지사항 번호(seq_notice_no)를 기준으로 상세 정보를 조회
			String sql = " select seq_movie_no, movie_title, director, actor, running_time, start_date, content, category, poster_file "
					+ " from "
					+ " ( "
					+ " select movie_title, director, actor, running_time, start_date, content, poster_file, seq_movie_no "
					+ " from tbl_movie "
					+ " ) m, "
					+ " ( "
					+ " select category "
					+ " from tbl_category "
					+ " ) c " 
					+ " where seq_movie_no = ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, seq_movie_no);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				mvo = new MovieVO_wonjae();
				mvo.setSeq_movie_no(rs.getInt("seq_movie_no"));
				mvo.setMovie_title(rs.getString("movie_title"));
				mvo.setDirector(rs.getString("director"));
				mvo.setActor(rs.getString("actor"));
				mvo.setRunning_time(rs.getString("running_time"));
				mvo.setStart_date(rs.getString("start_date"));
				mvo.setContent(rs.getString("content"));				
				mvo.setPoster_file(rs.getString("poster_file"));
				
				CategoryVO cvo = new CategoryVO();
				cvo.setCategory(rs.getString("category"));
				
				mvo.setCvo(cvo);
			}
		} finally {
			close();
		}
		return mvo;
	} // end of movieDetail

	@Override
	public boolean insertMovieLike(MemberVO loginuser, int seq_movie_no) throws SQLException {
	    boolean result = false;
	    
	    try {
	        conn = ds.getConnection();
	        
	        String sql = "insert into tbl_like (fk_user_id, fk_seq_movie_no) values (?, ?)";
	        
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, loginuser.getUserid());
	        pstmt.setInt(2, seq_movie_no);
	        
	        int resultnum = pstmt.executeUpdate();
	        
	        result = (resultnum > 0);  // 성공 여부 판단
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close();
	    }
	    return result;
	}

	@Override
	public boolean removeMovieLike(MemberVO loginuser, int seq_movie_no) throws SQLException {
	    boolean result = false;
	    
	    try {
	        conn = ds.getConnection();
	        
	        String sql = "delete from tbl_like where fk_user_id = ? and fk_seq_movie_no = ?";
	        
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, loginuser.getUserid());
	        pstmt.setInt(2, seq_movie_no);
	        
	        int resultnum = pstmt.executeUpdate();
	        
	        result = (resultnum > 0); // 성공 여부 판단
	    } finally {
	        close();
	    }
	    return result;
	}

	@Override
	public boolean checkMovieLike(MemberVO loginuser, int seq_movie_no) throws SQLException {
	    boolean isLiked = false;

	    try {
	        conn = ds.getConnection();

	        String sql = "select count(*) from tbl_like where fk_user_id = ? and fk_seq_movie_no = ?";

	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, loginuser.getUserid());
	        pstmt.setInt(2, seq_movie_no);

	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            int count = rs.getInt(1); // 좋아요를 누른 개수
	            if (count > 0) {
	                isLiked = true; // 좋아요 상태
	            }
	        }
	    } finally {
	        close();
	    }
	    return isLiked;
	}

	

	
	

	
	
	
}

	
	
	


