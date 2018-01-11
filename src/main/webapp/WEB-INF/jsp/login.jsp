<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<jsp:include page="fragments/header.jsp"/>
<body>
<nav class="navbar navbar-inverse bg-inverse" role="navigation">
    <div class="container">
        <div class="row">
            <div class="col">
                <p class="navbar-brand">PomoApp</p>
            </div>
            <div class="col">
                <form class="navbar-form navbar-right" action="${pageContext.request.contextPath}/spring_security_check"
                      method="post">
                    <div class="row">
                        <div class="col">
                            <div class="form-group">
                                <input type="text" class="form-control" name="username" placeholder="Email">
                            </div>
                        </div>
                        <div class="col">
                            <div class="form-group">
                                <input type="password" class="form-control" name="password" placeholder="Password">
                            </div>
                        </div>
                        <div class="col">
                            <button type="submit" class="btn btn-default">Sign In</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</nav>

<div class="container">
    <a class="btn btn-lg btn-success" href="register">Register &raquo;</a>
    <button type="submit" class="btn btn-lg btn-primary" onclick="setCredentials('user1@yandex.ru', 'password1')">
        User1
    </button>
    <button type="submit" class="btn btn-lg btn-primary" onclick="setCredentials('user2@gmail.com', 'password1')">
        User2
    </button>
</div>
</body>

<script type="text/javascript">
    function setCredentials(username, password) {
        $('input[name="username"]').val(username);
        $('input[name="password"]').val(password);
    }
</script>
</html>