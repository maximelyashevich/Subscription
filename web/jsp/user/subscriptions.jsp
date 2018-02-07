<%--
  Created by IntelliJ IDEA.
  User: Максим
  Date: 05.02.2018
  Time: 5:49
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
    <title><fmt:message key="label.mySubscriptions" bundle="${rb}"/></title>
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
<body style="   background: url('/resource/image/background/3.jpg') no-repeat;">
<c:import url="/jsp/user/common/headerUser.jsp" />
<div class="container" style="min-height: 100%">
<div id="sSubscriptions" class="componentUserBlock" style="display:block; padding-top: 50px; width: 1050px;">
    <div class="modal-content" style="height: 75%; width: 65%;">
        <div class="componentContent" id="subscriptionsTable"
             style="width: 100%; height: 85%; margin-left: 0;  margin-top: 7%;  overflow: auto;">
            <table id="subTable" class="table-fill">
                <thead>
                <tr>
                    <th class="text-center"><fmt:message key="label.subscriptionS" bundle="${rb}"/> ID</th>
                    <th class="text-center"><fmt:message key="label.paperTitle" bundle="${rb}"/></th>
                    <th class="text-center"><fmt:message key="label.sStart" bundle="${rb}"/></th>
                    <th class="text-center"><fmt:message key="label.sFinish" bundle="${rb}"/></th>
                    <th class="text-center"><fmt:message key="label.price" bundle="${rb}"/></th>
                </tr>
                </thead>
                <tbody class="table-hover">
                <%--@elvariable id="subscriptionsForUser" type="java.util.ArrayList"--%>
                <c:forEach items="${subscriptionsForUser}" var="subscription">
                    <tr>
                        <td class="text-center">${subscription.id}</td>
                        <td class="text-center">${subscription.paperEdition.title}</td>
                        <td class="text-center">${subscription.subscriptionRegistration}</td>
                        <td class="text-center">${subscription.subscriptionFinish}</td>
                        <td class="text-center">${subscription.price}$</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</div>
<c:import url="/jsp/common/footer.jsp"/>
</body>
</html>
