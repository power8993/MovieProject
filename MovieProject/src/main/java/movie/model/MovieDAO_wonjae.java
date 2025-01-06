package movie.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import member.domain.MemberVO;
import movie.domain.MovieReviewVO;
import movie.domain.MovieVO_wonjae;

public interface MovieDAO_wonjae {
	
	// 영화상세보기
	MovieVO_wonjae movieDetail(int seq_movie_no) throws SQLException;

	// 영화에 좋아요 추가
	boolean insertMovieLike(MemberVO userid, int seq_movie_no) throws SQLException;

	// 좋아요가 이미 체크되어있는지확인
	boolean removeMovieLike(MemberVO userid, int seq_movie_no) throws SQLException;

	// 현재 영화에 대해 사용자가 좋아요를 눌렀는지 확인
	boolean checkMovieLike(MemberVO userid, int seq_movie_no) throws SQLException;

	// 로그인된 사용자라면 결제 여부를 확인
	boolean checkuserpay(MemberVO loginuser, int seq_movie_no) throws SQLException;

	// 리뷰조회
	List<MovieReviewVO> reviewDetail(int seq_movie_no) throws SQLException;
	
	// 리뷰 데이터 저장
	MovieReviewVO submitReview(int seq_movie_no, MemberVO loginuser, int rating, String review) throws SQLException;

	// 후기 전체페이지 계산 
	int getTotalPage(Map<String, String> paraMap) throws SQLException;

	// 뷰단에서 "페이징 처리시 보여주는 순번 공식" 에서 사용하기 위해 후기의 총개수 알아오기
	int getTotalMovieReviewsCount(Map<String, String> paraMap) throws SQLException;
	
	// 리뷰 목록보여주는 함수 
	List<MovieReviewVO> selectReview(Map<String, String> paraMap) throws SQLException;


}
