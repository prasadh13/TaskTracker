/*
 * Controller Servlet to add task from group home page 
 * to MySQL Database
 * @date 10/09/2014
 */
package Servlet;
import org.apache.log4j.*;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




import org.apache.log4j.Logger;

import com.google.gson.Gson;

/**
 * Servlet implementation class AddTask
 */
@WebServlet("/AddTask")
public class AddTask extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(AddTask.class);
	
	Connection conn = null;


	String taskName, dueDate,taskPoints, groupID,query,userName;
	int  dueIn; // Due in these many days
	Date nextDate;
	String dateString;

	DatabaseConnection dbconn;
	ResultSet userRes;

	protected void taskAddition(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException, SQLException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		Random random = new Random();
		try {
			dbconn = new DatabaseConnection();
			conn = dbconn.setConnection();
			taskName = request.getParameter("taskName");
			dueDate = request.getParameter("dueDate");
			taskPoints = request.getParameter("taskPoints");
			String assignedMember = request.getParameter("assignedMember");
			String username = request.getParameter("username");
			String recurDays = request.getParameter("recurDays");
			String isMaster = request.getParameter("isMaster");

			

			String queryUsername = "Select groupID from usercred where username=\'"+username+"\'";
			ResultSet resultUsername = dbconn.getResult(queryUsername, conn);
			int groupID = 0;
			if(resultUsername.next()){
				groupID = resultUsername.getInt(1);
			}
			
			PreparedStatement preparedStatement;
			PreparedStatement preparedStatement3;
			if(isMaster.equals("1")){
				
				 preparedStatement3 = conn.prepareStatement("insert into mastertasktable(taskName,taskPoints,recur_days) values (?,?,?)");	       	 
		         preparedStatement3.setString(1, taskName);
		         preparedStatement3.setFloat(2, Float.parseFloat(taskPoints));
		         preparedStatement3.setFloat(3, Float.parseFloat(recurDays));	
		         preparedStatement3.executeUpdate();
			}
			else{	
			
				if(dueDate.length() < 10){
					dueIn = Integer.parseInt(dueDate);
					Calendar cal = Calendar.getInstance();
					Date currDate = new Date();
					cal.setTime(currDate);
					cal.add(Calendar.DATE, dueIn);
					nextDate = cal.getTime();
					SimpleDateFormat dateOnly = new SimpleDateFormat("MM/dd/yyyy");
					dateString = dateOnly.format(nextDate);
					
				}
				else {
					dateString = dueDate;
				}
			 System.out.println("creating a normal task");
			 int taskID = random.nextInt();
	         preparedStatement = conn.prepareStatement("insert into taskdescription(taskID,taskName,dueDate,groupID,taskPoints,recur_days,Master) values (?,?, ?, ?, ?,?,?)");
	       	 preparedStatement.setInt(1,taskID);	       	 
	         preparedStatement.setString(2, taskName);
	         preparedStatement.setString(3, dateString);
	         preparedStatement.setInt(4,groupID);
	         preparedStatement.setFloat(5, Float.parseFloat(taskPoints));
	         preparedStatement.setFloat(6, Float.parseFloat(recurDays));
	         preparedStatement.setFloat(7, (float) 0.0);
	         preparedStatement.executeUpdate();
			         
			 PreparedStatement preparedStatement2;
			 if(!assignedMember.equals("None")){
				preparedStatement2 = conn.prepareStatement("insert into taskassignment(taskID,userName) values (?,?)");
				preparedStatement2.setInt(1, taskID);
			preparedStatement2.setString(2, assignedMember);
			
			preparedStatement2.executeUpdate();
			}
			else{
				preparedStatement2 = conn.prepareStatement("insert into taskassignment(taskID) values (?)");
				preparedStatement2.setInt(1,taskID);
				preparedStatement2.executeUpdate();
				
			}

				System.out.println("Successfully inserted task and into database!");
				RequestDispatcher rd = request.getRequestDispatcher("/home.jsp");
				rd.forward(request, response);
			}
				List list = new ArrayList();
				list.add("true");
				String json2 = new Gson().toJson(list);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json2);

			} catch (Exception e) {
				logger.error("Add task has a problem working",e);
			} finally {
				conn.close();
				out.close();
			}
		}

    public AddTask() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			taskAddition(request, response);
			
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
			logger.error("Add task has a problem working");
			taskAddition(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}



