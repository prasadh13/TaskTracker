/*
 * Controller Servlet to facilitate communication 
 * JSP view and MySQL Database
 * @date 9/28/2014
 */
package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

@WebServlet("/ValidateLogin")
public class ValidateLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(ValidateLogin.class);
	Connection conn;
	ResultSet res;

	String username, password, queryLogin,person;

	DatabaseConnection dbconn;

	// method to authenticate users for the application 
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException, SQLException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		try {
			String referrer = request.getHeader("referer");
			username = request.getParameter("username");
			password = request.getParameter("password");

			String queryLogin = "select * from usercred where userName = \'"
					+ username + "\' and password = \'" + password + "\'";
			logger.info("login Query: "+queryLogin);


			dbconn = new DatabaseConnection();
			conn = dbconn.setConnection();
			res = dbconn.getResult(queryLogin, conn);
			if (res.next()) {
				request.setAttribute("person",res.getString(3));
				request.setAttribute("username",username);
				
				RequestDispatcher rd= request.getRequestDispatcher("/home.jsp");
				rd.forward(request, response);
			}
			else {
				RequestDispatcher rd = request
						.getRequestDispatcher("/LoginFailed.jsp");
				rd.forward(request, response);
			}
		} catch (Exception e) {
			logger.info("Authetntication failed...");
			RequestDispatcher rd = request
					.getRequestDispatcher("/LoginFailed.jsp");
			rd.forward(request, response);
		} finally {
			conn.close();
			out.close();
		}
	} // processRequest() method close

	public ValidateLogin() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
