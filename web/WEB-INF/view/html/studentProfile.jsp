<%--
  Created by IntelliJ IDEA.
  User: romanvaznik
  Date: 06/12/2019
  Time: 21:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>StudentProfile</title>
</head>
<body>
<%@ include file="header.jsp"%>
<br/>
<div class="pageTitle"><p>Информация о студенте</p></div>
<hr>

<div class="infoForm">
    <p>ФИО: ${student.name} ${student.surname} ${student.patronymic}</p>
    <p>Телефон: ${student.phoneNumber}</p>
    <p>Почта: ${student.email}</p>
</div>
</body>
</html>
