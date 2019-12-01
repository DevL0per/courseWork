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
        <input type="hidden" name="parameter" value="ввод оценок">
        <input type="hidden" name="studentId" value="<c:out value="${student.studentNumber}"/>">
        <input type="submit" name = "studentName" value="<c:out value="${student.name}"/>">
    </form>
</c:forEach>
</body>
</html>
