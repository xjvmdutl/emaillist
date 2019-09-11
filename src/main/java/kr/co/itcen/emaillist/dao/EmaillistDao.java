package kr.co.itcen.emaillist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import kr.co.itcen.emaillist.vo.EmaillistVo;

public class EmaillistDao {

	public Boolean insert(EmaillistVo vo1) {
		  Connection connection = null;
	      PreparedStatement pstmt = null;
	      Statement stmt = null;
	      Boolean result = false;
	      ResultSet rs =null;
	      try {
	         connection = getConnection();
	         // 3. Statment 객체 생성(받아오기)

	         // 4. SQL문 실행
	         String sql = "insert into emaillist values(null,?,?,?)";
	         pstmt = connection.prepareStatement(sql);
	         pstmt.setString(1, vo1.getFirstname());
	         pstmt.setString(2, vo1.getLastname());
	         pstmt.setString(3, vo1.getEmail());
	         
	         int count = pstmt.executeUpdate();
	         result = (count==1);
	         stmt=connection.createStatement();
	         rs =stmt.executeQuery("select last_insert_id()");//본래는 메소드 한번에 쿼리 하나씩이지만 이건 특이한 케이스이다.
	         if(rs.next()) {
	        	Long no=rs.getLong(1);
	         	vo1.setNo(no);
	         }
	      }catch (SQLException e) {
	         System.out.println("error : " + e);
	      } finally {
	         try{
	        	if(pstmt != null) {
	               pstmt.close();
	            }if(rs!=null) {
	            	rs.close();
	            }if(stmt!=null) {
	            	stmt.close();
	            }if(connection != null){
	               connection.close();
	            }
	         } catch (Exception e) {
	            // TODO: handle exception
	            e.printStackTrace();
	         }
	      }
	      return result;
	}
	private Connection getConnection() throws SQLException{//카피페이스트를 할 경우에는 반드시 중복된 코드를 제거해 주는 메소드가 있어야한다.
		Connection connection=null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
	
	        // 2. 연결하기
	        String url = "jdbc:mariadb://192.168.1.86:3306/webdb?characterEncoding=utf8";
	        // 아래는 네트워크 코드
	        connection = DriverManager.getConnection(url, "webdb", "webdb");
	        // 3. Statment 객체 생성(받아오기)
		}catch (ClassNotFoundException e) {
	         System.out.println("Fail to Loading Driver :" + e);
	    }
		return connection;
	}
	public void delete(Long no) {
		
	}
	public void delete() {
		Connection connection = null;
	      PreparedStatement pstmt = null;
	      try {
	         connection = getConnection();
	         // 3. Statment 객체 생성(받아오기)

	         // 4. SQL문 실행
	         String sql = "delete from emaillist";
	         pstmt = connection.prepareStatement(sql);
	         pstmt.executeUpdate();
	      }catch (SQLException e) {
	         System.out.println("error : " + e);
	      } finally {
	         try{
	        	 if (pstmt != null) {
	               pstmt.close();
	            }

	            if (connection != null) {
	               connection.close();
	            }
	         } catch (Exception e) {
	            // TODO: handle exception
	            e.printStackTrace();
	         }
	      }
	}
	public List<EmaillistVo> getList() {
		Connection connection = null;
	      PreparedStatement pstmt = null;
	      List<EmaillistVo> result = new ArrayList<EmaillistVo>();
	      ResultSet rs =null;
	      try {  
	         connection = getConnection();
	         String sql = "select * from emaillist order by no desc";
	         pstmt = connection.prepareStatement(sql);
	         rs= pstmt.executeQuery();
	         while(rs.next()) {
	        	 Long no=rs.getLong(1);
	        	 String firstname = rs.getString(2);
	        	 String lastname = rs.getString(3);
	        	 String email = rs.getString(4);
	        	 
	        	 EmaillistVo vo = new EmaillistVo();
	        	 vo.setNo(no);
	        	 vo.setFirstname(firstname);
	        	 vo.setLastname(lastname);
	        	 vo.setEmail(email);
	        	 result.add(vo);
	         }
	         //result = (count==1);
	      }catch (SQLException e) {
	         System.out.println("error : " + e);
	      }finally {
	         try {
	        	if(rs!=null) {
	        		rs.close();
	        	}
	            if (pstmt != null) {
	               pstmt.close();
	            }
	            if (connection != null) {
	               connection.close();
	            }
	         } catch (Exception e) {
	            // TODO: handle exception
	            e.printStackTrace();
	         }
	      }
	      return result;
	}
}
