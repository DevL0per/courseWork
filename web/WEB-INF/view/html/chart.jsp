<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style><%@include file="../css/chartStyle.css"%></style>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Schedule</title>

    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script>
        google.charts.load('current', {packages: ['corechart', 'bar']});
        google.charts.setOnLoadCallback(drawBasic);

        function drawBasic() {

            var data = google.visualization.arrayToDataTable([
                ['', 'средний балл'],
                <c:forEach items="${requestScope.gradesPerSemester}" var="count">
                ['<c:out value="${count.key}"/>', <c:out value="${count.value}"/>],
                </c:forEach>
            ]);

            var options = {
                title: 'Средний балл за семестры',
                chartArea: {width: '50%'},
                hAxis: {
                    title: 'Балл',
                    minValue: 1
                },
                vAxis: {
                    title: 'Семестер'
                }
            };
            var chart = new google.visualization.BarChart(document.getElementById('chart_div'));
            chart.draw(data, options);
        }
    </script>
</head>
<body>
<%@ include file="header.jsp"%>
<br/>
<div class="pageTitle"><p>График успеваемости</p></div>
<hr/>
<div class="chartDiv">
    <div id="chart_div" ></div>
    <br/>
    <input type="button" onclick="history.back()" value="Назад" class="backButton"/>
</div>
</body>
</html>
