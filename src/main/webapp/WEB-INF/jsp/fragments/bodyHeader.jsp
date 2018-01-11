<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="header.jsp"/>
</head>
<body>
<nav class="navbar navbar-toggleable-xl navbar-inverse bg-inverse">
    <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse"
            data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false"
            aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <a class="navbar-brand" href="${pageContext.request.contextPath}/">PomoApp</a>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/">Timer<span
                        class="sr-only">(current)</span></a>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/log">Log<span
                        class="sr-only">(current)</span></a>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/profile">Profile<span
                        class="sr-only">(current)</span></a>
            </li>
        </ul>
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/logout">
            Logout
        </a>
    </div>
</nav>
</body>
</html>
