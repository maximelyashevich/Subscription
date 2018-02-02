<html>
<head><meta charset="utf-8">
    <title>404 - Not found</title>
    <style>
        @import "/resource/css/error.css" screen;
    </style>
</head>
<body>
<c:if test="${!user.isAdmin()}">
    <c:import url="/jsp/user/common/header.jsp" />
</c:if>
<c:if test="${user.isAdmin()}">
    <c:import url="../admin/adminHeader.jsp" />
</c:if>
<div class="whiteback" style="font-size: 21px">
    <b>Request from ${pageContext.errorData.requestURI} is failed</b>
    <br/>
    <br/>
    <b>Servlet name or type:</b> ${pageContext.errorData.servletName}
    <br/>
    <br/>
    <b>Status code:</b> ${pageContext.errorData.statusCode}
    <br/>
    <br/>
    <b>Exception:</b> ${pageContext.errorData.throwable}
    <br/>
    <br/>
    <b>${wrongAction} 1</b>
    <br/>
    <br/>
    <b>${exceptionCause} 3</b>
    <br/>
    <br/>
    <b>${exceptionMessage} 4</b>
    <c:import url="/jsp/user/common/footer.jsp" />
</div>
</body>
</html>