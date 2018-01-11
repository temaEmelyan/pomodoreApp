<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ attribute name="name" required="true" description="Name of corresponding property in bean object" %>
<%@ attribute name="label" required="true" description="Field label" %>
<%@ attribute name="inputType" required="false" description="Input type" %>

<spring:bind path="${name}">
    <div class="form-group ${status.error ? 'error' : '' }">
        <label class="control-label col-sm-2">${label}</label>

        <div class="col-sm-3">
            <c:choose>
                <c:when test="${inputType == 'password'}"><form:password path="${name}" class="form-control"/></c:when>
                <c:when test="${inputType == 'number'}"><form:input path="${name}" type="number" class="form-control"/></c:when>
                <c:otherwise><form:input path="${name}" class="form-control"/></c:otherwise>
            </c:choose>
        </div>
        <div class="col-sm-7">
            <span class="help-inline">${status.errorMessage}</span>
        </div>
    </div>
</spring:bind>