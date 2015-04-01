/*
 * Controller Servlet to facilitate communication 
 * JSP view and MySQL Database
 * @date 9/28/2014
 */
package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

@WebServlet("/displayAndAddMembers")
public class displayAndAddMembers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(displayAndAddMembers.class);
	Connection conn;
	ResultSet res;
	ResultSet res3, res4;
	String username, password, query;

	DatabaseConnection dbconn;

	protected void checkMember(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		PreparedStatement preparedStatement = null;
		List<String> list = new ArrayList<String>();
		ResultSet resultFirstname;
		try {
			username = request.getParameter("username");
			String usernameMember = request.getParameter("name");
			System.out.println(usernameMember);
			String queryFirstname = "select firstName from usercred where username = \'"
					+ usernameMember + "\'";
			logger.info(queryFirstname);
			query = queryFirstname;
			dbconn = new DatabaseConnection();
			conn = dbconn.setConnection();
			resultFirstname = dbconn.getResult(query, conn);
			if (!resultFirstname.next()) {
				list.add("false");
				String json2 = new Gson().toJson(list);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json2);
			} else {
				addMember(request, response, resultFirstname.getString(1));

			}
		} catch (Exception e) {
			logger.error("Check memeber in adding members error",e);
		} finally {
			out.close();
		}

	}

	protected void addMember(HttpServletRequest request,
			HttpServletResponse response, String userName)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		PreparedStatement preparedStatement = null;
		ResultSet resultGroupid;
		List<String> list = new ArrayList<String>();
		try {
			username = request.getParameter("username");
			String usernameMember = request.getParameter("name");
			System.out.println(usernameMember);
			dbconn = new DatabaseConnection();
			conn = dbconn.setConnection();
			String queryGroupid = "select groupID from usercred where username = \'"
					+ username + "\'";
			query = queryGroupid;
			resultGroupid = dbconn.getResult(query, conn);
			if (resultGroupid.next()) {
				String groupid = resultGroupid.getString(1);
				System.out.println(resultGroupid.getString(1));
				String temp2 = "update usercred SET groupID =" + groupid
						+ " where userName = \'" + usernameMember + "\'";
				preparedStatement = conn.prepareStatement(temp2);

				preparedStatement.executeUpdate();

				list.add(userName);

				String json2 = new Gson().toJson(list);
				response.setContentType("application/json"); // Set content type of the response so that jQuery knows what it can expect.
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json2);
			}
		} catch (Exception e) {
			logger.error("Add member has a problem working",e);
			e.printStackTrace();
		} finally {
			out.close();
		}

	}

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		class DataObj {
			String userName;
			double totalPoints;
			double iniPoints;
			double weekPoints;
			double compPoints;

			DataObj() {
				userName = null;
				totalPoints = 0.0;
				iniPoints = 0;
				weekPoints = 0;
			}

			public String getUserName() {
				return userName;
			}

			public void setUserName(String userName) {
				this.userName = userName;
			}

			public double getTotalPoints() {
				return totalPoints;
			}

			public void setTotalPoints(String totalPoints) {
				this.totalPoints = Double.parseDouble(totalPoints);
			}

			public double getIniPoints() {
				return iniPoints;
			}

			public void setIniPoints(String iniPoints) {
				this.iniPoints = Double.parseDouble(iniPoints);
			}

			public double getWeekPoints() {
				return weekPoints;
			}

			public void setWeekPoints(String weekPoints) {
				this.weekPoints = Double.parseDouble(weekPoints);
			}
			
			public double getCompPoints() {
				return compPoints;
			}

			public void setCompPoints(String compPoints) {
				this.compPoints = Double.parseDouble(compPoints);
			}

		}

		List<DataObj> list = new ArrayList<DataObj>();
		PrintWriter out = response.getWriter();
		response.setHeader("Cache-control", "no-cache, no-store");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "-1");

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Access-Control-Max-Age", "86400");

		List<DataObj> list2 = new ArrayList<DataObj>();

		try {
			AdvanceDateBackend ad = new AdvanceDateBackend();
			SimpleDateFormat dateOnly = new SimpleDateFormat("MM/dd/yyyy");
			
			Date date = ad.getDate().getTime();  //Get current date
			
			String dateString = dateOnly.format(date);
			Date today = dateOnly.parse(dateString);
			java.sql.Date sqlToday = new java.sql.Date(today.getTime());  // Convert from java to SQL date
			logger.info("Today SQL " + sqlToday);
			
			Calendar c = Calendar.getInstance();
			c.setTime(today);
			int recurDay = 335;   // Day of the year for start of recur '2014-12-01'

			int dayOfYear = ad.getDate().get(Calendar.DAY_OF_YEAR);

			logger.info("Day of year:" + recurDay + " " + dayOfYear);

			int lowday = (dayOfYear - recurDay) % 7;
			Date lowerN = null, higherN = null;

			c.add(Calendar.DATE, -1 * lowday);
			lowerN = c.getTime();
			System.out.println("Lowday" + lowday);
			String lowerString = dateOnly.format(lowerN);
			Date lower = dateOnly.parse(lowerString);       //  Start of current week
			java.sql.Date sqlLower2 = new java.sql.Date(lower.getTime());
			String sqlLower = dateOnly.format(sqlLower2);
			
			System.out.println("Lower:" + lower);
			int diff = lowday + (7 - lowday);
			
			logger.info("lower string:"+lowerString);
			
			logger.info("Diff" + diff);
			c.add(Calendar.DATE, diff);
			higherN = c.getTime();
			String higherString = dateOnly.format(higherN);
			Date higher = dateOnly.parse(higherString);    // End of current week
			java.sql.Date sqlHigher2 = new java.sql.Date(higher.getTime());
			String sqlHigher = dateOnly.format(sqlHigher2);
			logger.info("Higher:" + sqlHigher);

			username = request.getParameter("username");

			String queryTemp = "select groupID from usercred where username = \'"
					+ username + "\'";
			query = queryTemp;
			dbconn = new DatabaseConnection();
			conn = dbconn.setConnection();
			logger.info("connected");
			res = dbconn.getResult(query, conn);
			if (res.next()) {
				logger.info("Result success welcome " + res.getString(1));
				String queryUsercred = "select userName,points from usercred where groupID="+ res.getString(1) + " order by userName asc";
				System.out.println(queryUsercred);
				
				ResultSet rsTotal = dbconn.getResult(queryUsercred, conn);

				while (rsTotal.next()) {					
					DataObj dataObj = new DataObj();
					dataObj.setUserName(rsTotal.getString(1));
					dataObj.setTotalPoints(rsTotal.getString(2));
					list.add(dataObj);
					logger.info("rs total result"+rsTotal.getString(1));
					System.out.println(dataObj.getUserName());
				}

				if (list.size() > 0) {
					String queInit = "Select userName, sum(taskPoints) from taskdescription join taskassignment on "
							+ "taskAssignment.taskID=taskdescription.taskID where taskdescription.status = 0 "
							+ "and taskassignment.userName is NOT NULL and dueDate <= \'"+sqlLower+ "\' and groupID=\'"+ res.getString(1)+ "\'"
							+ "  group by userName order by userName asc";
					ResultSet rsInit = dbconn.getResult(queInit, conn);

					while (rsInit.next()) {
						for (int i = 0; i < list.size(); i++) {
							DataObj dataObj = list.get(i);
							if (dataObj.getUserName().compareTo(rsInit.getString(1)) == 0) {
								System.out.println("/Initial points"+ rsInit.getString(2));
								dataObj.setIniPoints(rsInit.getString(2));
							}
						}
					}
					logger.info("sql lower"+ sqlLower);
					logger.info("sql hower"+ sqlHigher);
					String queWeek = "Select userName, sum(taskPoints) from taskdescription join taskassignment on "
							+ "taskassignment.taskID=taskdescription.taskID where taskdescription.status = 0 "
							+ "and taskassignment.userName is NOT NULL and dueDate BETWEEN \'"+ sqlLower+ "\' and \'"+ sqlHigher+ "\' and groupID=\'"
							+ res.getString(1)+ "\'  group by userName order by userName asc";
					ResultSet rsWeek = dbconn.getResult(queWeek, conn);
					while (rsWeek.next()) {
						logger.info("rs week result"+rsWeek.getString(1));
						for (int i = 0; i < list.size(); i++) {
							DataObj dataObj = list.get(i);
							logger.info("rs week result"+rsWeek.getString(1));

							if (dataObj.getUserName().compareTo(rsWeek.getString(1)) == 0) {
								dataObj.setWeekPoints(rsWeek.getString(2));
							}
						}
					}
					
					String queComp = "Select userName, sum(taskPoints) from taskdescription join taskassignment on "
							+ "taskAssignment.taskID=taskdescription.taskID where taskdescription.status = 1 "
							+ "and completedOn BETWEEN \'"+ sqlLower+ "\' and \'"+ sqlHigher+ "\' and groupID=\'"
							+ res.getString(1)+ "\'  group by userName order by userName asc";
					ResultSet rsComp = dbconn.getResult(queComp, conn);

					while (rsComp.next()) {
						for (int i = 0; i < list.size(); i++) {
							DataObj dataObj = list.get(i);
							if (dataObj.getUserName().compareTo(rsComp.getString(1)) == 0) {
								dataObj.setCompPoints(rsComp.getString(2));
							}
						}
					}
				}
			}

			List<List<String>> listStr = new ArrayList<List<String>>();
			for (int i=0; i < list.size(); i++)
			{
				DataObj dataObj = list.get(i);
				List<String> objAsList = new ArrayList<String>(); 
				objAsList.add(dataObj.getUserName());
				objAsList.add(Double.toString(dataObj.getTotalPoints()));
				objAsList.add(Double.toString(dataObj.getIniPoints()));
				objAsList.add(Double.toString(dataObj.getWeekPoints()));
				objAsList.add(Double.toString(dataObj.getCompPoints()));
				logger.info("Initial points: "+dataObj.getIniPoints());
				logger.info("Weekly points: "+dataObj.getWeekPoints());
				listStr.add(objAsList);
			}
			String json2 = new Gson().toJson(listStr);
			response.setContentType("application/json"); // Set content type of the response so that jQuery
															// knows what it can expect.
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json2);

		} catch (Exception e) {
			logger.error("Timebox has a problem working",e);
				e.printStackTrace();
		} finally {
			out.close();
		}
	} // processRequest() method close

	public displayAndAddMembers() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String operation;
		operation = request.getParameter("operation");
		if (operation.equals("listMembers")) {
			logger.info("inside members");
			processRequest(request, response);
		}

		if (operation.equals("checkMember")) {
			logger.info("inside add members");
			checkMember(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String operation;
		operation = request.getParameter("operation");
		if (operation.equals("listMembers")) {
			processRequest(request, response);
		}

		else if (operation.equals("checkMember")) {
			logger.info("inside check members");
			checkMember(request, response);
		}
	}
}
