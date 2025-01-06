package common.model;

import java.sql.SQLException;
import java.util.List;

import movie.domain.MovieVO_sangwoo;
import notice.domain.NoticeDTO;

public interface indexDAO {

	// 공지사항 최신글 3개 조회하기
	List<NoticeDTO> NoticeSelectTopThree() throws SQLException;
	
	//무비차트 예매율 기준 상위 5개 조회하기.(카드의 첫화면)
	List<MovieVO_sangwoo> showMovieChart() throws SQLException;
	
	//무비차트 예매율 기준 상위 5개 조회하기.(카드의 두번째화면)
	List<MovieVO_sangwoo> showMovieChart2() throws SQLException;

	//상영예정작 현재날짜에 가까운 영화개봉일 기준 상위 5개 초회(카드의 첫화면)
	List<MovieVO_sangwoo> showLaterMovies() throws SQLException;
	
	//상영예정작 현재날짜에 가까운 영화개봉일 기준 상위 5개 이후 5개 초회(카드의 첫화면)
	List<MovieVO_sangwoo> showLaterMovies2() throws SQLException;
}
