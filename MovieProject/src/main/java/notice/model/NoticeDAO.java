package notice.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import notice.domain.NoticeDTO;

public interface NoticeDAO {
	
	// 공지사항 목록보여주는 함수
	List<NoticeDTO> selectNotice(Map<String, String> paraMap) throws SQLException;

	// 특정 공지사항 상세 보여주는 함수
	NoticeDTO detailNotice(int seq_notice_no) throws SQLException;
	
	// 공지 글작성해주는 함수
	int insertNotice(NoticeDTO notice) throws SQLException;
	
	// 공지 수정해주는 함수
	int editNotice(NoticeDTO ndto) throws SQLException;
	
	// 공지 삭제해주는 함수
	int deleteNotice(int seq_notice_no) throws SQLException;
	
	// 페이징 처리를 위한 총페이지수 알아오기
	int getTotalPage(Map<String, String> paraMap) throws SQLException;

	/* >>> 뷰단에서 "페이징 처리시 보여주는 순번 공식" 에서 사용하기 위해 공지사항의 총개수 알아오기 <<< */
	int getTotalNoticeCount(Map<String, String> paraMap) throws SQLException;



	
	
	
}
