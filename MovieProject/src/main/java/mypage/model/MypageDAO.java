package mypage.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import member.domain.MemberVO;
import movie.domain.MovieLikeVO;
import movie.domain.MovieReviewVO;
import reservation.domain.PaymentVO;
import reservation.domain.PointVO;

public interface MypageDAO {
	
	//마이페이지 프로필 포인트 내역
	List<Map<String, Object>> myreservationprofile(String userid)throws SQLException;
	
	//나의 영화 랭킹 
	List<Map<String, String>> myranking(String userid)throws SQLException;
	
	
	
	//마이페이지 메인 리스트 = 나의 예매내역
	List<PaymentVO> main_mypage_Myreservationlist(String userid)throws SQLException;
	
	//마이페이지 메인 리스트 = 내가 본 영화
	List<PaymentVO> main_mypage_MovieWatchedList(String userid)throws SQLException;

	//마이페이지 메인 리스트 = 내가 쓴 리뷰
	List<MovieReviewVO> main_mypage_MovieReviewList(String userid)throws SQLException;
	
	//마이페이지 메인 리스트 = 기대되는 영화
	List<MovieLikeVO> main_mypage_MovieLikeList(String userid)throws SQLException;
	
	
	//마이페이지 포인트 적립/사용내역 합계 목록
	List<Map<String, Object>> myreservationpoint(String userid)throws SQLException;
	
	//마이페이지 포인트 적립/사용내역 목록 리스트
	List<PointVO> myreservationpointList(String userid)throws SQLException;
	
	
	//마이페이지 나의 예매내역 목록 전체
	List<PaymentVO> myreservationList(String userid)throws SQLException;
	
	//마이페이지 나의 예매<취소>내역 목록 전체
	List<PaymentVO> myreservationList_cancel(String userid)throws SQLException;
	
	//마이페이지 나의 예매내역 특정 보기(영수증 출력)
	List<PaymentVO> myreservationList_impUid(String impUid)throws SQLException;
	
	
	//마이페이지 내가 본 영화 목록 전체
	List<PaymentVO> mymoviewatchedList(String userid)throws SQLException;
	
	
	
	//마이페이지 기대되는 영화 좋아요 목록 전체 합계
	int totalmymovielike(String userid)throws SQLException;

	//마이페이지 기대되는 영화 좋아요 목록 리스트
	List<MovieLikeVO> selectBymymovielike(Map<String, String> paraMap)throws SQLException;
	
	//마이페이지 기대되는 영화 좋아요 특정 수정하기
	int mymoivereviewUpdate(MovieReviewVO mrvo)throws SQLException;
	
	//마이페이지 기대되는 영화 좋아요 특정 삭제하기
	int movielikeDelete(String fK_SEQ_MOVIE_NO)throws SQLException;
	
	
	
	//마이페이지 내가 쓴 평점 목록 전체 합계
	int totalmymoviereview(String userid)throws SQLException;
	
	//마이페이지 내가 쓴 평점 목록 리스트
	List<MovieReviewVO> mymoviereviewList(String userid)throws SQLException;

	//마이페이지 내가 쓴 평점 특정 삭제하기
	int mymoviereviewDelete(String seq_review_no)throws SQLException;
	
	

	//마이페이지 회원수정 비밀번호 체크
	boolean checkPassword(Map<String, String> paraMap) throws SQLException;

	//마이페이지 회원정보수정 이메일 체크
	boolean emailDuplicateCheck2(Map<String, String> paraMap)throws SQLException;

	//마이페이지 회원정보수정
	int updateMember(MemberVO member)throws SQLException;

	//마이페이지 회원정보수정 비밀번호 변경
	int mypwdUdate(Map<String, String> paraMap)throws SQLException;
	
	//마이페이지 회원정보수정에서 비밀번호 변경 날짜 가져오기
	MemberVO Mylastpwdchangedate(Map<String, String> paraMap)throws SQLException;
	
	

	//마이페이지 회원탈퇴
	boolean deletePassword(String userid)throws SQLException;























	

}
