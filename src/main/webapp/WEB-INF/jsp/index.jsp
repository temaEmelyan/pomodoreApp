<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<style>
    body {
        background-color: #333333;
    }

    .clickMe {
        -moz-appearance: button;
        -ms-appearance: button;
        -o-appearance: button;
        -webkit-appearance: button;
        appearance: button;
        text-decoration: none;
        color: #000;
        padding: 0.2em 0.4em;
    }
</style>
<html>
<head>
</head>
<body>
<section>
    <a class="clickMe" href="${pageContext.request.contextPath}/work">Kick ASS</a>
    <a class="clickMe" href="${pageContext.request.contextPath}/poms">Check out progress</a>
</section>
</body>
</html>