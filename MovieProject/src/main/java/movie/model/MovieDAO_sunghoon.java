package movie.model;

import java.sql.SQLException;
import java.util.List;

import movie.domain.MovieVO;

public interface MovieDAO_sunghoon {
	
	// 영화 예약 페이지에서 보여줄 영화 리스트
	List<MovieVO> reservationMovieList() throws SQLException;

}
