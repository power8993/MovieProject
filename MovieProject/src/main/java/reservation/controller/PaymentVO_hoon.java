package reservation.controller;

public class PaymentVO_hoon {
	
	// field
	String imp_uid;				// 결제 번호
	String fk_user_id;			// 사용자 아이디
	String fk_seq_showtime_no;	// 상영 영화 번호
	int pay_amount;				// 결제 금액
	String pay_type;			// 결제 방식
	String pay_success_date;	// 결제 날짜
	String pay_cancel_date;		// 결제 취소 날짜
	String pay_status;			// 결제 상태
	
	// method
	public String getImp_uid() {
		return imp_uid;
	}
	
	public void setImp_uid(String imp_uid) {
		this.imp_uid = imp_uid;
	}
	
	public String getFk_user_id() {
		return fk_user_id;
	}
	
	public void setFk_user_id(String fk_user_id) {
		this.fk_user_id = fk_user_id;
	}
	
	public String getFk_seq_showtime_no() {
		return fk_seq_showtime_no;
	}
	
	public void setFk_seq_showtime_no(String fk_seq_showtime_no) {
		this.fk_seq_showtime_no = fk_seq_showtime_no;
	}
	
	public int getPay_amount() {
		return pay_amount;
	}
	
	public void setPay_amount(int pay_amount) {
		this.pay_amount = pay_amount;
	}
	
	public String getPay_type() {
		return pay_type;
	}
	
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	
	public String getPay_success_date() {
		return pay_success_date;
	}
	
	public void setPay_success_date(String pay_success_date) {
		this.pay_success_date = pay_success_date;
	}
	
	public String getPay_cancel_date() {
		return pay_cancel_date;
	}
	
	public void setPay_cancel_date(String pay_cancel_date) {
		this.pay_cancel_date = pay_cancel_date;
	}
	
	public String getPay_status() {
		return pay_status;
	}
	
	public void setPay_status(String pay_status) {
		this.pay_status = pay_status;
	}


}
