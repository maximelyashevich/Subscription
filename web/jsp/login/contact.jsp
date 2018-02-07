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
    <title><fmt:message key="label.contacts" bundle="${rb}"/></title>
    <style>
        @import "/resource/css/main.css" screen;
        @import "/resource/css/user-style.css" screen;
        @import "/resource/css/signin.css" screen;
    </style>
    <link href='<c:url value="${pageContext.request.contextPath}/resource/font/1.css"/>' rel='stylesheet' type='text/css'>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resource/css/bootstrap.css"/>">
</head>
<body translate="no">
<c:import url="common/headerLogin.jsp" />
<main style="   background: url('/resource/image/background/3.jpg') no-repeat;">
    <div class="row" id="userProfile" style="margin-right: -15px; margin-left: 250px; margin-top: 125px;">
        <div class="col-md-7 ">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 ><fmt:message key="label.contacts" bundle="${rb}"/></h4>
                </div>
                <div class="panel-body">
                    <div class="box box-info">
                        <div class="box-body">
                            <div class="col-sm-5 col-xs-6 title " ><fmt:message key="label.admin" bundle="${rb}"/></div><div class="col-sm-7">Maxim Elyashevich</div>
                            <div class="clearfix"></div>
                            <div class="bot-border"></div>

                            <div class="col-sm-5 col-xs-6 title " ><fmt:message key="label.telephone" bundle="${rb}"/></div><div class="col-sm-7">+375298687442</div>
                            <div class="clearfix"></div>
                            <div class="bot-border"></div>

                            <div class="col-sm-5 col-xs-6 title " ><fmt:message key="label.email" bundle="${rb}"/></div><div class="col-sm-7 col-xs-6 ">edition.subscription@gmail.com</div>
                            <div class="clearfix"></div>
                            <div class="bot-border"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
            </div>
    <script src='<c:url value="/resource/js/jquery.js"/>'></script>
</main>
<c:import url="../common/footer.jsp" />
</body>
</html>