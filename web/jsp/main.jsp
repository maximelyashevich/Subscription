<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="en_US" scope="session" />
<!DOCTYPE html>
<head>
    <link rel='stylesheet prefetch' href='<c:url value="${pageContext.request.contextPath}/resource/css/cdnjs.css"/>'>
    <title>Welcome, user</title>
    <style>
        @import "/resource/css/header-main.css" screen;
        @import "/resource/css/login-main.css" screen;
        @import "/resource/css/footer.css" screen;
        @import "/resource/css/main-style.css" screen;
    </style>
    <link href='<c:url value="${pageContext.request.contextPath}/resource/font/1.css"/>' rel='stylesheet' type='text/css'>

    <script>
        window.console = window.console || function(t) {};
        if (document.location.search.match(/type=embed/gi)) {
            window.parent.postMessage("resize", "*");
        }
    </script>
</head>
<body>
<header class="header-main" style="position: fixed; width: 100%">
    <div class="header-limiter">
        <h1><a href="#"><span>Subscription</span></a></h1>
        <nav>
            <a href="#">Overview</a>
            <a href="#">Surveys</a>
            <a href="#">Reports</a>
            <a href="#">Roles</a>
        </nav>
        <div class="header-user-menu">
            ${user.firstName}
            <img src="<c:url value="${pageContext.request.contextPath}/resource/image/user/2.jpg"/>" alt="User Image"/>
            <ul>
                <li><a href="#">Settings</a></li>
                <li><a href="#">Payments</a></li>
                <li><a href="controller?command=logout">Logout</a></li>
            </ul>
        </div>
    </div>
</header>
<main>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span12">

            <div class="page-header">
                <h3>Subscription</h3>
                <p>
                    We subscribe to every word. Subscribe and you.</p>
            </div>

            <div class="carousel slide" id="myCarousel">
                <div class="carousel-inner">
                    <div class="item active">
                        <ul class="thumbnails">
                            <c:forEach items="${papers}" var="paper" varStatus="status" begin="0" end="3">
                            <li class="span3">
                            <div class="thumbnail">
                            <a href="#">
                                <div class="imgdiv">
                                <img src="${paper.imagePath}"alt="">
                                </div>
                            </a>
                            </div>
                            <div class="caption">
                            <h4>${paper.title}</h4>
                            <p>${paper.description}</p>
                            <a class="btn btn-mini" href="#">&raquo; Read More</a>
                            </div>
                            </li>
                            </c:forEach>
                        </ul>
                    </div>
                            <c:forEach begin="1" end="${papers.size()/4}" varStatus="loop">
                    <div class="item">
                                <ul class="thumbnails">
                                <c:forEach items="${papers}" var="paper" varStatus="status" begin="${loop.index*4}" end="${loop.index*4+3}" step="1">
                                    <li class="span3">
                                        <div class="thumbnail">
                                            <a href="#">
                                            <div class="imgdiv">
                                                <img src="${paper.imagePath}"alt="">
                                            </div>
                                            </a>
                                        </div>
                                        <div class="caption">
                                            <h4>${paper.title}</h4>
                                            <p>${paper.description}</p>
                                            <a class="btn btn-mini" href="#">&raquo; Read More</a>
                                        </div>
                                    </li>
                                </c:forEach>
                                </ul>
                    </div>
                            </c:forEach>
                </div>

                <div class="control-box">
                    <a data-slide="prev" href="#myCarousel" class="carousel-control left">‹</a>
                    <a data-slide="next" href="#myCarousel" class="carousel-control right">›</a>
                </div><!-- /.control-box -->

            </div><!-- /#myCarousel -->

        </div><!-- /.span12 -->
    </div><!-- /.row -->
</div><!-- /.container -->
</main>
<script src='<c:url value="${pageContext.request.contextPath}/resource/js/jquery.js"/>'></script>
<script src='<c:url value="${pageContext.request.contextPath}/resource/js/lib-carousel.js"/>'></script>
<script >// Carousel Auto-Cycle
$(document).ready(function() {
    $('.carousel').carousel({
        interval: 6000
    })
});
</script>
    <c:import url="../jsp/common/footer.jsp" />
</body>
</html>