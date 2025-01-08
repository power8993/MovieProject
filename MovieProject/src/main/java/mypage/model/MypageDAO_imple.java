package mypage.model;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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
import movie.domain.ShowtimeVO;
import reservation.domain.PaymentVO;
import reservation.domain.PointVO;
import reservation.domain.TicketVO;
import util.security.AES256;
import util.security.SecretMyKey;
import util.security.Sha256;

public class MypageDAO_imple implements MypageDAO {

	private DataSource ds; // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다.
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	private AES256 aes;

	// 생성자
	public MypageDAO_imple() {
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			ds = (DataSource) envContext.lookup("jdbc/semioracle");

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
	} // end of private void close()------------------------------------
	
	
	
	
	
	

	/////////////////////////////////////////////////////////////////////////////////////////////////////////

	// 마이페이지 프로필 - 포인트 적립/사용내역 목록
	@Override
	public List<Map<String, Object>> myreservationprofile(String userid) throws SQLException {
		 List<Map<String, Object>> myreservationprofile = new ArrayList<>();
		    
		    try {
		        conn = ds.getConnection();
		        
		        String sql = " SELECT fk_user_id as userid, " +
		                     " sum(case when point_type = 1 then point else 0 end) as total_earned, " +
		                     " sum(case when point_type = 0 then point else 0 end) as total_deducted, " +
		                     " sum(case when point_type = 1 then point else 0 end) -  " +
		                     "  sum(case when point_type = 0 then point else 0 end) as total_points " +
		                     " FROM tbl_point " +
		                     " WHERE FK_USER_ID = ? " +
		                     " GROUP BY FK_USER_ID ";
		        
		        pstmt = conn.prepareStatement(sql);
		        pstmt.setString(1, userid);
		        
		        rs = pstmt.executeQuery();
		        
		        while (rs.next()) {
		            Map<String, Object> paraMap = new HashMap<>();
		            paraMap.put("userid", rs.getString("userid"));
		            paraMap.put("total_earned", rs.getInt("total_earned"));
		            paraMap.put("total_deducted", rs.getInt("total_deducted"));
		            paraMap.put("total_points", rs.getInt("total_points"));
		            myreservationprofile.add(paraMap);
		        }
		        
		    } finally {
		        close();
		    }
		    
		    return myreservationprofile;
	}
	
	
	
	
	@Override
	public List<Map<String, String>> myranking(String userid) throws SQLException {
		List<Map<String, String>> myranking = new ArrayList<>();

		try {
			conn = ds.getConnection();

			 String sql = " SELECT * "
			 		+ " FROM ( "
			 		+ "    SELECT  "
			 		+ "        RANK() OVER (ORDER BY COUNT(FK_USER_ID) DESC) AS myranking, "
			 		+ "        FK_USER_ID AS userid, "
			 		+ "        PAY_STATUS, "
			 		+ "        COUNT(FK_USER_ID) AS FK_USER_ID_COUNT "
			 		+ "    FROM  "
			 		+ "        tbl_payment "
			 		+ "    WHERE "
			 		+ "        PAY_STATUS = '결제 완료' "
			 		+ "    GROUP BY  "
			 		+ "        FK_USER_ID, PAY_STATUS "
			 		+ " ) ranked_data "
			 		+ " WHERE userid = ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
		
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Map<String, String> map = new HashMap<>();
			    map.put("userid", rs.getString("userid"));
			    map.put("myranking", rs.getString("myranking"));
			    myranking.add(map);
			}

		} finally {
			close();

		}

		return myranking;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	// 마이페이지 메인 리스트 = 나의 예매내역 -- 20분 전 확인불가
	@Override
	public List<PaymentVO> main_mypage_Myreservationlist(String userid) throws SQLException {
		List<PaymentVO> main_mypage_Myreservationlist = new ArrayList<>();

		try {
			conn = ds.getConnection();

			String sql = " SELECT * " + " FROM ( "
					+ "    SELECT ROW_NUMBER() OVER (ORDER BY p.PAY_SUCCESS_DATE DESC) AS RNO, "
					+ "    p.FK_USER_ID as userid, " + "    p.IMP_UID, "
					+ "    LISTAGG(t.SEAT_NO, ',') WITHIN GROUP (ORDER BY t.SEAT_NO) AS SEAT_NO_LIST,  "
					+ "    s.FK_SEQ_MOVIE_NO, " + "    m.POSTER_FILE, "
					+ "    case when length(movie_title) > 23 then substr(movie_title,1,20) || ' ...' else movie_title end as movie_title, "
					+ "    p.PAY_SUCCESS_DATE, "
					+ "    to_char(s.START_TIME, 'yyyy-mm-dd hh24:mi') as START_TIME, to_char(s.END_TIME, 'hh24:mi') as END_TIME, "
					+ "    p.PAY_AMOUNT, " + "    p.PAY_STATUS " + " FROM  tbl_payment p " + " JOIN  tbl_ticket t "
					+ " ON p.IMP_UID = t.FK_IMP_UID " + " JOIN tbl_showtime s "
					+ " ON s.SEQ_SHOWTIME_NO = p.FK_SEQ_SHOWTIME_NO " + " JOIN tbl_movie m "
					+ " ON s.FK_SEQ_MOVIE_NO = m.SEQ_MOVIE_NO "
					+ " WHERE p.FK_USER_ID = ? AND p.PAY_STATUS = '결제 완료' AND SYSDATE <= (s.START_TIME - INTERVAL '20' MINUTE) "
					+ " GROUP BY p.FK_USER_ID, p.IMP_UID, s.FK_SEQ_MOVIE_NO, m.POSTER_FILE, m.MOVIE_TITLE, p.PAY_SUCCESS_DATE, START_TIME, END_TIME, p.PAY_AMOUNT, p.PAY_STATUS "
					+ " ) " + " WHERE RNO BETWEEN 1 AND 2 ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				PaymentVO pvo = new PaymentVO();
				pvo.setFk_user_id(rs.getString("userid"));
				pvo.setImp_uid(rs.getString("imp_uid"));
				pvo.setPay_success_date(rs.getString("pay_success_date"));
				pvo.setPay_amount(rs.getInt("pay_amount"));
				pvo.setPay_status(rs.getString("pay_status"));

				ShowtimeVO svo = new ShowtimeVO();
				svo.setFk_seq_movie_no(rs.getInt("fk_seq_movie_no"));
				svo.setStart_time(rs.getString("start_time"));
				svo.setEnd_time(rs.getString("end_time"));

				MovieVO mvo = new MovieVO();
				mvo.setPoster_file(rs.getString("poster_file"));
				mvo.setMovie_title(rs.getString("movie_title"));
				svo.setMvo(mvo);

				TicketVO tvo = new TicketVO();
				tvo.setSeat_no_list(rs.getString("seat_no_list"));

				pvo.setSvo(svo);
				pvo.setTvo(tvo);

				main_mypage_Myreservationlist.add(pvo);

			}

		} finally {
			close();
		}

		return main_mypage_Myreservationlist;
	}

	// 마이페이지 메인 리스트 = 내가 본 영화 -- 마감되고 10분 후
	@Override
	public List<PaymentVO> main_mypage_MovieWatchedList(String userid) throws SQLException {
		List<PaymentVO> main_mypage_MovieWatchedList = new ArrayList<>();

		try {
			conn = ds.getConnection();

			String sql = " SELECT * " + " FROM ( "
					+ "    SELECT ROW_NUMBER() OVER (ORDER BY s.START_TIME DESC) AS RNO, "
					+ "    p.FK_USER_ID as userid, " + "    p.IMP_UID,    " + "    s.FK_SCREEN_NO, "
					+ "    LISTAGG(t.SEAT_NO, ',') WITHIN GROUP (ORDER BY t.SEAT_NO) AS SEAT_NO_LIST, "
					+ "    COUNT(t.SEAT_NO) AS SEAT_COUNT, " + "    s.FK_SEQ_MOVIE_NO, " + "    m.POSTER_FILE, "
					+ "    case when length(movie_title) > 23 then substr(movie_title,1,20) || ' ...' else movie_title end as movie_title, "
					+ "    to_char(s.START_TIME, 'yyyy-mm-dd hh24:mi') as START_TIME, "
					+ "    to_char(s.END_TIME, 'hh24:mi') as END_TIME, " + "    p.PAY_STATUS " + " FROM  tbl_payment p "
					+ " JOIN  tbl_ticket t " + " ON p.IMP_UID = t.FK_IMP_UID " + " JOIN tbl_showtime s "
					+ " ON s.SEQ_SHOWTIME_NO = p.FK_SEQ_SHOWTIME_NO " + " JOIN tbl_movie m "
					+ " ON s.FK_SEQ_MOVIE_NO = m.SEQ_MOVIE_NO "
					+ " WHERE p.FK_USER_ID = ? AND p.PAY_STATUS = '결제 완료'   AND SYSDATE >= (s.END_TIME + INTERVAL '10' MINUTE)  "
					+ " GROUP BY p.FK_USER_ID, p.IMP_UID,s.FK_SCREEN_NO, s.FK_SEQ_MOVIE_NO, m.POSTER_FILE, MOVIE_TITLE, START_TIME, END_TIME, p.PAY_STATUS "
					+ " )  " + " WHERE RNO BETWEEN 1 AND 2 ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				PaymentVO pvo = new PaymentVO();
				pvo.setFk_user_id(rs.getString("userid"));
				pvo.setImp_uid(rs.getString("imp_uid"));
				pvo.setPay_status(rs.getString("pay_status"));

				ShowtimeVO svo = new ShowtimeVO();
				svo.setFk_screen_no(rs.getInt("fk_screen_no"));
				svo.setFk_seq_movie_no(rs.getInt("fk_seq_movie_no"));
				svo.setStart_time(rs.getString("start_time"));
				svo.setEnd_time(rs.getString("end_time"));

				MovieVO mvo = new MovieVO();
				mvo.setPoster_file(rs.getString("poster_file"));
				mvo.setMovie_title(rs.getString("movie_title"));
				svo.setMvo(mvo);

				TicketVO tvo = new TicketVO();
				tvo.setSeat_no_list(rs.getString("seat_no_list"));
				tvo.setSeat_count(rs.getInt("seat_count"));

				pvo.setSvo(svo);
				pvo.setTvo(tvo);

				main_mypage_MovieWatchedList.add(pvo);
			}
		} finally {
			close();
		}

		return main_mypage_MovieWatchedList;
	}

	// 마이페이지 메인 리스트 = 내가 쓴 리뷰
	@Override
	public List<MovieReviewVO> main_mypage_MovieReviewList(String userid) throws SQLException {
		List<MovieReviewVO> main_mypage_MovieReviewList = new ArrayList<>();

		try {
			conn = ds.getConnection();

			String sql = " select POSTER_FILE, MOVIE_TITLE, SEQ_REVIEW_NO, FK_SEQ_MOVIE_NO, userid,MOVIE_RATING,REVIEW_CONTENT, to_char(REVIEW_WRITE_DATE, 'yyyy/mm/dd') as REVIEW_WRITE_DATE "
					+ " from " + " ( "
					+ " select ROW_NUMBER() OVER (ORDER BY SEQ_REVIEW_NO DESC) AS RNO, SEQ_REVIEW_NO,FK_SEQ_MOVIE_NO,FK_USER_ID as userid, "
					+ " MOVIE_RATING,REVIEW_CONTENT,REVIEW_WRITE_DATE, M.POSTER_FILE, case when length(movie_title) > 23 then substr(movie_title,1,20) || ' ...' else movie_title end as movie_title "
					+ " from TBL_REVIEW R " + " join TBL_MOVIE M " + " ON R.FK_SEQ_MOVIE_NO = M.SEQ_MOVIE_NO "
					+ " WHERE FK_USER_ID = ? " + " ) " + " WHERE RNO BETWEEN 1 AND 2 ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);

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

	// 마이페이지 메인 리스트 = 기대되는 영화
	@Override
	public List<MovieLikeVO> main_mypage_MovieLikeList(String userid) throws SQLException {
		List<MovieLikeVO> main_mypage_MovieLikeList = new ArrayList<>();

		try {
			conn = ds.getConnection();

			String sql = " SELECT FK_SEQ_MOVIE_NO, userid, POSTER_FILE, case when length(movie_title) > 23 then substr(movie_title,1,20) || ' ...' else movie_title end as movie_title, to_char(START_DATE, 'yyyy/mm/dd') as START_DATE, to_char(END_DATE, 'yyyy/mm/dd') as END_DATE "
					+ " FROM ( " + "    SELECT ROW_NUMBER() OVER (ORDER BY FK_SEQ_MOVIE_NO DESC) AS RNO, "
					+ "    FK_SEQ_MOVIE_NO, FK_USER_ID AS userid, M.POSTER_FILE, M.MOVIE_TITLE, M.START_DATE, M.END_DATE "
					+ "    FROM TBL_LIKE L " + "    JOIN TBL_MOVIE M " + "    ON L.FK_SEQ_MOVIE_NO = M.SEQ_MOVIE_NO "
					+ "    WHERE FK_USER_ID = ? " + " ) " + " WHERE RNO BETWEEN 1 AND 2 ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				MovieLikeVO mlvo = new MovieLikeVO();
				mlvo.setFK_USER_ID(rs.getString("userid"));
				mlvo.setFK_SEQ_MOVIE_NO(rs.getInt("FK_SEQ_MOVIE_NO"));

				MovieVO mvo = new MovieVO();
				mvo.setPoster_file(rs.getString("poster_file"));
				mvo.setMovie_title(rs.getString("movie_title"));
				mvo.setStart_date(rs.getString("start_date"));
				mvo.setEnd_date(rs.getString("end_date"));
				mlvo.setMvo(mvo);

				main_mypage_MovieLikeList.add(mlvo);
			}
		} finally {
			close();
		}

		return main_mypage_MovieLikeList;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////

	// 마이페이지 나의 예매내역 목록 전체 -- 20분 전 확인불가
	@Override
	public List<PaymentVO> myreservationList(String userid) throws SQLException {
		List<PaymentVO> myreservationList = new ArrayList<>();

		try {
			conn = ds.getConnection();

			String sql = " SELECT " + "    p.FK_USER_ID as userid, " + "    p.IMP_UID, " + "    s.FK_SCREEN_NO, "
					+ "    LISTAGG(t.SEAT_NO, ',') WITHIN GROUP (ORDER BY t.SEAT_NO) AS SEAT_NO_LIST, "
					+ "    COUNT(t.SEAT_NO) AS SEAT_COUNT, " + "    s.FK_SEQ_MOVIE_NO, " + "    m.POSTER_FILE, "
					+ "    m.MOVIE_TITLE, " + "    p.PAY_SUCCESS_DATE, "
					+ "    to_char(s.START_TIME, 'yyyy-mm-dd hh24:mi') as START_TIME, " + "    p.PAY_AMOUNT, "
					+ "    p.PAY_STATUS " + " FROM tbl_payment p " + " JOIN tbl_ticket t "
					+ " ON p.IMP_UID = t.FK_IMP_UID " + " JOIN tbl_showtime s "
					+ " ON s.SEQ_SHOWTIME_NO = p.FK_SEQ_SHOWTIME_NO " + " JOIN tbl_movie m "
					+ " ON s.FK_SEQ_MOVIE_NO = m.SEQ_MOVIE_NO "
					+ " WHERE p.FK_USER_ID = ? AND p.PAY_STATUS = '결제 완료' AND SYSDATE <= (s.START_TIME - INTERVAL '20' MINUTE) "
					+ " GROUP BY "
					+ "    p.FK_USER_ID, p.IMP_UID, s.FK_SCREEN_NO, s.FK_SEQ_MOVIE_NO,  m.POSTER_FILE, m.MOVIE_TITLE, "
					+ "    p.PAY_SUCCESS_DATE, START_TIME,  p.PAY_AMOUNT, p.PAY_STATUS "
					+ " ORDER BY p.PAY_SUCCESS_DATE DESC ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				PaymentVO pvo = new PaymentVO();
				pvo.setFk_user_id(rs.getString("userid"));
				pvo.setImp_uid(rs.getString("imp_uid"));
				pvo.setPay_success_date(rs.getString("pay_success_date"));
				pvo.setPay_amount(rs.getInt("pay_amount"));
				pvo.setPay_status(rs.getString("pay_status"));

				ShowtimeVO svo = new ShowtimeVO();
				svo.setFk_screen_no(rs.getInt("fk_screen_no"));
				svo.setFk_seq_movie_no(rs.getInt("fk_seq_movie_no"));
				svo.setStart_time(rs.getString("start_time"));

				MovieVO mvo = new MovieVO();
				mvo.setPoster_file(rs.getString("poster_file"));
				mvo.setMovie_title(rs.getString("movie_title"));
				svo.setMvo(mvo);

				TicketVO tvo = new TicketVO();
				tvo.setSeat_no_list(rs.getString("seat_no_list"));
				tvo.setSeat_count(rs.getInt("seat_count"));

				pvo.setSvo(svo);
				pvo.setTvo(tvo);

				myreservationList.add(pvo);
			}
		} finally {
			close();
		}

		return myreservationList;
	}

	// 마이페이지 나의 예매<취소>내역 목록 전체
	@Override
	public List<PaymentVO> myreservationList_cancel(String userid) throws SQLException {
		List<PaymentVO> myreservationList_cancel = new ArrayList<>();

		try {
			conn = ds.getConnection();

			String sql = "SELECT " + " p.FK_USER_ID as userid, " + "  p.IMP_UID, " + " m.SEQ_MOVIE_NO, "
					+ "   case when length(movie_title) > 23 then substr(movie_title,1,20) || ' ...' else movie_title end as movie_title,  "
					+ "    to_char(s.START_TIME, 'yyyy-mm-dd hh24:mi') as START_TIME, "
					+ "    to_char(p.PAY_CANCEL_DATE, 'yyyy-mm-dd') as PAY_CANCEL_DATE, " + "    p.PAY_AMOUNT, "
					+ "    p.PAY_STATUS " + " FROM tbl_payment p " + " JOIN tbl_showtime s "
					+ " ON s.SEQ_SHOWTIME_NO = p.FK_SEQ_SHOWTIME_NO " + " JOIN tbl_movie m "
					+ " ON s.FK_SEQ_MOVIE_NO = m.SEQ_MOVIE_NO " + " WHERE p.FK_USER_ID = ? AND p.PAY_STATUS = '결제 취소' "
					+ " GROUP BY " + "     p.FK_USER_ID, p.IMP_UID, m.SEQ_MOVIE_NO, m.MOVIE_TITLE,  "
					+ "  PAY_CANCEL_DATE, START_TIME,  p.PAY_AMOUNT, p.PAY_STATUS "
					+ " ORDER BY p.PAY_CANCEL_DATE DESC ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				PaymentVO pvo = new PaymentVO();
				pvo.setFk_user_id(rs.getString("userid"));
				pvo.setImp_uid(rs.getString("imp_uid"));
				pvo.setPay_cancel_date(rs.getString("pay_cancel_date"));
				pvo.setPay_amount(rs.getInt("pay_amount"));
				pvo.setPay_status(rs.getString("pay_status"));

				ShowtimeVO svo = new ShowtimeVO();
				svo.setStart_time(rs.getString("start_time"));
				pvo.setSvo(svo);

				MovieVO mvo = new MovieVO();
				mvo.setSeq_movie_no(rs.getInt("seq_movie_no"));
				mvo.setMovie_title(rs.getString("movie_title"));
				svo.setMvo(mvo);

				myreservationList_cancel.add(pvo);
			}
		} finally {
			close();
		}

		return myreservationList_cancel;
	}

	// 마이페이지 나의 예매내역 특정 보기(영수증 출력)
	@Override
	public List<PaymentVO> myreservationList_impUid(String imp_uid) throws SQLException {
		List<PaymentVO> myreservationList_impUid = new ArrayList<>();

		try {
			conn = ds.getConnection();

			String sql = " SELECT  "
					+ "    p.FK_USER_ID AS userid, p.IMP_UID, s.FK_SCREEN_NO,LISTAGG(t.SEAT_NO, ',') WITHIN GROUP (ORDER BY t.SEAT_NO) AS SEAT_NO_LIST,COUNT(t.SEAT_NO) AS SEAT_COUNT,s.FK_SEQ_MOVIE_NO, "
					+ "   m.POSTER_FILE, m.MOVIE_TITLE, TO_CHAR(s.START_TIME, 'yyyy-mm-dd hh24:mi') AS START_TIME, TO_CHAR(s.END_TIME, 'hh24:mi') AS END_TIME, p.PAY_STATUS, "
					+ "    RTRIM( "
					+ "        CASE WHEN SUM(CASE WHEN t.TICKET_AGE_GROUP = '성인' THEN 1 ELSE 0 END) > 0 "
					+ "             THEN '일반 ' || SUM(CASE WHEN t.TICKET_AGE_GROUP = '성인' THEN 1 ELSE 0 END) || ', ' ELSE '' END || "
					+ "        CASE WHEN SUM(CASE WHEN t.TICKET_AGE_GROUP = '청소년' THEN 1 ELSE 0 END) > 0 \r\n"
					+ "             THEN '청소년 ' || SUM(CASE WHEN t.TICKET_AGE_GROUP = '청소년' THEN 1 ELSE 0 END) || ', ' ELSE '' END || "
					+ "        CASE WHEN SUM(CASE WHEN t.TICKET_AGE_GROUP = '어린이' THEN 1 ELSE 0 END) > 0 \r\n"
					+ "             THEN '어린이 ' || SUM(CASE WHEN t.TICKET_AGE_GROUP = '어린이' THEN 1 ELSE 0 END) || ', ' ELSE '' END, "
					+ "        ', ' " + "    ) AS AGE_GROUP_COUNT_LIST " + " FROM  tbl_payment p "
					+ " JOIN tbl_ticket t ON p.IMP_UID = t.FK_IMP_UID "
					+ " JOIN tbl_showtime s ON s.SEQ_SHOWTIME_NO = p.FK_SEQ_SHOWTIME_NO "
					+ " JOIN tbl_movie m ON s.FK_SEQ_MOVIE_NO = m.SEQ_MOVIE_NO "
					+ " WHERE p.IMP_UID = ? AND p.PAY_STATUS = '결제 완료' AND SYSDATE <= (s.START_TIME - INTERVAL '20' MINUTE) "
					+ " GROUP BY p.FK_USER_ID,  p.IMP_UID,  s.FK_SCREEN_NO, s.FK_SEQ_MOVIE_NO, m.POSTER_FILE, m.MOVIE_TITLE, s.START_TIME, s.END_TIME, p.PAY_STATUS "
					+ " ORDER BY START_TIME DESC ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, imp_uid);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				PaymentVO pvo = new PaymentVO();
				pvo.setFk_user_id(rs.getString("userid"));
				pvo.setImp_uid(rs.getString("imp_uid"));
				pvo.setPay_status(rs.getString("pay_status"));

				ShowtimeVO svo = new ShowtimeVO();
				svo.setFk_screen_no(rs.getInt("fk_screen_no"));
				svo.setFk_seq_movie_no(rs.getInt("fk_seq_movie_no"));
				svo.setStart_time(rs.getString("start_time"));
				svo.setEnd_time(rs.getString("end_time"));

				MovieVO mvo = new MovieVO();
				mvo.setPoster_file(rs.getString("poster_file"));
				mvo.setMovie_title(rs.getString("movie_title"));
				svo.setMvo(mvo);

				TicketVO tvo = new TicketVO();
				tvo.setSeat_no_list(rs.getString("seat_no_list"));
				tvo.setSeat_count(rs.getInt("seat_count"));
				tvo.setAGE_GROUP_COUNT_LIST(rs.getString("AGE_GROUP_COUNT_LIST"));

				pvo.setSvo(svo);
				pvo.setTvo(tvo);

				myreservationList_impUid.add(pvo);

			}
		} finally {
			close();
		}

		return myreservationList_impUid;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////

	//마이페이지 포인트 적립/사용내역 합계 목록
	@Override
	public List<Map<String, Object>> myreservationpoint(String userid) throws SQLException {
		 List<Map<String, Object>> myreservationpoint = new ArrayList<>();
		    
		    try {
		        conn = ds.getConnection();
		        
		        String sql = " SELECT fk_user_id as userid, " +
		                     " sum(case when point_type = 1 then point else 0 end) as total_earned, " +
		                     " sum(case when point_type = 0 then point else 0 end) as total_deducted, " +
		                     " sum(case when point_type = 1 then point else 0 end) -  " +
		                     "  sum(case when point_type = 0 then point else 0 end) as total_points " +
		                     " FROM tbl_point " +
		                     " WHERE FK_USER_ID = ? " +
		                     " GROUP BY FK_USER_ID ";
		        
		        pstmt = conn.prepareStatement(sql);
		        pstmt.setString(1, userid);
		        
		        rs = pstmt.executeQuery();
		        
		        while (rs.next()) {
		            Map<String, Object> paraMap = new HashMap<>();
		            paraMap.put("userid", rs.getString("userid"));
		            paraMap.put("total_earned", rs.getInt("total_earned"));
		            paraMap.put("total_deducted", rs.getInt("total_deducted"));
		            paraMap.put("total_points", rs.getInt("total_points"));
		            myreservationpoint.add(paraMap);
		        }
		        
		    } finally {
		        close();
		    }
		    
		    return myreservationpoint;
	}
	
	
	//마이페이지 포인트 적립/사용내역 목록 리스트
		@Override
		public List<PointVO> myreservationpointList(String userid) throws SQLException {
			List<PointVO> myreservationpointList = new ArrayList<>();
			
			try {
				conn = ds.getConnection();

				String sql = " SELECT "
						+ "    SEQ_POINT_NO, "
						+ "    FK_USER_ID as userid, "
						+ "    FK_IMP_UID, "
						+ "     POINT_TYPE, "
						+ "    POINT, "
						+ "     TO_CHAR(POINT_DATE,'yyyy-mm-dd') AS POINT_DATE "
						+ " FROM tbl_point "
						+ " WHERE FK_USER_ID = ? ";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userid);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					PointVO povo = new PointVO();
					povo.setFk_user_id(rs.getString("userid"));
					povo.setSeq_point_no(rs.getInt("seq_point_no"));
					povo.setPoint(rs.getInt("point"));
					povo.setPoint_type(rs.getInt("point_type"));
					povo.setPoint_date(rs.getString("point_date"));
					
					myreservationpointList.add(povo);
				}
			} finally {
				close();
			}	
			return myreservationpointList;
		}


	/////////////////////////////////////////////////////////////////////////////////////////////////////////	
	// 마이페이지 내가 본 영화 목록 전체 -- 마감되고 10분 후
	@Override
	public List<PaymentVO> mymoviewatchedList(String userid) throws SQLException {
		List<PaymentVO> mymoviewatchedList = new ArrayList<>();

		try {
			conn = ds.getConnection();

			String sql = " SELECT " + "    p.FK_USER_ID as userid, " + "    p.IMP_UID,   " + "    s.FK_SCREEN_NO, "
					+ "    LISTAGG(t.SEAT_NO, ',') WITHIN GROUP (ORDER BY t.SEAT_NO) AS SEAT_NO_LIST, "
					+ "    COUNT(t.SEAT_NO) AS SEAT_COUNT, " + "    s.FK_SEQ_MOVIE_NO, " + "    m.POSTER_FILE, "
					+ "    m.MOVIE_TITLE, " + " 	to_char(s.START_TIME, 'yyyy-mm-dd hh24:mi') as START_TIME, "
					+ "    to_char(s.END_TIME, 'hh24:mi') as END_TIME, " + "    p.PAY_STATUS " + " FROM  tbl_payment p "
					+ " JOIN  tbl_ticket t " + " ON p.IMP_UID = t.FK_IMP_UID " + " JOIN tbl_showtime s "
					+ " ON s.SEQ_SHOWTIME_NO = p.FK_SEQ_SHOWTIME_NO " + " JOIN tbl_movie m "
					+ " ON s.FK_SEQ_MOVIE_NO = m.SEQ_MOVIE_NO "
					+ " WHERE p.FK_USER_ID = ? AND p.PAY_STATUS = '결제 완료'   AND SYSDATE >= (s.END_TIME + INTERVAL '10' MINUTE) "
					+ " GROUP BY p.FK_USER_ID, p.IMP_UID,s.FK_SCREEN_NO, s.FK_SEQ_MOVIE_NO, m.POSTER_FILE, m.MOVIE_TITLE, START_TIME, END_TIME, p.PAY_STATUS "
					+ " ORDER BY  START_TIME DESC ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				PaymentVO pvo = new PaymentVO();
				pvo.setFk_user_id(rs.getString("userid"));
				pvo.setImp_uid(rs.getString("imp_uid"));
				pvo.setPay_status(rs.getString("pay_status"));

				ShowtimeVO svo = new ShowtimeVO();
				svo.setFk_screen_no(rs.getInt("fk_screen_no"));
				svo.setFk_seq_movie_no(rs.getInt("fk_seq_movie_no"));
				svo.setStart_time(rs.getString("start_time"));
				svo.setEnd_time(rs.getString("end_time"));

				MovieVO mvo = new MovieVO();
				mvo.setPoster_file(rs.getString("poster_file"));
				mvo.setMovie_title(rs.getString("movie_title"));
				svo.setMvo(mvo);

				TicketVO tvo = new TicketVO();
				tvo.setSeat_no_list(rs.getString("seat_no_list"));
				tvo.setSeat_count(rs.getInt("seat_count"));

				pvo.setSvo(svo);
				pvo.setTvo(tvo);

				mymoviewatchedList.add(pvo);
			}
		} finally {
			close();
		}

		return mymoviewatchedList;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////

	// 마이페이지 내가 쓴 평점 목록 전체 합계
	@Override
	public int totalmymoviereview(String userid) throws SQLException {
		int totalmymoviereview = 0;

		try {
			conn = ds.getConnection();

			String sql = " select count(*) " + " from TBL_REVIEW " + " where fk_user_id = ? ";

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
	public List<MovieReviewVO> mymoviereviewList(String userid) throws SQLException {
		List<MovieReviewVO> mymoviereviewList = new ArrayList<>();

		try {
			conn = ds.getConnection();

			String sql = " select POSTER_FILE, MOVIE_TITLE, SEQ_REVIEW_NO,userid,FK_SEQ_MOVIE_NO,MOVIE_RATING,REVIEW_CONTENT,to_char(REVIEW_WRITE_DATE, 'yyyy/mm/dd') as REVIEW_WRITE_DATE "
					+ " from " + " ( "
					+ " select SEQ_REVIEW_NO,FK_SEQ_MOVIE_NO,FK_USER_ID as userid,MOVIE_RATING,REVIEW_CONTENT,REVIEW_WRITE_DATE, M.POSTER_FILE, M.MOVIE_TITLE "
					+ " from TBL_REVIEW R " + " join TBL_MOVIE M " + " ON R.FK_SEQ_MOVIE_NO = M.SEQ_MOVIE_NO "
					+ " WHERE FK_USER_ID = ? " + " ) " + " order by SEQ_REVIEW_NO desc ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);

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

	// 마이페이지 내가 쓴 평점 특정 수정하기
	@Override
	public int mymoivereviewUpdate(MovieReviewVO mrvo) throws SQLException {
		int result = 0;

		try {
			conn = ds.getConnection();

			String sql = " update TBL_REVIEW set MOVIE_RATING = ?, REVIEW_CONTENT =?, REVIEW_WRITE_DATE = sysdate "
					+ " where FK_USER_ID=? and seq_review_no = ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mrvo.getMovie_rating());
			pstmt.setString(2, mrvo.getReview_content());
			pstmt.setString(3, mrvo.getFk_user_id());
			pstmt.setInt(4, mrvo.getSeq_review_no());

			result = pstmt.executeUpdate();

		} finally {
			close();
		}
		return result;
	}

	// 마이페이지 내가 쓴 평점 특정 삭제하기
	@Override
	public int mymoviereviewDelete(String seq_review_no) throws SQLException {
		int n = 0;

		try {
			conn = ds.getConnection();

			String sql = " delete from TBL_REVIEW where SEQ_REVIEW_NO = to_number(?) ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, seq_review_no);

			n = pstmt.executeUpdate();

		} finally {
			close();
		}

		return n;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////

	// 마이페이지 기대되는 영화 좋아요 목록 전체 합계
	@Override
	public int totalmymovielike(String userid) throws SQLException {
		int totalmymovielike = 0;

		try {
			conn = ds.getConnection();

			String sql = " select count(*) " + " from TBL_LIKE " + " where fk_user_id = ? ";

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

	// 마이페이지 기대되는 영화 좋아요 목록 리스트
	@Override
	public List<MovieLikeVO> selectBymymovielike(Map<String, String> paraMap) throws SQLException {
		List<MovieLikeVO> mymovielikeList = new ArrayList<>();

		try {
			conn = ds.getConnection();

			String sql = " SELECT FK_SEQ_MOVIE_NO, userid, POSTER_FILE, MOVIE_TITLE,to_char(START_DATE, 'yyyy/mm/dd') START_DATE, to_char(END_DATE, 'yyyy/mm/dd') as END_DATE "
					+ " FROM  " + " ( " + "    SELECT row_number() over(order by FK_SEQ_MOVIE_NO desc) AS RNO "
					+ "         , FK_SEQ_MOVIE_NO, FK_USER_ID as userid, M.POSTER_FILE, M.MOVIE_TITLE, M.START_DATE, M.END_DATE "
					+ "    FROM TBL_LIKE L " + "    JOIN TBL_MOVIE M " + "    ON L.FK_SEQ_MOVIE_NO = M.SEQ_MOVIE_NO "
					+ "    WHERE FK_USER_ID = ? " + " ) V " + " WHERE rno between ? and ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paraMap.get("userid"));
			pstmt.setString(2, paraMap.get("start"));
			pstmt.setString(3, paraMap.get("end"));

			rs = pstmt.executeQuery();

			while (rs.next()) {
				MovieLikeVO mlvo = new MovieLikeVO();
				mlvo.setFK_USER_ID(rs.getString("userid"));
				mlvo.setFK_SEQ_MOVIE_NO(rs.getInt("FK_SEQ_MOVIE_NO"));

				MovieVO mvo = new MovieVO();
				mvo.setPoster_file(rs.getString("poster_file"));
				mvo.setMovie_title(rs.getString("movie_title"));
				mvo.setStart_date(rs.getString("start_date"));
				mvo.setEnd_date(rs.getString("end_date"));
				mlvo.setMvo(mvo);

				mymovielikeList.add(mlvo);
			}
		} finally {
			close();
		}

		return mymovielikeList;
	}

	// 마이페이지 기대되는 영화 좋아요 특정 삭제하기
	@Override
	public int movielikeDelete(String fK_SEQ_MOVIE_NO) throws SQLException {
		int n = 0;

		try {
			conn = ds.getConnection();

			String sql = " delete from TBL_LIKE where fK_SEQ_MOVIE_NO = to_number(?) ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, fK_SEQ_MOVIE_NO);

			n = pstmt.executeUpdate();

		} finally {
			close();
		}

		return n;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////

	// 비밀번호 일치하는지 검증
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

	// 회원정보수정- 이메일 중복검사
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

	// 회원정보수정-이름,이메일,휴대전화만 변경가능
	@Override
	public int updateMember(MemberVO member) throws SQLException {
		int result = 0;

		try {
			conn = ds.getConnection();

			String sql = " update tbl_member set name = ? " + " , email = ? " + " , mobile = ? "
					+ " where user_id = ? ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, member.getName());
			pstmt.setString(2, aes.encrypt(member.getEmail())); // 이메일을 AES256 알고리즘으로 양방향 암호화 시킨다.
			pstmt.setString(3, aes.encrypt(member.getMobile())); // 휴대폰번호를 AES256 알고리즘으로 양방향 암호화 시킨다.
			pstmt.setString(4, member.getUserid());

			result = pstmt.executeUpdate();

		} catch (GeneralSecurityException | UnsupportedEncodingException e) {
		} finally {
			close();
		}

		return result;
	}

	// 비밀번호 변경
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

	// 마이페이지 회원정보수정에서 비밀번호 변경 날짜 가져오기
	@Override
	public MemberVO Mylastpwdchangedate(Map<String, String> paraMap) throws SQLException {
	    MemberVO mvo = null;

	    try {
	        conn = ds.getConnection();

	        String sql = " SELECT PWD_CHANGE_DATE as lastpwdchangedate " +
	                     " FROM tbl_member WHERE USER_ID = ? ";

	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, paraMap.get("userid"));

	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            mvo = new MemberVO();
	            mvo.setLastpwdchangedate(rs.getString("lastpwdchangedate"));
	        }
	    } finally {
	        close();
	    }

	    return mvo;  // MemberVO 객체 하나를 반환
	}


	// 회원탈퇴
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


	
	
}// end of public class MypageDAO_imple implements MypageDAO-----
