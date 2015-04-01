package Servlet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;



/*We have added the config file option here to provide the flexibility of turning the 
 * calendar testability option either on or off. The config file has 2 attributes as flag and the advance date,
 * which the user wants to set it to.*/
@WebServlet("/AdvanceDate")

public class AdvanceDate extends HttpServlet {
	
	Logger logger = Logger.getLogger(AdvanceDate.class);
	void getDate(HttpServletRequest request,
			HttpServletResponse response)  throws ServletException, IOException, ParseException{
		try {
		PrintWriter out = response.getWriter();
		response.setHeader("Cache-control", "no-cache, no-store");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "-1");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Access-Control-Max-Age", "86400");
		
		
		
		//reading the properties from the config file
		Properties prop = new Properties();
		String json;
		String propFileName = "config.properties";
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
		if (inputStream != null) {
			prop.load(inputStream);
		} 
		else {
			logger.error("Error in finding config file");
		}
		
		logger.info("This the config value: " + prop.getProperty("advanceDate"));
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		
		//checking the flag calendarTestability
		if(Integer.parseInt(prop.getProperty("calendarTestability")) == 1){
			//setting the date as specified by the user in the config file
			cal.setTime(format.parse(prop.getProperty("advanceDate")));
			System.out.println("After doing setTime "+ format.format(cal.getTime()));
			json = new Gson().toJson(format.format(cal.getTime()));
			response.setContentType("application/json"); 
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
		}
	}
		catch (Exception e) {
			System.out.println("Did not advcence Date: Error");
			e.printStackTrace();

		}
}
	
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String operation;
		operation = request.getParameter("operation");
		System.out.println("inside doGet(CT)");
		if (operation.equals("getDate")) {
			System.out.println("Inside doGet if");
			try {
				getDate(request, response);
			} catch (ParseException e) {
				logger.error("parse problem in Advance Date",e);
			//	e.printStackTrace();
			}
		}
	}
	

}
