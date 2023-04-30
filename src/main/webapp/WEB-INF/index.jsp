<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isErrorPage="true" %>    


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login and Registration</title>
</head>
<body>
	<h1>Register</h1>
	<h3>Join our growing community.</h3>
	
	<div>
		<form:form method="POST" modelAttribute="newUser" action="/register">
			<table>
				<tr>
					<th colspan="2">Register</th>
				</tr>
				<tr>
					<td><form:label path="userName">Username:</form:label></td>
					<td><form:input path="userName" /><form:errors path="userName" /></td>
				</tr>
				<tr>
					<td><form:label path="email">Email:</form:label></td>
					<td><form:input type="email" path="email" /><form:errors path="email" />${emailExistsError}</td>
				</tr>
				<tr>
					<td><form:label path="password">Password:</form:label></td>
					<td><form:password path="password" /><form:errors path="password" /></td>
				</tr>
				<tr>
					<td><form:label path="confirm">Confirm PW:</form:label></td>
					<td><form:password path="confirm" /><form:errors path="confirm" />${passwordMatchError}</td>
				</tr>
				<tr>
					<th colspan="2"><input type="submit" value="Submit"></th>
				</tr>
			</table>
		</form:form>
		<p>${successRegistrationMessage}</p>
	</div>
	
	<div>
		<form:form method="POST" modelAttribute="newLogin" action="/login">
			<table>
				<tr>
					<th colspan="2">Log In</th>
				</tr>
				
				<tr>
					<td><form:label path="email">Email:</form:label></td>
					<td><form:input type="email" path="email" /><form:errors path="email" />${loginError}</td>
				</tr>
				<tr>
					<td><form:label path="password">Password:</form:label></td>
					<td><form:password path="password" /><form:errors path="password" /></td>
				</tr>
				
				<tr>
					<th colspan="2"><input type="submit" value="Submit"></th>
				</tr>
			</table>
		</form:form>
	</div>
	
	
</body>
</html>