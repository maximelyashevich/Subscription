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
        @import "/resource/css/header-main.css" screen;
        @import "/resource/css/own-login-styling.css" screen;
        @import "/resource/css/footer.css" screen;
    </style>
    <link href='<c:url value="${pageContext.request.contextPath}/resource/font/1.css"/>' rel='stylesheet' type='text/css'>
    <script type="text/javascript" src="<c:url value="${pageContext.request.contextPath}/resource/js/main.js"/>"></script>
</head>
<body translate="no">
<header class="header-main" style="position: fixed; width: 100%">
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
<main style="   background: url('/resource/image/background/3.jpg') no-repeat;">
    <%--<form action="upload" enctype="multipart/form-data" method="post">--%>
        <%--&lt;%&ndash;<input id="profile-image-upload" class="hidden" type="file">&ndash;%&gt;--%>
        <%--Upload file: <input type="file" name="content">--%>
        <%--<input type="submit" value="Upload File">--%>
    <%--</form>--%>
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
    <%--<script src="<c:url value="${pageContext.request.contextPath}/resource/js/stopExecuteOnTimeout.js"/>"></script>--%>

    <script src='<c:url value="${pageContext.request.contextPath}/resource/js/jquery.js"/>'></script>

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
<c:import url="../jsp/common/footer.jsp" />
</body>
</html>