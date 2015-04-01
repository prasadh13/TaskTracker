/*
 * Servlet implementation class Validation
 * @date 10/9/2014
 */
package Servlet;

import java.io.IOException;
import java.sql.Connection;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

public class Validation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection conn = null;
	ResultSet rs;
	String queryUserName;
	Logger logger = Logger.getLogger(Validation.class);
	DatabaseConnection dbconn;
	Map<String, String> users = new LinkedHashMap<String, String>();
	
	//Method to fetch all the users from Usercred database table
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException, SQLException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

			String userName = request.getParameter("rusername");
			try {
				System.out.println(userName);
				dbconn = new DatabaseConnection();
				conn = dbconn.setConnection();
				queryUserName = "select userName from usercred where userName = \'"+ userName + "\'";
				logger.info("Query Username: "+queryUserName);
				 rs =dbconn.getResult(queryUserName, conn);
				 boolean unique = rs.first();
				 System.out.println("stored the value");
				 System.out.println(unique);
				 if(!unique)
				 {
					 users.put("valid","true");
					 String json2 = new Gson().toJson(users);
					 response.getWriter().write(json2);
				 }
			 else{
				 users.put("valid","false");
				 String json2 = new Gson().toJson(users);
				 response.getWriter().write(json2);
			 }
			}
			catch(Exception e) {
				logger.error("error in validation",e);
				e.printStackTrace();
			}
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Validation() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			processRequest(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			processRequest(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
