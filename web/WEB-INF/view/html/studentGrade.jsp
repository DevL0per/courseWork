<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>StudentGrades</title>
    <style><%@include file="../css/studentGrade.css"%></style>
</head>
<body>
${student.name}
&nbsp;${student.surname}
&nbsp;${student.patronymic}

<form action="<c:url value="/MyServlet"/>" method="get">
    <div class = "ExaminGrade">
        <select id = "sel1" name = "gradeSelect1">
        <c:forEach var="subject" items="${requestScope.subjects}">
            <option><c:out value="${subject.name}"/></option>
        </c:forEach>
        </select>
        <input type="text" name="grade1">
    </div>

    <div class = "ExaminGrade">
        <select id = "sel2" name = "gradeSelect2">
            <c:forEach var="subject" items="${requestScope.subjects}">
                <option><c:out value="${subject.name}"/></option>
            </c:forEach>
        </select>
        <input type="text" name="grade2">
    </div>

    <div class = "ExaminGrade">
        <select id = "sel3" name = "gradeSelect3">
            <c:forEach var="subject" items="${requestScope.subjects}">
                <option><c:out value="${subject.name}"/></option>
            </c:forEach>
        </select>
        <input type="text" name="grade3">
    </div>

    <div class = "ExaminGrade">
        <select id = "sel4" name = "gradeSelect4">
            <c:forEach var="subject" items="${requestScope.subjects}">
                <option><c:out value="${subject.name}"/></option>
            </c:forEach>
        </select>
        <input type="text" name="grade4">
    </div>

    <div class = "ExaminGrade">
        <select id = "sel5" name = "gradeSelect5">
            <c:forEach var="subject" items="${requestScope.subjects}">
                <option><c:out value="${subject.name}"/></option>
            </c:forEach>
        </select>
        <input type="text" name="grade5">
    </div>
    <input type="hidden" name = "studentNumber" value= "${student.studentNumber}">
    <input type="submit" name = "parameter" value = "рассчитать" class = "calculateButton">
</form>
</body>
</html>
