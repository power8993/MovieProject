package movie.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import movie.domain.MovieVO;
import movie.domain.ShowTimeVO_sunghoon;

public interface MovieDAO_sunghoon {
	
	// 영화 예약 페이지에서 보여줄 영화 리스트
	List<MovieVO> reservationMovieList() throws SQLException;

	// 영화와 날짜를 선택했을 때 보여줄 상영하는 영화 시간 리스트
	List<ShowTimeVO_sunghoon> getScreenTime(Map<String, String> paraMap) throws SQLException;

	// 영화 티켓 가격 가져오기
	List<Integer> getTicketPrice() throws SQLException;

}
