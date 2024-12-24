package movie.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import movie.domain.MovieVO;

public interface MovieDAO {
	
	// 영화를 등록해주는 메소드(tbl_movie 테이블에 insert)
	int registerMovie(MovieVO movie) throws SQLException;
	
	// 영화를 등록 페이지에서 [등록된 영화 조회]를 통해 검색한 영화들을 보여주는 페이지(select)
	List<MovieVO> selectMovieRegister(String movie_title) throws SQLException;
	
	// 페이징 처리를 안한 모든 영화 목록 보여주기(select)
	List<MovieVO> selectMovieList(Map<String, String> paraMap) throws SQLException;

	// 등록된 영화를 보여주는 페이지에서 영화 클릭 시, 해당 영화 상세 내용 보여주기(select)
	MovieVO selectMovieDetail(String seq) throws SQLException;

	

}
