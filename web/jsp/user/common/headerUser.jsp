<%--
  Created by IntelliJ IDEA.
  User: Максим
  Date: 05.02.2018
  Time: 5:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<%--@elvariable id="userLocale" type="String"--%>
<fmt:setLocale value="${userLocale}" />
<fmt:setBundle basename="resource.pagecontent" var="rb"/>
<html>
<head>
    <style>
        @import "/resource/css/header.css" screen;
        @import "/resource/css/footer.css" screen;
        @import "/resource/css/main.css" screen;
        @import "/resource/css/user-style.css" screen;
        @import "/resource/css/popup.css" screen;
    </style>
    <link href='<c:url value="/resource/font/1.css"/>' rel='stylesheet' type='text/css'>
    <link rel='stylesheet prefetch' href='https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css'>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resource/css/bootstrap.css"/>">
</head>
<body>
<header class="header-main" style="float: left; height: 85px">
    <div class="header-limiter">
        <h1><a href="${pageContext.request.contextPath}/jsp/main/main.jsp"><span>Subscription</span></a></h1>
        <nav>
            <a href="${pageContext.request.contextPath}/jsp/user/user.jsp" ><fmt:message key="label.myProfile" bundle="${rb}"/></a>
            <a href="${pageContext.request.contextPath}/jsp/user/subscriptions.jsp"><fmt:message key="label.mySubscriptions" bundle="${rb}"/></a>
            <a href="${pageContext.request.contextPath}/jsp/main/main.jsp"><fmt:message key="label.mainPage" bundle="${rb}"/></a>
        </nav>
        <div class="header-user-menu" style="margin-right: 16%; z-index: 99999999;">
            <div id="parent" style="padding: 5% 0;">
                <%--@elvariable id="user" type="com.elyashevich.subscription.entity.User"--%>
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
                <li>
                    <form action="${pageContext.request.contextPath}/controller" method="post">
                        <a href="#" onclick="parentNode.submit();"><fmt:message key="label.logout" bundle="${rb}"/></a>
                        <input type="hidden" name="command" value="logout"/>
                    </form>
            </ul>
        </div>
    </div>
    <ul style="float: left; margin-left: 82%; width: 100px; margin-top: -30px;">
        <form method="post" style="height: 25px; font-size: 16px;" action="${pageContext.request.contextPath}/controller">
            <input type="hidden" name="command" value="language"/>
            <input type="hidden" name="pagePath" value="${pageContext.request.requestURL}">
            <select class="select-lang" style="background-color: transparent;  color: #ffffffbd;  width: 100px;" name="locale_language" onchange="this.form.submit()" title="<fmt:message key="label.selectlang" bundle="${ rb }"/>">
                <option value="en_US" style="background-color: transparent;     color: darkgray;" selected=""> <fmt:message key="label.language" bundle="${ rb }"/></option>
                <option value="en_US" style="background-color: transparent;     color: darkgray;">English</option>
                <option value="ru_RU" style="background-color: transparent;     color: darkgray;">Русский</option>
            </select>
        </form>
    </ul>
</header>
</body>
</html>

