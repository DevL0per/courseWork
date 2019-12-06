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
<br/>
<div class = "profileImage">
    <img src="https://elysator.com/wp-content/uploads/blank-profile-picture-973460_1280-e1523978675847.png" alt="Письма мастера дзен">
</div>

<form action="<c:url value="/MyServlet"/>" method="get" class="studentAccountButton">
    <input type="hidden" name="studentId" value="${student.studentNumber}">
    <p><input type="submit" name="parameter" value="редактировать аккаунт"></p>
</form>

<form action="<c:url value="/MyServlet"/>" method="get" class= "studentAccountButton">
    <input type="hidden" name = "studentId" value="${student.studentNumber}">
    <input type="submit" name="parameter" value="показать статистику">
</form>

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

    <c:choose>
        <c:when test="${studentProgresses != null}">
            <table border="1" width="100%" cellpadding="5">
                <tr>
                    <th>Семестр</th>
                    <th>Предмет</th>
                    <th>Оценка</th>
                </tr>

                <c:forEach items="${requestScope.studentProgresses}" var="count">
                    <tr>
                        <td><c:out value="${count.key.numberOfSemester}"/></td>
                        <td><c:out value="${count.value.name}"/></td>
                        <td><c:out value="${count.key.grade}"/></td>
                    </tr>
                </c:forEach>
            </table>
            <br>
            <p>Осталось балл до повышения стипендии: ${averageBallToNextScholarship}</p>
        </c:when>
        <c:when test="${studentProgresses == null}">
            Оценки не выставлены
        </c:when>
    </c:choose>
    <p>Стипендия ${student.scholarship}</p>
</div>
</body>
</html>
</body>
</html>
