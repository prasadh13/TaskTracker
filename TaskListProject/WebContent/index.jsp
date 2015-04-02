<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>JSP Login Page</title>
</head>

<body>
	<script type="text/javascript">
		function validateUsername() {
			var text = document.getElementById("username").value;
			if (text == "") {
				document.getElementById("errorusername").innerHTML = "provide UserName";
			} else {
				document.getElementById("errorusername").innerHTML = "OK";
			}
		}

		function validatePassword() {
			var text = document.getElementById("pass").value;
			if (text == "") {
				document.getElementById("errorpassword").innerHTML = "provide Password";
			} else {
				document.getElementById("errorpassword").innerHTML = "OK";
			}
		}
	</script>
<div class="container" align="center">
	<div id="w" style="margin-top:6em">
		<img src="images/roommatelogo.jpg"  width="440" height="340"/>
		<br><br>
		<h2 id="info">Task tracker for room mates.</h2>
		<br><br>
		</div>
	<form action="ValidateLogin" method="POST" name="AdminLogin">
		<table>
			<tr>
				<td>UserName:</td>
				<td><input type="text" id="username" name="username"
					onblur="validateUsername();"></td>
				<td><label id="errorusername"></label></td>
			</tr>

			<tr>
				<td>Password:</td>
				<td><input type="password" id="pass" name="pass"
					onblur="validatePassword();"></td>
				<td><label id="errorpassword"></label></td>
			</tr>

			<tr>
				<td></td>
				<td><input type="submit" value="Login"></td>
				<td></td>
			</tr>
		</table>

	</form>
</body>
</html>