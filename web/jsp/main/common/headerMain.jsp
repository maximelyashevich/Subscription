<%--@elvariable id="user" type="com.elyashevich.subscription.entity.User"--%>
<%--
  Created by IntelliJ IDEA.
  User: Максим
  Date: 05.02.2018
  Time: 4:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<%--@elvariable id="userLocale" type="java.lang.String"--%>
<fmt:setLocale value="${userLocale}" />
<fmt:setBundle basename="resource.pagecontent" var="rb"/>
<html>
<head>
    <link rel='stylesheet prefetch' href='<c:url value="/resource/css/stylecdnj.css"/>'>
    <style>
        @import "/resource/css/header.css" screen;
        @import "/resource/css/signin.css" screen;
        @import "/resource/css/main.css" screen;
    </style>
    <link href='<c:url value="/resource/font/1.css"/>' rel='stylesheet' type='text/css'>
    <link rel='stylesheet prefetch' href='https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css'>
    <link href='http://fonts.googleapis.com/css?family=Cookie' rel='stylesheet' type='text/css'>
</head>
<body>
<header class="header-main" style="height: 85px">
    <div class="header-limiter" style="float: left;">
        <h1><a href="#"><span>Subscription</span></a></h1>
        <nav style="margin-top: 15px;">
            <a href="${pageContext.request.contextPath}/jsp/user/user.jsp"><fmt:message key="label.myPage" bundle="${rb}"/></a>
        </nav>
        <div class="header-user-menu" style="margin-right: -115%;">
            <div id="parent" style="padding: 5% 0;">
                <ctg:userRole user="${user}"/>
                <img src="${user.imagePath}" style="border-radius: 50%; position: absolute; top: 6px; height: 26px; z-index: 9999; width: 28px; right: 10px;" alt="User Image">
            </div>
            <ul>
                <li>
                    <form method="post" action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="command" value="money"/>
                        <input type="hidden" name="userID" value="${user.id}">
                        <button type="submit"><fmt:message key="label.balance" bundle="${rb}"/></button>
                    </form>
                </li>
                <li><a href="${pageContext.request.contextPath}/controller?command=logout"><fmt:message key="label.logout" bundle="${rb}"/></a></li>
            </ul>
        </div>
    </div>
    <ul style="float: left; margin-left: 90%; width: 100px; margin-top: -30px;">
        <form method="post" style="height: 25px; font-size: 16px;" action="${pageContext.request.contextPath}/controller">
            <input type="hidden" name="command" value="language"/>
            <input type="hidden" name="pagePath" value="${pageContext.request.requestURL}">
            <select class="select-lang" name="locale_language" onchange="this.form.submit()" title="<fmt:message key="label.selectlang" bundle="${ rb }"/>">
                <option value="en_US" selected=""> <fmt:message key="label.language" bundle="${ rb }"/></option>
                <option value="en_US">English</option>
                <option value="ru_RU">Русский</option>
            </select>
        </form>
    </ul>
</header>
</body>
</html>
