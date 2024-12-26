package movie.domain;

public class ShowTimeVO_yeo {
	
	// === Field === //
	   private int   seq_showtime;        // 상영 영화번호
	   private int fk_seq_movie_no;       // 영화번호
	   private int fk_screenNO;           // 상영관 번호
	   private String start_time;         // 상영 시작 시간
	   private String end_tiem;           // 상영 종료 시간
	   private int total_viewer;          // 누적 관객 수 
	   private int seat_arr;              // 좌석 배열
	   private int unused_seat;           // 남은 좌석수
	   
	   
	   // === Method === //
	   public int getSeq_showtime() {
	      return seq_showtime;
	   }
	   public void setSeq_showtime(int seq_showtime) {
	      this.seq_showtime = seq_showtime;
	   }
	   public int getFk_seq_movie_no() {
	      return fk_seq_movie_no;
	   }
	   public void setFk_seq_movie_no(int fk_seq_movie_no) {
	      this.fk_seq_movie_no = fk_seq_movie_no;
	   }
	   public int getFk_screenNO() {
	      return fk_screenNO;
	   }
	   public void setFk_screenNO(int fk_screenNO) {
	      this.fk_screenNO = fk_screenNO;
	   }
	   public String getStart_time() {
	      return start_time;
	   }
	   public void setStart_time(String start_time) {
	      this.start_time = start_time;
	   }
	   public String getEnd_tiem() {
	      return end_tiem;
	   }
	   public void setEnd_tiem(String end_tiem) {
	      this.end_tiem = end_tiem;
	   }
	   public int getTotal_viewer() {
	      return total_viewer;
	   }
	   public void setTotal_viewer(int total_viewer) {
	      this.total_viewer = total_viewer;
	   }
	   public int getSeat_arr() {
	      return seat_arr;
	   }
	   public void setSeat_arr(int seat_arr) {
	      this.seat_arr = seat_arr;
	   }
	   public int getUnused_seat() {
	      return unused_seat;
	   }
	   public void setUnused_seat(int unused_seat) {
	      this.unused_seat = unused_seat;
	   }


}
