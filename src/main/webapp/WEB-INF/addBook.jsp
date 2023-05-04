<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isErrorPage="true" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Book Share</title>
</head>
<body>
	<a href="../books">back to the shelves</a>
	<h1>Add a book to Your Shelf!</h1>
	
	<div>
		<form:form method="POST" modelAttribute="book" action="/createBook">
			<table>
				<tr>
					<td></td>
					<td>
						<p><form:errors path="title" /></p>
						<p><form:errors path="author" /></p>
						<p><form:errors path="myThoughts" /></p>
					</td>
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
			<form:input type="hidden" path="user" value="${loggedUser.getId()}" />
		</form:form>
		
	</div>
	
</body>
</html>