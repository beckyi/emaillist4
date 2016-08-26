package kr.ac.sungkyul.emaillist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import kr.ac.sungkyul.emaillist.vo.EmailListVo;

@Repository
public class EmailListDao {
	
	public Connection getConnection(){
		Connection conn = null;
		try {
			// 1.드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 2.연결 얻어오기 (Connection 얻기)
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
			
		} catch (ClassNotFoundException e) {
			 e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
		return conn;
	}
	
	public boolean insert(EmailListVo vo){
		Connection conn = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try {
			conn = getConnection();
			
			// 3. PreparedStatement 준비 (? 인자값 전달 가능)
			String sql = "insert into emaillist values(seq_emaillist.nextval, ?, ?, ?, sysdate)";
			pstmt = conn.prepareStatement(sql);
			
			// 4.바인딩
			pstmt.setString(1, vo.getFirstname());
			pstmt.setString(2, vo.getLastname());
			pstmt.setString(3, vo.getEmail());

			// 5.query 실행
			count = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			try {
				// 6.자원정리
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();

				}
			} catch (SQLException e) {
				System.out.println("error: " + e);
			}
		}
		return (count==1);
	}
	
	public List<EmailListVo> getList(){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		List<EmailListVo> list = new ArrayList<EmailListVo>();
		
		try {
			// 1.드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 2.연결 얻어오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
			
			stmt = conn.createStatement();
			
			String sql = "select no, first_name, last_name, email, to_char(reg_date,'yyyy-mm-dd') from emaillist";
			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				Long no = rs.getLong(1);
				String firstname = rs.getString(2);
				String lastname = rs.getString(3);
				String email = rs.getString(4);
				String regDate = rs.getString(5);
				
				EmailListVo vo = new EmailListVo();
				vo.setNo(no);
				vo.setFirstname(firstname);
				vo.setLastname(lastname);
				vo.setEmail(email);
				vo.setRegDate(regDate);
				
				list.add(vo);
			}

		} catch (ClassNotFoundException e) {
			// e.printStackTrace();
			System.out.println("드라이버 로딩 실패: " + e);
		} catch (SQLException e){
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
}
