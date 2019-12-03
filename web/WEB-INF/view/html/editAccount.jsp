<%--
  Created by IntelliJ IDEA.
  User: romanvaznik
  Date: 02/12/2019
  Time: 22:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>EditAccount</title>
    <style><%@include file="/WEB-INF/view/css/registrationStyle.css"%></style>
</head>
<body>

<%@ include file="header.jsp"%>

<div class = "window">
    <form action="<c:url value="/MyServlet"/>" method="get">

        <div class = "field">
            <label for = "in1">Имя</label>
            <input type = "text" name = "newName" id = "in1" value="${student.name}"><br/>
        </div>

        <div class = "field">
            <label for = "in2">Фамилия</label>
            <input type = "text" name = "newSurname" id = "in2" value="${student.surname}"><br/>
        </div>

        <div class = "field">
            <label for = "in3">Отчество</label>
            <input type = "text" name = "newPatronymic" id = "in3" value="${student.patronymic}"><br/>
        </div>

        <div class = "field">
            <label for = "in5">Телефон</label>
            <input type = "text" name = "newPhoneNumber" id = "in5" value="${student.phoneNumber}"><br/>
        </div>

        <div class = "field">
            <label for = "in6">Почта</label>
            <input type = "text" name = "newEmail" id = "in6" value="${student.email}"><br/>
        </div>

        <div class = "field">
            <label for = "in7">Пароль</label>
            <input type = "password" name = "newPassword" id = "in7" value="${student.password}"><br/>
        </div>

        <input type = "submit" name="parameter" value = "редактировать аккаунт" class = "registrationButton/">

    </form>

<%--<form action="<c:url value="/MyServlet"/>" method="get">--%>
<%--    <input type="text" name="newName" value="${student.name}">--%>
<%--    <input type="text" name="newSurname" value="${student.surname}">--%>
<%--    <input type="text" name="newPatronymic" value="${student.patronymic}">--%>
<%--    <input type="text" name="newPhoneNumber" value="${student.phoneNumber}">--%>
<%--    <input type="text" name="newEmail" value="${student.email}">--%>
<%--    <input type="password" name="newPassword" value="${student.password}">--%>
<%--    --%>
<%--    --%>
<%--    <c:forEach var="group" items="${requestScope.groups}">--%>
<%--        <option><c:out value="${group.numberOfGroup}"/></option>--%>
<%--    </c:forEach>--%>
<%--</form>--%>
</body>
</html>
