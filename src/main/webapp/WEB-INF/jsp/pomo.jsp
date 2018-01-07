<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Pomodoro</title>

    <base href="${pageContext.request.contextPath}/"/>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">

    <script type="text/javascript" src="webjars/jquery/3.2.1/dist/jquery.min.js" defer></script>
    <script type="text/javascript" src="webjars/bootstrap/3.3.7-1/js/bootstrap.min.js" defer></script>
    <script src="${pageContext.request.contextPath}/resources/js/app.js"></script>
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
</html>