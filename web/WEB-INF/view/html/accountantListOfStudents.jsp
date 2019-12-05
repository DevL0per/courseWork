
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ListOfStudents</title>
    <style><%@include file="../css/listStyle.css"%></style>
</head>
<body>
<%@ include file="header.jsp"%>
<div class="pageTitle"><p>Студенты</p></div>
<hr/>

<form action="<c:url value="/MyServlet"/>" method="get" class = "searchStudentsForm">
    <input type="text" name = "searchParameter" placeholder="Поиск...">
    <br/>
    <input type="hidden" name="group" value="${group}">
    <input type="submit" name="parameter" value="список студентов">
</form>
<br/>

<c:forEach var="student" items="${requestScope.students}">
    <p>
    <form action="<c:url value="/MyServlet"/>" method="get" class = "listForm">
        <p><c:out value="${student.name}"/> <c:out value="${student.surname}"/></p>
        <input type="submit" name = "parameter" value="выставить оценоки">
        <input type="hidden" name="group" value="${group}">
        <c:if test="${student.role == Role.BANNED}">
            <input type="submit" name="parameter" value="разблокировать студента">
        </c:if>
        <c:if test="${student.role == Role.STUDENT}">
            <input type="submit" name="parameter" value="заблокировать студента">
        </c:if>
        <input type="submit" name = "parameter" value="редактировать оценки">
        <input type="hidden" name="studentId" value="<c:out value="${student.studentNumber}"/>">
    </form>
    <br/>
    <br/>
</c:forEach>
</body>
</html>
