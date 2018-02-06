<%--@elvariable id="nullPage" type="java.lang.String"--%>
<%--@elvariable id="wrongAction" type="java.lang.String"--%>
<%--@elvariable id="errorLoginPassMessage" type="java.lang.String"--%>
<%--@elvariable id="titleMessage" type="java.lang.String"--%>
<%--@elvariable id="userLocale" type="java.lang.String"--%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${userLocale}" />
<fmt:setBundle basename="resource.pagecontent" var="rb"/>
<!DOCTYPE html>
<html>
<head>
    <title>Log In</title>
    <style>
        @import "/resource/css/signin-signup.css" screen;
    </style>
    <link href='<c:url value="${pageContext.request.contextPath}/resource/font/1.css"/>' rel='stylesheet' type='text/css'>
   </head>
<body translate="no">
<c:import url="../common/headerLogin.jsp" />
<main style="   background: url('/resource/image/background/3.jpg') no-repeat;">
    <div class="form">
                <h1><fmt:message key="label.welcome" bundle="${ rb }"/></h1>
                <form id="loginForm" method="POST" action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="command" value="login"/>
                    <div class="error">
                        <h2>${titleMessage}</h2>
                        <h2>
                            ${errorLoginPassMessage}
                            ${wrongAction}
                            ${nullPage}
                        </h2>
                    </div>
                    <div class="field-wrap">
                        <label>
                            <fmt:message key="label.login" bundle="${ rb }"/>
                            <span class="req">*</span>
                        </label>
                        <input type="text" name="login" id="loginID2" required title=""/>
                    </div>
                    <div class="field-wrap">
                        <label>
                            <fmt:message key="label.password" bundle="${ rb }"/><span class="req">*</span>
                        </label>
                        <input type="password" name="password" id="password2" required title=""/>
                    </div>
                    <p>
                        <button class="button button-block"><fmt:message key="label.button" bundle="${ rb }"/></button>
                    </p>
                </form>
            </div>
    <script src='<c:url value="/resource/js/jquery.js"/>'></script>
</main>
<c:import url="../common/footer.jsp" />
</body>
</html>