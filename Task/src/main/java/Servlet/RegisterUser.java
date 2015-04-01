/*
 * Servlet to sign-up new users with data entered in sign-up page
 * Validation is taken care up by java scrippt in the page itself
 */
package Servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Servlet implementation class RegisterUser
 */
@WebServlet("/RegisterUser")
public class RegisterUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(RegisterUser.class);
	Connection conn = null;

	String firstName, lastname, email, userName, password, rePassword;

	DatabaseConnection dbconn;

	// method to register new user. Will be called from doGet/doPost service method
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		response.setContentType("text/html");
		// PrintWriter out = response.getWriter();

		firstName = request.getParameter("firstname");
		lastname = request.getParameter("lastname");
		email = request.getParameter("email");
		userName = request.getParameter("username");
		password = request.getParameter("password");
		rePassword = request.getParameter("repassword");

		// Proceed only if both passwords match while signing up
		if (password.equals(rePassword)) {
			System.out.println("Creating a new connection");
			dbconn = new DatabaseConnection();
			conn = dbconn.setConnection();
			System.out.println("Connection created");
			PreparedStatement preparedStatement;
			preparedStatement = conn
					.prepareStatement("insert into usercred(userName,password,firstName,lastname,email) values (?, ?, ?, ?, ?)");

			preparedStatement.setString(1, userName);
			preparedStatement.setString(2, password);
			preparedStatement.setString(3, firstName);
			preparedStatement.setString(4, lastname);
			preparedStatement.setString(5, email);

			preparedStatement.executeUpdate();
			request.setAttribute("person", userName);
			request.getRequestDispatcher("/home.jsp").forward(
						request, response);

			System.out.println("Successfully inserted new user into database!");
			conn.close();
		}
		else{
			System.out.println("Both passwords doesn't match, retry...");
		}
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			processRequest(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
