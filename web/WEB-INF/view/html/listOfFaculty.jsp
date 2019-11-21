<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>List</title>
</head>
<body>
 <%@ include file="header.jsp"%>
 <br/>
 Факультеты
 <hr/>
     <ul>
 <c:forEach var="faculty" items="${requestScope.faculties}">
     <li><form action="<c:url value="/MyServlet"/>" method="get">
         <input type="hidden" name="parameter" value="показать специальности факультета">
         <input type="hidden" name="facultyId" value="<c:out value="${faculty.id}"/>">
         <input type="submit" name = "faculty" value="<c:out value="${faculty.name}"/>">
     </form>
     </li>
 </c:forEach>
     </ul>
</body>
</html>
