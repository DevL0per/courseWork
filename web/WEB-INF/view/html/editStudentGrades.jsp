<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>EditStudentGrades</title>
</head>
<body>
<%@ include file="header.jsp"%>
Редактирование последних выставленных оценок
<hr/>
<c:forEach items="${requestScope.lastGrades}" var="count">
    <form action="<c:url value="/MyServlet"/>" method="get">
        <p><c:out value="${count.key.name}"/>
            <input type="hidden" name="gradeId" value="<c:out value="${count.value.numberOfGrade}"/>">
            <input type="text" name = "value" value="<c:out value="${count.value.grade}"/>">
            <input type="submit" name = "parameter" value="редактировать оценки"/>
        </p>
    </form>
</c:forEach>
</body>
</html>
