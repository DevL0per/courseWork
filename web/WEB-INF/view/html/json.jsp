<%--
  Created by IntelliJ IDEA.
  User: romanvaznik
  Date: 04/12/2019
  Time: 11:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style><%@include file="../css/schedule.css"%></style>
    <title>Parse json</title>
</head>
<body>
<%@ include file="header.jsp"%>
<br>
Расписание на сегодня
<hr/>
<div class = "scheduleForm">
<input type="text" id = "groupNumber" name="groupNumber">
<input type="button" id = "groupButton" value="Расписание">
</div>

<br/>
<p class="headerForSchedule">расписание</p>
<div id = "json" class="schedule"></div>
<script>
    var xmlHttp = new XMLHttpRequest();

    document.getElementById("groupButton").onclick = showSchedule;

    function showSchedule() {

        var element = document.getElementById("json");
        element.innerHTML = '';

        var groupNumber = document.getElementById("groupNumber").value;
        var url = 'https://journal.bsuir.by/api/v1/studentGroup/schedule?studentGroup=' + groupNumber;
        xmlHttp.open("GET", url, false);

        xmlHttp.send(null);
        var json = JSON.parse(xmlHttp.responseText);
        console.log(json);
        var $civ = document.getElementById('json')
        json.todaySchedules.forEach(function (elem) {
            var $civ = document.getElementById('json');
            $civ.appendChild(document.createElement("div"));
            if (elem.auditory.length > 0) {
                $civ.append(elem.auditory[0]);
            }
            if (elem.employee.length > 0) {
                $civ.append(' ' + elem.employee[0].fio);
            }
            $civ.append(' ' + elem.subject);
            $civ.appendChild(document.createElement("hr"));
        });
    }
</script>
</body>
</html>
