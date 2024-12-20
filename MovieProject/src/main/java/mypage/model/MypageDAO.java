package mypage.model;

import java.sql.SQLException;
import java.util.Map;

public interface MypageDAO {

	//회원수정 비밀번호 체크
	boolean checkPassword(Map<String, String> paraMap) throws SQLException;

}
