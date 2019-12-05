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
    <style><%@include file="../css/addFormStyle.css"%></style>
</head>
<body>
<%@ include file="header.jsp"%>
<br/>
<p class="formHeader">Добавление</p>
<form action="<c:url value="/MyServlet"/>" method="post" class = "addForm">
    <input type="text" name = "newGroup">
    <input type="text" name="newGroupCourse">
    <input type="hidden" name="specialtyId" value="${specialtyId}">
    <input type="submit" name="parameter" value="добавить группу">
</form>
</body>
</html>
