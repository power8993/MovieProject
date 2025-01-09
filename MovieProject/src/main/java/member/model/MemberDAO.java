package member.model;

import java.sql.SQLException;
import java.util.Map;

import member.domain.MemberVO;

public interface MemberDAO {
	
	// 회원가입을 해주는 메소드(tbl_member 테이블에 insert)
	int registerMember(MemberVO memeber) throws SQLException;

	// ID 중복검사 (tbl_member 테이블에서 userid 가 존재하면 true 를 리턴해주고, userid 가 존재하지 않으면 false 를 리턴한다)
	boolean idDuplicateCheck(String userid) throws SQLException;

	// ID 중복검사 (tbl_member 테이블에서 email 이 존재하면 true 를 리턴해주고, email 이 존재하지 않으면 false 를 리턴한다)
	boolean emailDuplicateCheck(String email) throws SQLException;

	// 로그인 처리
	MemberVO login(Map<String, String> paraMap) throws SQLException;

	// 아이디 찾기(성명, 이메일을 입력받아서 해당 사용자의 아이디를 알려준다)
	String findUserid(Map<String, String> paraMap) throws SQLException;

	// 비밀번호 찾기(아이디, 이메일을 입력받아서 해당 사용자가 존재하는지 유무를 알려준다)
	boolean isUserExist(Map<String, String> paraMap) throws SQLException;

	// 비밀번호 변경하기
	int pwdUpdate(Map<String, String> paraMap) throws SQLException;

	// 마지막 로그인 날짜 구하기
	String lastLogin(Map<String, String> paraMap) throws SQLException;

	//휴면 전환 날짜 날짜 구하기
	String idleChange(Map<String, String> paraMap) throws SQLException;

	//휴면계정 아이디의 전화번호 구하기
	String idleMemberMobile(String userid) throws SQLException;

	//휴면계정 인증이 완료되었으니 idle_staus를 1으로,  logingap의 데이터 삭제
	int idleStatusUpdate(String idleMemberMobile) throws SQLException;

	// 로그인 기록 테이블의 login_date컬럼 데이터 삭제
	int loginHistoryDelete(String idleMemberMobile) throws SQLException;

	// 비밀번호 변경 3개월 이상 시 현재 비밀번호와 사용자가 입력한 비밀번호 비교하기
	boolean currentPwd(String userid,String inputPwd) throws SQLException;

	// 비밀번호 변경 3개월 이상 시 비밀번호 변경하기(update)
	int threeMonthPwdChange(String userid,String pwd) throws SQLException;

	//인증하고자 하는 전화번호가 존재하는지 확인. DB에 존재하지않으면 false 존재하면 true
	boolean PhoneDuplicateCheck(String phoneNumber) throws SQLException;
}
