<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>listOfStudents</title>
    <style><%@include file="../css/listStyle.css"%></style>
</head>
<body>
<%@ include file="header.jsp"%>
<br/>
Студенты
<hr/>
<c:forEach var="student" items="${requestScope.students}">
    <form action="<c:url value="/MyServlet"/>" method="get" class = "listForm">
        <p>"<c:out value="${student.name}"/></p>
    </form>
</c:forEach>
</body>
</html>
