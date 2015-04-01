<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Where Tasking matters</title>
		<!-- Adding all the required source files -->
		
		<link type="text/css" rel="stylesheet" href="css/bootstrap.min.css" />
		<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
		<link type="text/css" rel="stylesheet" href="css/bootstrapValidator.min.css" />
		<link type="text/css" rel="stylesheet" href="css/metro-bootstrap.min.css" />
		<link type="text/css" rel="stylesheet" href="//code.jquery.com/ui/1.11.1/themes/smoothness/jquery-ui.css">
		<link type="text/css" rel="stylesheet" href="css/style.css" />
		<script type="text/javascript" src="js/jquery-1.11.0.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="js/bootstrapValidator.min.js"></script>
		<script type="text/javascript" src="js/jquery-ui.min.js"></script> 
		<script type="text/javascript" src="js/jquery.widget.min.js"></script>
		<script type="text/javascript" src="js/metro-tile-transform.js"></script>
		<script type="text/javascript" src="js/userValidation.js"></script>
		<script type="text/javascript" src="js/AjaxResponseScript.js"></script>
		<script type="text/javascript" src="https://www.google.com/jsapi"></script>
		<script type="text/javascript">
      		google.load("visualization", "1", {packages:["corechart"]});
      		google.setOnLoadCallback(drawChart);
		</script>
		<script>
			  $(function() {
			    $( "#dueDate2" ).datepicker({ minDate: 0});	    
			  });
			  
			  $(document).on('focus','.dueDate', function(){
			      $(this).datepicker({ minDate: 0});
			    });
			  
			  
			  
		 </script>		 
		<script type="text/javascript">
			window.onload = function() {
				getGroupRecurTasks();
				advanceDate();
			};
		</script>
		<style type="text/css">
				th{text-align:center;}
		</style>
	</head>

	<body id="main">
			<!-- DIV for top most title bar on the home page -->
			<div class="container-fluid">
				<nav class="navbar navbar-default" role="navigation">
			  	<!-- Brand and toggle get grouped for better mobile display -->
			  
			    	<a class="navbar-brand">The Room mate Agreement</a>
			 		 <!-- Collect the nav links, forms, and other content for toggling -->
			  		<div class="collapse navbar-collapse navbar-ex1-collapse">
			    		<ul class="nav navbar-nav">
			      			<li><a href="#" style="text-decoration:none;" onclick="toggleVisibility('homePage'),clearBox('mybody'),clearBox('grpbody'),clearBox('assignedTo');">My Tasks</a></li>		      			
			    		</ul>
			    		<ul class="nav navbar-nav navbar-right" style="margin-right:1em;">
						      <li style="font-size:inherit; font-family:inherit; padding-top:1.14em;padding-right:2em;">Welcome, <%=request.getAttribute("person") %> !</li>
						      <li><a href="welcome.jsp">Logout</a></li>
			    		</ul>
			  		</div>
				</nav>
			
			<!--Tile options to the left of the screen  -->
			<div id="menu">
				<div class=row>
					<div class="col-md-3">
						<div class="thumbnail tile tile double tile-wide tile-orange" data-click="transform">
							<a href="#" style="text-decoration:none;" onclick="toggleVisibility('myroomies'),clearBox('newroomies');" >
								<i id="icon" class="fa fa-3x fa-users fa-inverse"></i>
								 <h1 id="icon-text">My Roomies</h1>
							</a>
						</div>
					</div>
				</div>
				
				<div class=row>
					<div class="col-md-3">
						<div class="thumbnail tile tile double tile-wide tile-emrald" data-click="transform">
							<a href="#" style="text-decoration:none;" onclick="toggleVisibility('grouptasks'),clearBox('tbody'),clearBox('tbody2');" >
								 <i id="icon" class="fa fa-3x fa-tasks fa-inverse"></i>
								 <h1 id="icon-text">Group Tasks</h1>
							</a>
						</div>
					</div>
				</div>
				
				<div class=row>
					<div class="col-md-3">
						<div class="thumbnail tile tile double tile-wide tile-maroon" data-click="transform">
							<a href="#" style="text-decoration:none;" onclick="toggleVisibility('addtasks'),clearBox('assignedTo'),clearBox('masterTaskTable');" >
								<i id="icon" class="fa fa-3x fa-cogs fa-inverse"></i>
								 <h1 id="icon-text">Add a task</h1>
							</a>
						</div>
					</div>
				</div>
				
				<div class=row>
					<div class="col-md-3">
						<div class="thumbnail tile tile double tile-wide tile-blue" data-click="transform">
							<a href="#" style="text-decoration:none;" onclick="toggleVisibility('allcompletetasks'),clearBox('assignedTo'), clearBox('allbody');" >
								<i id="icon" class="fa fa-3x fa-cloud fa-inverse"></i>
								<h1 id="icon-text">Repository</h1>
							</a>
						</div>
					</div>
				</div>
			</div>		<!-- Left pane div ends -->
			
			<!-- My roomie page starts -->
			<div >
			<div id="myroomies" style="display:none;">
				<h1 style="font-size:2em;">My Roomies</h1>
				<table id="newroomies" class="table table-hover table-bordered" style=" background-color:white;width:85%;border-radius:1em;text-align:center;"></table>
				<input type="text" name="addRoomie" id="addRoomie" class="form-control" placeholder="Add a Roomie" style="width:20%"></input>
				<button type="button" id="NewMember" class="btn btn-primary btn-md" style="margin-top:-4em;margin-left:13.75em;">
		  			<span class="glyphicon glyphicon-plus"></span>
				</button>
				<br>
				<button class="btn btn-success btn-lg" data-toggle="modal" data-target="#graphModal" style="margin-left:13em">View Work Ethic</button>
			</div>
			<!-- Modal for graph starts here  -->
			<div class="modal fade" id="graphModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog" style="width:45%;align:center;">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">Work Ethic of the house</h4>
                    </div>
                    <div class="modal-body">
                    <!-- The form is placed inside the body of modal -->
                    <div id="piechart" style="width:100%; height:100%"></div>
                    </div>
                </div>
            </div>
        </div>
			<!-- Modal for graph ends here -->
				<!-- My roomie page ends -->
			
			<!-- Group task page starts -->
			<div id="grouptasks" style="display:none;">
				<h1 style="font-size:2em;">Your group tasks</h1>
				<h5><i class="fa fa-exclamation-triangle" style = "color:#E80000;"></i>&nbsp;Overdue&nbsp;&nbsp;&nbsp;<i class="fa fa-circle-o-notch" style = "color:#002447;"></i>&nbsp;Master</h5>
				<table class="table table-hover" style=" background-color:white;width:60%;border-radius:1em;text-align:center;">
				     <thead>
				          <tr>
				          	  <th></th>	
				              <th>Task name</th>
				              <th>Task points</th>
							  <th>Due Date</th>           
				              <th>Assignee</th>
				           </tr>
				     </thead>
			     	 <tbody id="tbody"></tbody>    
				</table>
				<br>
				<h1 style="font-size:2em;">Unassigned Tasks</h1>
				<table id="unassignedTasks" class="table table-hover" style=" background-color:white;width:60%;border-radius:1em;text-align:center;">
				     <thead>
				          <tr>
				              <th>Task name</th>
				              <th>Task points</th>
							  <th>Due Date</th>           
				              <th>Assign To</th>
				              <th>Action</th>
				           </tr>
				     </thead>
			     	 <tbody id="tbody2"></tbody>    
				</table>
			
							
			</div>	<!-- Group task page ends -->
			
			<!-- Home page starts containing recurring and my tasks -->
			<div id="homePage" style="display:none;">
 				<p style="font-size:2em;">My tasks</p>
					<table class="table table-hover" style=" background-color:white;width:60%;borfer:none;border-radius:1em;text-align:center;" id = "myTasksTable">
					     <thead>
					          <tr>
					              
					              <th>Task name</th>
					              <th>Task points</th>
								  <th>Due Date</th>
								  <th>Recurring? </th>
								  <th>Recur days </th>
								  <th>Action</th>
								  
								<!-- <th>Completed on</th>  -->             
					           </tr>
					     </thead>
					     <tbody id="mybody"></tbody>    
				   </table>
				   <br><br>
				<p style="font-size:2em;">Recurring tasks</p>
				<table class="table table-hover" style="background-color:white; width:60%;border-radius:1em;text-align:center;" id = "recurTasksTable">
				     <thead>
				          <tr>
				              
				              <th>Task name</th>
				              <th>Task points</th>
							  <th>Due Date</th>  
							  <th>Assigned To</th> 
							  <th>Action</th>        
				           </tr>
				     </thead>
				     <tbody id="grpbody"></tbody>    
				</table>
			</div>	
				   <br><br>			
					<!-- Home page ends -->
			
			<!-- Repository page starts containing all completed tasks-->
			<div id="allcompletetasks" style="display:none;">
				<p style="font-size:2em">All tasks completed</p>
				<table class="table table-hover" style="background-color:white; width:60%;border-radius:1em;text-align:center;">
				     <thead>
				          <tr>
				              <th>Task name</th>
				              <th>recur_days</th>
				              <th>Completed On</th>
				              <th>Assignee</th>
				           </tr>
				     </thead>
			     	 <tbody id="allbody"></tbody>    
				</table>				
			</div>	<!-- Group task page ends -->		
			
			<!-- Page starts to add new tasks -->
			<div id="addtasks" style="display:none;">
			<form id="addTask" method="post" class="form-inline">
			
				<input type="text" class="form-control" id="taskName" name="taskName" placeholder="Name the Task"/>		
				<input type="text" class="form-control checkbox_id" id="dueDate2" name="dueDate" placeholder="Due date or number of days"/>				
				<input type="text" class="form-control" id="taskPoints" name="taskPoints" placeholder="Points(scale of 1-9)"/>
				<input type="text" class="form-control" id="recurDays" name="recurDays" placeholder="number of days to recur"/>
				<input type="checkbox" name="checkbox" id="checkbox_id" value="value">
				<label for="checkbox_id">Master?</label>
				<select class="form-control" id="assignedTo"></select>	

				
				<button id="log" class="btn btn-primary"style="width:5%; margin-top:0.25em;">Add</button>	
			</form>
			<br>
			<table class="table table-striped table-hover" style=" background-color:white;width:60%;border-radius:1em;border-style: none; text-align:center;">
				     <thead>
				          <tr>
				          	  <th>Master Task Number</th>
				              <th>Task name</th>
				              <th>Task points</th>
							  <th>Due Date</th>
							  <th>Reuse?</th>
				          </tr>
				     </thead>
			     	 <tbody id="masterTaskTable"></tbody>    
				</table>
			
			</div>	<!-- Page ends to add new tasks -->	
		</div>	
	</body>  <!--  HTML body ends -->
	
	<!--  Java script section starts here -->
	<script type="text/javascript">
   		var data = new google.visualization.DataTable();
   		var modifiedDate;
   		var  numRows = 0;
   		data.addColumn('string', 'User Name');
   		data.addColumn('number', 'Completed');
   	  	data.addColumn('number', 'Assigned');
		var divs = ["myroomies", "grouptasks", "addtasks","homePage", "allcompletetasks"];
		var visibleDivId = null;
		var members=[];
		function clearBox(elementID)
		{
		    document.getElementById(elementID).innerHTML = "";
		}
		
		//Method to add and display members on "My Roomies" page
		$("#NewMember").click(function(e) {                  // Locate HTML DOM element with ID "somebutton" and assign the following function to its "click" event...
			   name = document.getElementById("addRoomie").value;
			   username = "<%=request.getAttribute("username")%>";
			   url = "displayAndAddMembers?username="+username+"&operation=checkMember&name="+name;
			   $.post(url, function(responseJson) {    // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response JSON...			    	 
			        var $ul = $('<ul>').appendTo($('#newroomies'));		// Create HTML <ul> element and append it to HTML DOM element with ID "somediv".
			    	$.each(responseJson, function(index, item) {
			    		if(item == "false"){
			    			alert("user does not exist");
			    			return false;
			    		}			    		
			            $('<li>').text(item).appendTo($ul);      // Create HTML <li> element, set its text content with currently iterated item and append it to the <ul>.
			        });
			    });
			});
		
		function displayMembers(){
			username = "<%= request.getAttribute("username")%>";
			url = "displayAndAddMembers?username="+ username + "&operation=listMembers";
			
			$.get(url, function(responseJson) {    					// Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response JSON...
				var $options = $('#assignedTo'); 
				var $ul = $('<ul>').appendTo($('#newroomies'));// Create HTML <ul> element and append it to HTML DOM element with ID "somediv".
				$('<option>').text("None").appendTo($options);
		    	$('<li>').text("None").appendTo($ul); 
				$.each(responseJson, function(index, item) {  // Iterate over the JSON array.		            
		            $('<option>').text(item[0]).appendTo($options);		            	
		            $('<li>').text(item[0]).appendTo($ul);// Create HTML <li> element, set its text content with currently iterated item and append it to the <ul>.
		            });        
		        });	
		}
		function advanceDate(){
		
			url = "AdvanceDate?operation=getDate";
			$.get(url, function(responseJson) {
				if(responseJson != ''){
					modifiedDate = new Date(responseJson);
				}
				
				else{
					var sysDate = new Date();
					var dd = sysDate.getDate();
					var mm = sysDate.getMonth(); //January is 0
					var yyyy = sysDate.getFullYear();
					modifiedDate = new Date(yyyy,mm,dd);
				}
			});
		}
		
		function reuseTask(task_no,taskName,taskPoints,due_date){
			if(due_date.value=="" || due_date.value==null){
				
				alert("Please enter a valid due date");
			}
			else{
			
				username = "<%= request.getAttribute("username")%>";
				url = "CreateTasks?username="+ username+"&task_no="+task_no+"&taskName="+taskName+"&taskPoints="+taskPoints+"&dueDate="+due_date.value;
				$.get(url, function(responseJson) {
					$.each(responseJson, function (index, item) {
						if(item=="true"){
								alert("Done");
								
							}
							else{
								alert("Not done");
							}
				   }); 
				
				});
			}
			
		}
		
		function displayMasterTasks(){
			username = "<%= request.getAttribute("username")%>";
			url = "DisplayMasterTasks?username="+ username;
			button_no = -1;
			$.get(url, function(responseJson) { 
		
	            $.each(responseJson, function(index, item) {
	      
	            	 var tr = document.createElement('tr');   
			    	 var td1 = document.createElement('td');
			    	 var td2 = document.createElement('td');
			    	 var td3 = document.createElement('td');
			    	 var td4 = document.createElement('td');
			    	 var td5 = document.createElement('td');
			    	 var text1 = document.createTextNode(item[0]);
			    	 var text2 = document.createTextNode(item[1]);
			    	 var text3 = document.createTextNode(parseFloat(item[2]).toFixed(2));
			    	 td1.appendChild(text1);
			    	 td2.appendChild(text2);
			    	 td3.appendChild(text3);
			    	 tr.appendChild(td1);
			    	 tr.appendChild(td2);
			    	 tr.appendChild(td3);
			    	 var mi = document.createElement("input");
			    	 mi.setAttribute('type','text');
			    	 mi.setAttribute('class','dueDate');
			    	 mi.setAttribute('style','margin:0.5em;');
			    	 td5.appendChild(mi);
			    	 tr.appendChild(td5);
			    	 var element = document.createElement("input");
	                 element.type = "button";
	                 element.value = "Reuse Task";
	                 element.setAttribute("class","btn btn-info btn-sm");	         
	                 element.onclick = function() { 	 
	                     reuseTask(item[0],item[1],item[2],mi);
	                 };
	                 
	    			 td4.appendChild(element);
	    			 tr.appendChild(td4);
			    	 $('#masterTaskTable').append(tr);
	            });
			});	
		}
		function displayMembers2(){
			username = "<%= request.getAttribute("username")%>";
			url = "displayAndAddMembers?username="+ username + "&operation=listMembers";
			
			$.get(url, function(responseJson) {    			// Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response JSON...
		            $.each(responseJson, function(index, item) {  // Iterate over the JSON array.		            		           
		            var eachrow = "<tr>"
		                + "<td>" + item[0] + "</td>"
		                + "<td>" + item[1] + "</td>"
		                + "</tr>";
		   			 $('#newroomies').append(eachrow);		            	
		            });        
		        });	
		}
				
		//Method to assign members to task
		function assignTask(assignee,taskID){
			var e = document.getElementById(taskID);
			var strUser = e.options[e.selectedIndex].value
			url = "AssignTask?name="+ strUser + "&taskID="+taskID;
			$.get(url, function(responseJson) {

				$.each(responseJson, function (index, item) {
					if(item=="True"){
						clearBox('tbody');
						clearBox('tbody2');
						getGroupAndUnassignedTasks();
						//	document.getElementById("table1").deleteRow(row_no);
							alert("Done");
							
						}
						else{
							alert("Not done");
						}
			   }); 
			
			});
			
		}
		
		function assignTask2(taskID){
			var e = $('#'+taskID).val();
			url = "AssignTask?name="+ e + "&taskID="+taskID;
			$.get(url, function(responseJson) {

				$.each(responseJson, function (index, item) {
					if(item=="True"){
						clearBox('mybody');
						clearBox('grpbody');
						getMyTasks();
						getGroupRecurTasks();
						
						getGroupRecurTasks();
							alert("Done");
							
						}
						else{
							alert("Not done");
						}
			   }); 
			
			});
			
		}

		function displayMembers2(){
			username = "<%= request.getAttribute("username")%>";
			url = "displayAndAddMembers?username="+ username + "&operation=listMembers";
			var earnPoints = [];
			var iniPoints = [];
			var weekPoints = [];
			var compPoints = [];
			var members = [];
			var progress = [];
			if(data.getNumberOfRows()){
				data.removeRows(0, data.getNumberOfRows());	
			}
			$.get(url, function(responseJson) {    			// Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response JSON...
		            $.each(responseJson, function(index, item) {  // Iterate over the JSON array.		            		           

		            	members.push(item[0]);
		            	earnPoints.push(item[1]);
		            	iniPoints.push(Math.round(parseFloat(item[2])*100)/100);
		            	weekPoints.push(parseFloat(item[3]).toFixed(2));
		            	compPoints.push(parseFloat(item[4]).toFixed(2));
		            	progress.push((( parseInt(item[4]) / (parseInt(item[2]) + parseInt(item[3]) + parseInt(item[4])))* 100).toFixed(2));
		   			 $('#newroomies').append(eachrow);	
		   			 
		   			data.addRow([item[0], parseInt(item[4]), (parseInt(item[2]) + parseInt(item[3]) + parseInt(item[4]))]);
		   			drawChart();
		            }); 

					var eachrow = "<tr>"
		                + "<th>" + "Member" + "</th>"
		                + "<th>" + "Total Points" + "</th>"
		                + "<th>" + "Initial Points" + "</th>"
		                + "<th>" + "Weekly Points"  + "</th>"
		                + "<th>" + "This week completed"  + "</th>"
		                + "<th>" + "Progress"  + "</th>"
		                + "<th>" + "Start of Recur"  + "</th>"
		                + "</tr>";
		   			 $('#newroomies').append(eachrow);	
					for (i = 0; i < members.length; i++) {
						
						if(progress[i] == "NaN")
							progress[i] = 0.0;
						 var eachrow = "<tr>"
				                + "<td>" + members[i] + "</td>"
				                + "<td>" + earnPoints[i] + "</td>"
				                + "<td>" + iniPoints[i] + "</td>"
				                + "<td>" + weekPoints[i] + "</td>"
				                + "<td>" + compPoints[i] + "</td>"
				                + "<td>" + progress[i] +"%" + "</td>"
				                + "<td>" + "2014-12-01" + "</td>"
				                + "</tr>";
				   			 $('#newroomies').append(eachrow);	
					}
		        });		
		}
	
	      function drawChart() {
 	          var options = {
 	        	width: 600,	  
 	            height: 400,
 	            title:'Task distribution graph ',
 	           bar: { groupWidth: '60%' },
	            backgroundColor: 'transparent',
	            hAxis: {title: "Roomies", titleTextStyle:{color:'#2F4F4F'}}

	          };
				var chart = new google.visualization.BarChart(document.getElementById('piechart'));

	          chart.draw(data, options);
	      }

		function toggleVisibility(divId) {
			if(visibleDivId === divId) {
		    	visibleDivId = null;
		  	} else {
		    	visibleDivId = divId;
		  	}
		  		  
		  	if(divId=="myroomies"){			  
			    username = "<%= request.getAttribute("username")%>";
			    displayMembers2();
		  	}
		  	
		  if(divId=="grouptasks"){
			  getGroupAndUnassignedTasks();		
		}
		  
		if(divId=="addtasks"){
			displayMembers();
			displayMasterTasks();
		}
			  
		if(divId=="homePage"){			  
			getMyTasks();
			getGroupRecurTasks();
		}
		if(divId == "allcompletetasks"){
			getAllTasks();
		}	  
			  
		hideNonVisibleDivs();
	}
		function stringToDate(_date,_format,_delimiter)
		{
			var formatLowerCase=_format.toLowerCase();
		    var formatItems=formatLowerCase.split(_delimiter);
		    var dateItems=_date.split(_delimiter);
		    var monthIndex=formatItems.indexOf("mm");
		    var dayIndex=formatItems.indexOf("dd");
		    var yearIndex=formatItems.indexOf("yyyy");
		    var month=parseInt(dateItems[monthIndex]);
		    month-=1;
		    var formatedDate = new Date(dateItems[yearIndex],month,dateItems[dayIndex]);
		    return formatedDate;
		}
		
		function getGroupAndUnassignedTasks(){
			 var table2 = document.createElement('table');
			    username = "<%= request.getAttribute("username")%>";
			    url = "displayTasks?username="+ username + "&operation=listTasks";
			    button_no=-1;
				$.get(url, function(responseJson) {   
					
					$.each(responseJson, function (index, item) {
					if(item[5] != null){
						var dateFromDb = stringToDate(item[2],"mm/dd/yyyy","/");
						if(modifiedDate.getTime()> dateFromDb.getTime() && item[8] > 0)
						{
							
							var eachrow = "<tr>"
								 + "<td><i class=\"fa fa-exclamation-triangle\" style = \"color:#E80000; margin-left:1.3em;\">&nbsp;&nbsp;</i><i class=\"fa fa-circle-o-notch\" style = \"color:#002447;\"></i></td>"
				                 + "<td>" + item[0] + "</td>"
				                 + "<td>" + Math.round(parseFloat(item[1])*100)/100 + "</td>"
				                 + "<td>" + item[2] + "</td>"
				                 + "<td>" + item[3] + "</td>"
				                 + "</tr>";
					     	
						}
						else if(modifiedDate.getTime()> dateFromDb.getTime()){
					     var eachrow = "<tr>"
					     			 +"<td><i class=\"fa fa-exclamation-triangle\" style = \"color:#E80000;\"></i></td>"
					                 + "<td>" + item[0] + "</td>"
					                 + "<td>" + Math.round(parseFloat(item[1])*100)/100 + "</td>"
					                 + "<td>" + item[2] + "</td>"
					                 + "<td>" + item[3] + "</td>"
					                 + "</tr>";
						}
						else if(item[8] > 0){
							var eachrow = "<tr>"
								 +"<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i class=\"fa fa-circle-o-notch\" style = \"color:#002447;\"></i></td>"
				                 + "<td>" + item[0] + "</td>"
				                 + "<td>" + Math.round(parseFloat(item[1])*100)/100 + "</td>"
				                 + "<td>" + item[2] + "</td>"
				                 + "<td>" + item[3] + "</td>"
				                 + "</tr>";
						}
						else{
							var eachrow = "<tr>"
								 + "<td></td>"
				                 + "<td>" + item[0] + "</td>"
				                 + "<td>" + Math.round(parseFloat(item[1])*100)/100 + "</td>"
				                 + "<td>" + item[2] + "</td>"
				                 + "<td>" + item[3] + "</td>"
				                 + "</tr>";
						}
						$('#tbody').append(eachrow);
					     if(item[4] == null){
					    	 var dateFromDb = stringToDate(item[2],"mm/dd/yyyy","/");
							 if(modifiedDate.getTime()> dateFromDb.getTime())
								{
									
							     	var eachrow = "<tr>"
						    	         + "<td><i class=\"fa fa-exclamation-triangle\" style = \"color:red;\"></i></td>"
						                 + "<td>" + item[0] + "</td>"
						                 + "<td>" + item[1] + "</td>"
						                 + "<td>" + item[2] + "</td>"
						                 + "<td>" + item[3] + "</td>"
						                 + "</tr>";
								}
							 else{
								 var eachrow = "<tr>"
					                 + "<td>" + item[0] + "</td>"
					                 + "<td>" + item[1] + "</td>"
					                 + "<td>" + item[2] + "</td>"
					                 + "<td>" + item[3] + "</td>"
					                 + "</tr>";
								 }
							 
				    	 var tr = document.createElement('tr');   
				    	 var td1 = document.createElement('td');
				    	 var td2 = document.createElement('td');
				    	 var td3 = document.createElement('td');
				    	 var td4 = document.createElement('td');
				    	 var td5 = document.createElement('td');
				    	 var td6 = document.createElement('td');
				    	 var td7 = document.createElement('td');
				    	 if(item[8] > 0)
				    		 {
				    		 var mTaskFlag = 1;
				    		 var dueTask = document.createElement("i");
				    		 dueTask.setAttribute('class','fa-circle-o-notch');
				    		 dueTask.setAttribute('style','color:#002447');
				    		 }
				    	 var text2 = document.createTextNode(item[0]);
				    	 var text3 = document.createTextNode(Math.round(parseFloat(item[1])*100)/100);
				    	 var text4 = document.createTextNode(item[2]);
				    	 if(mTaskFlag == 1)
				    	 {
				    		 td7.appendChild(dueTask);
				    		 mTaskFlag = 0;
				    	 	}
				    	 
				    	 td2.appendChild(text2);
				    	 td3.appendChild(text3);
				    	 td4.appendChild(text4);
				    	 
				    	url = "displayAndAddMembers?username="+ username + "&operation=listMembers";
				    	
				    
				    	var content = '<select id ='+item[5]+' >';
				    	var newDiv=document.createElement('div');
				    		$.get(url, function(responseJson) { 
				    			
				    			$.each(responseJson, function (index, item1) {
				    			
				    			
				    			content += "<option value='"+item1[0]+"'>"+item1[0]+"</option>";
				    		
				    	            }); 
				    			content += '</select>';
				    			newDiv.innerHTML= content;
				    			td5.appendChild(newDiv);

				    			var element = document.createElement("button");
				                $(element).html('Assign Task');
				                 element.onclick = function() { // Note this is a function
				                	 
				                     assignTask(button_no= button_no+1);
				                 };
				    		});
				    		var element = document.createElement("button");
			                 element.type = "button";
			                 element.setAttribute("class","btn btn-info btn-sm");
			                 element.setAttribute("id","taskAssign");
			                $(element).html('Assign Task');
			                button_no= button_no+1;
			                 element.onclick = function() {
			                	 
			                     assignTask(content,item[5]);
			                 };
			    			
			    			td6.appendChild(element);
							tr.appendChild(td2);
							tr.appendChild(td3);
							tr.appendChild(td4);
							tr.appendChild(td5);
							tr.appendChild(td6);

							$('#unassignedTasks').append(tr);
							
					     } 
						}
					});
					
			 });			
		}
	
		function hideNonVisibleDivs() {
		  var i, divId, div;
		
			  for(i = 0; i < divs.length; i++) {
				  divId = divs[i];
				    div = document.getElementById(divId);
			
				    if(visibleDivId === divId) {
				    	div.style.display = "block";
				    } else {			    	
				    	div.style.display = "none";
				    }
			  }
		}	
		
		function completeTask(taskID, recur_days, due_date, row_no){
			url = "TaskComplete?taskID="+taskID + "&recur_days="+recur_days + "&due_date="+ due_date;
			console.log("Data:"+taskID+" "+recur_days+" "+due_date);
			$.get(url, function(responseJson) {   
				$.each(responseJson, function (index, item) {
					
					if(item=="True"){
						alert("Done");
						clearBox('mybody');
						clearBox('grpbody');
						getMyTasks();
						getGroupRecurTasks();
					}
					else{
						alert("Not done");
					}
				});
			});
		}
		
		function getMyTasks(){
			username = "<%= request.getAttribute("username")%>";
		    url = "displayTasks?username="+ username + "&operation=listTasks";
		    var table1 = document.createElement('table');
		    var isRecur, recurDays;
			$.get(url, function(responseJson) {   
				i=0;
				$.each(responseJson, function (index, item) { 
					
				     if(item[4] == username && item[7] == null){
				    	 
				    	 var element = document.createElement("input");
		                 element.type = "button";
		                 element.value = "Task Complete";
		                 element.name = "asfd"; 
				    	element.setAttribute("class","btn btn-success btn-sm");
		                 element.onclick = function() { // Note this is a function

		                     completeTask(item[5],item[6],item[2],i);
		                 }; 
		                 var inputDate = document.createElement("input");
		                 inputDate.setAttribute('type', 'text');
		                 inputDate.setAttribute('id','a'+item[5]);
		                 inputDate.setAttribute('class','datepicker');
		                 $(".datepicker").datepicker();
		                 
				    	 var tr = document.createElement('tr');   
				    	 var td2 = document.createElement('td');
				    	 var td3 = document.createElement('td');
				    	 var td4 = document.createElement('td');
				    	 var td5 = document.createElement('td');
				    	 var td6 = document.createElement('td');
				    	 var td7 = document.createElement('td');
				    	 var text2 = document.createTextNode(item[0]);
				    	 var text3 = document.createTextNode(parseFloat(item[1]).toFixed(2));
				    	 var text4 = document.createTextNode(item[2]);
				    	 
				    	 if(item[6] == 0) {
				    		 var noRecur = document.createElement("i");
				    		 noRecur.setAttribute('class','fa fa-times fa-lg');
				    		 noRecur.setAttribute('style','color:#E80000');
				    		 recurDays = "N/A";
				    	 }
				    	 else {
				    		 var noRecur = document.createElement("i");
				    		 noRecur.setAttribute('class','fa fa-check fa-lg');
				    		 noRecur.setAttribute('style','color:#00B800');
				    	 	 recurDays = item[6];
				    	 }
				    	 var text6 = document.createTextNode(recurDays);
				    	 td2.appendChild(text2);
				    	 td3.appendChild(text3);
				    	 td4.appendChild(text4);
				    	 td5.appendChild(noRecur);
				    	 td6.appendChild(text6);
				    	 td7.appendChild(element);
						tr.appendChild(td2);
						tr.appendChild(td3);
						tr.appendChild(td4);
						tr.appendChild(td5);
						tr.appendChild(td6);
						tr.appendChild(td7);

						$('#myTasksTable').append(tr);
						i=i+1;				    	
		                 }	     
				});
			});
			
		}
		
		function getGroupRecurTasks(){
			username = "<%= request.getAttribute("username")%>";
		    var mytable = document.createElement('table');
		    url = "displayRecurTasks?username="+ username + "&operation=listGroupTasks";
		    button_no=-1;
			$.get(url, function(responseJson) {   
				$.each(responseJson, function (index, item) {
				if(item[6] > 0){
					if(item[4] == null){
						 var tr = document.createElement('tr');

				    	 var td1 = document.createElement('td');
				    	 var td2 = document.createElement('td');
				    	 var td3 = document.createElement('td');
				    	 var td4 = document.createElement('td');
				    	 var td5 = document.createElement('td');
				    	 var td6 = document.createElement('td');
		
				    	 var text2 = document.createTextNode(item[0]);
				    	 var text3 = document.createTextNode(parseFloat(item[1]).toFixed(2));
				    	 var text4 = document.createTextNode(item[2]);
				    	 
				    	 td2.appendChild(text2);
				    	 td3.appendChild(text3);
				    	 td4.appendChild(text4);
				    	 
				    	url = "displayAndAddMembers?username="+ username + "&operation=listMembers";
				    	
				    
				    	var content = '<select id ='+item[5]+' >';
				    	var newDiv=document.createElement('div');
				    		$.get(url, function(responseJson) { 
				    			
				    			$.each(responseJson, function (index, item1) {				    			
				    			
				    			content += "<option value='"+item1[0]+"'>"+item1[0]+"</option>";
				    			
				    			
				    	            	
				    	            }); 
				    			content += '</select>';
				    			newDiv.innerHTML= content;
				    			td5.appendChild(newDiv);
				    			var element = document.createElement("button");
						    	element.setAttribute("class","btn btn-info btn-sm");
						    	$(element).html('Assign Task');
				                 element.onclick = function() { // Note this is a function
				                	 
				                     assignTask2(item[5]);
				                 };
				    			
				    			td6.appendChild(element);
				    			
				    		});
			    			
							
							tr.appendChild(td2);
							tr.appendChild(td3);
							tr.appendChild(td4);
							tr.appendChild(td5);
							tr.appendChild(td6);
							
							$('#recurTasksTable').append(tr);
						
					}
					else if(item[4] == username){
						var element = document.createElement("button");
				    	 element.setAttribute("class","btn btn-success btn-sm");
				    	 $(element).html('Task complete');
			                 element.onclick = function() { // Note this is a function
			                     completeTask(item[5],item[6], item[2], button_no= button_no+1);
			                 };
					    	 
			                 var inputDate = document.createElement("input");
			                 inputDate.setAttribute('type', 'text');
			                 inputDate.setAttribute('id','a'+item[5]);
			                 inputDate.setAttribute('class','datepicker');
			                 $(".datepicker").datepicker();
			                 
					    	 var tr = document.createElement('tr');
					    	 var td1 = document.createElement('td');
					    	 var td2 = document.createElement('td');
					    	 var td3 = document.createElement('td');
					    	 var td4 = document.createElement('td');
					    	 var td5 = document.createElement('td');
					    	 var td6 = document.createElement('td');					    	 
					    	 
					    	 var text2 = document.createTextNode(item[0]);
					    	 var text3 = document.createTextNode(parseFloat(item[1]).toFixed(2));
					    	 var text4 = document.createTextNode(item[2]);
					    	 var text5 = document.createTextNode(item[4]);
					    	 
					    	 td2.appendChild(text2);
					    	 td3.appendChild(text3);
					    	 td4.appendChild(text4);
					    	 td5.appendChild(text5);
					    	 td6.appendChild(element);
					    	 
							 tr.appendChild(td2);
							 tr.appendChild(td3);
							 tr.appendChild(td4);
						  	 tr.appendChild(td5);
							 tr.appendChild(td6);

							 $('#recurTasksTable').append(tr);
					}
					else{
					     var eachrow = "<tr>"
			                 + "<td>" + item[0] + "</td>"
			                 + "<td>" + item[1] + "</td>"
			                 + "<td>" + item[2] + "</td>"
			                 + "<td>" + item[3] + "</td>"
			                 + "<td>"+""+"</td>"
			                 + "</tr>";
			  $('#recurTasksTable').append(eachrow);
 
					}
				}
			});
			});
		}
		
		function getAllTasks(){
		    var mytable = document.createElement('table');
		    username = "<%= request.getAttribute("username")%>";
		    url = "displayRecurTasks?username="+ username + "&operation=listAllTasks";
		    button_no=-1;
			$.get(url, function(responseJson) {   
				
				$.each(responseJson, function (index, item) {
					if(item[4] != null){
					     var eachrow = "<tr>"
					                 + "<td>" + item[1] + "</td>"
					                 + "<td>" + item[2] + "</td>"
					                 + "<td>" + item[3] + "</td>"
					                 + "<td>" + item[4] + "</td>"
					                 + "</tr>";
					  $('#allbody').append(eachrow);
					}
			});
			});
		}
		
		$(function() {
			  enable_cb();
			  $("#checkbox_id").click(enable_cb);
			});

			function enable_cb() {
			  if (this.checked) {
				  $("#assignedTo").prop("disabled", true);
			    $("input.checkbox_id").attr("disabled", true);
			  } else {
				$("input.checkbox_id").removeAttr("disabled");
				$("#assignedTo").prop("disabled", false);
			  }
			}
		
		$("#log").click(function() {                  // Locate HTML DOM element with ID "somebutton" and assign the following function to its "click" event...
			   taskName = document.getElementById("taskName").value;
			   dueDate = document.getElementById("dueDate2").value;
			   taskPoints = document.getElementById("taskPoints").value;
			   assignedMember = document.getElementById("assignedTo").value;
			   recurDays = document.getElementById("recurDays").value;
			   username = "<%=request.getAttribute("username")%>";
			   var atLeastOneIsChecked = $('input[name="checkbox"]:checked').length > 0?1:0;
			   url = "AddTask?username="+username+"&taskPoints="+taskPoints+"&dueDate="+dueDate+"&assignedMember="+assignedMember+"&taskName="+taskName+"&recurDays="+recurDays+"&isMaster="+atLeastOneIsChecked;
			   $.post(url, function(responseJson) {    // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response JSON...
			    	$.each(responseJson, function(index, item) {
			    		if(item=="true"){
			    			alert("task successfully added");
			    		}
			            $('<li>').text(item).appendTo($ul);      // Create HTML <li> element, set its text content with currently iterated item and append it to the <ul>.
			        });
			    });
			});
	
	</script>
</html>