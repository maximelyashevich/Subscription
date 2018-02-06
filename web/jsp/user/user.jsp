<%--@elvariable id="user" type="com.elyashevich.subscription.entity.User"--%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<%--@elvariable id="userLocale" type="String"--%>
<fmt:setLocale value="${userLocale}" />
<fmt:setBundle basename="resource.pagecontent" var="rb"/>
<html>
<head>
    <title><fmt:message key="label.welcomeUser" bundle="${rb}"/></title>
    <style>
        @import "/resource/css/main.css" screen;
        @import "/resource/css/user-style.css" screen;
        @import "/resource/css/popup.css" screen;
    </style>
    <link href='<c:url value="/resource/font/1.css"/>' rel='stylesheet' type='text/css'>
    <link rel='stylesheet prefetch' href='https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css'>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resource/css/bootstrap.css"/>">
</head>
<body style="   background: url('/resource/image/background/3.jpg') no-repeat;">
<c:import url="/jsp/user/common/headerUser.jsp" />
<div class="container" style="min-height: 100%">
    <div id="modalUserWindow" class="modal" style="background-color: rgba(0,0,0,0.35);">
        <div id="photoModal" class="modal-content" style="margin-top: 150px;">
            <span class="close">&times;</span>
                <form method="POST" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="profile"/>
                    <input type="hidden" name="amount" value="${user.amount}"/>
                    <input type="hidden" name="type" value="${user.type}"/>
                    <input type="hidden" name="id" value="${user.id}"/>
                    <div class="field-wrap">
                    <label><fmt:message key="label.firstName" bundle="${rb}"/></label>
                  <input type="text" name="first_name" value="${user.firstName}" title="<fmt:message key="label.firstName" bundle="${rb}"/>"/>
                   </div>
                 <div class="field-wrap">
                 <label><fmt:message key="label.lastName" bundle="${rb}"/></label>
                   <input type="text" name="last_name" value="${user.lastName}" title="<fmt:message key="label.lastName" bundle="${rb}"/>"/>
                  </div>
                <div><label><fmt:message key="label.birthday" bundle="${rb}"/>:</label>
                    <input type="date" name="dob" min="1905-01-01" max="2018-01-01" value="${user.birthday}" title="<fmt:message key="label.birthday" bundle="${rb}"/>"/></div>
                    <div class="field-wrap">
                        <label><fmt:message key="label.country" bundle="${rb}"/>:</label>
                        <input type="text" name="countryC" value="${user.address.country}" title="<fmt:message key="label.country" bundle="${rb}"/>"/>
                    </div>
                    <div class="field-wrap">
                        <label><fmt:message key="label.city" bundle="${rb}"/>:</label>
                        <input type="text" name="city" value="${user.address.city}" title="<fmt:message key="label.city" bundle="${rb}"/>"/>
                    </div>
                    <div class="field-wrap">
                        <label><fmt:message key="label.postIndex" bundle="${rb}"/>:</label>
                        <input type="text" name="postIndex" value="${user.address.postIndex}" title="<fmt:message key="label.postIndex" bundle="${rb}"/>"/>
                    </div>
                    <div class="field-wrap">
                        <label><fmt:message key="label.detailAddress" bundle="${rb}"/>:</label>
                        <input type="text" name="detailAddress" value="${user.address.detailAddress}" title="<fmt:message key="label.detailAddress" bundle="${rb}"/>"/>
                    </div>
        <button><fmt:message key="label.saveChanges" bundle="${rb}"/></button></form>
            <div id="userInformation"></div>
        </div>
    </div>
    <div class="row" id="userProfile" style="margin-right: -15px; margin-left: 250px; margin-top: 125px;">
        <div class="col-md-7 ">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 ><fmt:message key="label.profile" bundle="${rb}"/></h4>
                </div>
                <div class="panel-body">
                    <div class="box box-info">
                        <div class="box-body">
                            <div class="col-sm-6">
                                <div align="center">

                                    <form action="${pageContext.request.contextPath}/upload" enctype="multipart/form-data" method="POST">
                                        <label for="image">
                                            <input type="file" name="image" onchange="this.form.submit()" id="image" style="display:none;" accept="image/*" required/>
                                            <img src="${user.imagePath}" class="img-circle img-responsive" style="width: 152px; height:138px" id="contactImage"/>
                                        </label>
                                        <input type="hidden" name="secret" value="user">
                                        <input type="hidden" name="userID" value="${user.id}"/>
                                        <div style="color:#999;" ><fmt:message key="label.clickToChange" bundle="${rb}"/></div>
                                    </form>
                                </div>
                                <br>
                            </div>
                            <div class="col-sm-6">
                                <h4 style="color:#00b1b1;">${user.firstName} ${user.lastName}</h4>
                                <span><p>${user.type}</p></span>
                            </div>
                            <div class="clearfix"></div>
                            <hr style="margin:5px 0 5px 0;">


                            <div class="col-sm-5 col-xs-6 title " ><fmt:message key="label.firstName" bundle="${rb}"/></div><div class="col-sm-7 col-xs-6 ">${user.firstName}</div>
                            <div class="clearfix"></div>
                            <div class="bot-border"></div>

                            <div class="col-sm-5 col-xs-6 title " ><fmt:message key="label.lastName" bundle="${rb}"/></div><div class="col-sm-7">${user.lastName}</div>
                            <div class="clearfix"></div>
                            <div class="bot-border"></div>

                            <div class="col-sm-5 col-xs-6 title " ><fmt:message key="label.birthday" bundle="${rb}"/>:</div><div class="col-sm-7">${user.birthday}</div>

                            <div class="clearfix"></div>
                            <div class="bot-border"></div>

                            <div class="col-sm-5 col-xs-6 title " ><fmt:message key="label.country" bundle="${rb}"/>:</div><div class="col-sm-7">${user.address.country}</div>
                            <div class="clearfix"></div>
                            <div class="bot-border"></div>

                            <div class="col-sm-5 col-xs-6 title " ><fmt:message key="label.city" bundle="${rb}"/>:</div><div class="col-sm-7">${user.address.city}</div>
                            <div class="clearfix"></div>
                            <div class="bot-border"></div>

                            <div class="col-sm-5 col-xs-6 title " ><fmt:message key="label.postIndex" bundle="${rb}"/>:</div><div class="col-sm-7">${user.address.postIndex}</div>
                            <div class="clearfix"></div>
                            <div class="bot-border"></div>

                            <div class="col-sm-5 col-xs-6 title " ><fmt:message key="label.detailAddress" bundle="${rb}"/>:</div><div class="col-sm-7">${user.address.detailAddress}</div>
                            <div class="clearfix"></div>
                            <div class="bot-border"></div>

                            <div class="col-sm-5 col-xs-6 title " ><fmt:message key="label.email" bundle="${rb}"/>:</div><div class="col-sm-7">${user.email}</div>


                            <div class="clearfix"></div>
                            <div class="bot-border"></div>
                            <div class="col-sm-5 col-xs-6 title " ><fmt:message key="label.amount" bundle="${rb}"/>:</div><div class="col-sm-7">${user.amount}$</div>


                            <div class="clearfix"></div>
                            <div class="bot-border"></div>
                        </div>
                        <button onclick="showMoreInformation()"><fmt:message key="label.editProfile" bundle="${rb}"/></button>
                    </div>
                </div>
            </div>
        </div>
        <script src='<c:url value="${pageContext.request.contextPath}/resource/js/jquery.js"/>'></script>
        <script>
            var modalUser = document.getElementById('modalUserWindow'),
                span = document.getElementsByClassName("close")[0];
            function showMoreInformation() {
                modalUser.style.display = "block";
            }
            span.onclick = function() {
                modalUser.style.display = "none";
            };
            window.onclick = function(event) {
                if (event.target === modalUser) {
                    modalUser.style.display = "none";
                }
            };
            $(function() {
                $('#profile-image1').on('click', function() {
                    $('#profile-image-upload').click();
                });
            });
        </script>
    </div>
</div>
<c:import url="/jsp/common/footer.jsp"/>
</body>
</html>
