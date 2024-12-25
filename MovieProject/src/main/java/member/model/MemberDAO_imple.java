package member.model;

import java.io.Console;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import member.domain.MemberVO;
import util.security.AES256;
import util.security.SecretMyKey;
import util.security.Sha256;

public class MemberDAO_imple implements MemberDAO {
	
	private DataSource ds;	// DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다.
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private AES256 aes;

	// 생성자
	public MemberDAO_imple() {
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

	
	
	// 회원가입을 해주는 메소드(tbl_member 테이블에 insert)
	@Override
	public int registerMember(MemberVO member) throws SQLException {
		
		int result = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql = " insert into tbl_member(USER_ID, pwd, name, email, mobile, gender, birthday) "
					   + " values(?,?,?,?,?,?,?) ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, member.getUserid());
			pstmt.setString(2, Sha256.encrypt(member.getPwd()));	// 암호를 SHA256 알고리즘으로 단방향 암호화 시킨다. 
			pstmt.setString(3, member.getName());
			pstmt.setString(4, aes.encrypt(member.getEmail()));		// 이메일을 AES256 알고리즘으로 양방향 암호화 시킨다.
			pstmt.setString(5, aes.encrypt(member.getMobile()));		// 휴대폰을 AES256 알고리즘으로 양방향 암호화 시킨다.	
	        pstmt.setString(6, member.getGender());
	        pstmt.setString(7, member.getBirthday());
	        
	        result = pstmt.executeUpdate();
			
		} catch(GeneralSecurityException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return result;
		
	} // end of public int registerMember(MemberVO member)-----------------------------------------------------------

	
	// ID 중복검사 (tbl_member 테이블에서 userid 가 존재하면 true 를 리턴해주고, userid 가 존재하지 않으면 false 를 리턴한다)
	@Override
	public boolean idDuplicateCheck(String userid) throws SQLException {
		
		boolean isExists = false;
		
		try {
			conn = ds.getConnection();
			
			String sql = " select USER_ID "
					   + " from tbl_member "
					   + " where USER_ID = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			
			rs = pstmt.executeQuery();
			
			isExists = rs.next();	// 행이 있으면(중복된 userid) true,
									// 행이 없으면(사용가능한 userid) false
			
		} finally {
			close();
		}
		
		return isExists;
		
	} // end of public boolean idDuplicateCheck(String userid)-------------------------------------------------------

	
	// ID 중복검사 (tbl_member 테이블에서 email 이 존재하면 true 를 리턴해주고, email 이 존재하지 않으면 false 를 리턴한다)
	@Override
	public boolean emailDuplicateCheck(String email) throws SQLException {

		boolean isExists = false;
		
		try {
			conn = ds.getConnection();
			
			String sql = " select email "
					   + " from tbl_member "
					   + " where email = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, aes.encrypt(email));
			
			rs = pstmt.executeQuery();
			
			isExists = rs.next();	// 행이 있으면(중복된 email) true,
									// 행이 없으면(사용가능한 email) false
		
		} catch(GeneralSecurityException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return isExists;
		
	} // end of public boolean emailDuplicateCheck(String email)------------------------------------------------------

	
	// 로그인 처리
	   @Override
	   public MemberVO login(Map<String, String> paraMap) throws SQLException {
	      
	      MemberVO member = null;
	      
	      try {
	         conn = ds.getConnection();
	         
				/*
				 * String sql =
				 * " SELECT USER_ID, NAME, email, mobile ,IDLE_STATUS, birthday, PWD_CHANGE_DATE as lastpwdchangedate "
				 * + " FROM TBL_MEMBER " +
				 * " WHERE USER_STATUS = 1 AND USER_ID = ? and pwd = ? ";
				 */
	         
	         
	         
	         String sql = " SELECT USER_ID, name,pwdchangegap, "
	                  + "        NVL( lastlogingap, TRUNC( months_between(sysdate, REGISTERDAY) ) ) AS lastlogingap, idle_status, email, mobile, gender, birthday "
	                  + " FROM "
	                  + " (select user_id, name, "
	                  + "         trunc( months_between(sysdate, PWD_CHANGE_DATE) ) AS pwdchangegap, "
	                  + "         registerday, idle_status, email, mobile, gender, birthday "
	                  + " FROM tbl_member "
	                  + " WHERE user_status = 1 AND user_id = ? AND pwd = ? "
	                  + " ) M "
	                  + " CROSS JOIN "
	                  + " (select trunc( months_between(sysdate, min(login_date) ) ) AS lastlogingap "
	                  + " FROM tbl_login_history "
	                  + " WHERE fk_user_id = ? "
	                  + " ) H " ;
	         
	         pstmt = conn.prepareStatement(sql);
	         
	         pstmt.setString(1, paraMap.get("userid"));
	         pstmt.setString(2, Sha256.encrypt(paraMap.get("pwd")));
	         pstmt.setString(3, paraMap.get("userid"));
	         
	         rs = pstmt.executeQuery();
	         
	         if(rs.next()) {
	            member = new MemberVO();
	            
	            member.setUserid(rs.getString("USER_ID"));
	            member.setName(rs.getString("name"));
	            
	            //// 마지막로그인과 오늘 날짜 차이 출력해보기 ////
	            int lastLoginGap = rs.getInt("lastlogingap");
	            // System.out.println("Last login gap: " + lastLoginGap);
	            ////////////////////////////////////////////////////////
	            
	            member.setIdle(99); // 휴면계정이 아니라면 99를 넣어줌. 휴면계정이라면 0을 전달.
	            
	            if( lastLoginGap >= 12 ) {
	               // 마지막으로 로그인 한 날짜시간이 현재시각으로 부터 1년이 지났으면 휴면으로 지정
	               member.setIdle(0);
	               
	               if(rs.getInt("idle_status") == 1) {
	                  // === tbl_member 테이블의 idle 컬럼의 값을 0로 변경하기 === //
	                  sql = " update tbl_member set idle_status = 0 "
	                     + " where user_id = ? ";
	                  
	                  pstmt = conn.prepareStatement(sql);
	                  pstmt.setString(1, paraMap.get("userid"));
	                  
	                  pstmt.executeUpdate();
	                  
	               } // end of if(rs.getInt("idle") == 0)-----------------------------
	               
	            } // end of if( rs.getInt("lastlogingap") >= 12 )-----------------------
	            
	            
	            // === 휴면이 아닌 회원만 tbl_loginhistory(로그인기록) 테이블에 insert 하기 시작 === //
	            
	            if( lastLoginGap < 12) {
	               sql = " insert into tbl_login_history(HISTORY_NO, fk_user_id, CLIENT_IP) "
	                  + " values(SEQ_HISTORY_NO.nextval, ?, ?) ";
	               
	               pstmt = conn.prepareStatement(sql);
	               pstmt.setString(1, paraMap.get("userid"));
	               pstmt.setString(2, paraMap.get("clientip"));
	               
	               pstmt.executeUpdate();
	               // === 휴면이 아닌 회원만 tbl_loginhistory(로그인기록) 테이블에 insert 하기 끝 === //
	               
	               if( rs.getInt("pwdchangegap") >= 3 ) {
	                  // 마지막으로 암호를 변경한 날짜가 현재시각으로 부터 3개월이 지났으면 true
	                  // 마지막으로 암호를 변경한 날짜가 현재시각으로 부터 3개월이 지나지 않았으면 false 
	                  
	                  member.setRequirePwdChange(true); // 로그인시 암호를 변경해라는 alert 를 띄우도록 할때 사용한다.
	               }
	            }
		           member.setEmail( aes.decrypt(rs.getString("email")) );
	               member.setMobile( aes.decrypt(rs.getString("mobile")) );
	               member.setBirthday(rs.getString("birthday"));
	               member.setLastpwdchangedate(rs.getString("pwdchangegap"));
	               
	         } // end of if(rs.next())------------------------------
	         
	      } catch(GeneralSecurityException | UnsupportedEncodingException e) {
	         e.printStackTrace();
	      } finally {
	         close();
	      }
	
	return member;
	
} // end of public MemberVO login(Map<String, String> paraMap)--------------------------------------------

	
	// 아이디 찾기(성명, 이메일을 입력받아서 해당 사용자의 아이디를 알려준다)
	@Override
	public String findUserid(Map<String, String> paraMap) throws SQLException {
		
		String userid = null;
		
		try {
			
			conn = ds.getConnection();
			
			String sql = " select user_id,email "
					   + " from tbl_member "
					   + " where user_status = 1 and name = ? and email = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paraMap.get("name"));
			pstmt.setString(2, aes.encrypt(paraMap.get("email")));
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				userid = rs.getString("user_id");
			}
			
		} catch(GeneralSecurityException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return userid;
		
	} // end of public String findUserid()--------------------------------

	
	// 비밀번호 찾기(아이디, 이메일을 입력받아서 해당 사용자가 존재하는지 유무를 알려준다)
	@Override
	public boolean isUserExist(Map<String, String> paraMap) throws SQLException {
		
		boolean isUserExist = false;
		
		try {
			
			conn = ds.getConnection();
			
			String sql = " select user_id "
					   + " from tbl_member "
					   + " where USER_STATUS = 1 and user_id = ? and email = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paraMap.get("userid"));
			pstmt.setString(2, aes.encrypt(paraMap.get("email")));
			
			rs = pstmt.executeQuery();
			
			isUserExist = rs.next();
			
		} catch(GeneralSecurityException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return isUserExist;
		
	} // end of public boolean isUserExist(Map<String, String> paraMap)---------------------------------------

	
	// 비밀번호 변경하기
	@Override
	public int pwdUpdate(Map<String, String> paraMap) throws SQLException {
		
		int result = 0;
		try {
			conn = ds.getConnection();
			String sql = " update tbl_member set pwd = ? " /*, lastpwdchangedate = sysdate 비밀번호 찾기 시 비밀번호를 변경함*/
					   + " where user_id = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, Sha256.encrypt(paraMap.get("new_pwd")) ); // 암호를 SHA256 알고리즘으로 단방향 암호화 시킨다.
			pstmt.setString(2, paraMap.get("userid") ); 
			

			result = pstmt.executeUpdate();
	
		}catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	  
		return result;
		
	} // end of public int pwdUpdate(Map<String, String> paraMap)---------------------------------------

}
