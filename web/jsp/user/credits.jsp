<%--
  Created by IntelliJ IDEA.
  User: Максим
  Date: 05.02.2018
  Time: 5:54
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
<body style="   background: url('/resource/image/background/3.jpg') no-repeat;">
<c:import url="/jsp/user/common/headerUser.jsp" />
<div class="container" style="min-height: 100%">
<div id="cCredit" class="componentUserBlock" style="display:block; padding-top: 50px; width: 1050px;">
    <div class="modal-content" style="height: 75%; width: 65%;">
        <div class="componentContent" id="creditsTable"
             style="width: 100%; height: 100%; margin-left: 0;  margin-top: 7%;  overflow: auto;">
            <c:choose>
                <%--@elvariable id="creditsForUser" type="java.util.ArrayList"--%>
                <c:when test="${empty creditsForUser}"><%--@elvariable id="user" type="com.elyashevich.subscription.entity.User"--%>
                    <br>
                    ${user.firstName}, your credit history is empty.
                    <br/>
                </c:when>
                <c:otherwise>
                    <table id="creditTable" class="table-fill">
                        <thead>
                        <tr>
                            <th class="text-center">Credit ID</th>
                            <th class="text-center">Debt</th>
                            <th class="text-center">Interest rate (%)</th>
                            <th class="text-center">Payoff (days)</th>
                            <th class="text-center">Is active</th>
                        </tr>
                        </thead>
                        <tbody class="table-hover">
                        <c:forEach items="${creditsForUser}" var="credit">
                            <tr>
                                <td class="text-center">${credit.id}</td>
                                <td class="text-center">${credit.debt}$</td>
                                <td class="text-center">${credit.interestRate}%</td>
                                <td class="text-center">${credit.payoff}</td>
                                <td class="text-center">${credit.availability}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
<c:import url="/jsp/common/footer.jsp"/>
</body>
</html>
