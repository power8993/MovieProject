package mypage.model;

import java.sql.SQLException;
import java.util.Map;

import member.domain.MemberVO;

public interface MypageDAO {

	//회원수정 비밀번호 체크
	boolean checkPassword(Map<String, String> paraMap) throws SQLException;

	//회원정보수정 이메일 체크
	boolean emailDuplicateCheck2(Map<String, String> paraMap)throws SQLException;

	//회원정보수정
	int updateMember(MemberVO member)throws SQLException;

	//회원정보수정 비밀번호 변경
	int mypwdUdate(Map<String, String> paraMap)throws SQLException;

}
