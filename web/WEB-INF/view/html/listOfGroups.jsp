<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ListOfGroups</title>
    <style><%@include file="../css/listStyle.css"%></style>
</head>
<body>
<%@ include file="header.jsp"%>
<br/>
Группы
<hr/>
<c:forEach var="group" items="${requestScope.groups}">
    <form action="<c:url value="/MyServlet"/>" method="get" class = "listForm">
        <input type="hidden" name="parameter" value="список студентов">
        <input type="submit" name = "group" value="<c:out value="${group.numberOfGroup}"/>">
    </form>
</c:forEach>
</body>
</html>
