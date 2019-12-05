<%@ page import="model.Role" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>header</title>
    <style><%@include file="../css/navStyle.css"%></style>
</head>
<body>
<nav>
    <img src="https://www.bsuir.by/m/12_100229_1_91911.png" alt="BSUIR_LOGO" class = "logo">
    <div class = "title">
        <h3>Белорусский государственный</h3>
        <h3>университет информатики</h3>
        <h3>и радиоэлектроники</h3>
    </div>
    <ul>
        <c:choose>
            <c:when test="${role == Role.UNKNOWN || role == null}">
                <form action="<c:url value="/MyServlet"/>" method="get">
                    <li> <input type = "submit" name = "parameter" value="вход"> </li>
                </form>
                <form action="<c:url value="/MyServlet"/>" method="get">
                    <li> <input type = "submit" name = "parameter" value="регистрация"> </li>
                </form>
            </c:when>
            <c:when test="${role == Role.ACCOUNTANT || role == Role.STUDENT}">
                <form action="<c:url value="/MyServlet"/>" method="get">
                    <li> <input type = "submit" name = "parameter" value="выйти"> </li>
                </form>
                <form action="<c:url value="/MyServlet"/>" method="get">
                    <li> <input type = "submit" name = "parameter" value="кабинет"> </li>
                </form>
                <form action="<c:url value="/MyServlet"/>" method="get">
                    <li> <input type = "submit" name = "parameter" value="факультеты"> </li>
                </form>
            </c:when>
        </c:choose>
        <form action="<c:url value="/MyServlet"/>" method="get">
            <li> <input type = "submit" name = "parameter" value="расписание"> </li>
        </form>
    </ul>
</nav>
</body>
</html>
