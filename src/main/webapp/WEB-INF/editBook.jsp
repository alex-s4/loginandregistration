<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isErrorPage="true" %>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Book Share</title>
</head>
<body>
	<a href="../">back to the shelves</a>
	<h1>Change your Entry</h1>
	
	<div>
		<form:form action="/books/${book.id}/edit" method="POST" modelAttribute="book" >
			<input type="hidden" name="_method" value="put">
			<table>
				<tr>
					<td></td>
					<td><form:errors path="title" /><form:errors path="author" /><form:errors path="myThoughts" /></td>
				</tr>
				<tr>
					<td><form:label path="title">Title</form:label></td>
					<td><form:input path="title" /></td>
				</tr>
				<tr>
					<td><form:label path="author">Author</form:label></td>
					<td><form:input path="author" /></td>
				</tr>
				<tr>
					<td><form:label path="myThoughts">My Thoughts</form:label></td>
					<td><form:textarea path="myThoughts" /></td>
				</tr>
				
				<tr>
					<th colspan="2"><input type="submit" value="Submit"></th>
				</tr>
			</table>
			<form:input type="hidden" path="user" value="${loggedUser.id}" />
			
			<c:forEach var="borrower" items="${book.getBorrowers()}">
				<form:input type="hidden" path="borrowers" value="${borrower.getId()}" />
			</c:forEach>
			
			
		</form:form>
		
		<c:if test="${loggedUser.getId()==book.getUser().getId()}">
			<a href="/deleteBook/${book.getId()}">DELETE BOOK</a>
		</c:if>
		
	</div>
</body> 
</html>