package movie.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import movie.domain.MovieVO;
import movie.domain.ShowTimeVO_sunghoon;
import reservation.controller.TicketVO_hoon;
import reservation.domain.TicketVO;

public interface MovieDAO_sunghoon {
	
	// 영화 예약 페이지에서 보여줄 영화 리스트
	List<MovieVO> reservationMovieList() throws SQLException;

	// 영화와 날짜를 선택했을 때 보여줄 상영하는 영화 시간 리스트
	List<ShowTimeVO_sunghoon> getScreenTime(Map<String, String> paraMap) throws SQLException;

	// 영화 티켓 가격 가져오기
	List<Integer> getTicketPrice() throws SQLException;

	// 결제 내역 생성
	int makePayment(Map<String, String> paraMap) throws SQLException;

	// 티켓 생성
	int makeTicket(TicketVO_hoon ticket) throws SQLException;

	// 좌석 배열 가져오기
	String getSeatArr(int seq_showtime_no) throws SQLException;

	// 상영 영화 수정
	int updateShowtime(String seat_arr_str, int seq_showtime_no, int selected_seat_arr_length) throws SQLException;

	// 보유중인 포인트 가져오기
	int getHavingPoint(String userid) throws SQLException;

	// 포인트 거래 내역 만들기
	int makePoint(Map<String, String> paraMap) throws SQLException;

	// 티켓 정보 가져오기
	List<TicketVO> getTickets(String userid, String imp_uid) throws SQLException;

	// 결제 번호로 영화 정보 가져오기
	Map<String, String> getMovieTitle(String imp_uid) throws SQLException;

}
