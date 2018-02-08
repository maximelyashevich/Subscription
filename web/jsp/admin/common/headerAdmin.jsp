<%--@elvariable id="user" type="com.elyashevich.subscription.entity.User"--%>
<%--
  Created by IntelliJ IDEA.
  User: Максим
  Date: 04.02.2018
  Time: 3:40
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
    <style>
        @import "/resource/css/header.css" screen;
        @import "/resource/css/main.css" screen;
    </style>
    <link rel='stylesheet prefetch' href='https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css'>
</head>
<body>
<header class="header-main" style="height: 85px;">
    <div class="header-limiter" style="float: left;">
        <h1><a href="/jsp/admin/contentAdmin.jsp"><span>Subscription</span></a></h1>
        <div class="header-user-menu" style="margin-top:5px; margin-right: -235%;">
            <div id="parent" style="padding: 5% 0;">
                <ctg:userRole user="${user}"/>
            </div>
            <ul>
                <li><a href="${pageContext.request.contextPath}/controller?command=logout"><fmt:message key="label.logout" bundle="${rb}"/></a></li>
            </ul>
        </div>
    </div>
    <ul style="float: left; margin-left: 88%; width: 140px; margin-top: -30px;">
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
