<%--
  Created by IntelliJ IDEA.
  User: Максим
  Date: 04.02.2018
  Time: 3:34
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
    @import "/resource/css/sidebaradmin.css" screen;
</style>
</head>
<body>
<ul class="sidebar-menu">
    <li><span class="nav-section-title"></span></li>
    <li class="have-children"><a href="#"><span class="fa fa-briefcase"></span><fmt:message key="label.content" bundle="${rb}"/></a>
        <ul>
            <li><a href="#" onclick="location.replace('/jsp/admin/contentAdmin.jsp')"><fmt:message key="label.viewPapers" bundle="${rb}"/></a></li>
        </ul>
    </li>
    <li class="have-children"><a href="#"><span class="fa fa-user"></span><fmt:message key="label.user" bundle="${rb}"/></a>
        <ul>
            <li><a href="#" onclick="location.replace('/jsp/admin/userAdmin.jsp')"><fmt:message key="label.viewUser" bundle="${rb}"/></a></li>
        </ul>
    </li>
    <li class="have-children"><a href="#"><span class="fa fa-check-square-o"></span><fmt:message key="label.subscriptionS" bundle="${rb}"/></a>
        <ul>
            <li><a href="#" onclick="location.replace('/jsp/admin/subscriptionAdmin.jsp')"><fmt:message key="label.view" bundle="${rb}"/></a></li>
        </ul>
    </li>
    <li class="have-children"><a href="#"><span class="fa fa-options"></span><fmt:message key="label.option" bundle="${rb}"/></a>
        <ul>
            <li><a href="#" onclick="location.replace('/jsp/admin/addPaperAdmin.jsp')"><fmt:message key="label.addPaper" bundle="${rb}"/></a></li>
        </ul>
    </li>
</ul>
</body>
</html>
