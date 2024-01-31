<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.example.dto.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <% User user = (User) session.getAttribute("user"); %>
    <title>User List: <c:out value="${user.getRole()}"/></title>
</head>
<body>
    <h1>Logged in as: ${user.getEmail()}</h1>
    <h2>List of ${usersRole}s</h2>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Email</th>
            <th>Name</th>
            <th>Delete User</th>
        </tr>
        <c:forEach var="userInfo" items="${usersList}">
            <tr>
                <td>${userInfo.getId()}</td>
                <td>${userInfo.getEmail()}</td>
                <td>${userInfo.getName()}</td>
                <td>
                    <form action="${pageContext.request.contextPath}/${user.getRole()}" method="post">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="actionRole" value="${usersRole}">
                        <input type="hidden" name="userId" value="${userInfo.getId()}">
                        <input type="submit" value="Remove">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>

    <h2>Add ${usersRole}</h2>
    <form action="${pageContext.request.contextPath}/${user.getRole()}" method="post">
        <input type="hidden" name="action" value="add"}>
        <input type="hidden" name="actionRole" value="${usersRole}"}>
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required>
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" required>
        <input type="submit" value="Add user">
    </form>

    <br>
    <br>
    <form action="${pageContext.request.contextPath}/${user.getRole()}" method="get">
        <input type="submit" value="Back to Dashboard"/>
    </form>
</body>
</html>

