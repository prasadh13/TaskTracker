/*
 * Database connection model class to be called by controller servlet
 * @date 9/28/2014
 */
package Servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;

public class DatabaseConnection {
	public Statement stmt;
	public ResultSet res;
	public Connection conn;
	Logger logger = Logger.getLogger(DatabaseConnection.class);
	// Default constructor
	public DatabaseConnection() {

	}

	// Method to establish connection using JDBC driver
	public Connection setConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/tasklab?"+ "user=root&password=");
		} catch (Exception e) {
			logger.error("DatabaseConnection has a problem working",e);
			System.out.println("Failed database connection");
			e.printStackTrace();
		}
		return conn;
	}

	// Method to return query output as Resultset
	public ResultSet getResult(String query, Connection conn) {
		this.conn = conn;
		try {
			stmt = conn.createStatement();
			res = stmt.executeQuery(query);

		} catch (Exception e) {
			System.out.println("Error in executing query");
			e.printStackTrace();
		}
		return res;
	}
}
