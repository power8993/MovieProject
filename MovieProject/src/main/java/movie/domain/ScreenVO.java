package movie.domain;

public class ScreenVO {
	
	// === Field === //
	private int screen_no;	// 상영관번호
	private int seat_cnt; 	// 총 좌석 수
	
	
	public int getScreen_no() {
		return screen_no;
	}
	
	public void setScreen_no(int screen_no) {
		this.screen_no = screen_no;
	}
	
	public int getSeat_cnt() {
		return seat_cnt;
	}
	
	public void setSeat_cnt(int seat_cnt) {
		this.seat_cnt = seat_cnt;
	}
}
