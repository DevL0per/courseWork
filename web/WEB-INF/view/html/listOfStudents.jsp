<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>listOfStudents</title>
    <style><%@include file="../css/listStyle.css"%></style>
</head>
<body>
<%@ include file="header.jsp"%>
<br/>
<div class="pageTitle"><p>Студенты</p></div>
<hr/>
<c:forEach var="student" items="${requestScope.students}">
    <div class = "listForm">
        <p><c:out value="${student.name}"/></p>
    </div>
</c:forEach>
</body>
</html>
