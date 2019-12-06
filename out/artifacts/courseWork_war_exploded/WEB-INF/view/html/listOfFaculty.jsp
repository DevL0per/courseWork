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
 <div class="pageTitle"><p>Факультеты</p></div>
 <hr/>
 <form action="<c:url value="/MyServlet"/>" method="post" class = "addFacultyButton">
     <input type="submit" name = "parameter" value="добавить факультет">
     <input type="submit" name="parameter" value="добавить предмет">
 </form>
 <c:forEach var="faculty" items="${requestScope.faculties}">
     <p>
     <form action="<c:url value="/MyServlet"/>" method="get" class = "listForm">
         <input type="hidden" name="parameter" value="показать специальности факультета">
         <input type="hidden" name="facultyId" value="<c:out value="${faculty.id}"/>">
         <input type="submit" name = "faculty" value="<c:out value="${faculty.name}"/>">
     </form>
     <form action="<c:url value="/MyServlet"/>" method="get" class = "listParameters">
         <input type="hidden" name="facultyId" value="<c:out value="${faculty.id}"/>">
         <input type="submit" name = "parameter" value="добавить специальность">
     </form>
     <br/> <br/>
     </p>
 </c:forEach>
</body>
</html>
