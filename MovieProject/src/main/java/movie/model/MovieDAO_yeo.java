package movie.model;

import java.sql.SQLException;
import java.util.List;
import movie.domain.CategoryVO_yeo;
import movie.domain.MovieVO;
import movie.domain.MovieVO_yeo;

public interface MovieDAO_yeo {
	

	List<MovieVO_yeo> select_Movies() throws SQLException;
	
	// 상영중 영화 가져오기(예매율 순)
    List<MovieVO_yeo> select_run_Movies() throws SQLException;
	
	// 상영예정작 가져오기(개봉날짜 순)
	List<MovieVO_yeo> select_Upc_Movies() throws SQLException;
	
	// 장르 종류 가져오기
	List<CategoryVO_yeo> selectcategory() throws SQLException;
		
	// 특정 장르의 영화 가져오기(전체)
    List<MovieVO_yeo> allgetMoviesByGenre(String genreCode) throws Exception;
	
	// 특정 장르의 영화 가져오기(상영중)
    List<MovieVO_yeo> getMoviesByGenre(String genreCode) throws Exception;
    
    // 특정 장르의 영화 가져오기(상영예정)
    List<MovieVO_yeo> ucgetMoviesByGenre(String genreCode) throws Exception;

    // 영화 시간표 가져오기 
 	//List<MovieVO_yeo> selectMovieTime() throws SQLException;

    // 선택한 날짜 영화 시간표 가져오기 (시간 순)
 	List<MovieVO_yeo> selectMovieTimeByDate(String selectedDate) throws SQLException;
 	
 	// 선택한 날짜 영화 시간표 가져오기 (1관수 순)
 	List<MovieVO_yeo> selectMovieTimeByDateNO1(String selectedDate) throws SQLException;
 	
    // 선택한 날짜 영화 시간표 가져오기 (2관수 순)
  	List<MovieVO_yeo> selectMovieTimeByDateNO2(String selectedDate) throws SQLException;

    // 검색 기능 구현 
	List<MovieVO_yeo> searchMovies(String trim) throws SQLException;

	



}