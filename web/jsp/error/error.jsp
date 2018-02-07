<%--@elvariable id="exceptionMessage" type="java.lang.String"--%>
<%--@elvariable id="exceptionCause" type="java.lang.String"--%>
<%--@elvariable id="wrongAction" type="java.lang.String"--%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Error page</title>
    <style>
        @import "/resource/css/error.css" screen;
    </style>
</head>
<body>
<c:import url="/jsp/login/common/headerLogin.jsp" />
<%--@elvariable id="user" type="com.elyashevich.subscription.entity.User"--%>
<div class="whiteback" style="font-size: 21px">
    <b>Request from ${pageContext.errorData.requestURI} is failed</b>
    <br/>
    <b>Servlet name or type:</b> ${pageContext.errorData.servletName}
    <br/>
    <b>Status code:</b> ${pageContext.errorData.statusCode}
    <br/>
    <b>Exception:</b> ${pageContext.errorData.throwable}
    <br/>
    <b>${wrongAction} 1</b>
    <br/>
    <b>${exceptionCause} 3</b>
    <br/>
    <b>${exceptionMessage} 4</b>
</div>
<c:import url="/jsp/common/footer.jsp" />
</body>
</html>