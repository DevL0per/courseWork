<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ListOfSpecialities.jsp</title>
</head>
<body>
<%@ include file="header.jsp"%>
<br/>
Специальности
<hr/>
<c:forEach var="specialty" items="${requestScope.specialties}">
    <li><form action="<c:url value="/MyServlet"/>" method="get">
        <input type="hidden" name="parameter" value="показать группы специальности">
        <input type="hidden" name="specialtyId" value="<c:out value="${specialty.id}"/>">
        <input type="submit" name = "specialty" value="<c:out value="${specialty.name}"/>">
    </form>
    </li>
</c:forEach>
</body>
</html>
