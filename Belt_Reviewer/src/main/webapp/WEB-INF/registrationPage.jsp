<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<!DOCTYPE html>
<html>
<head>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <meta charset="UTF-8">
    <title>Registration Page</title>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <div class="container-fluid">
    <a class="navbar-brand">Welcome To Event Lister!</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav">
      <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="/logout">Start Listing</a>
        </li>
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="/logout">About us</a>
        </li>
      </ul>
    </div>
  </div>
</nav>
<div class="p-3 mb-2 bg-secondary text-white">
	<div class="d-flex justify-content-evenly">
    
    
    <div class="input-group mb-3" style="margin-left: 100px">
    
    <form:form method="POST" action="/registration" modelAttribute="user">
    <h1>Register!</h1>
   		<p>
            <form:label for="exampleFormControlInput1" class="form-label" path="firstName">First Name:</form:label>
            <form:errors class="text-danger" path="firstName"/>
            <form:input type="firstName" path="firstName" class="form-control" id="exampleFormControlInput1" placeholder=""/>
        </p>
        <p>
            <form:label for="exampleFormControlInput1" class="form-label" path="lastName">Last Name:</form:label>
            <form:errors class="text-danger" path="lastName"/>
            <form:input type="lastName" path="lastName" class="form-control" id="exampleFormControlInput1" placeholder=""/>
        </p>
        <p>
            <form:label for="exampleFormControlInput1" class="form-label" path="email">Email:</form:label>
            <form:errors class="text-danger" path="email"/>
            <form:input type="email" path="email" class="form-control" id="exampleFormControlInput1" placeholder=""/>
        </p>
        <p>
            <form:label for="exampleFormControlInput1" class="form-label" path="email">Location:</form:label>
            <form:errors class="text-danger" path="location"/>
            <form:input type="location" path="location" class="form-control" id="exampleFormControlInput1" placeholder=""/>
        </p>
        <p>
			<form:label path="state">State:</form:label>
				<form:select Class="state" path="state">
					<c:forEach items="${states}" var="state">
						<form:option value="${state}"><c:out value="${state}"/></form:option>
					</c:forEach>
				</form:select>
		</p>
        <p>
            <form:label for="exampleFormControlInput1" class="form-label" path="password">Password:</form:label>
            <form:errors class="text-danger" path="password"/>
            <form:password path="password" class="form-control" id="exampleFormControlInput1" placeholder=""/>
        </p>
        <p>
            <form:label for="exampleFormControlInput1" class="form-label" path="passwordConfirmation">Password Confirmation:</form:label>
            <form:errors class="text-danger" path="password"/>
            <form:password path="passwordConfirmation" class="form-control" id="exampleFormControlInput1" placeholder=""/>
        </p>
        <input type="submit" value="Register!"/>
    </form:form>
    
    </div>
    
    	<div class="input-group mb-3" style="margin-left: 100px">
    
    <form method="post" action="/login">
    <h1>Login</h1>
    <p><c:out value="${error}" /></p>
        <p>
            <label for="email"for="exampleFormControlInput1" class="form-label">Email</label>
            <form:errors class="text-danger" path="email"/>
            <input id="email" name="email" type="email" class="form-control" id="exampleFormControlInput1" placeholder=""/>
        </p>
        <p>
            <label for="password" for="exampleFormControlInput1" class="form-label">Password</label>
            <form:errors class="text-danger" path="password"/>
            <input type="password" id="password" name="password" class="form-control" id="exampleFormControlInput1" placeholder=""/>
        </p>
        <input type="submit" value="Login!"/>
    </form>    
    </div>
    </div>
    </div>
</body>
</html>
