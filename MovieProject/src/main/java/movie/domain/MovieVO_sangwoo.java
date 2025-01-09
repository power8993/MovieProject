package movie.domain;

public class MovieVO_sangwoo {
	
	// === Field === //
	private int	seq_movie_no;		   // 영화번호
	private String fk_category_code;   // 장르코드
	private String movie_title;        // 영화제목
	private String content;            // 줄거리
	private String director;           // 감독
	private String actor;              // 배우
	private String movie_grade;        // 상영등급
	private String running_time;       // 러닝타임
	private int like_count;            // 좋아요 수
	


	private String start_date;         // 상영시작일(개봉일)
	private String end_date;           // 상영종료일
	private String poster_file;        // 포스터 파일명
	private String video_url;          // 비디오URL
	private String register_date;      // 등록일자
	
	
	private int remaining_day;
	private double bookingRate = 0.0;   // 예매율(%)
	
	// === Join Field === // 
	private ScreenVO   scvo;
	private CategoryVO catevo;
	private ShowtimeVO showvo;


	
	// === Method === //
	public ShowtimeVO getShowvo() {
		return showvo;
	}

	public void setShowvo(ShowtimeVO showvo) {
		this.showvo = showvo;
	}

	public ScreenVO getScvo() {
		return scvo;
	}

	public void setScvo(ScreenVO scvo) {
		this.scvo = scvo;
	}

	public CategoryVO getCatevo() {
		return catevo;
	}

	public void setCatevo(CategoryVO catevo) {
		this.catevo = catevo;
	}
	
	public int getSeq_movie_no() {
		return seq_movie_no;
	}
	
	public void setSeq_movie_no(int seq_movie_no) {
		this.seq_movie_no = seq_movie_no;
	}
	
	public String getFk_category_code() {
		return fk_category_code;
	}
	
	public void setFk_category_code(String fk_category_code) {
		this.fk_category_code = fk_category_code;
	}
	
	public String getMovie_title() {
		return movie_title;
	}
	
	public void setMovie_title(String movie_title) {
		this.movie_title = movie_title;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getDirector() {
		return director;
	}
	
	public void setDirector(String director) {
		this.director = director;
	}
	
	public String getActor() {
		return actor;
	}
	
	public void setActor(String actor) {
		this.actor = actor;
	}
	
	public String getMovie_grade() {
		return movie_grade;
	}
	
	public void setMovie_grade(String movie_grade) {
		this.movie_grade = movie_grade;
	}
	
	public String getRunning_time() {
		return running_time;
	}
	
	public void setRunning_time(String running_time) {
		this.running_time = running_time;
	}
	
	public int getLike_count() {
		return like_count;
	}
	
	public void setLike_count(int like_count) {
		this.like_count = like_count;
	}
	
	public String getStart_date() {
		return start_date;
	}
	
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	
	public String getEnd_date() {
		return end_date;
	}
	
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	
	public String getPoster_file() {
		return poster_file;
	}
	
	public void setPoster_file(String poster_file) {
		this.poster_file = poster_file;
	}
	
	public String getVideo_url() {
		return video_url;
	}
	
	public void setVideo_url(String video_url) {
		this.video_url = video_url;
	}
	
	public String getRegister_date() {
		return register_date;
	}
	
	public void setRegister_date(String register_date) {
		this.register_date = register_date;
	}
	
	public int getRemaining_day() {
		return remaining_day;
	}

	public void setRemaining_day(int remaining_day) {
		this.remaining_day = remaining_day;
	}
	
	public double getBookingRate() {
        return bookingRate;
    }

    public void setBookingRate(double bookingRate) {
        this.bookingRate = bookingRate;
    }
	
	
	// === User Method === //
	public String category_name(String fk_category_code) {
		String result = "";
		
		switch (fk_category_code) {
			case "1":
				result = "액션";
				break;
			case "2":
				result = "코미디";
				break;
			case "3":
				result = "드라마";
				break;
			case "4":
				result = "스릴러";
				break;
			case "5":
				result = "로맨스";
				break;
			case "6":
				result = "sf";
				break;
			case "7":
				result = "판타지";
				break;
			case "8":
				result = "애니메이션";
				break;
			case "9":
				result = "역사";
				break;
			case "10":
				result = "범죄";
				break;
			case "11":
				result = "스포츠";
				break;
			case "12":
				result = "느와르";
				break;
		}
		
		return result;
	}// end of public String category_name(String fk_category_code) {}-------------------------------------
}
