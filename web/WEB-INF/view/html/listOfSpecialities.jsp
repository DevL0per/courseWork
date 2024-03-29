<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ListOfSpecialities.jsp</title>
    <style><%@include file="../css/listStyle.css"%></style>
</head>
<body>
<%@ include file="header.jsp"%>
<br/>
<div class="pageTitle"><p>Специальности</p></div>
<hr/>
<c:forEach var="specialty" items="${requestScope.specialties}">
    <form action="<c:url value="/MyServlet"/>" method="get" class = "listForm">
        <input type="hidden" name="parameter" value="показать группы специальности">
        <input type="hidden" name="specialtyId" value="<c:out value="${specialty.id}"/>">
        <input type="submit" name = "specialty" value="<c:out value="${specialty.name}"/>">
    </form>
    <form action="<c:url value="/MyServlet"/>" class="listParameters" method="get">
        <input type="hidden" name="specialtyId" value="<c:out value="${specialty.id}"/>">
        <input type="submit" name="parameter" value="добавить группу">
    </form>
    <br/><br/>
</c:forEach>
</body>
</html>
