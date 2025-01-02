package member.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import member.domain.MemberVO;
import member.domain.MemberVO_hongbi;

public interface MemberDAO_hongbi {

	// **** Admin Method **** //
	
	// 페이징 처리를 위한 검색이 있는 또는 검색이 없는 회원에 대한 총페이지수 알아오기
	int getTotalMemberPage(Map<String, String> paraMap) throws SQLException;
	
	// 페이징 처리를 한 모든 회원 리스트 보여주기 (select)
	List<MemberVO> selectMemberListPaging(Map<String, String> paraMap) throws SQLException;
	
	// 검색이 있는 또는 검색이 없는 회원의 총개수 알아오기
	int getTotalMemberCount(Map<String, String> paraMap) throws SQLException;
	
	// 회원 목록을 보여주는 페이지에서 회원 클릭 시, 해당 회원 상세 정보 보여주기(select)
	MemberVO_hongbi selectMemberDetail(String user_id) throws SQLException;
}
