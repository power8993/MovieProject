package common.model;

import java.sql.SQLException;
import java.util.List;

import notice.domain.NoticeDTO;

public interface indexDAO {

	// 공지사항 최신글 3개 조회하기
	List<NoticeDTO> NoticeSelectTopThree() throws SQLException;

}
