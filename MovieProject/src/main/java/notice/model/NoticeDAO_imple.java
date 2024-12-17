package notice.model;

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
	public List<NoticeDTO> selectNotice() throws SQLException {
	    List<NoticeDTO> NoticeList = new ArrayList<>();
	    
	    try {
	        conn = ds.getConnection();
	        
	        String sql = "SELECT seq_notice_no, notice_subject, notice_write_date, views "
	                   + "FROM TBL_NOTICE "
	                   + "ORDER BY seq_notice_no";
	        
	        pstmt = conn.prepareStatement(sql);
	        
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
	        
	        // 공지사항 번호(seq_notice_no)를 기준으로 상세 정보를 조회
	        String sql = "SELECT seq_notice_no, notice_subject, notice_content, notice_write_date, views "
	                   + "FROM TBL_NOTICE "
	                   + "WHERE seq_notice_no = ?";
	        
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
	        String sql = "INSERT INTO TBL_NOTICE (SEQ_NOTICE_NO, notice_subject, notice_content, notice_write_date, views) "
	                   + "VALUES (SEQ_NOTICE_NO.nextval, ?, ?, SYSDATE, 0)";

	        pstmt = conn.prepareStatement(sql);
	        
	        pstmt.setString(1, notice.getNotice_subject());
	        pstmt.setString(2, notice.getNotice_content());

	        result = pstmt.executeUpdate(); // 성공 시 1, 실패 시 0 반환
	    } finally {
	        close();
	    }
	    return result;
	}

	
}

