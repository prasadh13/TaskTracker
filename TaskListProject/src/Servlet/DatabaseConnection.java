/*
 * Database connection model class to be called by controller servlet
 * @date 9/28/2014
 */
package Servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseConnection {
	public Statement stmt;
	public ResultSet res;
	public Connection conn;

	// Default constructor
	public DatabaseConnection() {

	}

	// Method to establish connection using JDBC driver
	public Connection setConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager
					.getConnection("jdbc:mysql://localhost/TaskAllocation?"
							+ "user=scott&password=tiger");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed database connection");
		}
		return conn;
	}

	// Method to return query output as resultset
	public ResultSet getResult(String query, Connection conn) {
		this.conn = conn;
		try {
			stmt = conn.createStatement();
			res = stmt.executeQuery(query);

/*			while (res.next()) {
				System.out.print("Column 1 returned ");
				System.out.println(res.getString(2) + " " + res.getString(3));
			}*/
		} catch (Exception e) {

			e.printStackTrace();
		}
		return res;
	}
}
