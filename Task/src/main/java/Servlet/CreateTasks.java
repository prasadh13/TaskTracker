package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

/**
 * Servlet implementation class CreateTasks
 */
public class CreateTasks extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(CreateTasks.class);
	Connection conn = null;


	String taskName, dueDate,taskPoints, groupID,query,userName;
	int  dueIn; // Due in these many days
	Date nextDate;
	String dateString;

	DatabaseConnection dbconn;
	ResultSet userRes;

	protected void taskCreation(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException, SQLException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		Random random = new Random();
		try {
			dbconn = new DatabaseConnection();
			conn = dbconn.setConnection();
			logger.info("inside add task");
			taskName = request.getParameter("taskName");
			dueDate = request.getParameter("dueDate");
			taskPoints = request.getParameter("taskPoints");
			String username = request.getParameter("username");
			String taskNo = request.getParameter("task_no");
			if(dueDate.length() < 10){
				dueIn = Integer.parseInt(dueDate);
				Calendar cal = Calendar.getInstance();
				Date currDate = new Date();
				cal.setTime(currDate);
				cal.add(Calendar.DATE, dueIn);
				nextDate = cal.getTime();
				SimpleDateFormat dateOnly = new SimpleDateFormat("MM/dd/yyyy");
				dateString = dateOnly.format(nextDate);
				logger.info("new date is......."+dateString);
				
			}
			else {
				dateString = dueDate;
			}
			String queryGroupid = "Select groupID from usercred where username=\'"+username+"\'";
			ResultSet resultGroupid = dbconn.getResult(queryGroupid, conn);
			int groupID = 0;
			if(resultGroupid.next()){
				logger.info("was in here");
				groupID = resultGroupid.getInt(1);
			}
			
			PreparedStatement preparedStatement;
			PreparedStatement preparedStatement2;
			
			 int taskID = random.nextInt();
	         preparedStatement = conn.prepareStatement("insert into taskdescription(taskID,taskName,dueDate,groupID,taskPoints,Master) values (?,?, ?, ?, ?,?)");
	       	 preparedStatement.setInt(1,taskID);	       	 
	         preparedStatement.setString(2, taskName);
	         preparedStatement.setString(3, dateString);
	         preparedStatement.setInt(4,groupID);
	         preparedStatement.setFloat(5, Float.parseFloat(taskPoints));
	         preparedStatement.setInt(6, Integer.parseInt(taskNo));
	         preparedStatement.executeUpdate();

				preparedStatement2 = conn.prepareStatement("insert into taskassignment(taskID) values (?)");
				preparedStatement2.setInt(1,taskID);
				preparedStatement2.executeUpdate();
					
				List list = new ArrayList();
				list.add("true");
				String json2 = new Gson().toJson(list);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json2);

			} catch (Exception e) {
				logger.error("Create master tasdk has a problem",e);

			} finally {
				conn.close();
				out.close();
			}
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateTasks() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			System.out.println("aaya in create");
			taskCreation(request,response);
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
			System.out.println("aaya in create");
			taskCreation(request,response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
