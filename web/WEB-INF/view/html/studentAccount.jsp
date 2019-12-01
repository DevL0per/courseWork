
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>StudentAccount</title>
</head>
<body>
<html>
<head>
    <title>Registration</title>
    <style><%@include file="/WEB-INF/view/css/accountMenuStyle.css"%></style>
</head>
<body>
<%@ include file="header.jsp"%>
<div class = "profileImage">
    <img src="https://elysator.com/wp-content/uploads/blank-profile-picture-973460_1280-e1523978675847.png" alt="Письма мастера дзен">
</div>

<div class = "mainInfo">
    <p>Фио: ${student.name} ${student.surname} ${student.patronymic}</p>
    <br>
    <p>Email: ${student.email} </p>
</div>

<div class = "info">
    <p>группа: ${group.numberOfGroup}</p>
    <p>курс: ${group.course}</p>
    <p>специальности: ${specialty.name}</p>
    <p>успеваемость: </p>
    <%if (request.getAttribute("subjects") != null){ %>
    <table border="1" width="100%" cellpadding="5">
        <tr>
<c:forEach var="subject" items="${requestScope.subjects}">
            <th><c:out value="${subject.name}"/></th>
</c:forEach>
        </tr>
        <tr>
<c:forEach var="studentProgress" items="${requestScope.studentProgresses}">
            <td>
                <c:out value="${studentProgress.grade}"/>
            </td>
</c:forEach>
        </tr>
    </table>
    <%} else {%>
    Оценки не выставлены
    <%}%>
    <p>Стипендия ${student.scholarship}</p>
</div>
</body>
</html>
</body>
</html>
