<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="en_US" scope="session" />
<fmt:setBundle basename="resources.pagecontent" var="rb" />
<!DOCTYPE html>
<head>
    <link rel='stylesheet prefetch' href='https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.1/css/bootstrap.min.css'>
    <style>
        @import "/resource/css/main.css" screen;
        @import "/resource/css/header.css" screen;
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



<!-- Delete This -->
<div class="footer">
    <a href="http://simonalex.com/">&hearts; Recommend</a> | <a href="https://twitter.github.com/bootstrap/">Get Subscription</a> | <a href="https://placehold.it/">Get Placeholder</a>
  <!--
  !!!!!!!!!!!!!!!!!!!!!!!!!!!!
  -->
</div>
</body></html>