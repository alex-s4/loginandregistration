<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isErrorPage="true" %>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>   

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Book Lender Dashboard</title>
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
	<h3>Hello, ${loggedUser.getUserName()}. Welcome to..</h3>
	<h1>The Book Broker!</h1>
	<a href="../books">back to the shelves</a>
	
	<p>Available Books to Borrow</p>
	
	<table>
 		<tr>
 			<th>ID</th>
 			<th>Title</th>
 			<th>Author Name</th>
 			<th>Owner</th>
 			<th>Actions</th>
 		</tr>
 		
 		<c:forEach var="book" items="${books}">
 		
 		<c:if test="${!booksBorrowed.contains(book) }">
 		
	 		<tr>
	 			<td>${book.getId()}</td>
	 			<td><a href="../books/${book.getId()}">${book.getTitle()}</a></td>
	 			<td>${book.getAuthor()}</td>
	 			<td>${book.getUser().getUserName()}</td>
	 			<td>
	 				<c:if test="${loggedUser.getEmail().equals(book.getUser().getEmail())}">
						<a href="/books/${book.getId()}/edit">edit</a>&nbsp;<a href="/deleteBook/${book.getId()}">delete</a>
					</c:if>
					<c:if test="${!loggedUser.getEmail().equals(book.getUser().getEmail())}">
						<a href="/books/borrow/${book.getId()}">borrow</a>
					</c:if>
	 			</td>
	 		</tr>
 		
 		</c:if>
 		
 		</c:forEach>
 		
 	</table>
	
	<c:if test="${!loggedUser.getBooksBorrowed().isEmpty()}">
 	
 		<p>Books I'm Borrowing...</p>
 		<table>
	 		<tr>
	 			<th>ID</th>
	 			<th>Title</th>
	 			<th>Author Name</th>
	 			<th>Owner</th>
	 			<th>Actions</th>
	 		</tr>
	 		
	 		<c:forEach var="borrowedBook" items="${booksBorrowed}">
	 		<tr>
	 			<td>${borrowedBook.getId()}</td>
	 			<td><a href="../books/${borrowedBook.getId()}">${borrowedBook.getTitle()}</a></td>
	 			<td>${borrowedBook.getAuthor()}</td>
	 			<td>${borrowedBook.getUser().getUserName()}</td>
	 			<td>
	 				<a href="/books/return/${borrowedBook.getId()}">return</a>
	 			</td>
	 		</tr>
	 		</c:forEach>
 		</table>
 	
 	
 	</c:if>
 	
 	
 	
</body>
</html>