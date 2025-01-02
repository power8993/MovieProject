package movie.domain;

import member.domain.MemberVO;

public class MovieReviewVO {
	
	// === Field === //
	private int seq_review_no;  //영화리뷰번호
	private int fk_seq_movie_no; // 영화번호
	private String fk_user_id; //유저아이디
	private int movie_rating;  //평점 (1~5)
	private String review_content; //평점 내용
	private String review_write_date; //평점 날짜
	
	// === Join Field === // 
	private MovieVO mvo; // 무비vo
	private MemberVO mbvo; // 멤버vo
	
	public int getSeq_review_no() {
		return seq_review_no;
	}
	public void setSeq_review_no(int seq_review_no) {
		this.seq_review_no = seq_review_no;
	}
	public int getFk_seq_movie_no() {
		return fk_seq_movie_no;
	}
	public void setFk_seq_movie_no(int fk_seq_movie_no) {
		this.fk_seq_movie_no = fk_seq_movie_no;
	}
	public String getFk_user_id() {
		return fk_user_id;
	}
	public void setFk_user_id(String fk_user_id) {
		this.fk_user_id = fk_user_id;
	}
	public int getMovie_rating() {
		return movie_rating;
	}
	public void setMovie_rating(int movie_rating) {
		this.movie_rating = movie_rating;
	}
	public String getReview_content() {
		return review_content;
	}
	public void setReview_content(String review_content) {
		this.review_content = review_content;
	}
	public String getReview_write_date() {
		return review_write_date;
	}
	public void setReview_write_date(String review_write_date) {
		this.review_write_date = review_write_date;
	}
	public MovieVO getMvo() {
		return mvo;
	}
	public void setMvo(MovieVO mvo) {
		this.mvo = mvo;
	}
	public MemberVO getMbvo() {
		return mbvo;
	}
	public void setMbvo(MemberVO mbvo) {
		this.mbvo = mbvo;
	}
	
}
