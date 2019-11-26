
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
    <table border="1" width="100%" cellpadding="5">
        <tr>
            <th>Математика</th>
            <th>Физика</th>
        </tr>
        <tr>
            <td>3</td>
            <td>5</td>
        </tr>
    </table>
</div>
</body>
</html>
</body>
</html>
