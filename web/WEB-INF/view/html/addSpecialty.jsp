
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>addSpecialty</title>
    <style><%@include file="../css/addFormStyle.css"%></style>
</head>
<body>
<%@ include file="header.jsp"%>
<br/>
<p class="formHeader">Добавление</p>
<form action="<c:url value="/MyServlet"/>" method="post" class="addForm">
    <input type="text" name = "newSpecialty">
    <input type="hidden" name="facultyId" value="${facultyId}">
    <input type="hidden" name="parameter" value="добавить специальность">
    <input type="submit" value = "добавить специальность">
</form>
</body>
</html>
