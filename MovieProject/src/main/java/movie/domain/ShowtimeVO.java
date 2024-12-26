package movie.domain;

public class ShowtimeVO {
	
	// === Field === //
	private int seq_showtime_no; 	// 상영 영화 번호
	private int fk_seq_movie_no;	// 영화번호
	private int fk_screen_no;    	// 상영관 번호
	private String start_time;      // 상영 시작 시간
	private String end_time;        // 상영 종료 시간
	private int total_viewer;    	// 누적 관객 수
	private String seat_arr;        // 좌석 배열
	private int unused_seat;     	// 남은 좌석 수
	
	
	
	
	// === Method === //
	public int getSeq_showtime_no() {
		return seq_showtime_no;
	}
	
	public void setSeq_showtime_no(int seq_showtime_no) {
		this.seq_showtime_no = seq_showtime_no;
	}
	
	public int getFk_seq_movie_no() {
		return fk_seq_movie_no;
	}
	
	public void setFk_seq_movie_no(int fk_seq_movie_no) {
		this.fk_seq_movie_no = fk_seq_movie_no;
	}
	
	public int getFk_screen_no() {
		return fk_screen_no;
	}
	
	public void setFk_screen_no(int fk_screen_no) {
		this.fk_screen_no = fk_screen_no;
	}
	
	public String getStart_time() {
		return start_time;
	}
	
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	
	public String getEnd_time() {
		return end_time;
	}
	
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	
	public int getTotal_viewer() {
		return total_viewer;
	}
	
	public void setTotal_viewer(int total_viewer) {
		this.total_viewer = total_viewer;
	}
	
	public String getSeat_arr() {
		return seat_arr;
	}
	
	public void setSeat_arr(String seat_arr) {
		this.seat_arr = seat_arr;
	}
	
	public int getUnused_seat() {
		return unused_seat;
	}
	
	public void setUnused_seat(int unused_seat) {
		this.unused_seat = unused_seat;
	}
	

}

