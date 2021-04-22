<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
    
<!DOCTYPE html>
<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
	<meta charset="UTF-8">
	<title>Event Dashboard</title>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <div class="container-fluid">
    <a class="navbar-brand">Welcome <c:out value="${user.firstName}" /></a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav">
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="/logout">Logout</a>
        </li>
      </ul>
    </div>
  </div>
</nav>
</body>
<div class="p-3 mb-2 bg-secondary text-white">
	<div style="display: inline-block;">

	<h1 style="margin-left: 100px">Events Going on in <c:out value="${user.state}" />:</h1>

		<div class="input-group mb-3" style="margin-left: 100px">

			<c:if test="${instate.size() > 0}">
				<table class="table table-info">
					<thead>
						<tr>
							<th scope="col">Name</th>
							<th scope="col">Date</th>
							<th scope="col">Location</th>
							<th scope="col">Host</th>
							<th scope="col">Action/Status</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${instate}" var="in">
							<tr>
								<td><a href="/events/${in.id}"><c:out
											value="${in.name}" /></a></td>
								<td><fmt:formatDate pattern="MM dd, yyyy"
										value="${in.date}" /></td>
								<td><c:out value="${in.location}" /></td>
								<td><c:out value="${in.user.firstName}" /></td>
								<c:choose>
									<c:when test="${in.user == user}">
										<td>*Attending* | <a href="/events/${in.id}/edit">Edit</a>
											| <a href="events/${in.id}/delete">Delete</a></td>
									</c:when>
									<c:otherwise>
										<c:set var="attending" value="${false}" />
										<c:forEach items="${in.getJoinedUsers()}" var="attendee">
											<c:if test="${attendee == user}">
												<c:set var="attending" value="${true}" />
											</c:if>
										</c:forEach>
										<c:choose>
											<c:when test="${attending == false}">
												<td><a href="/events/${in.id}/join">Join</a></td>
											</c:when>
											<c:otherwise>
												<td>*Attending* | <a href="events/${in.id}/cancel">Cancel</a></td>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
		</div>
		<h2 style="margin-left: 100px">Here are some of the events in other states:</h2>
		<table class="table table-warning" style="margin-left: 100px">
			<thead>
				<tr>
					<th scope="col">Name</th>
					<th scope="col">Date</th>
					<th scope="col">Location</th>
					<th scope="col">State</th>
					<th scope="col">Host</th>
					<th scope="col">Action</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${outofstate}" var="out">
					<tr>
						<td><a href="/events/${out.id}"><c:out
									value="${out.name}" /></a></td>
						<td><fmt:formatDate pattern="MMMM dd, yyyy"
								value="${out.date}" /></td>
						<td><c:out value="${out.location}" /></td>
						<td><c:out value="${out.state}" /></td>
						<td><c:out value="${out.user.firstName}" /></td>
						<c:choose>
							<c:when test="${out.user == user}">
								<td>*Attending* | <a href="/events/${out.id}/edit">Edit</a>
									| <a href="events/${out.id}/delete">Delete</a></td>
							</c:when>
							<c:otherwise>
								<c:set var="attending" value="${false}" />
								<c:forEach items="${out.getJoinedUsers()}" var="goer">
									<c:if test="${goer == user}">
										<c:set var="attending" value="${true}" />
									</c:if>
								</c:forEach>
								<c:choose>
									<c:when test="${attending == false}">
										<td><a href="/events/${out.id}/join">Join</a></td>
									</c:when>
									<c:otherwise>
										<td>*Attending* | <a href="events/${out.id}/cancel">Cancel</a></td>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="input-group mb-3" style="margin-left: 100px">
			<form:form method="POST" action="/addevent" modelAttribute="event">
				<h2>Create an event of your own!</h2>
				<p>
					<form:label class="form-label" path="name">Name:</form:label>
					<form:errors class="text-danger"  path="name" />
					<form:input type="name" path="name" class="form-control" id="name" />
				</p>
				<p>
					<form:label for="date" class="form-label" path="date">Date(yyyy-mm-dd):</form:label>
					<form:input type="text" path="date" class="form-control" id="date" placeholder="yyyy-mm-dd" />
				</p>
				<p>
					<form:label for="location" class="form-label" path="location">Location:</form:label>
					<form:errors class="text-danger"  path="location" />
					<form:input type="text" path="location" class="form-control" id="location" />
				</p>
				<p>
					<form:label path="state" for="state">State: </form:label>
					<form:errors class="text-danger" path="state" />
					<form:select class="form-control" path="state">
						<c:forEach items="${states}" var="state">
							<form:option path="state" value="${state}">
								<c:out value="${state}" />
							</form:option>
						</c:forEach>
					</form:select>
				</p>
				<form:hidden path="user" value="${user.id}" />
				<input type="submit" value="Add event!" />
			</form:form>
		</div>
	</div>
	</div>
</body>
</html>
