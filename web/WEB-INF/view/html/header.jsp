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
    <div class = "title">
        <h3>белорусский</h3>
        <h3>государственный</h3>
        <h3>университет</h3>
        <h4>информатики</h4>
        <h4>и радиоэлектроники </h4>
    </div>
    <ul>
        <%if(session.getAttribute("role") == Role.UNKNOWN || session.getAttribute("role") == null) {%>
        <form action="<c:url value="/MyServlet"/>" method="get">
            <li> <input type = "submit" name = "parameter" value="вход"> </li>
        </form>
        <%}else { %>
        <form action="<c:url value="/MyServlet"/>" method="get">
            <li> <input type = "submit" name = "parameter" value="выйти"> </li>
        </form>
        <form action="<c:url value="/MyServlet"/>" method="get">
            <li> <input type = "submit" name = "parameter" value="кабинет"> </li>
        </form>
        <form action="<c:url value="/MyServlet"/>" method="get">
            <li> <input type = "submit" name = "parameter" value="факультеты"> </li>
        </form>
        <%}%>
        <form action="<c:url value="/MyServlet"/>" method="get">
            <li> <input type = "submit" name = "parameter" value="регистрация"> </li>
        </form>
    </ul>
</nav>
</body>
</html>
