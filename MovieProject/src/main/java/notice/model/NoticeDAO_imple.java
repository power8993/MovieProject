package notice.model;

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

import notice.domain.NoticeDTO;


public class NoticeDAO_imple implements NoticeDAO {

	private DataSource ds;
	private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    
	// 생성자
	public NoticeDAO_imple() {
 		
 		try {
 			Context initContext = new InitialContext();
 		    Context envContext  = (Context)initContext.lookup("java:/comp/env");
 		    ds = (DataSource)envContext.lookup("jdbc/semioracle"); 		     	
 		    
 		} catch(NamingException e) {
 			e.printStackTrace();
 		}
 	}
    
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
	}// end of private void close()---------------
    
	// 공지사항글 전체 조회 함수
	@Override
	public List<NoticeDTO> selectNotice(Map<String, String> paraMap) throws SQLException {
	    List<NoticeDTO> NoticeList = new ArrayList<>();
	    
	    try {
	        conn = ds.getConnection();
	                	        	        
	        String sql = " SELECT RNO, seq_notice_no, notice_subject, notice_write_date, views "
	        		   + " FROM  (SELECT rownum AS RNO, seq_notice_no, notice_subject, notice_write_date, views "
	        		   + "             FROM (select seq_notice_no, notice_subject, notice_write_date, views "
	        		   + "			from TBL_NOTICE "
	        		   + "			order by seq_notice_no desc) V ) T "
	        		   + "WHERE T.RNO BETWEEN ? AND ? ";
	        
	        pstmt = conn.prepareStatement(sql);
	        
	        int currentShowPageNo = Integer.parseInt(paraMap.get("currentShowPageNo"));
			int sizePerPage = Integer.parseInt(paraMap.get("sizePerPage"));
	        
			pstmt.setInt(1, (currentShowPageNo * sizePerPage) - (sizePerPage -1));
			pstmt.setInt(2, (currentShowPageNo * sizePerPage));
			
	        rs = pstmt.executeQuery();
	        
	        while(rs.next()) {
	            NoticeDTO ndto = new NoticeDTO();
	            ndto.setSeq_notice_no(rs.getInt("seq_notice_no"));
	            ndto.setNotice_subject(rs.getString("notice_subject"));
	            ndto.setNotice_wtite_date(rs.getDate("notice_write_date"));  // Date로 변환
	            ndto.setViews(rs.getInt("views"));
	            
	            NoticeList.add(ndto);
	        }
	    } finally {
	        close();
	    }
	    return NoticeList;
	}

	// 공지사항 상세보기 페이지 실행 함수
	@Override
	public NoticeDTO detailNotice(int seq_notice_no) throws SQLException {
	    NoticeDTO ndto = null;
	    
	    try {
	        conn = ds.getConnection();
	        
	        // 조회수 증가 쿼리
	        String sql = "UPDATE TBL_NOTICE SET views = views + 1 WHERE seq_notice_no = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, seq_notice_no); // seq_notice_no 파라미터 설정
	        pstmt.executeUpdate(); // 조회수 증가 실행
	        
	        // 공지사항 번호(seq_notice_no)를 기준으로 상세 정보를 조회
	        sql = " SELECT seq_notice_no, notice_subject, notice_content, notice_write_date, views "
	                   + " FROM TBL_NOTICE "
	                   + " WHERE seq_notice_no = ? ";
	        
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, seq_notice_no); // seq_notice_no 파라미터 설정
	        
	        rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            ndto = new NoticeDTO();
	            ndto.setSeq_notice_no(rs.getInt("seq_notice_no"));
	            ndto.setNotice_subject(rs.getString("notice_subject"));
	            ndto.setNotice_content(rs.getString("notice_content")); // 글내용 추가
	            ndto.setNotice_wtite_date(rs.getDate("notice_write_date"));
	            ndto.setViews(rs.getInt("views"));
	        }
	    } finally {
	        close();
	    }
	    return ndto;
	}

	@Override
	public int insertNotice(NoticeDTO notice) throws SQLException {
	    int result = 0;
	    try {
	        conn = ds.getConnection();

	        // SEQ_NOTICE_NO는 자동으로 생성되므로 삽입하지 않음
	        String sql = " INSERT INTO TBL_NOTICE (SEQ_NOTICE_NO, notice_subject, notice_content, notice_write_date, views) "
	                   + " VALUES (SEQ_NOTICE_NO.nextval, ?, ?, SYSDATE, 0) ";

	        pstmt = conn.prepareStatement(sql);
	        
	        pstmt.setString(1, notice.getNotice_subject());
	        pstmt.setString(2, notice.getNotice_content());

	        result = pstmt.executeUpdate(); // 성공 시 1, 실패 시 0 반환
	    } finally {
	        close();
	    }
	    return result;
	}
	
	// 공지사항 수정하기
	@Override
	public int editNotice(NoticeDTO ndto) throws SQLException {
		int result = 0;
	    try {
	        conn = ds.getConnection();

	        // SEQ_NOTICE_NO는 자동으로 생성되므로 삽입하지 않음
	        String sql = " update TBL_NOTICE set notice_subject = ? , notice_content = ? , notice_update_date = sysdate where SEQ_NOTICE_NO = ? ";

	        pstmt = conn.prepareStatement(sql);
	        
	        pstmt.setString(1, ndto.getNotice_subject());
	        pstmt.setString(2, ndto.getNotice_content());
	        pstmt.setInt(3, ndto.getSeq_notice_no());

	        result = pstmt.executeUpdate(); // 성공 시 1, 실패 시 0 반환
	    } finally {
	        close();
	    }
	    return result;
	}

	// 공지 삭제해주는 함수
	@Override
	public int deleteNotice(int seq_notice_no) throws SQLException {

		int result = 0;

		try {
			String sql = " delete from tbl_notice where seq_notice_no = ? ";

			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, seq_notice_no);
			result = pstmt.executeUpdate();

		} finally {
			close();
		}
		return result;
	}

	@Override
	public int getTotalPage(Map<String, String> paraMap) throws SQLException {
		
		int totalPage = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql = " select ceil(count(*)/?) "
					   + " from tbl_notice ";
					   										
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, Integer.parseInt(paraMap.get("sizePerPage")));
			
			rs = pstmt.executeQuery();
			
			rs.next();
			
			totalPage = rs.getInt(1); // 몇번째 컬럼인지 알려줘야한다 지금은 ceil(count(*)/?)여기이다.
			
		} finally {
			close();
		}		
		return totalPage;
	}

	@Override
	public int getTotalNoticeCount(Map<String, String> paraMap) throws SQLException {
		int totalNoticeCount = 0;

		try {
			conn = ds.getConnection();

			String sql = " select count(*) " 
			           + " from tbl_notice ";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();

			rs.next();

			totalNoticeCount = rs.getInt(1); // 몇번째 컬럼인지 알려줘야한다 지금은 ceil(count(*)/?)여기이다.

		} finally {
			close();
		}

		return totalNoticeCount;
	}
    
	

	
	
}

