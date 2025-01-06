package reservation.domain;

import member.domain.MemberVO;

public class PointVO {
	
	// field
	int seq_point_no;		// 포인트 번호
	String fk_user_id;		// 사용자 아이디
	String fk_imp_uid;			// 결제 번호
	int point_type;			// 포인트 상태 (0 : 차감, 1 : 적립)
	int point;				// 포인트 양
	String point_date;		// 적립 or 사용 날짜
	
	
	// === Join Field === //
	private PaymentVO pvo; // 결제VO
	private MemberVO mbvo; // 멤버VO
	
	
	// === Method === //
	public int getSeq_point_no() {
		return seq_point_no;
	}
	public void setSeq_point_no(int seq_point_no) {
		this.seq_point_no = seq_point_no;
	}
	public String getFk_user_id() {
		return fk_user_id;
	}
	public void setFk_user_id(String fk_user_id) {
		this.fk_user_id = fk_user_id;
	}
	public String getFk_imp_uid() {
		return fk_imp_uid;
	}
	public void setFk_imp_uid(String fk_imp_uid) {
		this.fk_imp_uid = fk_imp_uid;
	}
	public int getPoint_type() {
		return point_type;
	}
	public void setPoint_type(int point_type) {
		this.point_type = point_type;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public String getPoint_date() {
		return point_date;
	}
	public void setPoint_date(String point_date) {
		this.point_date = point_date;
	}
	public PaymentVO getPvo() {
		return pvo;
	}
	public void setPvo(PaymentVO pvo) {
		this.pvo = pvo;
	}
	public MemberVO getMbvo() {
		return mbvo;
	}
	public void setMbvo(MemberVO mbvo) {
		this.mbvo = mbvo;
	}
	
	

}
