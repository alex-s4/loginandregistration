<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Dashboard</title>
</head>
<body>
 	<h1>Welcome, ${loggedUser.getUserName()}</h1>
 	<p>This is your dashboard. Nothing to see here yet.</p>
 	<a href="../logout">logout</a>
</body>
</html>