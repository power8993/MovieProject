package mypage.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import member.domain.MemberVO;
import member.model.MemberDAO;
import movie.domain.MovieLikeVO;
import movie.domain.MovieReviewVO;

public interface MypageDAO {
	
	//마이페이지 메인 리스트 = 내가 쓴 리뷰
	List<MovieReviewVO> main_mypage_MovieReviewList(Map<String, String> paraMap)throws SQLException;
	
	//마이페이지 메인 리스트 = 기대되는 영화
	List<MovieLikeVO> main_mypage_MovieLikeList(Map<String, String> paraMap)throws SQLException;

	//마이페이지 회원수정 비밀번호 체크
	boolean checkPassword(Map<String, String> paraMap) throws SQLException;

	//마이페이지 회원정보수정 이메일 체크
	boolean emailDuplicateCheck2(Map<String, String> paraMap)throws SQLException;

	//마이페이지 회원정보수정
	int updateMember(MemberVO member)throws SQLException;

	//마이페이지 회원정보수정 비밀번호 변경
	int mypwdUdate(Map<String, String> paraMap)throws SQLException;

	//마이페이지 회원탈퇴
	boolean deletePassword(String userid)throws SQLException;

	//마이페이지 기대되는 영화 좋아요 목록 전체 합계
	int totalmymovielike(String userid)throws SQLException;

	//마이페이지 기대되는 영화 좋아요 목록 리스트
	List<MovieLikeVO> selectBymymovielike(Map<String, String> paraMap)throws SQLException;
	
	//마이페이지 내가 쓴 평점 목록 전체 합계
	int totalmymoviereview(String userid)throws SQLException;
	
	//마이페이지 내가 쓴 평점 목록 리스트
	List<MovieReviewVO> mymoviereviewList(Map<String, String> paraMap)throws SQLException;

	//마이페이지 회원정보수정에서 비밀번호 변경 날짜 가져오기
	List<MemberVO> Mylastpwdchangedate(Map<String, String> paraMap)throws SQLException;

}
