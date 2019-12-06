<%--
  Created by IntelliJ IDEA.
  User: romanvaznik
  Date: 18/11/2019
  Time: 13:56
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
    <style><%@include file="/WEB-INF/view/css/registrationStyle.css"%></style>
</head>
<body>
<%@ include file="header.jsp"%>
<br/>
 <div class="pageTitle"><p>Регистрация</p></div>
<hr/>
<div class = "window">
    <form action="<c:url value="/registrationServlet"/>" method="post">

        <div class = "field">
            <label for = "in1">Имя</label>
            <input type = "text" name = "name" id = "in1"><br/>
        </div>

        <div class = "field">
            <label for = "in2">Фамилия</label>
            <input type = "text" name = "surname" id = "in2"><br/>
        </div>

        <div class = "field">
            <label for = "in3">Отчество</label>
            <input type = "text" name = "patronymic" id = "in3"><br/>
        </div>

        <div class = "field">
            <label for = "in4">Номер студенческого</label>
            <input type = "text" name = "studentNumber" id = "in4"><br/>
        </div>

        <div class = "field">
            <label for = "sel2">Группа</label>
            <select id = "sel2" name="group">
                <c:forEach var="group" items="${requestScope.groups}">
                    <option><c:out value="${group.numberOfGroup}"/></option>
                </c:forEach>
            </select>
            <br/>
        </div>

        <div class = "field">
            <label for = "in5">Телефон</label>
            <input type = "text" name = "number" id = "in5"><br/>
        </div>

        <div class = "field">
            <label for = "in6">Почта</label>
            <input type = "email" name = "email" id = "in6"><br/>
        </div>

        <div class = "field">
            <label for = "in7">Пароль</label>
            <input type = "password" name = "password" id = "in7"><br/>
        </div>

        <div class = "field">
            <label for = "in7">Форма обучения</label>
            <select name="formOfTraining">
                <option>бюджетная</option>
                <option>платная</option>
            </select>
        </div>

        <input type="submit" value = "Регистрация" class = "registrationButton">

    </form>

    <div class="errorMessageClass">

        <c:if test="${errorMessage != null}">
            ${errorMessage}
        </c:if>
    </div>

</div>
</body>
</html>
