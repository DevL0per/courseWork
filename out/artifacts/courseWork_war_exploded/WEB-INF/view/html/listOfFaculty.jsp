<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>List</title>
    <style><%@include file="../css/listStyle.css"%></style>
</head>
<body>
 <%@ include file="header.jsp"%>
 <br/>
  Факультеты
 <hr/>
 <c:forEach var="faculty" items="${requestScope.faculties}">
     <form action="<c:url value="/MyServlet"/>" method="get" class = "listForm">
         <input type="hidden" name="parameter" value="показать специальности факультета">
         <input type="hidden" name="facultyId" value="<c:out value="${faculty.id}"/>">
         <input type="submit" name = "faculty" value="<c:out value="${faculty.name}"/>">
     </form>
 </c:forEach>
</body>
</html>
