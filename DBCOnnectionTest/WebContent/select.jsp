// This program retrievs data from the databse

<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
 
<html>
<head>
<title>SELECT Operation</title>
</head>
<body>
//------Create a database schema "test" containg a table named "user_details". It has two columns "uname" and "pwd" insert the values if you want-------

<sql:setDataSource var="snapshot" driver="com.mysql.jdbc.Driver"
url="jdbc:mysql://localhost/test"
user="root"  password=""/>
 
<sql:query dataSource="${snapshot}" var="result">
SELECT * from user_details;
</sql:query>
 
<table border="1" width="100%">
<tr>
   <th>Username</th>
   <th>Password</th>
</tr>
<c:forEach var="row" items="${result.rows}">
<tr>
   <td><c:out value="${row.uname}"/></td>
   <td><c:out value="${row.pwd}"/></td>
</tr>
</c:forEach>
</table>
 
</body>
</html>