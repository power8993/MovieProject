package reservation.controller;

public class TicketVO_hoon {

	// field
	int seq_ticket_no;			// 티켓 번호
	String fk_imp_uid;			// 결제 번호
	String seat_no;				// 좌석 이름
	int ticket_price;			// 티켓 가격
	String ticket_age_group;	// 티켓 연령대
	
	// method
	public int getSeq_ticket_no() {
		return seq_ticket_no;
	}
	public void setSeq_ticket_no(int seq_ticket_no) {
		this.seq_ticket_no = seq_ticket_no;
	}
	public String getFk_imp_uid() {
		return fk_imp_uid;
	}
	public void setFk_imp_uid(String fk_imp_uid) {
		this.fk_imp_uid = fk_imp_uid;
	}
	public String getSeat_no() {
		return seat_no;
	}
	public void setSeat_no(String seat_no) {
		this.seat_no = seat_no;
	}
	public int getTicket_price() {
		return ticket_price;
	}
	public void setTicket_price(int ticket_price) {
		this.ticket_price = ticket_price;
	}
	public String getTicket_age_group() {
		return ticket_age_group;
	}
	public void setTicket_age_group(String ticket_age_group) {
		this.ticket_age_group = ticket_age_group;
	}
	

}
