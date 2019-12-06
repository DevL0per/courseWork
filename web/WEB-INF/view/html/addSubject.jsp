<%--
  Created by IntelliJ IDEA.
  User: romanvaznik
  Date: 06/12/2019
  Time: 22:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>AddSubject</title>
    <style><%@include file="../css/addFormStyle.css"%></style>
</head>
<body>
<%@ include file="header.jsp"%>
<br/>
<p class="formHeader">Добавление</p>
<form action="<c:url value="/MyServlet"/>" method="post" class = "addForm">
    <input type="text" name = "newSubjectName">
    <input type="submit" name="parameter" value="добавить предмет">
</form>
<div class="errorMessageClass">
    <c:if test="${errorMessage != null}">
        ${errorMessage}
    </c:if>
</div>
</body>
</html>
