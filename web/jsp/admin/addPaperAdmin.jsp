<%--@elvariable id="titleAdd" type="java.lang.String"--%>
<%--@elvariable id="nullPage" type="java.lang.String"--%>
<%--@elvariable id="wrongAction" type="java.lang.String"--%>
<%--@elvariable id="errorLoginPassMessage" type="java.lang.String"--%>
<%--@elvariable id="users" type="java.util.ArrayList"--%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<%--@elvariable id="userLocale" type="java.lang.String"--%>
<fmt:setLocale value="${userLocale}" />
<fmt:setBundle basename="resource.pagecontent" var="rb"/>
<html>
<head>
    <title><fmt:message key="label.addPaper" bundle="${rb}"/></title>
    <style>
        @import "/resource/css/signin.css" screen;
        @import "/resource/css/main.css" screen;
        @import "/resource/css/signin.css" screen;
        @import "/resource/css/admin.css" screen;
        @import "/resource/font/google-api.css" screen;
    </style>
    <link rel='stylesheet prefetch' href='https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css'>
    <link href='http://fonts.googleapis.com/css?family=Cookie' rel='stylesheet' type='text/css'>
</head>
<body>
<c:import url="/jsp/admin/common/headerAdmin.jsp" />
<main style="padding: 50px 0; background: url('/resource/image/background/3.jpg') no-repeat;">
    <c:import url="/jsp/admin/common/sidebar.jsp" />
    <div class="content">
    <div class="form" style="margin-left: 20%; margin-top: 0">
        <form method="POST" action="${pageContext.request.contextPath}/controller">
            <input type="hidden" name="command" value="add_new_paper">
            <input type="hidden" name="users" value="${users}">
            <h2>${titleAdd}</h2>
            <h2>
                ${errorLoginPassMessage}
                ${wrongAction}
                ${nullPage}
            </h2>
            <div class="field-wrap" style="font-size: 25px; text-align: center;">
                <fmt:message key="label.addPaper" bundle="${rb}"/></div>
                    <div class="field-wrap">
                        <p style="width: 450px; margin-bottom: -5px;"><fmt:message key="label.selectTypeOfFuturePaper" bundle="${rb}"/>:</p>
                        <label for="radioChoice1" style="float: left; width: 30%;"><fmt:message key="label.magazine" bundle="${rb}"/></label>
                        <input type="radio" id="radioChoice1"
                               name="myRadio" value="1" checked="checked" style="height: 15px; float: left; width: 20px; margin: 10px 22% 0;">
                        <label for="radioChoice2" style="float: left;  margin-left: 150px;"><fmt:message key="label.newspaper" bundle="${rb}"/></label>
                        <input type="radio" id="radioChoice2"
                               name="myRadio" value="2" style="height: 15px; margin: 10px 30px -20px; width: 20px; float: left;">
                        <label for="radioChoice3" style="float: left; margin-left: 325px;"><fmt:message key="label.book" bundle="${rb}"/></label>
                        <input type="radio" id="radioChoice3"
                               name="myRadio" value="3" style="height: 15px; float: left;  width: 20px; margin: -14px 76% -20px;">
                    </div>
            <div class="field-wrap" style="margin-top: 55px;">
                <label>
                    <fmt:message key="label.name" bundle="${rb}"/>
                    <span class="req">*</span>
                </label>
                <input type="text" name="paperTitle" required title="<fmt:message key="label.paperTitle" bundle="${ rb }"/>"/>
            </div>
            <div class="field-wrap">
                <fmt:message key="label.descriptionD" bundle="${rb}"/>
                <textarea name="description" rows="4" cols="50" style="margin-top: 0; margin-bottom: 0;  height: 100px; border: 1px solid #a0b3b0;" title="<fmt:message key="label.description" bundle="${rb}"/>">
                <fmt:message key="label.periodicalPaper" bundle="${rb}"/>
                </textarea>
            </div>
            <div class="field-wrap">
                <fmt:message key="label.publishingPeriodicity" bundle="${rb}"/>:
                <select class="select-lang" name="period" style="float: left; width: 45px;  margin: -25px 63% 0; height: 30px;" title="<fmt:message key="label.selectlang" bundle="${ rb }"/>">
                    <c:forEach begin="1" end="10" varStatus="loop">
                        <option value="${loop.index}">${loop.index}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="field-wrap">
                <label>
                    <fmt:message key="label.priceExample" bundle="${rb}"/>
                    <span class="req">*</span>
                </label>
                <input type="text" name="price" required pattern="^-?[0-9]+(?:\.[0-9]{1,5})?" title=" <fmt:message key="label.moneyIn" bundle="${rb}"/>"/>
            </div>
            <div class="field-wrap">
                <label>
                    <fmt:message key="label.ageRExample" bundle="${rb}"/>
                    <span class="req">*</span>
                </label>
                <input type="text" name="restriction" required pattern="[0-9]{1,2}" title="<fmt:message key="label.restrictionIn" bundle="${rb}"/>"/>
            </div>
            <div class="field-wrap">
                <p style="width: 450px; margin-bottom: 15px;"><fmt:message key="label.selectGenresFuture" bundle="${rb}"/>:</p>
                <fieldset class="group" style="max-height: 100px; overflow-y: scroll;">
                <ul class="checkbox">
                    <%--@elvariable id="genres" type="java.util.ArrayList"--%>
                    <c:forEach items="${genres}" var="genre">
                        <li style="width: 100%;">
                            <label for="${ctg:notnull(genre).name}" style="position: unset; transform: none; color: rgb(151, 153, 155);  pointer-events: auto;  font-size: 18px;   float: left;">
                                <input type="checkbox" name="checkBox" class="checkbox" id="${ctg:notnull(genre).name}" value="${ctg:notnull(genre).name}"
                                       style="height: auto; width: 20px;  float: left;  margin-left: 0;   margin-top: 5px;"/>
                                    ${ctg:notnull(genre).name}
                            </label>
                        </li>
                    </c:forEach>
                </ul>
            </fieldset>
            </div>
            <p>
                <button class="button button-block" style="height: 10%"><fmt:message key="label.add" bundle="${rb}"/></button>
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