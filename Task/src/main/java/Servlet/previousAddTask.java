
/*
 * Controller Servlet to add task from group home page 
 * to MySQL Database
 * @date 10/09/2014
 */

package Servlet;

import java.util.Random;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class AddTask
 */
@WebServlet("/AddTask")
public class previousAddTask extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Connection conn = null;

	String taskName, dueDate, taskPoints, groupID, userName;

	DatabaseConnection dbconn;
	ResultSet userRes;

	protected void taskAddition(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		try {

			// Create JDBC connection and fetch parameter values entered from
			// view page
			dbconn = new DatabaseConnection();
			conn = dbconn.setConnection();

			taskName = request.getParameter("taskName");
			dueDate = request.getParameter("dueDate");
			taskPoints = request.getParameter("taskPoints");
			String assignedMember = request.getParameter("assignedMember");
			String username = request.getParameter("username");
			String queryGroupID = "Select groupID from usercred where username=\'"
					+ username + "\'";
			ResultSet resGroupID = dbconn.getResult(queryGroupID, conn);
			int groupID = 0;
			if (resGroupID.next()) {
				groupID = resGroupID.getInt(1);
			}

			System.out.println("Connection created successfully");

			// Insert fetched data from view into database table taskdescription
			PreparedStatement preparedStatement;
			preparedStatement = conn
					.prepareStatement("insert into taskdescription(taskName, dueDate, groupID, taskPoints)"
							+ " values (?, ?, ?, ?)");
			preparedStatement.setString(1, taskName);
			preparedStatement.setString(2, dueDate);
			preparedStatement.setInt(3, groupID);
			preparedStatement.setFloat(4, Float.parseFloat(taskPoints));

			preparedStatement.executeUpdate();

			// Fetch user name and task ID from database and insert into
			// taskassignment table
		

			String queryTaskID = "Select taskID from taskdescription where taskName=\'"
					+ taskName + "\'";
			ResultSet resTaskID = dbconn.getResult(queryTaskID, conn);
			if (resTaskID.next()) {
				System.out
						.println("Fetched user name and taskid values successfully and assigning task to user");
				PreparedStatement preparedStatement2;
				if(!assignedMember.equals("None")){
					preparedStatement2 = conn
							.prepareStatement("insert into taskassignment(taskID,userName) values (?,?)");
					preparedStatement2.setInt(1, resTaskID.getInt(1));
					String queryUsername = "Select userName from usercred where firstName=\'"
							+ assignedMember + "\'";
					ResultSet resUsername = dbconn.getResult(queryUsername, conn);
				if(resUsername.next()){
				preparedStatement2.setString(2, resUsername.getString(1));
				}
				else {
				//	logger.log("Encountered error while assigning task to user");
				}
				
				preparedStatement2.executeUpdate();
				}
				else{
					preparedStatement2 = conn
							.prepareStatement("insert into taskassignment(taskID) values (?)");
					preparedStatement2.setInt(1, resTaskID.getInt(1));
					preparedStatement2.executeUpdate();
					
					
				}
				
				
				
				
				}

			 


			System.out.println("Successfully inserted task and into database!");
			RequestDispatcher rd = request.getRequestDispatcher("/home.jsp");
			rd.forward(request, response);

			// Set content type of the response and add fetched data to JSON object 
			List list = new ArrayList();
			list.add("true");
			String json2 = new Gson().toJson(list);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json2);

		} catch (Exception e) {
			System.out.println("Error  in adding the task");
			e.printStackTrace();

		} finally {
			conn.close();
			out.close();
		}
	}

	public previousAddTask() {
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
		try {
			taskAddition(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			taskAddition(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
