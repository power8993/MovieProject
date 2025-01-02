package member.domain;

public class PaymentVO_hongbi {
	
	// === Field === //
	private String imp_uid;				// 결제번호
	private String fk_user_id;  		// 아이디
	private int fk_seq_showtime_no;     // 상영 영화 번호
	private int pay_amount;       		// 결제 금액
	private String pay_type;          	// 결제 방법(유형)
	private String pay_success_date;    // 결제 완료 일시
	private String pay_cancel_date;     // 결제 취소 일시
	private String pay_status;			// 결제 상태

	// === Method === //
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
	
	public int getFk_seq_showtime_no() {
		return fk_seq_showtime_no;
	}
	
	public void setFk_seq_showtime_no(int fk_seq_showtime_no) {
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
