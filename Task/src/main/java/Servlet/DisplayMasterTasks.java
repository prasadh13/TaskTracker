package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

/**
 * Servlet implementation class DisplayMasterTasks
 */
public class DisplayMasterTasks extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(DisplayMasterTasks.class);  
    /**
     * @see HttpServlet#HttpServlet()
     */
	Connection conn;
	ResultSet res;
	ResultSet res2;
	ResultSet res3;
	String username, password, query;

	DatabaseConnection dbconn;
	void getMasterTasks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

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

				String query = "Select MasterTaskNo,TaskName,TaskPoints from mastertasktable";
				res2 = dbconn.getResult(query, conn);
				System.out.println(query);
				while(res2.next()){		
					System.out.println("sdfvsd");
						List<String> item = new ArrayList<String>();
						item.add(res2.getString(1));
						item.add(res2.getString(2));
						item.add(res2.getString(3));
						tsk.add(item);
						
				}
				
				String json2 = new Gson().toJson(tsk);
				response.setContentType("application/json"); 
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json2);
			}

			catch (Exception e) {
				logger.error("Display master tasks has a problem working",e);
				RequestDispatcher rd = request
						.getRequestDispatcher("/LoginFailed.jsp");
				rd.forward(request, response);
			} finally {
				out.close();
			}
		}
	
	
	
	
	
	
	
	
    public DisplayMasterTasks() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		getMasterTasks(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
