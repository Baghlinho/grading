<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.example.dto.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <% User user = (User) session.getAttribute("user"); %>
    <title>Course Stats: <c:out value="${user.getRole()}"/></title>
</head>
<body>
    <h1>Logged in as: ${user.getEmail()}</h1>
    <c:choose>
        <c:when test="${coursesWithStats.isEmpty()}">
            <h2>Does not have any courses</h2>
        </c:when>
        <c:otherwise>
            <h2>Courses and their stats:</h2>
            <h3><em>click on the course name to view the students grades</em></h3>
            <table border="1">
                <tr>
                    <th>Course</th>
                    <th>Average</th>
                    <th>Maximum</th>
                    <th>Minimum</th>
                    <th>Number of Students</th>
                </tr>
                <c:forEach var="course" items="${coursesWithStats}">
                    <c:set var="courseStats" value="${course.getCourseStats()}" />
                    <tr>
                        <td>
                            <a href="${pageContext.request.contextPath}/${user.getRole()}?id=${course.getId()}&name=${course.getName()}">
                                ${course.getName()}
                            </a>
                        </td>
                        <td>${courseStats.getAverage()}</td>
                        <td>${courseStats.getMax()}</td>
                        <td>${courseStats.getMin()}</td>
                        <td>${courseStats.getStudentsCount()}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>
    <br>
    <br>
    <form action="${pageContext.request.contextPath}/logout" method="post">
        <input type="submit" value="Logout"/>
    </form>
</body>
</html>