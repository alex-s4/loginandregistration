<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isErrorPage="true" %>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>   

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Dashboard</title>
<style>
	table, th, td {
  border: 1px solid black;
  border-collapse: collapse;
  }
  th, td {
  padding: 6px;
}
</style>
</head>
<body>
 	<h1>Welcome, ${loggedUser.getUserName()}</h1>
 	<p>Books from everyone's shelves:</p>
 	<a href="../bookmarket">Book Broker</a>
 	<a href="../books/new">+ Add a to my shelf!</a>
 	<a href="../logout">logout</a>
 	
 	<table>
 		<tr>
 			<th>ID</th>
 			<th>Title</th>
 			<th>Author Name</th>
 			<th>Posted By</th>
 			
 		</tr>
 		
 		<c:forEach var="book" items="${books}">
 		
	 		<tr>
	 			<td>${book.getId()}</td>
	 			<td><a href="../books/${book.getId()}">${book.getTitle()}</a></td>
	 			<td>${book.getAuthor()}</td>
	 			<td>${book.getUser().getUserName()}</td>
	 			
	 		</tr>
 		
 		</c:forEach>
 		
 	</table>
 	
</body>
</html>