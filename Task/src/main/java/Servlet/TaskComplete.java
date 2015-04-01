package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

/**
 * Servlet implementation class TaskComplete
 */
@WebServlet("/TaskComplete")
public class TaskComplete extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(TaskComplete.class);
	
	DatabaseConnection dbconn;
	ResultSet userRes;
	Connection conn = null;
	String query;
	protected void taskCompletion(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException, SQLException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		try {
			dbconn = new DatabaseConnection();
			conn = dbconn.setConnection();
			String dueDate1 = request.getParameter("due_date");
			int taskID = Integer.parseInt(request.getParameter("taskID"));
			int recur = Integer.parseInt(request.getParameter("recur_days"));
			String userName;

			
			String query2 = "select taskPoints,groupID from taskdescription where taskID= \'"+ taskID + "\'" ;
			ResultSet res = dbconn.getResult(query2, conn);
			if(res.next()){
				int groupID = res.getInt(2);
				String query3 = "Select taskassignment.userName from taskdescription join taskAssignment on taskAssignment.taskID=taskdescription.taskID where taskdescription.groupID= \'" + groupID + "\'" +"and taskdescription.taskID=\'"+taskID+"\'";
				ResultSet res2 = dbconn.getResult(query3, conn);
				if(res2.next()){
					userName = res2.getString(1);
					String query4 = "Select points from usercred where userName= \'" + userName + "\'";
					ResultSet res3 = dbconn.getResult(query4, conn);
					if(res3.next()){
						int points = res3.getInt(1);
						int totalPoints = points + res.getInt(1);
						String query5 = "update usercred SET points = \'"+ totalPoints + "\'" +" where userName= \'" + userName + "\'";
						Statement statement2 = conn.createStatement();
						statement2.execute(query5);
					}
					
				}
			
			}

			if(recur>0) {
				SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
				Calendar cal = Calendar.getInstance();
				cal.setTime(format.parse(dueDate1));
				cal.add(Calendar.DATE, recur);
				dueDate1 = format.format(cal.getTime()); 
				query = "update taskdescription SET dueDate = \'" + dueDate1 + "\'where taskID= \'" + taskID + "\'";
				String query3 = "update taskassignment SET username= DEFAULT where taskID= \'" + taskID + "\'";
				Statement statement2 = conn.createStatement();
				statement2.execute(query3);
			}else{
				SimpleDateFormat dateOnly = new SimpleDateFormat("MM/dd/yy");
			    Calendar date = Calendar.getInstance(); // today
			    String todayDate = dateOnly.format(date.getTime());
			    query = "update taskdescription SET status = 1, completedOn = \'"+ todayDate +"\' where taskID= \'" + taskID + "\'";
			}
				
			Statement statement = conn.createStatement();
			statement.execute(query);		
			List list = new ArrayList();
			list.add("True");
			String json2 = new Gson().toJson(list);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json2);
			
	} catch (Exception e) {
		logger.error("COmplete task has a problem working",e);
		e.printStackTrace();

	} finally {
		conn.close();
		out.close();
	}
}
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaskComplete() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			taskCompletion(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			taskCompletion(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
