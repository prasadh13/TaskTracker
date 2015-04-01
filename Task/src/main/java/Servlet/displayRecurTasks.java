package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

/**
 * Servlet implementation class displayRecurTasks
 */
@WebServlet("/displayRecurTasks")
public class displayRecurTasks extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(displayRecurTasks.class);
	/**
	 * @see HttpServlet#HttpServlet()
	 */

	Connection conn;
	ResultSet myTaskResult,groupTaskResult, firstNameResult;
	String username, password, queryGroupID, queryMyTaskDetail,queryFirstName,queryGroupTaskDetail;

	DatabaseConnection dbconn;

	public displayRecurTasks() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void displayMyTasks(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<List<String>> tsk = new ArrayList<List<String>>();

		PrintWriter out = response.getWriter();
		response.setHeader("Cache-control", "no-cache, no-store");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "-1");

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Access-Control-Max-Age", "86400");

		try {
			username = request.getParameter("username");
			String queryGroupID = "Select groupID from userCred where userName= \'"
					+ username + "\'";
			dbconn = new DatabaseConnection();
			conn = dbconn.setConnection();
			myTaskResult = dbconn.getResult(queryGroupID, conn);
			String groupID = null;
			while (myTaskResult.next()) {     

				groupID = myTaskResult.getString(1);
			}

			queryMyTaskDetail = "Select taskName, taskPoints,dueDate,taskassignment.userName,taskAssignment.taskID,taskdescription.recur_days, "
					+ "taskdescription.completedOn from taskdescription join taskAssignment on taskAssignment.taskID=taskdescription.taskID "
					+ "where taskdescription.groupID= \'"
					+ groupID + "\' and userName= \'"+ username + "\'";
			
			dbconn = new DatabaseConnection();
			conn = dbconn.setConnection();
			myTaskResult= dbconn.getResult(queryMyTaskDetail, conn);

			while (myTaskResult.next()) {
				queryFirstName = "Select firstName from usercred where userName=\'"
						+ (myTaskResult.getString(4)) + "\'";
				firstNameResult = dbconn.getResult(queryFirstName, conn);
				if (firstNameResult.next()) {
					List<String> item = new ArrayList<String>();
					item.add(myTaskResult.getString(1));
					item.add(myTaskResult.getString(2));
					item.add(myTaskResult.getString(3));
					item.add(firstNameResult.getString(1));
					item.add(myTaskResult.getString(4));
					item.add(myTaskResult.getString(5));
					item.add(myTaskResult.getString(6));
					item.add(myTaskResult.getString(7));
					tsk.add(item);
				} else {
					List<String> item = new ArrayList<String>();
					item.add(myTaskResult.getString(1));
					item.add(myTaskResult.getString(2));
					item.add(myTaskResult.getString(3));
					item.add("null");
					item.add(myTaskResult.getString(4));
					item.add(myTaskResult.getString(5));
					item.add(myTaskResult.getString(6));
					item.add(myTaskResult.getString(7));
					tsk.add(item);

				}

			}
			String json2 = new Gson().toJson(tsk);
			response.setContentType("application/json"); // Set content type of
															// the response so
															// that jQuery knows
															// what it can
															// expect.
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json2);

		}

		catch (Exception e) {
			logger.error("Display recur task has a problem working",e);
		} finally {
			out.close();
		}
	} // displayMyTasks() method close

	
	protected void displayGroupTasks(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<List<String>> tsk = new ArrayList<List<String>>();

		PrintWriter out = response.getWriter();
		response.setHeader("Cache-control", "no-cache, no-store");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "-1");

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Access-Control-Max-Age", "86400");

		try {
			username = request.getParameter("username");
			String queryGroupID = "Select groupID from userCred where userName= \'"
					+ username + "\'";
			dbconn = new DatabaseConnection();
			conn = dbconn.setConnection();
			groupTaskResult = dbconn.getResult(queryGroupID, conn);
			String groupID = null;
			while (groupTaskResult.next()) {     

				groupID = groupTaskResult.getString(1);
			}

			queryGroupTaskDetail = "Select taskName, taskPoints,dueDate,taskassignment.userName,taskAssignment.taskID,taskdescription.recur_days, "
					+ "taskdescription.completedOn from taskdescription join taskAssignment on taskAssignment.taskID=taskdescription.taskID "
					+ "where taskdescription.groupID= \'  "
					+ groupID + "\' and taskdescription.completedOn IS NULL ";
			
			dbconn = new DatabaseConnection();
			conn = dbconn.setConnection();
			groupTaskResult= dbconn.getResult(queryGroupTaskDetail, conn);

			while (groupTaskResult.next()) {
				queryFirstName = "Select firstName from usercred where userName=\'"
						+ (groupTaskResult.getString(4)) + "\'";
				firstNameResult = dbconn.getResult(queryFirstName, conn);
				if (firstNameResult.next()) {
					List<String> item = new ArrayList<String>();
					item.add(groupTaskResult.getString(1));
					item.add(groupTaskResult.getString(2));
					item.add(groupTaskResult.getString(3));
					item.add(firstNameResult.getString(1));
					item.add(groupTaskResult.getString(4));
					item.add(groupTaskResult.getString(5));
					item.add(groupTaskResult.getString(6));
					item.add(groupTaskResult.getString(7));
					tsk.add(item);
				} else {
					List<String> item = new ArrayList<String>();
					item.add(groupTaskResult.getString(1));
					item.add(groupTaskResult.getString(2));
					item.add(groupTaskResult.getString(3));
					item.add("null");
					item.add(groupTaskResult.getString(4));
					item.add(groupTaskResult.getString(5));
					item.add(groupTaskResult.getString(6));
					item.add(groupTaskResult.getString(7));
					tsk.add(item);

				}

			}
			String json2 = new Gson().toJson(tsk);
			response.setContentType("application/json"); // Set content type of
															// the response so
															// that jQuery knows
															// what it can
															// expect.
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json2);

		}

		catch (Exception e) {
			RequestDispatcher rd = request
					.getRequestDispatcher("/LoginFailed.jsp");
			rd.forward(request, response);
		} finally {
			out.close();
		}
	} // displayGroupTasks() method close
	
	protected void displayAllTasks(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<List<String>> tsk = new ArrayList<List<String>>();

		PrintWriter out = response.getWriter();
		response.setHeader("Cache-control", "no-cache, no-store");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "-1");

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Access-Control-Max-Age", "86400");

		try {
			username = request.getParameter("username");
			String queryGroupID = "Select groupID from userCred where userName= \'"
					+ username + "\'";
			dbconn = new DatabaseConnection();
			conn = dbconn.setConnection();
			groupTaskResult = dbconn.getResult(queryGroupID, conn);
			String groupID = null;
			while (groupTaskResult.next()) {     

				groupID = groupTaskResult.getString(1);
			}

			queryGroupTaskDetail = "Select taskAssignment.taskID, taskName, recur_days,completedOn, taskassignment.userName from taskdescription "
					+ "join taskAssignment on taskAssignment.taskID=taskdescription.taskID where completedOn IS NOT NULL";
			dbconn = new DatabaseConnection();
			conn = dbconn.setConnection();
			groupTaskResult= dbconn.getResult(queryGroupTaskDetail, conn);

			while (groupTaskResult.next()) {
				queryFirstName = "Select firstName from usercred where userName=\'"
						+ (groupTaskResult.getString(5)) + "\'";
				firstNameResult = dbconn.getResult(queryFirstName, conn);
				if (firstNameResult.next()) {
					List<String> item = new ArrayList<String>();
					item.add(groupTaskResult.getString(1));
					item.add(groupTaskResult.getString(2));
					item.add(groupTaskResult.getString(3));
					item.add(groupTaskResult.getString(4));
					item.add(firstNameResult.getString(1));

					tsk.add(item);
				}
			}
			String json2 = new Gson().toJson(tsk);
			response.setContentType("application/json"); // Set content type of
															// the response so
															// that jQuery knows
															// what it can
															// expect.
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json2);

		}

		catch (Exception e) {
			RequestDispatcher rd = request
					.getRequestDispatcher("/LoginFailed.jsp");
			rd.forward(request, response);
		} finally {
			out.close();
		}
	} // displayGroupTasks() method close
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String operation;
		operation = request.getParameter("operation");
		if (operation.equals("listMyTasks")) {
			displayMyTasks(request, response);
		}
		else if (operation.equals("listGroupTasks")) {
			displayGroupTasks(request, response);
		}
		else if (operation.equals("listAllTasks")) {
			displayAllTasks(request, response);
		}		

	}
}
