<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<!DOCTYPE html>
<html>
<head>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <meta charset="UTF-8">
    <title>Login Page</title>
</head>
<body>
    <h1>Login</h1> 
    <p><c:out value="${error}" /></p>
    <div class="input-group mb-3" style="margin-left: 100px">
   
    <form method="post" action="/login">
        <p>
            <label for="email"for="exampleFormControlInput1" class="form-label">Email</label>
            <form:errors class="text-danger"  path="email"/>
            <input id="email" name="email" type="email" class="form-control" id="exampleFormControlInput1" placeholder=""/>
        </p>
        <p>
            <label for="password" for="exampleFormControlInput1" class="form-label">Password</label>
            <form:errors class="text-danger"  path="password"/>
            <input type="password" id="password" name="password" class="form-control" id="exampleFormControlInput1" placeholder=""/>
        </p>
        <input type="submit" value="Login!"/>
    </form>    
    </div>
</body>
</html>
