$(document).ready(function() {
	$("#Members").click(function(e) {// Locate HTML DOM element with ID "somebutton" and assign the following function to its "click" event...
	    username = "<%=request.getAttribute('username')%>";
	    alert(username);
	    url = "ValidateLogin?username="+ username + "&operation=listMembers"
		$.get(url, function(responseJson) {    // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response JSON...
	            var $ul = $('<ul>').appendTo($('#ajaxResponse')); // Create HTML <ul> element and append it to HTML DOM element with ID "somediv".
	            $.each(responseJson, function(index, item) { // Iterate over the JSON array.
	                $('<li>').text(item).appendTo($ul);      // Create HTML <li> element, set its text content with currently iterated item and append it to the <ul>.
	            });
	        });
	    });
	$("#NewMember").click(function(e) {                  // Locate HTML DOM element with ID "somebutton" and assign the following function to its "click" event...
		   name = document.getElementById("newMembers").value;
		   username = "<%=request.getAttribute('username')%>";
		   url = "ValidateLogin?username="+username+"&operation=checkMember&name="+name;
		   $.post(url, function(responseJson) {    // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response JSON...
		      	
		    	var $ul = $('<ul>').appendTo($('#ajaxResponse')); // Create HTML <ul> element and append it to HTML DOM element with ID "somediv".
		        
		    	$.each(responseJson, function(index, item) {
		    		if(item=="false"){
		    			alert("user does not exist");
		    			return false;
		    		}
		    		alert(item);
		    		alert(index);
		            $('<li>').text(item).appendTo($ul);      // Create HTML <li> element, set its text content with currently iterated item and append it to the <ul>.
		        });
		    });
		});
});