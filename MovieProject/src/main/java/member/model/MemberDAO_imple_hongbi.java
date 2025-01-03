package member.model;

import java.io.Console;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import member.domain.MemberVO;
import member.domain.MemberVO_hongbi;
import movie.domain.CategoryVO;
import movie.domain.MovieVO;
import util.security.AES256;
import util.security.SecretMyKey;
import util.security.Sha256;

public class MemberDAO_imple_hongbi implements MemberDAO_hongbi {
	
	private DataSource ds;	// DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다.
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private AES256 aes;

	// 생성자
	public MemberDAO_imple_hongbi() {
		try {
			Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/semioracle");
			
			aes = new AES256(SecretMyKey.KEY);
			// SecretMyKey.KEY 은 우리가 만든 암호화/복호화 키이다.
			
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	} // end of public ProductDAO_imple()--------------------------------
	
	// 사용한 자원을 반납하는 close() 메소드 생성하기
	private void close() {
		try {
			if(rs != null) {rs.close(); rs = null;}
			if(pstmt != null) {pstmt.close(); pstmt = null;}
			if(conn != null) {conn.close(); conn = null;}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	} // end of private void close()------------------------------------

	
	
	////////////////////////////////////////////////////////////////////////////
	// **** Admin Method **** //
	
	// 페이징 처리를 위한 검색이 있는 또는 검색이 없는 회원에 대한 총페이지수 알아오기
	@Override
	public int getTotalMemberPage(Map<String, String> paraMap) throws SQLException {
		
		int total_page = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql = " select ceil(count(*)/?) "
					   + " from tbl_member "
					   + " where user_id != 'admin' ";
			
			String search_type = paraMap.get("search_type");
			String search_word = paraMap.get("search_word");
			
			// 검색유형이 email 이라면 암호화 처리
			if("email".equals(search_type)) {
				search_word = aes.encrypt(search_word);
			}
			
			if(!search_type.isBlank() && !search_word.isBlank()) {
				// 검색유형 + 검색어
				sql += " and "+ search_type +" like '%'||?||'%' ";
			}

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, Integer.parseInt(paraMap.get("size_per_page")));
			
			if(!search_type.isBlank() && !search_word.isBlank()) { 
				// 검색유형 + 검색어
				pstmt.setString(2, search_word);
			}

			rs = pstmt.executeQuery();
			
			rs.next();
			
			total_page = rs.getInt(1);
			
		} catch(GeneralSecurityException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return total_page;
	}// end of public int getTotalMemberPage(Map<String, String> paraMap) {}--------------------------

	
	
	// 페이징 처리를 한 모든 회원 리스트 보여주기 (select)
	@Override
	public List<MemberVO> selectMemberListPaging(Map<String, String> paraMap) throws SQLException {
		
		List<MemberVO> memberList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();

			String sql = " select rno, user_id, name, birthday, gender, registerday, idle_status, user_status "
					   + " from "
					   + " (select rownum as rno "
					   + "      , user_id "
					   + "      , name "
					   + "      , substr(birthday,1,4)||'/'||substr(birthday,6,2)||'/'||substr(birthday,9,2) as birthday "
					   + "      , decode(gender, 1, '남', '여') as gender "
					   + "      , to_char(registerday, 'yyyy-mm-dd') as registerday "
					   + "      , idle_status "
					   + "      , user_status "
					   + " from tbl_member "
					   + " where user_id != 'admin' ";
					   
			String search_type = paraMap.get("search_type");
			String search_word = paraMap.get("search_word");
			
			
			// 검색유형이 email 이라면 암호화 처리
			if("email".equals(search_type)) {
				search_word = aes.encrypt(search_word);
			}
			
			if(!search_type.isBlank() && !search_word.isBlank()) {
				// 검색유형 + 검색어
				sql += " and "+ search_type +" like '%'||?||'%' ";
			}

			sql += " ) "
				 + " where rno between ? and ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			int current_showpage_no = Integer.parseInt(paraMap.get("current_showpage_no"));
			int size_per_page = Integer.parseInt(paraMap.get("size_per_page"));
			
			if(!search_type.isBlank() && !search_word.isBlank()) { 
				// 검색유형 + 검색어
				pstmt.setString(1, search_word);
				pstmt.setInt(2, (current_showpage_no * size_per_page) - (size_per_page - 1));
				pstmt.setInt(3, (current_showpage_no * size_per_page));
			}
			else {
				// 검색이 없는 경우 
				pstmt.setInt(1, (current_showpage_no * size_per_page) - (size_per_page - 1));
				pstmt.setInt(2, (current_showpage_no * size_per_page));
			}
			
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {

				MemberVO mvo = new MemberVO(); 
				mvo.setUserid(rs.getString("user_id"));
				mvo.setName(rs.getString("name"));
				mvo.setBirthday(rs.getString("birthday"));
				mvo.setGender(rs.getString("gender"));
				mvo.setRegisterday(rs.getString("registerday"));
				mvo.setIdle(rs.getInt("idle_status"));
				mvo.setStatus(rs.getInt("user_status"));

				memberList.add(mvo);
			}// end of while()----------------------
			
		} catch(GeneralSecurityException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return memberList;
	}// end of public List<MovieVO> selectMemberListPaging(Map<String, String> paraMap) throws SQLException {}-------------------

	
	
	// 검색이 있는 또는 검색이 없는 회원의 총개수 알아오기
	@Override
	public int getTotalMemberCount(Map<String, String> paraMap) throws SQLException {

		int total_member_count = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql = " select count(*) "
					   + " from tbl_member "
					   + " where user_id != 'admin' ";
			
			String search_type = paraMap.get("search_type");
			String search_word = paraMap.get("search_word");
			
			// 검색유형이 email 이라면 암호화 처리
			if("email".equals(search_type)) {
				search_word = aes.encrypt(search_word);
			}
			
			if(!search_type.isBlank() && !search_word.isBlank()) {
				// 검색유형 + 검색어
				sql += " and "+ search_type +" like '%'||?||'%' ";
			}

			pstmt = conn.prepareStatement(sql);
			
			if(!search_type.isBlank() && !search_word.isBlank()) { 
				// 검색유형 + 검색어
				pstmt.setString(1, search_word);
			}

			rs = pstmt.executeQuery();
			
			rs.next();
			
			total_member_count = rs.getInt(1);
			
		} catch(GeneralSecurityException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return total_member_count;
	}// end of public int getTotalMemberCount(Map<String, String> paraMap) throws SQLException {}---------------------------------

	
	
	// 회원 목록을 보여주는 페이지에서 회원 클릭 시, 해당 회원 상세 정보 보여주기(select)
	@Override
	public MemberVO_hongbi selectMemberDetail(String user_id) throws SQLException {
		
		MemberVO_hongbi mvo = null;
		
		try {
			conn = ds.getConnection();
			
			String sql = " with "
					   + " m as "
					   + " (select user_id, name, mobile, email "
					   + " from tbl_member), "
					   + " pt as "
					   + " (select fk_user_id, nvl(sum(point),0) as point_sum "
					   + " from tbl_point "
					   + " group by fk_user_id), "
					   + " p as "
					   + " (select fk_user_id, nvl(sum(pay_amount),0) as pay_sum, "
					   + "         nvl((select count(*) from tbl_payment) "
					   + "       - (select count(*) from tbl_payment where pay_status = '결제 취소'),0) as reserved_cnt "
					   + " from tbl_payment "
					   + " group by fk_user_id) "
					   + " select m.user_id, m.name, m.mobile, m.email, nvl(p.reserved_cnt, 0) as reserved_cnt, nvl(p.pay_sum, 0) as pay_sum, nvl(pt.point_sum, 0) as point_sum "
					   + " from m left join pt "
					   + " on( m.user_id = pt.fk_user_id ) "
					   + " left join p "
					   + " on( m.user_id = p.fk_user_id ) "
					   + " where m.user_id = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user_id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				mvo = new MemberVO_hongbi();
				mvo.setUserid(rs.getString("user_id"));
				mvo.setName(rs.getString("name"));
				mvo.setMobile(aes.decrypt(rs.getString("mobile")));
				mvo.setEmail(aes.decrypt(rs.getString("email")));
				mvo.setReserved_cnt(rs.getInt("reserved_cnt"));
				mvo.setPay_sum(rs.getInt("pay_sum"));
				mvo.setPoint_sum(rs.getInt("point_sum"));
				
			}
			
		} catch(GeneralSecurityException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return mvo;
	}// end of public MemberVO selectMemberDetail(String user_id) throws SQLException {}------------------------------------------

}
