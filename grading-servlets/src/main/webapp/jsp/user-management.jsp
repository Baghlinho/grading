<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.example.dto.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <% User user = (User) session.getAttribute("user"); %>
    <title>User Management: <c:out value="${user.getRole()}"/></title>
</head>
<body>
    <h1>Logged in as: ${user.getEmail()}</h1>
    <h2>Choose Role of Users</h2>
    <div>
        <a href="${pageContext.request.contextPath}/${user.getRole()}?list=student">Manage Students</a>
        <br>
        <a href="${pageContext.request.contextPath}/${user.getRole()}?list=instructor">Manage Instructors</a>
        <br>
        <a href="${pageContext.request.contextPath}/${user.getRole()}?list=admin">Manage Admins</a>
    </div>
    <br>
    <br>
    <form action="${pageContext.request.contextPath}/logout" method="post">
        <input type="submit" value="Logout"/>
    </form>
</body>
</html>

