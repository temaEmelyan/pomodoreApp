<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="fragments/header.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="myContainer container-fluid">
    <table class="table table-hover" style="background-color: #333; color: #eeeeee">
        <thead class="thead-inverse">
        <tr>
            <th>Date Time</th>
            <th>Duration</th>
        </tr>
        </thead>
        <jsp:useBean id="pomos" scope="request" type="java.util.List"/>
        <c:forEach items="${pomos}" var="pomo">
            <jsp:useBean id="pomo" type="com.temelyan.pomoapp.model.Pomo"/>
            <tr>
                <td>
                        ${fn:formatDateTime(pomo.finish)}
                </td>
                <td class="durationElement">${pomo.duration}</td>
            </tr>
        </c:forEach>

        <tr style="background-color: #414141">
            <td>
                Overall
            </td>
            <td class="durationElement">
                ${sumDuration}
            </td>
        </tr>
    </table>
</div>
</body>
<script>
    let toHHMMSS = (secs) => {
        let sec_num = parseInt(secs, 10);
        let hours = Math.floor(sec_num / 3600) % 24;
        let minutes = Math.floor(sec_num / 60) % 60;
        let seconds = sec_num % 60;
        return [hours, minutes, seconds]
            .map(v => v < 10 ? "0" + v : v)
            .filter((v, i) => v !== "00" || i > 0)
            .join(":")
    };

    $('.durationElement').each(function () {
        $(this).text(toHHMMSS($(this).text()));
    });

</script>
</html>
