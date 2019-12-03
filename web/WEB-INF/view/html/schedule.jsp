<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: romanvaznik
  Date: 03/12/2019
  Time: 22:38
  To change this template use File | Settings | File Templates.
--%>
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
График успеваемости
<hr/>
<div id="chart_div" style="width: 500px; height: 400px;"></div>
<input type="button" onclick="history.back()" value="Назад"/>
</body>
</html>
