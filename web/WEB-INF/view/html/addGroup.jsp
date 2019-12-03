<%--
  Created by IntelliJ IDEA.
  User: romanvaznik
  Date: 03/12/2019
  Time: 13:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>addGroup</title>
</head>
<body>
<%@ include file="header.jsp"%>
<form action="<c:url value="/MyServlet"/>" method="get">
    <input type="text" name = "newGroup">
    <input type="text" name="newGroupCourse">
    <input type="hidden" name="specialtyId" value="${specialtyId}">
    <input type="submit" name="parameter" value="добавить группу">
</form>
</body>
</html>
