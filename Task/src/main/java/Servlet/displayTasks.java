package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
//import java.sql.PreparedStatement;
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

@WebServlet("/displayTasks")
public class displayTasks extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(displayTasks.class);
	Connection conn;
	ResultSet res;
	ResultSet res2;
	ResultSet res3;
	String username, password, query;

	DatabaseConnection dbconn;

	protected void processRequest(HttpServletRequest request,
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

			String temp2 = "Select groupID from userCred where userName= \'"
					+ username + "\'";
			query = temp2;
			dbconn = new DatabaseConnection();
			conn = dbconn.setConnection();
			res = dbconn.getResult(query, conn);
			String groupID = null;
			while (res.next()) {

				groupID = res.getString(1);
			}

			String temp = "Select taskName, taskPoints,dueDate,taskassignment.userName,taskAssignment.taskID,taskdescription.recur_days, "
					+ "taskdescription.completedOn, taskdescription.Master from taskdescription join taskAssignment on taskAssignment.taskID=taskdescription.taskID where taskdescription.groupID= \'"
					+ groupID + "\'" + "and taskdescription.status=0";
			
			query = temp;

			dbconn = new DatabaseConnection();
			conn = dbconn.setConnection();
			res = dbconn.getResult(query, conn);

			while (res.next()) {
				String query2 = "Select firstName from usercred where userName=\'"
						+ (res.getString(4)) + "\'";
				res3 = dbconn.getResult(query2, conn);
				if (res3.next()) {
					List<String> item = new ArrayList<String>();
					item.add(res.getString(1));
					item.add(res.getString(2));
					item.add(res.getString(3));
					item.add(res3.getString(1));
					item.add(res.getString(4));
					item.add(res.getString(5));
					item.add(res.getString(6));
					item.add(res.getString(7));
					item.add(res.getString(8));
					tsk.add(item);
				} else {
					List<String> item = new ArrayList<String>();
					item.add(res.getString(1));
					item.add(res.getString(2));
					item.add(res.getString(3));
					item.add("null");
					item.add(res.getString(4));
					item.add(res.getString(5));
					item.add(res.getString(6));
					item.add(res.getString(7));
					item.add(res.getString(8));
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
	} // processRequest() method close
	
	protected void pendingTasks(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		List<List<String>> pendingTask = new ArrayList<List<String>>();

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
			System.out.println(username);

			String tempQ = "Select groupID from userCred where userName= \'"
					+ username + "\'";
			query = tempQ;
			dbconn = new DatabaseConnection();
			conn = dbconn.setConnection();
			res = dbconn.getResult(query, conn);
			String groupID = null;
			while (res.next()) {

				groupID = res.getString(1);
			}

			String temp = "Select taskName, taskPoints,dueDate,taskassignment.userName,taskAssignment.taskID,taskdescription.recur_days, "
					+ "taskdescription.completedOn, taskdescription.Master from taskdescription join taskAssignment on taskAssignment.taskID=taskdescription.taskID where taskdescription.groupID= \'"
					+ groupID + "\'" + "and taskdescription.status=0";
			
			query = temp;
			dbconn = new DatabaseConnection();
			conn = dbconn.setConnection();
			res = dbconn.getResult(query, conn);

			while (res.next()) {
				String query2 = "Select firstName from usercred where userName=\'"
						+ (res.getString(4)) + "\'";
				res3 = dbconn.getResult(query2, conn);
				if (res3.next()) {
					List<String> item = new ArrayList<String>();
					item.add(res.getString(1));
					item.add(res.getString(2));
					item.add(res.getString(3));
					item.add(res3.getString(1));
					item.add(res.getString(4));
					item.add(res.getString(5));
					item.add(res.getString(6));
					item.add(res.getString(7));
					item.add(res.getString(8));
					pendingTask.add(item);
				} else {
					List<String> item = new ArrayList<String>();
					item.add(res.getString(1));
					item.add(res.getString(2));
					item.add(res.getString(3));
					item.add("null");
					item.add(res.getString(4));
					item.add(res.getString(5));
					item.add(res.getString(6));
					item.add(res.getString(7));
					item.add(res.getString(8));
					pendingTask.add(item);

				}

			}
			String json2 = new Gson().toJson(pendingTask);
			response.setContentType("application/json"); // Set content type of
															// the response so
															// that jQuery knows
															// what it can
															// expect.
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json2);

		}

		catch (Exception e) {
			logger.error("Dsiplay task has a problem working",e);
		} finally {
			out.close();
		}
	}
	
	
	
	

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String operation;
		operation = request.getParameter("operation");
		if (operation.equals("listTasks")) {
			processRequest(request, response);
		}

	}

}
