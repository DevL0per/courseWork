
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ListOfStudents</title>
    <style><%@include file="../css/listStyle.css"%></style>
</head>
<body>
<%@ include file="header.jsp"%>
Студенты
<hr/>
<c:forEach var="student" items="${requestScope.students}">
    <p>
    <form action="<c:url value="/MyServlet"/>" method="get" class = "listForm">
        <p><c:out value="${student.name}"/> <c:out value="${student.surname}"/></p>
        <input type="submit" name = "parameter" value="выставить оценоки">
        <input type="submit" name = "parameter" value="редактировать оценки">
        <input type="hidden" name="studentId" value="<c:out value="${student.studentNumber}"/>">
    </form>
    <br/>
    <br/>
</c:forEach>
</body>
</html>
