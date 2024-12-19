package movie.model;

import java.sql.SQLException;
import java.util.List;

import movie.domain.MovieVO;

public interface MovieDAO_yeo {
	
	// 모든 영화 정보 가져오기
	List<MovieVO> select_Movies() throws SQLException;


}