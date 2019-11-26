<%--
  Created by IntelliJ IDEA.
  User: romanvaznik
  Date: 14/11/2019
  Time: 13:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <style><%@include file="/WEB-INF/view/css/loginStyle.css"%></style>
</head>
<body>
    <%@ include file="header.jsp"%>
<div class="myClass">
    <h1>Login</h1>
    <form method="post" action="${pageContext.request.contextPath}/MyServlet">
        <input type="text" required placeholder="email" name="login" class = "loginFieldClass">
        <br/>
        <input type="password" required placeholder="password" name="password" class = "loginFieldClass">
        <br/>
        <input type="submit" value="Войти">
    </form>
    <form method="get" action="${pageContext.request.contextPath}/registrationServlet" class = "registrationButton">
        <input type="submit" name = "button" value="Регистрация">
    </form>
</div>
</body>
</html>
