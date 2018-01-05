<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Pomodoro</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
</head>
<body>
<div id="pomodoro-app">
    <div id="container">
        <div id="timer">
            <div id="time">
                <span id="minutes">25</span>
                <span id="colon">:</span>
                <span id="seconds">00</span>
            </div>
            <div id="filler"></div>
        </div>

        <div id="buttons">
            <button id="work">Work</button>
            <button id="shortBreak">Short Break</button>
            <button id="longBreak">Long Break</button>
            <button id="stop">Stop</button>
        </div>
    </div>
</div>
</body>
<script src="${pageContext.request.contextPath}/resources/js/app.js"></script>
</html>