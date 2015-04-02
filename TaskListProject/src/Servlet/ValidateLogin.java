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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ValidateLogin")
public class ValidateLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Connection conn;
	ResultSet res;

	String username, password, query;

	DatabaseConnection dbconn;

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		try {
			username = request.getParameter("username");
			password = request.getParameter("pass");

			String temp = "select * from usercred where username = \'"
					+ username + "\' and passphrase = \'" + password + "\'";
			System.out.println(temp);
			query = temp;
			dbconn = new DatabaseConnection();

			conn = dbconn.setConnection();
			res = dbconn.getResult(query, conn);
			 request.setAttribute("person", res);
			
/*			while (res.next()) {
				System.out.println("Column 1 returned ");
				System.out.println(res.getString(1)+" "+res.getString(2) + " " + res.getString(3));
			}*/

			if (res.next()) {
				System.out.println("Result success welcome "+res.getString(2) );
/*				RequestDispatcher rd = request
						.getRequestDispatcher("/LoginSuccess.jsp");
				rd.forward(request, response);*/
				request.getRequestDispatcher("/LoginSuccess.jsp").forward(request, response);
			}
			else {
				RequestDispatcher rd = request
						.getRequestDispatcher("/LoginFailed.jsp");
				rd.forward(request, response);
			}
		} catch (Exception e) {
			RequestDispatcher rd = request
					.getRequestDispatcher("/LoginFailed.jsp");
			rd.forward(request, response);
		} finally {
			out.close();
		}
	} // processRequest() method close

	public ValidateLogin() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
