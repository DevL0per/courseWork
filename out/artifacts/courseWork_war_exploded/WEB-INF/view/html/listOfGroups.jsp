<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ListOfGroups</title>
</head>
<body>
<%@ include file="header.jsp"%>
<br/>
Группы
<hr/>
<c:forEach var="group" items="${requestScope.groups}">
    <li><form action="<c:url value="/MyServlet"/>" method="get">
        <input type="hidden" name="parameter" value="показать группы специальности">
        <input type="submit" name = "group" value="<c:out value="${group.numberOfGroup}"/>">
    </form>
    </li>
</c:forEach>
</body>
</html>
