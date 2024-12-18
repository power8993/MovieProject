package movie.model;

import java.sql.SQLException;

import movie.domain.MovieVO;

public interface MovieDAO_yeo {
	
	// 영화를 등록해주는 메소드(tbl_movie 테이블에 insert)
	int registerMovie(MovieVO movie) throws SQLException;

}
