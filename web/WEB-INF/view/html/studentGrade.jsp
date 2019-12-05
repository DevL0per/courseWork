<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>StudentGrades</title>
    <style><%@include file="../css/studentGrade.css"%></style>
</head>
<body>

<%@ include file="header.jsp"%>
<br>

${student.name}&nbsp;${student.surname}&nbsp;${student.patronymic}

<p>Семестр: ${semester}</p>

<c:choose>
    <c:when test="${error == null}">
        <form action="<c:url value="/MyServlet"/>" method="get">
            <div class = "examGrade">
                <select id = "sel1" name = "gradeSelect1">
                    <c:forEach var="subject" items="${requestScope.subjects}">
                        <option><c:out value="${subject.name}"/></option>
                    </c:forEach>
                    <option>-</option>
                </select>
                <select name="grade1">
                    <c:forEach var = "number" begin="0" end="10">
                        <option>${number}</option>
                    </c:forEach>
                </select>
            </div>

            <div class = "examGrade">
                <select id = "sel2" name = "gradeSelect2">
                    <c:forEach var="subject" items="${requestScope.subjects}">
                        <option><c:out value="${subject.name}"/></option>
                    </c:forEach>
                    <option>-</option>
                </select>
                <select name="grade2">
                    <c:forEach var = "number" begin="0" end="10">
                        <option>${number}</option>
                    </c:forEach>
                </select>
            </div>

            <div class = "examGrade">
                <select id = "sel3" name = "gradeSelect3">
                    <c:forEach var="subject" items="${requestScope.subjects}">
                        <option><c:out value="${subject.name}"/></option>
                    </c:forEach>
                    <option>-</option>
                </select>
                <select name="grade3">
                    <c:forEach var = "number" begin="0" end="10">
                        <option>${number}</option>
                    </c:forEach>
                </select>
            </div>

            <div class = "examGrade">
                <select id = "sel4" name = "gradeSelect4">
                    <c:forEach var="subject" items="${requestScope.subjects}">
                        <option><c:out value="${subject.name}"/></option>
                    </c:forEach>
                    <option>-</option>
                </select>
                <select name="grade4">
                    <c:forEach var = "number" begin="0" end="10">
                        <option>${number}</option>
                    </c:forEach>
                </select>
            </div>

            <div class = "examGrade">
                <select id = "sel5" name = "gradeSelect5">
                    <c:forEach var="subject" items="${requestScope.subjects}">
                        <option><c:out value="${subject.name}"/></option>
                    </c:forEach>
                    <option>-</option>
                </select>
                <select name="grade5">
                    <c:forEach var = "number" begin="0" end="10">
                        <option>${number}</option>
                    </c:forEach>
                </select>
            </div>

            <input type="hidden" name = "numberOfSemester" value="${semester}">
            <input type="hidden" name = "studentNumber" value= "${student.studentNumber}">
            <input type="hidden" name = "formOfTraining" value= "${student.formOfTraining}">
            <input type="submit" name = "parameter" value = "рассчитать" class = "calculateButton">
        </form>
    </c:when>
    <c:when test="${error != null}">
        ${error}
    </c:when>
</c:choose>

</body>
</html>
