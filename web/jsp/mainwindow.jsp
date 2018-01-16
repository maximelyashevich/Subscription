<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="en_US" scope="session" />
<fmt:setBundle basename="resources.pagecontent" var="rb" />
<!DOCTYPE html>
<head>
    <link rel='stylesheet prefetch' href='https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.1/css/bootstrap.min.css'>
    <style>
        @import "/css/mainwindow.css" screen;
        @import "/css/header-login-signup.css" screen;
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
<header class="header-login-signup">
    <div class="header-limiter">
        <h1><a href="#"><span>Subscription</span></a></h1>
        <nav>
            <a href="#">Home</a>
            <a href="#" class="selected">Blog</a>
            <a href="#">Pricing</a>
        </nav>
        <ul>
            <li class="tab"><a href="/jsp/login.jsp">Log In/Sign up</a></li>
        </ul>
    </div>
</header>
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
                            <li class="span3">
                                <div class="thumbnail">
                                    <a href="#"><img src="https://upload.wikimedia.org/wikipedia/en/3/3e/The-Times-5-June-2013.jpg" alt=""></a>
                                </div>
                                <div class="caption">
                                    <h4>The Times</h4>
                                    <p>The Times is a popular British daily (Monday to Saturday) national newspaper based in London, England.  </p>
                                    <a class="btn btn-mini" href="#">&raquo; Read More</a>
                                </div>
                            </li>
                            <li class="span3">
                                <div class="thumbnail">
                                    <a href="#"><img src="https://blogs-images.forbes.com/tomiogeron/files/2013/01/Forbes_cover012113.jpg" alt=""></a>
                                </div>
                                <div class="caption">
                                    <h4>Forbes</h4>
                                    <p>Forbes is an American business magazine. Published bi-weekly, it features original articles topics.</p>
                                    <a class="btn btn-mini" href="#">&raquo; Read More</a>
                                </div>
                            </li>
                            <li class="span3">
                                <div class="thumbnail">
                                    <a href="#"><img src="http://discovermagazine.com/~/media/Images/Issue%20Covers/2017/DSC-CV0517web.jpg" alt=""></a>
                                </div>
                                <div class="caption">
                                    <h4>Discover</h4>
                                    <p>Take an exciting adventure with magazine as it reports developments in the world around us.</p>
                                    <a class="btn btn-mini" href="#">&raquo; Read More</a>
                                </div>
                            </li>
                            <li class="span3">
                                <div class="thumbnail">
                                    <a href="#"><img src="https://subs.time.com/files/5814/2919/4010/Time_TIME_Magazine_Cover_220-_16.04.2015.jpg" alt=""></a>
                                </div>
                                <div class="caption">
                                    <h4>Time</h4>
                                    <p>Time is an American weekly news magazine published in New York City. It run by Henry Luce.</p>
                                    <a class="btn btn-mini" href="#">&raquo; Read More</a>
                                </div>
                            </li>
                        </ul>
                    </div><!-- /Slide1 -->
                    <div class="item">
                        <ul class="thumbnails">
                            <li class="span3">
                                <div class="thumbnail">
                                    <a href="#"><img src="https://placehold.it/360x240" alt=""></a>
                                </div>
                                <div class="caption">
                                    <h4>Praesent commodo</h4>
                                    <p>Nullam Condimentum Nibh Etiam Sem</p>
                                    <a class="btn btn-mini" href="#">&raquo; Read More</a>
                                </div>
                            </li>
                            <li class="span3">
                                <div class="thumbnail">
                                    <a href="#"><img src="https://placehold.it/360x240" alt=""></a>
                                </div>
                                <div class="caption">
                                    <h4>Praesent commodo</h4>
                                    <p>Nullam Condimentum Nibh Etiam Sem</p>
                                    <a class="btn btn-mini" href="#">&raquo; Read More</a>
                                </div>
                            </li>
                            <li class="span3">
                                <div class="thumbnail">
                                    <a href="#"><img src="https://placehold.it/360x240" alt=""></a>
                                </div>
                                <div class="caption">
                                    <h4>Praesent commodo</h4>
                                    <p>Nullam Condimentum Nibh Etiam Sem</p>
                                    <a class="btn btn-mini" href="#">&raquo; Read More</a>
                                </div>
                            </li>
                            <li class="span3">
                                <div class="thumbnail">
                                    <a href="#"><img src="https://placehold.it/360x240" alt=""></a>
                                </div>
                                <div class="caption">
                                    <h4>Praesent commodo</h4>
                                    <p>Nullam Condimentum Nibh Etiam Sem</p>
                                    <a class="btn btn-mini" href="#">&raquo; Read More</a>
                                </div>
                            </li>
                        </ul>
                    </div><!-- /Slide2 -->
                    <div class="item">
                        <ul class="thumbnails">
                            <li class="span3">
                                <div class="thumbnail">
                                    <a href="#"><img src="https://placehold.it/360x240" alt=""></a>
                                </div>
                                <div class="caption">
                                    <h4>Praesent commodo</h4>
                                    <p>Nullam Condimentum Nibh Etiam Sem</p>
                                    <a class="btn btn-mini" href="#">&raquo; Read More</a>
                                </div>
                            </li>
                            <li class="span3">
                                <div class="thumbnail">
                                    <a href="#"><img src="https://placehold.it/360x240" alt=""></a>
                                </div>
                                <div class="caption">
                                    <h4>Praesent commodo</h4>
                                    <p>Nullam Condimentum Nibh Etiam Sem</p>
                                    <a class="btn btn-mini" href="#">&raquo; Read More</a>
                                </div>
                            </li>
                            <li class="span3">
                                <div class="thumbnail">
                                    <a href="#"><img src="https://placehold.it/360x240" alt=""></a>
                                </div>
                                <div class="caption">
                                    <h4>Praesent commodo</h4>
                                    <p>Nullam Condimentum Nibh Etiam Sem</p>
                                    <a class="btn btn-mini" href="#">&raquo; Read More</a>
                                </div>
                            </li>
                            <li class="span3">
                                <div class="thumbnail">
                                    <a href="#"><img src="https://placehold.it/360x240" alt=""></a>
                                </div>
                                <div class="caption">
                                    <h4>Praesent commodo</h4>
                                    <p>Nullam Condimentum Nibh Etiam Sem</p>
                                    <a class="btn btn-mini" href="#">&raquo; Read More</a>
                                </div>
                            </li>
                        </ul>
                    </div><!-- /Slide3 -->
                </div>

                <div class="control-box">
                    <a data-slide="prev" href="#myCarousel" class="carousel-control left">‹</a>
                    <a data-slide="next" href="#myCarousel" class="carousel-control right">›</a>
                </div><!-- /.control-box -->

            </div><!-- /#myCarousel -->

        </div><!-- /.span12 -->
    </div><!-- /.row -->
</div><!-- /.container -->


<!-- Delete This -->
<div class="footer">
    <a href="http://simonalex.com/">&hearts; Recommend</a> | <a href="https://twitter.github.com/bootstrap/">Get Subscription</a> | <a href="https://placehold.it/">Get Placeholder</a>
  <!--
  !!!!!!!!!!!!!!!!!!!!!!!!!!!!
  -->
</div>
<script src='//production-assets.codepen.io/assets/common/stopExecutionOnTimeout-b2a7b3fe212eaa732349046d8416e00a9dec26eb7fd347590fbced3ab38af52e.js'></script><script src='//cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script><script src='https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.1/js/bootstrap.min.js'></script>
<script >// Carousel Auto-Cycle
$(document).ready(function() {
    $('.carousel').carousel({
        interval: 6000
    })
});
</script>
</body></html>