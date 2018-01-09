<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<jsp:include page="fragments/header.jsp"/>
<body>
<div class="container" id="start-page-container">
    <div class="row">
        <div class="col"></div>
        <div class="col"><a class="link-like-button rred" href="${pageContext.request.contextPath}/work">Kick ASS</a>
        </div>
        <div class="col"><a class="link-like-button" href="${pageContext.request.contextPath}/pomos">Check out
            progress</a>
        </div>
        <div class="col"></div>
    </div>
</div>
</body>
</html>