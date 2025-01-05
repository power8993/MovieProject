package common.model;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import movie.domain.MovieVO_sangwoo;
import movie.domain.ShowtimeVO;
import notice.domain.NoticeDTO;
import util.security.AES256;
import util.security.SecretMyKey;

public class indexDAO_imple implements indexDAO{
	
	private DataSource ds;	// DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다.
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private AES256 aes;

	// 생성자
	public indexDAO_imple() {
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

	
	

	// 공지사항 최신글 3개 조회하기
	@Override
	public List<NoticeDTO> NoticeSelectTopThree() throws SQLException {
		
		List<NoticeDTO> noticeList = new ArrayList<>();

		try {
			conn = ds.getConnection();

			String sql = " SELECT "
					+ "    SEQ_NOTICE_NO, "
					+ "    CASE "
					+ "        WHEN LENGTH(NOTICE_SUBJECT) > 26 THEN "
					+ "            CASE "
					+ "                WHEN SUBSTR(NOTICE_SUBJECT, 26, 1) = ' ' THEN SUBSTR(TRIM(NOTICE_SUBJECT), 1, 25) || '···'\n"
					+ "                ELSE SUBSTR(TRIM(NOTICE_SUBJECT), 1, 26) || '···'\n"
					+ "            END "
					+ "        ELSE TRIM(NOTICE_SUBJECT) "
					+ "    END AS NOTICE_SUBJECT, "
					+ "    NOTICE_WRITE_DATE "
					+ " FROM ( "
					+ "    SELECT SEQ_NOTICE_NO, NOTICE_SUBJECT, NOTICE_WRITE_DATE "
					+ "    FROM TBL_NOTICE "
					+ "    ORDER BY SEQ_NOTICE_NO DESC "
					+ " ) "
					+ " WHERE ROWNUM <= 3 ";

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // 날짜 포맷 지정

			while (rs.next()) {

				NoticeDTO noticeDTO = new NoticeDTO();
				noticeDTO.setSeq_notice_no(rs.getInt("SEQ_NOTICE_NO"));
				noticeDTO.setNotice_subject(rs.getString("NOTICE_SUBJECT"));
				noticeDTO.setNotice_wtite_date(rs.getDate("NOTICE_WRITE_DATE")); // 꺼낼 때 문자열로 변환
				/*
				 * imgvo.setImgno(rs.getInt("imgno"));
				 * imgvo.setImgname(rs.getString("imgname"));
				 * imgvo.setImgfilename(rs.getString("imgfilename"));
				 */
				noticeList.add(noticeDTO);
			} // end of while------------------

		} finally {
			close();
		}

		return noticeList;

	}

	//무비차트 예매율 기준 상위 5개 조회하기.
	@Override
	public List<MovieVO_sangwoo> showMovieChart() throws SQLException {
		
		List<MovieVO_sangwoo> movieShowList = new ArrayList<>();
		
		
		try {
			conn = ds.getConnection();

			String sql = " SELECT SEQ_SHOWTIME_NO, SEQ_MOVIE_NO, MOVIE_TITLE, POSTER_FILE "
					+ " FROM ( "
					+ "    SELECT DISTINCT S.SEQ_SHOWTIME_NO, "
					+ "           M.SEQ_MOVIE_NO, "
					+ "           M.MOVIE_TITLE, "
					+ "           M.POSTER_FILE, "
					+ "           COUNT(P.IMP_UID) AS BOOKING_COUNT, "
					+ "           ROW_NUMBER() OVER (ORDER BY COUNT(P.IMP_UID) DESC) AS RN "
					+ "    FROM TBL_MOVIE M "
					+ "    JOIN TBL_SHOWTIME S "
					+ "      ON M.SEQ_MOVIE_NO = S.FK_SEQ_MOVIE_NO "
					+ "    LEFT JOIN TBL_PAYMENT P "
					+ "      ON S.SEQ_SHOWTIME_NO = P.FK_SEQ_SHOWTIME_NO "
					+ "    GROUP BY S.SEQ_SHOWTIME_NO, M.SEQ_MOVIE_NO, M.MOVIE_TITLE, M.POSTER_FILE "
					+ " ) "
					+ " WHERE RN > 0 AND RN <= 5 ";

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				MovieVO_sangwoo MovieVO = new MovieVO_sangwoo();
				MovieVO.setSeq_movie_no(rs.getInt("seq_movie_no"));
				MovieVO.setMovie_title(rs.getString("MOVIE_TITLE"));
				MovieVO.setPoster_file(rs.getString("POSTER_FILE"));
				
				ShowtimeVO ShowtimeVO = new ShowtimeVO();
				ShowtimeVO.setSeq_showtime_no(rs.getInt("SEQ_SHOWTIME_NO")); //ShowtimeVO에 '상영중인영화번호'를 삽입
				MovieVO.setShowvo(ShowtimeVO); //MovieVO의 setShowvo에 ShowtimeVO를 삽입
				
				movieShowList.add(MovieVO);
				
			} // end of while------------------

		} finally {
			close();
		}
		
		return movieShowList;
	}
	
	//무비차트 예매율 기준 상위 5개 이후 5개 조회하기.
	@Override
	public List<MovieVO_sangwoo> showMovieChart2() throws SQLException {
		
		List<MovieVO_sangwoo> movieShowList2 = new ArrayList<>();
		
		
		try {
			conn = ds.getConnection();

			String sql = " SELECT SEQ_SHOWTIME_NO, SEQ_MOVIE_NO, MOVIE_TITLE, POSTER_FILE "
					+ " FROM (\n"
					+ "    SELECT DISTINCT S.SEQ_SHOWTIME_NO, "
					+ "           M.SEQ_MOVIE_NO, "
					+ "           M.MOVIE_TITLE, "
					+ "           M.POSTER_FILE, "
					+ "           COUNT(P.IMP_UID) AS BOOKING_COUNT, "
					+ "           ROW_NUMBER() OVER (ORDER BY COUNT(P.IMP_UID) DESC) AS RN "
					+ "    FROM TBL_MOVIE M "
					+ "    JOIN TBL_SHOWTIME S "
					+ "      ON M.SEQ_MOVIE_NO = S.FK_SEQ_MOVIE_NO "
					+ "    LEFT JOIN TBL_PAYMENT P "
					+ "      ON S.SEQ_SHOWTIME_NO = P.FK_SEQ_SHOWTIME_NO "
					+ "    GROUP BY S.SEQ_SHOWTIME_NO, M.SEQ_MOVIE_NO, M.MOVIE_TITLE, M.POSTER_FILE "
					+ " )  "
					+ " WHERE RN > 5 AND RN <= 10 ";

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				MovieVO_sangwoo MovieVO2 = new MovieVO_sangwoo();
				MovieVO2.setSeq_movie_no(rs.getInt("seq_movie_no"));
				MovieVO2.setMovie_title(rs.getString("MOVIE_TITLE"));
				MovieVO2.setPoster_file(rs.getString("POSTER_FILE"));
				
				ShowtimeVO ShowtimeVO2 = new ShowtimeVO();
				ShowtimeVO2.setSeq_showtime_no(rs.getInt("SEQ_SHOWTIME_NO")); //ShowtimeVO에 '상영중인영화번호'를 삽입
				MovieVO2.setShowvo(ShowtimeVO2); //MovieVO의 setShowvo에 ShowtimeVO를 삽입
				
				movieShowList2.add(MovieVO2);
				
			} // end of while------------------

		} finally {
			close();
		}
		
		return movieShowList2;
	}

	
	//상영예정작 현재날짜에 가까운 영화개봉일 기준 상위 5개 초회(카드의 첫화면)
	@Override
	public List<MovieVO_sangwoo> showLaterMovies() throws SQLException {
		
		List<MovieVO_sangwoo> laterMoviesList = new ArrayList<>();
		
		
		try {
			conn = ds.getConnection();

			String sql = " select  seq_movie_no, MOVIE_TITLE, POSTER_FILE from TBL_MOVIE "
					+ " where start_date>sysdate "
					+ " and ROWNUM<=5 "
					+ " order by start_date ";

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				MovieVO_sangwoo MovieVO = new MovieVO_sangwoo();
				MovieVO.setSeq_movie_no(rs.getInt("seq_movie_no"));
				MovieVO.setMovie_title(rs.getString("MOVIE_TITLE"));
				MovieVO.setPoster_file(rs.getString("POSTER_FILE"));
				
				laterMoviesList.add(MovieVO);
				
			} // end of while------------------

		} finally {
			close();
		}
		
		return laterMoviesList;
	}

	//상영예정작 현재날짜에 가까운 영화개봉일 기준 상위 5개 이후 5개 초회(카드의 첫화면)
	@Override
	public List<MovieVO_sangwoo> showLaterMovies2() throws SQLException {

		List<MovieVO_sangwoo> laterMoviesList2 = new ArrayList<>();
		
		
		try {
			conn = ds.getConnection();

			String sql = " SELECT seq_movie_no, MOVIE_TITLE, POSTER_FILE "
					+ " FROM ( "
					+ "    SELECT seq_movie_no, MOVIE_TITLE, POSTER_FILE, ROWNUM AS RN "
					+ "    FROM TBL_MOVIE "
					+ "    WHERE start_date > SYSDATE "
					+ "    ORDER BY start_date "
					+ " ) "
					+ " WHERE RN >= 6 and RN <= 10 ";

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				MovieVO_sangwoo MovieVO = new MovieVO_sangwoo();
				MovieVO.setSeq_movie_no(rs.getInt("seq_movie_no"));
				MovieVO.setMovie_title(rs.getString("MOVIE_TITLE"));
				MovieVO.setPoster_file(rs.getString("POSTER_FILE"));
				
				laterMoviesList2.add(MovieVO);
				
			} // end of while------------------

		} finally {
			close();
		}
		
		return laterMoviesList2;
	}

}
