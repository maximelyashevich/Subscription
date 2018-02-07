<%--@elvariable id="titleMessageR" type="java.lang.String"--%>
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
    <title><fmt:message key="label.signup" bundle="${rb}"/></title>
    <style>
        @import "/resource/css/signin.css" screen;
    </style>
    <link href='<c:url value="${pageContext.request.contextPath}/resource/font/1.css"/>' rel='stylesheet' type='text/css'>
</head>
<body translate="no">
<c:import url="common/headerLogin.jsp" />
<main style="   background: url('/resource/image/background/3.jpg') no-repeat;">
    <div class="form">
        <h1><fmt:message key="label.signup_for_free" bundle="${ rb }"/></h1>
        <form id="signupForm" method="POST" action="${pageContext.request.contextPath}/controller">
            <input type="hidden" name="command" value="registration"/>
            <div class="error">
                <h2>${titleMessageR}</h2>
                <h2>
                    ${errorLoginPassMessage}
                    ${wrongAction}
                    ${nullPage}
                </h2>
            </div>
            <div class="top-row">
                <div class="field-wrap">
                    <label>
                        <fmt:message key="label.first_name" bundle="${rb}"/><span class="req">*</span>
                    </label>
                    <input type="text" name="first_name" id="first_name" required
                           title="<fmt:message key="label.inputFirstName" bundle="${rb}"/>"/>
                </div>
                <div class="field-wrap">
                    <label>
                        <fmt:message key="label.last_name" bundle="${ rb }"/><span class="req">*</span>
                    </label>
                    <input type="text" name="last_name" id="last_name" required
                           title="<fmt:message key="label.inputSurname" bundle="${rb}"/>"/>
                </div>
            </div>

            <div class="mark">
                <div>
                    <fmt:message key="label.birthday" bundle="${ rb }"/>
                </div>
                <input type="date" name="dob" id="dob" min="1905-01-01" required title="">
            </div>
            <div class="top-row">
                <div class="field-wrap">
                    <label>
                        <fmt:message key="label.country" bundle="${rb}"/>
                        <span class="req">*</span>
                    </label>
                    <input type="text" name="countryC" id="countryC" required title=""/>
                </div>
                <div class="field-wrap">
                    <label>
                        <fmt:message key="label.city" bundle="${ rb }"/>
                        <span class="req">*</span>
                    </label>
                    <input type="text" name="city" id="city" required title=""/>
                </div>
                <div class="field-wrap">
                    <label>
                        <fmt:message key="label.postIndex" bundle="${rb}"/>
                        <span class="req">*</span>
                    </label>
                    <input type="text" name="postIndex" id="postIndex" pattern="^[\w_]{6,12}$" required title="<fmt:message key="label.postTitle" bundle="${rb}"/>"/>
                </div>
            </div>
            <div class="field-wrap">
                <label>
                    <fmt:message key="label.detailAddress" bundle="${rb}"/>
                    <span class="req">*</span>
                </label>
                <input type="text" name="detailAddress" id="detailAddress" required title=""/>
            </div>
            <div class="field-wrap">
                <label>
                    <fmt:message key="label.login" bundle="${rb}"/><span class="req">*</span>
                </label>
                <input type="text" name="login" id="loginID" pattern="^[\w_]{5,16}$" required title="<fmt:message key="label.loginTitle" bundle="${rb}"/>"/>
            </div>
            <div class="field-wrap">
                <label>
                    <fmt:message key="label.password" bundle="${ rb }"/><span class="req">*</span>
                </label>
                <input type="password" name="password" id="password" pattern="^[\wа-яА-Я][_\wа-яА-Я]{5,16}$" required title="<fmt:message key="label.passwordTitle" bundle="${rb}"/>"/>
            </div>
            <div class="field-wrap">
            <label>
                <fmt:message key="label.email" bundle="${ rb }"/><span class="req">*</span>
            </label>
            <input type="email" name="email" id="email" required title=""/>
        </div>
            <p>
                <button class="button button-block"><fmt:message key="label.get_started" bundle="${ rb }"/></button>
            </p>
        </form>
    </div>
    <script src='<c:url value="/resource/js/jquery.js"/>'></script>
</main>
<c:import url="../common/footer.jsp" />
</body>
</html>