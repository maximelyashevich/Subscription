<%--
  Created by IntelliJ IDEA.
  User: Максим
  Date: 03.02.2018
  Time: 19:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${userLocale}" />
<fmt:setBundle basename="resource.pagecontent" var="rb"/>
<html>
<head>
    <style>
        @import "/resource/css/header.css" screen;
    </style>
</head>
<body>
<header>
    <header class="header-main" style="height: 85px">
        <div class="header-limiter">
            <h1><a href="#"><span><fmt:message key="label.subscription" bundle="${ rb }"/></span></a></h1>
            <nav>
                <a href="#"><fmt:message key="label.home" bundle="${ rb }"/></a>
                <a href="#" class="selected"><fmt:message key="label.contact" bundle="${ rb }"/></a>
                <a href="#"><fmt:message key="label.about" bundle="${ rb }"/></a>
            </nav>
            <ul>
                <li class="tab active"><a href="${pageContext.request.contextPath}/jsp/login/login.jsp">
                    <fmt:message key="label.title" bundle="${ rb }"/>
                </a></li>
                <li class="tab"><a href="${pageContext.request.contextPath}/jsp/login/registration.jsp">
                    <fmt:message key="label.signup" bundle="${ rb }"/>
                </a></li>
                <li>
                    <form method="post" action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="command" value="language"/>
                        <input type="hidden" name="pagePath" value="${pageContext.request.requestURL}">
                        <select class="select-lang" name="locale_language" onchange="this.form.submit()" title="<fmt:message key="label.selectlang" bundle="${ rb }"/>">
                            <option value="en_US" selected=""> <fmt:message key="label.language" bundle="${ rb }"/></option>
                            <option value="en_US">English</option>
                            <option value="ru_RU">Русский</option>
                        </select>
                    </form>
                </li>
            </ul>
        </div>
    </header>
</header>
</body>
</html>
