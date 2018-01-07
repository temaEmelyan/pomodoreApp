<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Pomodoro</title>

    <base href="${pageContext.request.contextPath}/"/>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css"
          integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.1.1.slim.min.js"
            integrity="sha384-A7FZj7v+d/sdmMqp/nOQwliLvUsJfDHW+k9Omg/a/EheAdgtzNs3hpfag6Ed950n"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"
            integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb"
            crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js"
            integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn"
            crossorigin="anonymous"></script>

    <script type="text/javascript" src="webjars/jquery/3.2.1/dist/jquery.min.js" defer></script>
    <script type="text/javascript" src="webjars/bootstrap/3.3.7-1/js/bootstrap.min.js" defer></script>
    <script src="${pageContext.request.contextPath}/resources/js/app.js"></script>
</head>
<body>
<div id="pomodoro-app">
    <div class="container" style="padding-top: 50px; width: 600px">
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
    </div>

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