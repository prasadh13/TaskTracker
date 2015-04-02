package Servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MySQLConn {
	public static void main(String[] args) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/TaskAllocation", "scott","tiger");
			System.out.println("Connection success");
			
			String query = "SELECT * from usercred";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next())
				System.out.println("Name: "+rs.getString("username"));
		}catch(Exception e){
			System.out.println("Connection failure");
			
		}
	}
}
