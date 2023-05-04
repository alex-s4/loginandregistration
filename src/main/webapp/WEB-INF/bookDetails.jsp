<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isErrorPage="true" %>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Read Share</title>
</head>
<body>
	<h1><i>${book.getTitle()}</i></h1>
	<a href="../books">back to the shelves</a>
	
	<h3>
	<c:if test="${loggedUser.getEmail().equals(book.getUser().getEmail())}">
		<c:out value="You" />
	</c:if>
	<c:if test="${!loggedUser.getEmail().equals(book.getUser().getEmail())}">
		<c:out value="${book.getUser().getUserName()}" />
	</c:if>
	 read ${book.getTitle()} by ${book.getAuthor()}
	</h3>
	
	<h4>
		Here are 
		<c:if test="${loggedUser.getEmail().equals(book.getUser().getEmail())}">
			<c:out value="your" />
		</c:if>
		<c:if test="${!loggedUser.getEmail().equals(book.getUser().getEmail())}">
			<c:out value="${book.getUser().getUserName()}'s " />
		</c:if>
		thoughts:
	</h4>
	
	<hr />
	<p>${book.getMyThoughts()}</p>
	<hr />
	<c:if test="${loggedUser.getId()==book.getUser().getId()}">
		<a href="/books/${book.getId()}/edit">edit</a>
		<a href="/deleteBook/${book.getId()}">delete</a>
	</c:if>
</body>
</html>