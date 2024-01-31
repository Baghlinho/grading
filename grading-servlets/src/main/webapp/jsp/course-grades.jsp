<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.example.dto.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <% User user = (User) session.getAttribute("user"); %>
    <title>Course Grades: <c:out value="${user.getRole()}"/></title>
</head>
<body>
    <% String email = (String) session.getAttribute("email"); %>
    <h1>Logged in as: <%= email %></h1>
    <h2>${param.name} Course Grades</h2>
    <table border="1">
        <tr>
            <th>Student ID</th>
            <th>Student Name</th>
            <th>Grade Percent</th>
            <c:if test = "${user.getRole() eq 'instructor'}">
                <th>Enter New Grade</th>
            </c:if>
        </tr>
        <c:forEach var="studentGrade" items="${courseGrades}">
            <c:set var="student" value="${studentGrade.getStudent()}" />
            <tr>
                <td>${student.getId()}</td>
                <td>${student.getName()}</td>
                <td>${studentGrade.getGradePercent()}</td>
                <c:if test = "${user.getRole() eq 'instructor'}">
                    <td>
                        <form action="${pageContext.request.contextPath}/instructor" method="post">
                            <input type="hidden" name="courseId" value="${param.id}">
                            <input type="hidden" name="courseName" value="${param.name}">
                            <input type="hidden" name="gradeId" value="${studentGrade.getId()}">
                            <input type="number" name="updatedGrade" step="1"/>
                            <input type="submit" value="Update"/>
                        </form>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
    </table>
    <br>
    <br>
    <form action="${pageContext.request.contextPath}/${user.getRole()}" method="get">
        <input type="submit" value="Back to Dashboard"/>
    </form>
</body>
</html>