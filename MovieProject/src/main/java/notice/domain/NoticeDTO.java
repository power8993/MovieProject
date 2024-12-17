package notice.domain;

import java.sql.Date;

public class NoticeDTO {
    private Date notice_wtite_date; // 날짜 필드는 Date로 수정
    private int seq_notice_no;
    private String notice_subject;   
    private String notice_content;    
    private Date notice_update_date; // 수정일자도 Date로 수정
    private int views;
    
    // Getters and Setters
    public Date getNotice_wtite_date() {
        return notice_wtite_date;
    }
    public void setNotice_wtite_date(Date notice_wtite_date) {
        this.notice_wtite_date = notice_wtite_date;
    }
    public int getSeq_notice_no() {
        return seq_notice_no;
    }
    public void setSeq_notice_no(int seq_notice_no) {
        this.seq_notice_no = seq_notice_no;
    }
    public String getNotice_subject() {
        return notice_subject;
    }
    public void setNotice_subject(String notice_subject) {
        this.notice_subject = notice_subject;
    }
    public String getNotice_content() {
        return notice_content;
    }
    public void setNotice_content(String notice_content) {
        this.notice_content = notice_content;
    }
    public Date getNotice_update_date() {
        return notice_update_date;
    }
    public void setNotice_update_date(Date notice_update_date) {
        this.notice_update_date = notice_update_date;
    }
    public int getViews() {
        return views;
    }
    public void setViews(int views) {
        this.views = views;
    }
}
