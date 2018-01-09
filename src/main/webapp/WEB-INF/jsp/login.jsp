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
                <form class="navbar-form navbar-right" action="spring_security_check" method="post">
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
</body>
</html>