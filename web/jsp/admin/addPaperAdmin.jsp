<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setLocale value="${userLocale}" />
<fmt:setBundle basename="resources.pagecontent" var="rb"/>
<html>
<head>
    <title>Admin page. Add new paper</title>
    <style>
        @import "/resource/css/signin-signup.css" screen;
        @import "/resource/css/style-main.css" screen;
        @import "/resource/css/signin-signup.css" screen;
        @import "/resource/css/admin-style.css" screen;
        @import "/resource/font/google-api.css" screen;
    </style>
    <link rel='stylesheet prefetch' href='https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css'>
    <link href='http://fonts.googleapis.com/css?family=Cookie' rel='stylesheet' type='text/css'>
    <script type="text/javascript" src="/resource/js/adminStyle.js"></script>
</head>
<body>
<c:import url="/jsp/admin/common/headerAdmin.jsp" />
<main style="padding: 50px 0; background: url('/resource/image/background/3.jpg') no-repeat;">
    <c:import url="/jsp/admin/common/sidebar.jsp" />
    <div class="content">
    <div class="form" style="margin-left: 20%; margin-top: 0">
        <form id="signupForm" method="POST" action="/controller">
            <input type="hidden" name="command" value="registration"/>
            <div class="top-row">
                <div class="field-wrap">
                    <label>Name</label>
                    <input type="text" name="first_name" id="first_name" required
                           title="<fmt:message key="label.inputFirstName" bundle="${ rb }"/>"/>
                </div>
                <div class="field-wrap">
                    <label>
                        <fmt:message key="label.last_name" bundle="${ rb }"/><span class="req">*</span>
                    </label>
                    <input type="text" name="last_name" id="last_name" required
                           title="<fmt:message key="label.inputSurname" bundle="${ rb }"/>"/>
                </div>
            </div>

            <div class="mark">
                <div>
                    <fmt:message key="label.birthday" bundle="${ rb }"/>
                </div>
                <input type="date" name="dob" id="dob" min="1905-01-01" max="2018-01-01" required title="">
            </div>
            <div class="top-row">
                <div class="field-wrap">
                    <label>
                        <fmt:message key="label.country" bundle="${ rb }"/>
                        <span class="req">*</span>
                    </label>
                    <input type="text" name="countryC" id="countryC" required title=""/>
                </div>
                <div class="field-wrap">
                    <label>
                        <fmt:message key="label.city" bundle="${ rb }"/>
                        <span class="req">*</span>
                    </label>
                    <input type="text" name="city" id="city" required title=""/>
                </div>
                <div class="field-wrap">
                    <label>
                        <fmt:message key="label.postIndex" bundle="${ rb }"/>
                        <span class="req">*</span>
                    </label>
                    <input type="text" name="postIndex" id="postIndex" required title=""/>
                </div>
            </div>
            <div class="field-wrap">
                <label>
                    <fmt:message key="label.detailAddress" bundle="${ rb }"/>
                    <span class="req">*</span>
                </label>
                <input type="text" name="detailAddress" id="detailAddress" required title=""/>
            </div>
            <div class="field-wrap">
                <label>
                    <fmt:message key="label.login" bundle="${ rb }"/><span class="req">*</span>
                </label>
                <input type="text" name="login" id="loginID" required title=""/>
            </div>
            <div class="field-wrap">
                <label>
                    <fmt:message key="label.email" bundle="${ rb }"/><span class="req">*</span>
                </label>
                <input type="email" name="email" id="email" required title=""/>
            </div>
            <div class="field-wrap">
                <label>
                    <fmt:message key="label.password" bundle="${ rb }"/><span class="req">*</span>
                </label>
                <input type="password" name="password" id="password" required title=""/>
            </div>
            <p>
                <button class="button button-block" style="height: 10%">Add paper</button>
            </p>
        </form>
    </div>
    </div>
    <script src='//production-assets.codepen.io/assets/common/stopExecutionOnTimeout-b2a7b3fe212eaa732349046d8416e00a9dec26eb7fd347590fbced3ab38af52e.js'></script><script src='//cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script><script src='https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.1/js/bootstrap.min.js'></script>
    <script>
        $(document).ready(function(){

            $(".sidebar-menu > li.have-children a").on("click", function(i){
                i.preventDefault();
                if( ! $(this).parent().hasClass("active") ){
                    $(".sidebar-menu li ul").slideUp();
                    $(this).next().slideToggle();
                    $(".sidebar-menu li").removeClass("active");
                    $(this).parent().addClass("active");
                }
                else{
                    $(this).next().slideToggle();
                    $(".sidebar-menu li").removeClass("active");
                }
            });
        });
    </script>
    <script src='<c:url value="/resource/js/jquery.js"/>'></script>
</main>
<c:import url="../common/footer.jsp" />
</body>
</html>