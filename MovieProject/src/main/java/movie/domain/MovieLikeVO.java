package movie.domain;

import member.domain.MemberVO;

public class MovieLikeVO {
	
	private String FK_USER_ID;
	private int FK_SEQ_MOVIE_NO;
	private MovieVO mvo; // 무비vo
	private MemberVO mbvo; // 멤버vo
	
	
	public String getFK_USER_ID() {
		return FK_USER_ID;
	}
	public void setFK_USER_ID(String fK_USER_ID) {
		FK_USER_ID = fK_USER_ID;
	}
	public int getFK_SEQ_MOVIE_NO() {
		return FK_SEQ_MOVIE_NO;
	}
	public void setFK_SEQ_MOVIE_NO(int fK_SEQ_MOVIE_NO) {
		FK_SEQ_MOVIE_NO = fK_SEQ_MOVIE_NO;
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
