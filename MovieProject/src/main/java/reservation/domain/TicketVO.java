package reservation.domain;

public class TicketVO {

	// field
	int seq_ticket_no; // 티켓 번호
	String fk_imp_uid; // 결제 번호
	String seat_no; // 좌석 이름
	int ticket_price; // 티켓 가격
	String ticket_age_group; // 티켓 연령대
	
	// === Join Field === //
	private PaymentVO pvo; // 결제VO
	
	private String seat_no_list;  //좌석 총 인원수
	private int seat_count; // 총 매수
	private String AGE_GROUP_COUNT_LIST; //TICKET_AGE_GROUP + 인원수


	// === Method === //
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

	public PaymentVO getPvo() {
		return pvo;
	}

	public void setPvo(PaymentVO pvo) {
		this.pvo = pvo;
	}
	
	
	
	public String getSeat_no_list() {
		return seat_no_list;
	}

	public void setSeat_no_list(String seat_no_list) {
		this.seat_no_list = seat_no_list;
	}

	public int getSeat_count() {
		return seat_count;
	}

	public void setSeat_count(int seat_count) {
		this.seat_count = seat_count;
	}
	


	public String getAGE_GROUP_COUNT_LIST() {
		return AGE_GROUP_COUNT_LIST;
	}

	public void setAGE_GROUP_COUNT_LIST(String aGE_GROUP_COUNT_LIST) {
		AGE_GROUP_COUNT_LIST = aGE_GROUP_COUNT_LIST;
	}


}
