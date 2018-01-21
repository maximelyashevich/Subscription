<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="en_US" scope="session" />
<!DOCTYPE html>
<head>
    <link rel='stylesheet prefetch' href='https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.1/css/bootstrap.min.css'>
    <style>
        @import "/resource/css/header.css" screen;
        @import "/resource/css/login.css" screen;
        @import "/resource/css/footer.css" screen;
        @import "/resource/css/main.css" screen;
    </style>
    <link href='http://fonts.googleapis.com/css?family=Cookie' rel='stylesheet' type='text/css'>
    <script>
        window.console = window.console || function(t) {};
        if (document.location.search.match(/type=embed/gi)) {
            window.parent.postMessage("resize", "*");
        }
    </script>
</head>
<body>
<header class="header-main">
    <div class="header-limiter">
        <h1><a href="#"><span>Subscription</span></a></h1>
        <nav>
            <a href="#">Overview</a>
            <a href="#">Surveys</a>
            <a href="#">Reports</a>
            <a href="#">Roles</a>
        </nav>
        <div class="header-user-menu">
            ${user}
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
<script src='//production-assets.codepen.io/assets/common/stopExecutionOnTimeout-b2a7b3fe212eaa732349046d8416e00a9dec26eb7fd347590fbced3ab38af52e.js'></script><script src='//cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script><script src='https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.1/js/bootstrap.min.js'></script>
<script >// Carousel Auto-Cycle
$(document).ready(function() {
    $('.carousel').carousel({
        interval: 6000
    })
});
</script>
<footer class="footer-centered">
    <p class="footer-name">Elyashevich Maxim, Subscription &copy; 2018</p>
</footer>

</body></html>