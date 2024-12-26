package movie.model;

import java.sql.SQLException;
import java.util.List;

import movie.domain.CategoryVO_yeo;
import movie.domain.MovieVO;
import movie.domain.MovieVO_yeo;

public interface MovieDAO_yeo {
	
	// 모든 영화 정보 가져오기 (예매율 순으로 바꾸기)
	List<MovieVO_yeo> select_Movies() throws SQLException;
	
	// 상영중 영화 가져오기(개봉 날짜 순)
    List<MovieVO_yeo> select_run_Movies() throws SQLException;
	
	// 상영예정작 가져오기(예매율 순)
	List<MovieVO_yeo> select_Upc_Movies() throws SQLException;
	
	// 영화 시간표 가져오기 
	List<MovieVO_yeo> selectMovieTiem() throws SQLException;
	
	// 장르 종류 가져오기
	List<CategoryVO_yeo> selectcategory() throws SQLException;
	

}