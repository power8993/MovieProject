package mypage.model;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
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

import member.domain.MemberVO;
import movie.domain.MovieLikeVO;
import movie.domain.MovieVO;
import util.security.AES256;
import util.security.SecretMyKey;
import util.security.Sha256;

public class MypageDAO_imple implements MypageDAO {
	
	private DataSource ds;	// DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다.
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private AES256 aes;

	// 생성자
	public MypageDAO_imple() {
		try {
			Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/semioracle");
			
			aes = new AES256(SecretMyKey.KEY);
			// SecretMyKey.KEY 은 우리가 만든 암호화/복호화 키이다.
			
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	} // end of public ProductDAO_imple()--------------------------------
	
	// 사용한 자원을 반납하는 close() 메소드 생성하기
	private void close() {
		try {
			if(rs != null) {rs.close(); rs = null;}
			if(pstmt != null) {pstmt.close(); pstmt = null;}
			if(conn != null) {conn.close(); conn = null;}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	} // end of private void close()------------------------------------

	
	
	//비밀번호 일치하는지 검증
	@Override
	public boolean checkPassword(Map<String, String> paraMap) throws SQLException {
		boolean checkPassword = false;

		try {
			conn = ds.getConnection();

			String sql = " select pwd " + " from tbl_member " + " where user_id = ? and pwd = ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paraMap.get("userid"));
			pstmt.setString(2, Sha256.encrypt(paraMap.get("pwd")));

			rs = pstmt.executeQuery();
			checkPassword = rs.next();
		        
		} finally {
			close();
		}
		return checkPassword;
	}

	
	
	//회원정보수정- 이메일 중복검사
	@Override
	public boolean emailDuplicateCheck2(Map<String, String> paraMap) throws SQLException {
		boolean isExists = false;

		try {
			conn = ds.getConnection();

			String sql = " select email " + " from tbl_member " + " where user_id = ? and email = ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paraMap.get("userid"));
			pstmt.setString(2, aes.encrypt(paraMap.get("email")));

			rs = pstmt.executeQuery();

			isExists = rs.next();

		} catch (GeneralSecurityException | UnsupportedEncodingException e) {
		} finally {
			close();
		}

		return isExists;
	}

	
	
	//회원정보수정-이름,이메일,휴대전화만 변경가능
	@Override
	public int updateMember(MemberVO member) throws SQLException {
		int result = 0;

		try {
			conn = ds.getConnection();
			
			String sql = " update tbl_member set name = ? "
	                  + "                     , email = ? "
	                  + "                     , mobile = ? "
	                  + " where user_id = ? ";
			
			pstmt = conn.prepareStatement(sql);
	         
	         pstmt.setString(1, member.getName());
	         pstmt.setString(2, aes.encrypt(member.getEmail()) );  // 이메일을 AES256 알고리즘으로 양방향 암호화 시킨다. 
	         pstmt.setString(3, aes.encrypt(member.getMobile()) ); // 휴대폰번호를 AES256 알고리즘으로 양방향 암호화 시킨다. 
	         pstmt.setString(4, member.getUserid());
	         
	         result =  pstmt.executeUpdate();
	         
		} catch (GeneralSecurityException | UnsupportedEncodingException e) {
		}finally {
			close();
		}
		
		
		return result;
	}

	
	
	//비밀번호 변경
	@Override
	public int mypwdUdate(Map<String, String> paraMap) throws SQLException {
		int result = 0;

		try {
			conn = ds.getConnection();

			String sql = " update tbl_member set pwd = ?, PWD_CHANGE_DATE = sysdate " + " where user_id = ? ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, Sha256.encrypt(paraMap.get("new_pwd"))); // 암호를 SHA256 알고리즘으로 단방향 암호화 시킨다.
			pstmt.setString(2, paraMap.get("userid"));

			result = pstmt.executeUpdate();

		} finally {
			close();
		}

		return result;
	}
	
	
	
	
	
	//회원탈퇴
	@Override
	public boolean deletePassword(String userid) throws SQLException {
		boolean result = false;

		try {
			conn = ds.getConnection();

			 String sql = "UPDATE tbl_member SET user_status = 0 WHERE user_id = ?";
		        pstmt = conn.prepareStatement(sql);
		        pstmt.setString(1, userid);

		     int memberrow = pstmt.executeUpdate();
		     result = (memberrow > 0); 
			
		} finally {
			close();
		}

		return result;
	}

	
	//마이페이지 기대되는 영화 좋아요 목록 전체 합계
	@Override
	public int totalmymovielike(String userid) throws SQLException {
		int totalmymovielike = 0;
	      
	      try {
	         conn = ds.getConnection();
	         
	         String sql = " select count(*) "
	                  + " from TBL_LIKE "
	                  + " where fk_user_id = ? ";
	         
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setString(1, userid);
	         
	         rs = pstmt.executeQuery();
	         
	         rs.next();
	         
	         totalmymovielike = rs.getInt(1);
	         
	      } finally {
	         close();
	      }
	      
	      return totalmymovielike;
	}

	
	//마이페이지 기대되는 영화 좋아요 목록 리스트
	@Override
	public List<MovieLikeVO> selectBymymovielike(Map<String, String> paraMap) throws SQLException {
		List<MovieLikeVO> mymovielikeList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			
			String sql = " SELECT FK_SEQ_MOVIE_NO, userid, POSTER_FILE, MOVIE_TITLE, START_DATE "
					+ " FROM  "
					+ " ( "
					+ "    SELECT row_number() over(order by FK_SEQ_MOVIE_NO desc) AS RNO "
					+ "         , FK_SEQ_MOVIE_NO, FK_USER_ID as userid, M.POSTER_FILE, M.MOVIE_TITLE, M.START_DATE "
					+ "    FROM TBL_LIKE L "
					+ "    JOIN TBL_MOVIE M "
					+ "    ON L.FK_SEQ_MOVIE_NO = M.SEQ_MOVIE_NO "
					+ "    WHERE FK_USER_ID = ? "
					+ " ) V "
					+ " WHERE rno between ? and ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paraMap.get("userid"));
			pstmt.setString(2, paraMap.get("start"));
			pstmt.setString(3, paraMap.get("end"));
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MovieLikeVO mlvo = new MovieLikeVO();
				mlvo.setFK_USER_ID(rs.getString("userid"));
				mlvo.setFK_SEQ_MOVIE_NO(rs.getInt("FK_SEQ_MOVIE_NO"));
				
				MovieVO mvo = new MovieVO();
				mvo.setPoster_file(rs.getString("poster_file"));
				mvo.setMovie_title(rs.getString("movie_title"));
				mvo.setStart_date(rs.getString("start_date"));
				mlvo.setMvo(mvo);
				
				mymovielikeList.add(mlvo);
			}
		}finally {
			close();
		}
			
			
		return mymovielikeList;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}//end of public class MypageDAO_imple implements MypageDAO-----
