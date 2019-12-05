<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>EditStudentGrades</title>
    <style><%@include file="/WEB-INF/view/css/registrationStyle.css"%></style>
</head>
<body>
<%@ include file="header.jsp"%>
<div class="pageTitle"><p>Редактирование последних выставленных оценок</p></div>
<hr/>
<div class="editGradesForm">
<c:forEach items="${requestScope.lastGrades}" var="count">
    <form action="<c:url value="/MyServlet"/>" method="post">
            <p class="subjectName"><c:out value="${count.key.name}"/></p>
            <input type="hidden" name="gradeId" value="<c:out value="${count.value.numberOfGrade}"/>">
            <input type="text" name = "value" value="<c:out value="${count.value.grade}"/>">
            <input type="submit" name = "parameter" value="редактировать оценки"/>
    </form>
</c:forEach>
</div>
</body>
</html>
