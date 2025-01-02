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
import movie.domain.MovieReviewVO;
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
			
			String sql = " SELECT FK_SEQ_MOVIE_NO, userid, POSTER_FILE, MOVIE_TITLE,to_char(START_DATE, 'yyyy/mm/dd') START_DATE "
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

	//마이페이지 회원정보수정에서 비밀번호 변경 날짜 가져오기
	@Override
	public List<MemberVO> Mylastpwdchangedate(Map<String, String> paraMap) throws SQLException {
		 List<MemberVO> MylastpwdchangedateList = new ArrayList<>();
		 
		 try {
				conn = ds.getConnection();
				
				String sql = " SELECT PWD_CHANGE_DATE  "
						+ " FROM tbl_member where USER_ID = ? ";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, paraMap.get("userid"));
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					MemberVO mvo = new MemberVO();
					mvo.setLastpwdchangedate(rs.getString("lastpwdchangedate"));
					
					MylastpwdchangedateList.add(mvo);
				}
			}finally {
				close();
			}
						
		return MylastpwdchangedateList;
	}
	
	
	
	
	
	
	//마이페이지 내가 쓴 평점 목록 전체 합계
	@Override
	public int totalmymoviereview(String userid) throws SQLException {
		int totalmymoviereview = 0;
	      
	      try {
	         conn = ds.getConnection();
	         
	         String sql = " select count(*) "
	                  + " from TBL_REVIEW "
	                  + " where fk_user_id = ? ";
	         
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setString(1, userid);
	         
	         rs = pstmt.executeQuery();
	         
	         rs.next();
	         
	         totalmymoviereview = rs.getInt(1);
	         
	      } finally {
	         close();
	      }
	      
	      return totalmymoviereview;
	}


	// 마이페이지 내가 쓴 리뷰 목록 리스트
	@Override
	public List<MovieReviewVO> mymoviereviewList(Map<String, String> paraMap) throws SQLException {
		List<MovieReviewVO> mymoviereviewList = new ArrayList<>();

		try {
			conn = ds.getConnection();

			String sql = " select POSTER_FILE, MOVIE_TITLE, SEQ_REVIEW_NO,userid,FK_SEQ_MOVIE_NO,MOVIE_RATING,REVIEW_CONTENT,to_char(REVIEW_WRITE_DATE, 'yyyy/mm/dd') as REVIEW_WRITE_DATE "
					+ " from " + " ( "
					+ " select SEQ_REVIEW_NO,FK_SEQ_MOVIE_NO,FK_USER_ID as userid,MOVIE_RATING,REVIEW_CONTENT,REVIEW_WRITE_DATE, M.POSTER_FILE, M.MOVIE_TITLE "
					+ " from TBL_REVIEW R " + " join TBL_MOVIE M " + " ON R.FK_SEQ_MOVIE_NO = M.SEQ_MOVIE_NO "
					+ " WHERE FK_USER_ID = ? " + " ) " + " order by SEQ_REVIEW_NO desc ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paraMap.get("userid"));

			rs = pstmt.executeQuery();

			while (rs.next()) {
				MovieReviewVO mrvo = new MovieReviewVO();
				mrvo.setFk_user_id(rs.getString("userid"));
				mrvo.setSeq_review_no(rs.getInt("seq_review_no"));
				mrvo.setFk_seq_movie_no(rs.getInt("fk_seq_movie_no"));
				mrvo.setMovie_rating(rs.getInt("movie_rating"));
				mrvo.setReview_content(rs.getString("review_content"));
				mrvo.setReview_write_date(rs.getString("review_write_date"));

				MovieVO mvo = new MovieVO();
				mvo.setPoster_file(rs.getString("poster_file"));
				mvo.setMovie_title(rs.getString("movie_title"));
				mrvo.setMvo(mvo);

				mymoviereviewList.add(mrvo);
			}
		} finally {
			close();
		}
		return mymoviereviewList;
	}

	
	//마이페이지 메인 리스트 = 내가 쓴 리뷰
	@Override
	public List<MovieReviewVO> main_mypage_MovieReviewList(Map<String, String> paraMap) throws SQLException {
		List<MovieReviewVO> main_mypage_MovieReviewList = new ArrayList<>();

		try {
			conn = ds.getConnection();

			String sql = " select POSTER_FILE, MOVIE_TITLE, SEQ_REVIEW_NO, FK_SEQ_MOVIE_NO, userid,MOVIE_RATING,REVIEW_CONTENT, to_char(REVIEW_WRITE_DATE, 'yyyy/mm/dd') as REVIEW_WRITE_DATE "
					+ " from "
					+ " ( "
					+ " select ROW_NUMBER() OVER (ORDER BY SEQ_REVIEW_NO DESC) AS RNO, SEQ_REVIEW_NO,FK_SEQ_MOVIE_NO,FK_USER_ID as userid, "
					+ " MOVIE_RATING,REVIEW_CONTENT,REVIEW_WRITE_DATE, M.POSTER_FILE, M.MOVIE_TITLE "
					+ " from TBL_REVIEW R "
					+ " join TBL_MOVIE M "
					+ " ON R.FK_SEQ_MOVIE_NO = M.SEQ_MOVIE_NO "
					+ " WHERE FK_USER_ID = ? "
					+ " ) "
					+ " WHERE RNO BETWEEN 1 AND 2 ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paraMap.get("userid"));

			rs = pstmt.executeQuery();

			while (rs.next()) {
				MovieReviewVO mrvo = new MovieReviewVO();
				mrvo.setFk_user_id(rs.getString("userid"));
				mrvo.setSeq_review_no(rs.getInt("seq_review_no"));
				mrvo.setFk_seq_movie_no(rs.getInt("fk_seq_movie_no"));
				mrvo.setMovie_rating(rs.getInt("movie_rating"));
				mrvo.setReview_content(rs.getString("review_content"));
				mrvo.setReview_write_date(rs.getString("review_write_date"));

				MovieVO mvo = new MovieVO();
				mvo.setPoster_file(rs.getString("poster_file"));
				mvo.setMovie_title(rs.getString("movie_title"));
				mrvo.setMvo(mvo);

				main_mypage_MovieReviewList.add(mrvo);
			}
		} finally {
			close();
		}
		return main_mypage_MovieReviewList;
	}

	
	
	//마이페이지 메인 리스트 = 기대되는 영화
	@Override
	public List<MovieLikeVO> main_mypage_MovieLikeList(Map<String, String> paraMap) throws SQLException {
		List<MovieLikeVO> main_mypage_MovieLikeList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			
			String sql = " SELECT FK_SEQ_MOVIE_NO, userid, POSTER_FILE, MOVIE_TITLE, to_char(START_DATE, 'yyyy/mm/dd') as START_DATE "
					+ " FROM ( "
					+ "    SELECT ROW_NUMBER() OVER (ORDER BY FK_SEQ_MOVIE_NO DESC) AS RNO, "
					+ "    FK_SEQ_MOVIE_NO, FK_USER_ID AS userid, M.POSTER_FILE, M.MOVIE_TITLE, M.START_DATE "
					+ "    FROM TBL_LIKE L "
					+ "    JOIN TBL_MOVIE M "
					+ "    ON L.FK_SEQ_MOVIE_NO = M.SEQ_MOVIE_NO "
					+ "    WHERE FK_USER_ID = ? "
					+ " ) "
					+ " WHERE RNO BETWEEN 1 AND 2 ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paraMap.get("userid"));
			
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
				
				main_mypage_MovieLikeList.add(mlvo);
			}
		}finally {
			close();
		}
			
			
		return main_mypage_MovieLikeList;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}//end of public class MypageDAO_imple implements MypageDAO-----
