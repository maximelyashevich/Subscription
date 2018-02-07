<%--@elvariable id="exceptionMessage" type="java.lang.String"--%>
<%--@elvariable id="exceptionCause" type="java.lang.Throwable"--%>
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
    <title>Error page</title>
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
<c:import url="/jsp/login/common/headerLogin.jsp" />
<main style="   background: url('/resource/image/background/3.jpg') no-repeat; min-height: 450px;">
    <div class="row" id="userProfile" style="margin-right: -15px; margin-left: 250px; margin-top: 125px;">
        <div class="col-md-7 ">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 >Error page. More information</h4>
                </div>
                <div class="panel-body">
                    <div class="box box-info">
                        <div class="box-body">

                            <div class="col-sm-5 col-xs-6 title ">Request from: </div><div class="col-sm-7">${pageContext.errorData.requestURI} is failed</div>
                            <div class="clearfix"></div>
                            <div class="bot-border"></div>

                            <div class="col-sm-5 col-xs-6 title " >Servlet name or type: </div><div class="col-sm-7">${pageContext.errorData.servletName}</div>
                            <div class="clearfix"></div>
                            <div class="bot-border"></div>

                            <div class="col-sm-5 col-xs-6 title " >Status code: </div><div class="col-sm-7">${pageContext.errorData.statusCode}</div>
                            <div class="clearfix"></div>
                            <div class="bot-border"></div>

                            <div class="col-sm-5 col-xs-6 title " >Exception: </div><div class="col-sm-7">${pageContext.errorData.throwable}</div>
                            <div class="clearfix"></div>
                            <div class="bot-border"></div>

                            <div class="col-sm-5 col-xs-6 title " >Wrong action: </div><div class="col-sm-7">${wrongAction}</div>
                            <div class="clearfix"></div>
                            <div class="bot-border"></div>

                            <div class="col-sm-5 col-xs-6 title " >Exception cause: </div><div class="col-sm-7">${exceptionCause}</div>
                            <div class="clearfix"></div>
                            <div class="bot-border"></div>

                            <div class="col-sm-5 col-xs-6 title " >Exception message: </div><div class="col-sm-7">${exceptionMessage}</div>
                            <div class="clearfix"></div>
                            <div class="bot-border"></div>

                            <div class="col-sm-5 col-xs-6 title " style="min-width: 650px"></div><div class="col-sm-7"></div>
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

