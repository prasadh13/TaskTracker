package Servlet;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.*;

import org.junit.Test;

import com.google.gson.Gson;

import Servlet.DatabaseConnection;
import Servlet.displayAndAddMembers;
import Servlet.DisplayMasterTasks;

public class TestingItr3 {
	
	 
	@Test
	//test for MasterTasks
	
    public void testDisplayMasterTasks() throws Exception {
		Gson gson = new Gson();
        HttpServletRequest request = mock(HttpServletRequest.class);       
        HttpServletResponse response = mock(HttpServletResponse.class); 
        ResultSet res = mock(ResultSet.class);
        Connection conn;
        DatabaseConnection dbconn = mock(DatabaseConnection.class);
        DisplayMasterTasks vMasterTasks = new DisplayMasterTasks();
        when(request.getParameter("username")).thenReturn("phirlika");

        StringWriter sw = new StringWriter();
        PrintWriter printWriter = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(printWriter);
        vMasterTasks.doGet(request,response);
      
        List a = gson.fromJson(sw.toString(),ArrayList.class);
        List<List<String>> actual = new ArrayList<List<String>>();
        List<String> actualRow = new ArrayList<String>();
      
      	actualRow.add("1"); actualRow.add("Clear Trash"); actualRow.add("38");
      	actual.add(actualRow);
      	
      	actualRow = new ArrayList<String>();
      	
      	actualRow.add("3"); actualRow.add("Clean Dining table."); actualRow.add("4");
      	actual.add(actualRow);
       
       System.out.println("---------------------------------------------------------------------------------");
       System.out.println("Actual contents expected "+ actual); 
       System.out.println("Contents returned from the test "+ a);
       System.out.println("---------------------------------------------------------------------------------");
       assertTrue(a.containsAll(actual));
       
	}
	
	
	
	
	@Test
	//test for AddTask(testing a bug which reported erronous output)
	
    public void testAddTask() throws Exception {
		Gson gson = new Gson();
        HttpServletRequest request = mock(HttpServletRequest.class);       
        HttpServletResponse response = mock(HttpServletResponse.class); 
        ResultSet res = mock(ResultSet.class);
        Connection conn;
        DatabaseConnection dbconn = mock(DatabaseConnection.class);
        AddTask vAddTask = new AddTask();
        when(request.getParameter("taskName")).thenReturn("Clean something");
        when(request.getParameter("dueDate")).thenReturn("12/21/2014");
        when(request.getParameter("taskPoints")).thenReturn("10");
        when(request.getParameter("assignedMember")).thenReturn("Prasad");
        when(request.getParameter("username")).thenReturn("phirlika");
        when(request.getParameter("recurDays")).thenReturn("15");
        when(request.getParameter("isMaster")).thenReturn("No");

        StringWriter sw = new StringWriter();
        PrintWriter printWriter = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(printWriter);
        vAddTask.doGet(request,response);
      
       List a = gson.fromJson(sw.toString(),ArrayList.class);
       List<String> actual = new ArrayList<String>();
	   actual.add("True");  

       
       System.out.println("---------------------------------------------------------------------------------");
	   System.out.println("Actual contents expected "+ actual);  
	   System.out.println("Contents returned from the test "+ a);
	   System.out.println("---------------------------------------------------------------------------------");
	   assertTrue(a.containsAll(actual));
	}

	@Test
	//test for displaying all weekly points, earned points and pending points for a user
	
    public void testDisplayAndAddMembers() throws Exception {
		Gson gson = new Gson();
        HttpServletRequest request = mock(HttpServletRequest.class);       
        HttpServletResponse response = mock(HttpServletResponse.class); 
        ResultSet res = mock(ResultSet.class);
        Connection conn;
        DatabaseConnection dbconn = mock(DatabaseConnection.class);
        displayAndAddMembers daam = new displayAndAddMembers();
        when(request.getParameter("username")).thenReturn("phirlika");
        when(request.getParameter("operation")).thenReturn("listMembers");
        StringWriter sw = new StringWriter();
        PrintWriter printWriter = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(printWriter);
        daam.doGet(request,response);
        List a = gson.fromJson(sw.toString(),ArrayList.class);
       
       	List<List<String>> actual = new ArrayList<List<String>>();
       	List<String> actualRow = new ArrayList<String>();
       
       	actualRow.add("mkothari"); actualRow.add("44.0"); actualRow.add("0.0");
       	actualRow.add("32.0"); actualRow.add("0.0"); 
       
       	actual.add(actualRow);
       	
       	actualRow = new ArrayList<String>();
       	
       	actualRow.add("phirlika"); actualRow.add("476.0"); actualRow.add("0.0");
       	actualRow.add("0.0"); actualRow.add("0.0"); 
       
       	actual.add(actualRow);
       	actualRow = new ArrayList<String>();
       	
       	
       	actualRow.add("rakya"); actualRow.add("74.0"); actualRow.add("0.0");
       	actualRow.add("0.0"); actualRow.add("0.0"); 
       
       	actual.add(actualRow);
       	actualRow = new ArrayList<String>();
       	
       	actualRow.add("sksamant"); actualRow.add("33.0"); actualRow.add("0.0");
       	actualRow.add("0.0"); actualRow.add("0.0"); 
       
       	actual.add(actualRow);
       	actualRow = new ArrayList<String>();
       	
       	
       	actualRow.add("vcpatil"); actualRow.add("11.0"); actualRow.add("0.0");
       	actualRow.add("0.0"); actualRow.add("0.0"); 
       
       	actual.add(actualRow);

       	System.out.println("---------------------------------------------------------------------------------");
       	System.out.println("Actual contents expected "+ actual);
    	System.out.println("Contents returned from the test "+ a);
    	System.out.println("---------------------------------------------------------------------------------");
    	
    	assertEquals(a, actual);
    	
	}

	
	
	@Test
	//test for task complete which had a data format bug: which exploded when an uparseable date was added.
	
    public void testTaskComplete() throws Exception {
		Gson gson = new Gson();
        HttpServletRequest request = mock(HttpServletRequest.class);       
        HttpServletResponse response = mock(HttpServletResponse.class); 
        ResultSet res = mock(ResultSet.class);
        Connection conn;
        DatabaseConnection dbconn = mock(DatabaseConnection.class);
        TaskComplete tc = new TaskComplete();
		
		
        when(request.getParameter("due_date")).thenReturn("2014/11/18");
        when(request.getParameter("taskID")).thenReturn("1008");
        when(request.getParameter("recur_days")).thenReturn("15");
        
        StringWriter sw = new StringWriter();
        PrintWriter printWriter = new PrintWriter(sw);
        when(response.getWriter())
        .thenReturn(printWriter);
        tc.doGet(request,response);
        List a = gson.fromJson(sw.toString(),ArrayList.class);
        List<String> actual = new ArrayList<String>();
 	   	actual.add("True");  
 	   	System.out.println("---------------------------------------------------------------------------------");
 	   	System.out.println("Actual contents expected "+ actual);  
 	   	System.out.println("Contents returned from the test "+ a);
 	   	System.out.println("---------------------------------------------------------------------------------");
 	   	assertTrue(a.containsAll(actual));   
	}

}