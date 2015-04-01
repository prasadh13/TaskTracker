package Servlet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;




/*Added the config file option here to provide the flexibility of turning the 
 * calendar testability option either on or off. The config file has 2 attributes as flag and the advance date,
 * which the user wants to set it to.*/

public class AdvanceDateBackend {
	
	
	public Calendar getDate() throws IOException, ParseException{
		
		//reading the properties from the config file
		Properties prop = new Properties();								
		String propFileName = "config.properties";
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
		if (inputStream != null) {
			prop.load(inputStream);
		} 
		else {
			throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
		}
		
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		Calendar cal = Calendar.getInstance();

		//checking the flag calendarTestability
		if(Integer.parseInt(prop.getProperty("calendarTestability")) == 1){
			//setting the date as specified by the user in the cofig file
			cal.setTime(format.parse(prop.getProperty("advanceDate")));
			return cal;
		}
		else{
			System.out.println("After doing getInstance "+format.format(cal.getTime()));
			return cal;
		}
	}

}
