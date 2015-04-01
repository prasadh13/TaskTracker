
package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

/**
 * Servlet implementation class AssignTask
 */
@WebServlet("/AssignTask")
public class AssignTask extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DatabaseConnection dbconn;
	ResultSet userRes;
	Connection conn = null;  
	Logger logger = Logger.getLogger(AssignTask.class);
	
	protected void taskAssignment(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException{
		Map<Integer,Float> adjustPoints = new HashMap<Integer,Float>();
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		try {
			dbconn = new DatabaseConnection();
			conn = dbconn.setConnection();
			int taskID = Integer.parseInt(request.getParameter("taskID"));
			String name = request.getParameter("name");	
			int currentMaster = 0;
			Boolean flag = true;
			
			String checkMasterQuery = "Select Master from taskdescription where taskID= \'" + taskID+"\'";
			ResultSet masterResult = dbconn.getResult(checkMasterQuery, conn);
			if(masterResult.next()){
				int master = masterResult.getInt(1);
				if(master==0){
					flag = false;
				}
				
			}
			
			
			String queryUsername = "Select usercred.userName from taskdescription join usercred on usercred.groupID=taskdescription.groupID where taskdescription.taskID= \'" + taskID + "\'"+"and usercred.userName = \'" + name + "\'";
			System.out.println(queryUsername);
			ResultSet res = dbconn.getResult(queryUsername, conn);
			if(res.next()){
				String userName = res.getString(1);
				String queryGroupid = "Select groupID from userCred where userName= \'"
						+ userName + "\'";
				queryUsername = queryGroupid;
				dbconn = new DatabaseConnection();
				conn = dbconn.setConnection();
				res = dbconn.getResult(queryUsername, conn);
				String groupID = null;
				while (res.next()) {

					groupID = res.getString(1);
				}
			if (flag==true){	
				String queryCollectInfo = "Select taskPoints,dueDate,taskAssignment.taskID,taskdescription.Master from taskdescription join taskAssignment on taskAssignment.taskID=taskdescription.taskID where taskdescription.groupID = \'"
						+ groupID + "\'" + " and taskdescription.Master <> 0 and taskdescription.status=0 and taskassignment.userName is NULL";
				queryUsername = queryCollectInfo;

				ResultSet res2 = dbconn.getResult(queryUsername, conn);
				float totalPoints = (float) 0.0; 
				while (res2.next()) {
					adjustPoints.put(res2.getInt(3),res2.getFloat(1));
					totalPoints = totalPoints + res2.getInt(1);
					if(res2.getInt(3)==taskID){
						currentMaster = res2.getInt(4);
					}
				}
				logger.info("The size of tasks is:"+ adjustPoints.size());
				
				if(adjustPoints.size()>1){
					
				float distributePoints = (float) (adjustPoints.get(taskID) * 0.8);
				adjustPoints.remove(taskID);
				
				 Iterator it = adjustPoints.entrySet().iterator();
				    while (it.hasNext()) {
				        Map.Entry pairs = (Map.Entry)it.next();
				        float value = Float.parseFloat(pairs.getValue().toString());
				        int key = Integer.parseInt(pairs.getKey().toString());
				        
				        float adjustedValue = value + ((value/totalPoints)*distributePoints);
				        adjustPoints.put(key,adjustedValue);
				        
				        
						
						String queryMastertask = "Select Master from taskdescription where taskID= \'" + key + "\'";
						ResultSet resultMastertask = dbconn.getResult(queryMastertask, conn);
						while(resultMastertask.next()){
							if(resultMastertask.getInt(1)!=currentMaster){
							System.out.println("aaya");
							String updateMastertask = "update mastertasktable SET TaskPoints = \'" + adjustedValue + "\' where MasterTaskNo = \'" + resultMastertask.getInt(1) + "\'";
							Statement statement2 = conn.createStatement();
							statement2.execute(updateMastertask);
							
							
							String updateMaster = "update taskdescription SET taskPoints = \'" + adjustedValue + "\' where taskID= \'" + key + "\'";
							Statement statement = conn.createStatement();
							statement.execute(updateMaster);
							}
							
						}

				        it.remove(); 
				        // avoids a ConcurrentModificationException
				    }
				}
				
			}
				
				
				

				System.out.println(taskID);
				String query2 = "update taskassignment SET userName = \'" + userName + "\' where taskID= \'" + taskID + "\'";
				Statement statement = conn.createStatement();
				statement.execute(query2);
				
			}
			System.out.println("done assignment");
			
			List list = new ArrayList();
			list.add("True");
			String json2 = new Gson().toJson(list);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json2);
			
			
			
			
		}catch (Exception e) {
			logger.error("Error in assigning task", e);

		} finally {
			conn.close();
			out.close();
		}
	}
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AssignTask() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			taskAssignment(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("error in servlet assign task", e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

