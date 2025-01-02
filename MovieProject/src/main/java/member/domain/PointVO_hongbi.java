package member.domain;

public class PointVO_hongbi {
	
	// === Field === //
	private int seq_point_no; 		// 포인트 거래번호
	private String fk_user_id;  	// 아이디
	private String fk_imp_uid; 		// 결제번호
	private int point_type;   		// 포인트 거래유형 0(차감), 1(적립)
	private int point;          	// 포인트 거래금액
	private String point_date;		// 거래 일시
	
	
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

}
