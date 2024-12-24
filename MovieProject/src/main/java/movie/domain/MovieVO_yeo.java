package movie.domain;


public class MovieVO_yeo {
	
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
	private ShowTimeVO_yeo svo;
	private CategoryVO_yeo cg;
	
	
	
	
	// === Method === //
	public CategoryVO_yeo getCg() {
		return cg;
	}

	public void setCg(CategoryVO_yeo cg) {
		this.cg = cg;
	}

	public ShowTimeVO_yeo getSvo() {
		return svo;
	}

	public void setSvo(ShowTimeVO_yeo svo) {
		this.svo = svo;
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

}
