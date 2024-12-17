package notice.model;

import java.sql.SQLException;
import java.util.List;

import notice.domain.NoticeDTO;

public interface NoticeDAO {
	
	// 공지사항 목록보여주는 함수
	List<NoticeDTO> selectNotice() throws SQLException;

	// 특정 공지사항 상세 보여주는 함수
	NoticeDTO detailNotice(int seq_notice_no) throws SQLException;
	
	// 공지 글작성해주는 함수
	int insertNotice(NoticeDTO notice) throws SQLException;
}
