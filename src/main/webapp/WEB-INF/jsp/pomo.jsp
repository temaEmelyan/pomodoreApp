<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="en">
<jsp:include page="fragments/header.jsp"/>
<head>
    <script src="${pageContext.request.contextPath}/resources/js/app.js"></script>
</head>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div id="pomodoro-app">
    <div class="container-fluid myContainer">
        <div class="row" style="text-align: center;">
            <div class="col">
                <div class="row">
                    <div class="row mx-auto">
                        <button id="decreaseDuration" class="btn pl-min-btns">-</button>
                        <h3 id="duration" style="color:white; padding: 0 10px 0 10px;">25</h3>
                        <button id="increaseDuration" class="btn pl-min-btns">+</button>
                    </div>
                </div>
            </div>
            <div class="col" style="align-items: left">
                <div class="row">
                    <div class="mx-auto row">
                        <button id="decreaseDurationBrake" class="btn pl-min-btns">-</button>
                        <h3 id="durationBreak" style="color:white; padding: 0 10px 0 10px;">5</h3>
                        <button id="increaseDurationBrake" class="btn pl-min-btns">+</button>
                    </div>
                </div>
            </div>
        </div>

        <div id="timer">
            <div id="time">
                <span id="minutes">25</span>
                <span id="colon">:</span>
                <span id="seconds">00</span>
            </div>
            <div id="filler"></div>
        </div>

        <div class="row" id="buttons">
            <button id="work">Work</button>
            <button id="shortBreak">Short Break</button>
            <button id="longBreak">Long Break</button>
            <button id="stop">Stop</button>
        </div>
    </div>
</div>
</body>
<script>

</script>
</html>