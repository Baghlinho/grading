<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Grading System</title>
</head>
<body>
    <h1>Students Grading System</h1>
    <form action="${pageContext.request.contextPath}/login" method="post">
        Enter your credentials to login:
        <br>
        <label for="email">Email:</label>
        <input type="email" name="email" id="email" required>
        <br>
        <label for="password">Password:</label>
        <input type="password" name="password" id="password" required>
        <br>
        <input type="submit" value="Login">
    </form>
    <p><font color="red">${error}</font></p>
</body>
</html>