<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${userLocale}" />
<fmt:setBundle basename="resources.pagecontent" var="rb"/>
<!DOCTYPE html>
<html>
<head>
    <title>Log In or Sign Up</title>
    <style>
        @import "/resource/css/header.css" screen;
        @import "/resource/css/login.css" screen;
        @import "/resource/css/footer.css" screen;
    </style>
    <link href='http://fonts.googleapis.com/css?family=Cookie' rel='stylesheet' type='text/css'>
    <script type="text/javascript" src="<c:url value="${pageContext.request.contextPath}/resource/js/login-signup.js"/>"></script>
</head>
<body translate="no">
<header class="header-main">
    <div class="header-limiter">
        <h1><a href="#"><span>Subscription</span></a></h1>
        <nav>
            <a href="#"> <fmt:message key="label.home" bundle="${ rb }"/></a>
            <a href="#" class="selected">Blog</a>
            <a href="#">Pricing</a>
        </nav>
        <ul>
            <li class="tab active"><a href="#login" onclick="viewDiv('login', 'signup');">
                <fmt:message key="label.title" bundle="${ rb }"/>
            </a></li>
            <li class="tab"><a href="#signup" onclick="viewDiv('signup', 'login');">
                <fmt:message key="label.signup" bundle="${ rb }"/>
            </a></li>
            <li>
                <form method="post" action="/controller">
                    <input type="hidden" name="command" value="language"/>
                    <select class="select-lang" name="locale_language" onchange="this.form.submit()">
                        <option value="en_US" selected="">Language</option>
                        <option value="en_US">English</option>
                        <option value="ru_RU">Русский</option>
                    </select>
                </form>
            </li>
        </ul>
    </div>
</header>
<main>
<div class="form">
    <div class="tab-content">
        <div id="login">
            <h1><fmt:message key="label.welcome" bundle="${ rb }"/></h1>
            <form id="loginForm" method="POST" action="controller">
                <input type="hidden" name="command" value="login"/>
                <div class="error">
                <h2>${titleMessage}</h2>
                </div>
                <div class="field-wrap">
                    <label>
                        <fmt:message key="label.login" bundle="${ rb }"/>
                        <span class="req">*</span>
                    </label>
                    <input type="text" name="login" id="loginID2" required/>
                </div>
                <div class="field-wrap">
                    <label>
                        <fmt:message key="label.set_a_password" bundle="${ rb }"/><span class="req">*</span>
                    </label>
                    <input type="password" name="password" id="password2" required/>
                </div>

                <p class="forgot">
                    <a href="#">
                        <fmt:message key="label.forgot" bundle="${ rb }"/>
                    </a>
                </p>
                ${errorLoginPassMessage}
                ${wrongAction}
                ${nullPage}
                <p>
                    <button class="button button-block"><fmt:message key="label.button" bundle="${ rb }"/></button>
                </p>
                <p>
                    <button type="button" class="button-cancel" onclick="closeDiv('login');">
                        <fmt:message key="label.cancel" bundle="${ rb }"/></button>
                </p>
            </form>
        </div>
        <div id="signup">
            <h1><fmt:message key="label.signup_for_free" bundle="${ rb }"/></h1>
            <form id="signupForm" method="POST" action="controller">
                <input type="hidden" name="command" value="registration"/>
                <div class="top-row">
                    <div class="field-wrap">
                        <label>
                            <fmt:message key="label.first_name" bundle="${ rb }"/><span class="req">*</span>
                        </label>
                        <input type="text" name="first_name" id="first_name" required/>
                    </div>
                    <div class="field-wrap">
                        <label>
                            <fmt:message key="label.last_name" bundle="${ rb }"/><span class="req">*</span>
                        </label>
                        <input type="text" name="last_name" id="last_name" required/>
                    </div>
                </div>

                <div class="mark">
                    <div>
                    <fmt:message key="label.birthday" bundle="${ rb }"/>
                    </div>
                <input type="date" name="dob" id="dob" min="1905-01-01" max="2018-01-01" required>
                </div>
                <div class="field-wrap">
                    <label>
                        <fmt:message key="label.login" bundle="${ rb }"/><span class="req">*</span>
                    </label>
                    <input type="text" name="login" id="loginID" required/>
                </div>
                <div class="field-wrap">
                    <label>
                        <fmt:message key="label.email" bundle="${ rb }"/><span class="req">*</span>
                    </label>
                    <input type="email" name="email" id="email" required/>
                </div>
                <div class="field-wrap">
                    <label>
                        <fmt:message key="label.password" bundle="${ rb }"/><span class="req">*</span>
                    </label>
                    <input type="password" name="password" id="password" required/>
                </div>
                <p>
                    <button class="button button-block"><fmt:message key="label.get_started" bundle="${ rb }"/></button>
                </p>
                <p>
                    <button type="button" class="button-cancel" onclick="closeDiv('signup');">
                        <fmt:message key="label.cancel" bundle="${ rb }"/>
                    </button>
                </p>
            </form>
        </div>
    </div><!-- tab-content -->
</div>
<script src="//production-assets.codepen.io/assets/common/stopExecutionOnTimeout-b2a7b3fe212eaa732349046d8416e00a9dec26eb7fd347590fbced3ab38af52e.js"></script>

<script src='//cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

<script>

    $('.form').find('input, textarea').on('keyup blur focus', function (e) {
        var $this = $(this),
            label = $this.prev('label');
        if (e.type === 'keyup') {
            if ($this.val() === '') {
                label.removeClass('active highlight');
            } else {
                label.addClass('active highlight');
            }
        } else if (e.type === 'blur') {
            if ($this.val() === '') {
                label.removeClass('active highlight');
            } else {
                label.removeClass('highlight');
            }
        } else if (e.type === 'focus') {
            if ($this.val() === '') {
                label.removeClass('highlight');
            }
            else if ($this.val() !== '') {
                label.addClass('highlight');
            }
        }
    });

    $('.tab a').on('click', function (e) {
        e.preventDefault();
        $(this).parent().addClass('active');
        $(this).parent().siblings().removeClass('active');
        target = $(this).attr('href');
        $('.tab-content > div').not(target).hide();
        $(target).fadeIn(600);
    });
</script>
</main>
<footer class="footer-centered">
    <p class="footer-name">Elyashevich Maxim, Subscription &copy; 2018</p>
</footer>
</body>
</html>