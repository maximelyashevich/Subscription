<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="en_US" scope="session" />
<html>
<head>
    <title>Welcome, user</title>
    <style>
        @import "/resource/css/header-main.css" screen;
        @import "/resource/css/footer.css" screen;
        @import "/resource/css/mainstyle.css" screen;
        @import "/resource/css/user-style.css" screen;
        @import "/resource/css/popup.css" screen;
    </style>
    <link href='<c:url value="${pageContext.request.contextPath}/resource/font/1.css"/>' rel='stylesheet' type='text/css'>
    <link rel='stylesheet prefetch' href='https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css'>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
</head>
<body style="   background: url('/resource/image/background/3.jpg') no-repeat;">
<header class="header-main" style="margin-bottom: 25px">
    <div class="header-limiter">
        <h1><a href="#"><span>Subscription</span></a></h1>
        <nav>
            <a href="#" onclick="showMoreSubscriptionInformation()">My subscriptions</a>
            <a href="#">My credit history</a>
            <a href="/jsp/mainpage.jsp">Main page</a>
            <a href="#">Roles</a>
        </nav>
        <div class="header-user-menu">
            <div id="parent">
                USER
            </div>
            <ul>
                <li><a href="#" onclick="showMoreInformation()">Edit profile</a></li>
                <li><a href="#">Payments</a></li>
                <li><a href="controller?command=logout">Logout</a></li>
            </ul>
        </div>
    </div>
</header>
<div class="container">
    <div id="modalUserWindow" class="modal" style="background-color: rgba(0,0,0,0.35);">
        <!-- Modal content -->
        <div class="modal-content" style="margin-top: 150px;">
            <span class="close">&times;</span>
            <form method="POST" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="profile"/>
                <input type="hidden" name="amount" value="${user.amount}"/>
                <input type="hidden" name="type" value="${user.type}"/>
                <input type="hidden" name="id" value="${user.id}"/>
                <div class="field-wrap"><label>Login:</label>
                  <input type="text" name="login" value="${user.userName}"/>
                    </div>
                <div class="field-wrap">
                   <label>Password:</label>
                   <input type="password" name="password" value="${user.password}"/>
                    </div>
                <div class="field-wrap">
                <label>Email:</label>
                <input type="text" name="email" value="${user.email}"/></div>
                <div class="field-wrap">
                    <label>First name:</label>
                  <input type="text" name="firstName" value="${user.firstName}"/>
                  </div>
                 <div class="field-wrap">
                 <label>Last name:</label>
                   <input type="text" name="lastName" value="${user.lastName}"/>
                  </div>
                <div><label>Birthday:</label>
                    <input type="date" name="dob" min="1905-01-01" max="2018-01-01" value="${user.birthday}"/></div>
        <button>Save changes</button></form>
            <div id="userInformation"></div>
        </div>
    </div>
    <div id="modalSubscriptions" class="modal" style="background-color: rgba(0,0,0,0.35);">
        <!-- Modal content -->
        <div class="modal-content" style="margin-top: 100px; height: 65%;">
            <span class="close">&times;</span>
            <div class="content" id="subscriptionsTable"
                 style="width: 100%; height: 100%; margin-left: 0%;  margin-top: 7%;  overflow: auto;">
                <table id="subTable" class="table-fill">
                    <thead>
                    <tr>
                        <th class="text-center">Subscription ID</th>
                        <th class="text-center">Paper title</th>
                        <th class="text-center">Subscription/start</th>
                        <th class="text-center">Subscription/finish</th>
                        <th class="text-center">Price</th>
                    </tr>
                    </thead>
                    <tbody class="table-hover">
                    <c:forEach items="${subscriptionsForUser}" var="subscription">
                        <tr>
                            <td class="text-center">${subscription.id}</td>
                            <td class="text-center">${subscription.paperEdition.title}</td>
                            <td class="text-center">${subscription.subscriptionRegistration}</td>
                            <td class="text-center">${subscription.subscriptionFinish}</td>
                            <td class="text-center">${subscription.price}$</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                    <tbody>
                </table>
            </div>
        </div>
    </div>
    <div class="row" style="margin-right: -40px;
    margin-left: 250px;">
        <div class="col-md-7 ">
            <div class="panel panel-default">
                <div class="panel-heading">  <h4 >User Profile</h4></div>
                <div class="panel-body">
                    <div class="box box-info">
                        <div class="box-body">
                            <div class="col-sm-6">
                                <div  align="center"> <img alt="User Pic" src="/resource/image/user/user.jpg" id="profile-image1" class="img-circle img-responsive">
                                    <%--<form enctype="multipart/form-data" method="post" action="/upload">--%>
                                        <%--&lt;%&ndash;<input id="profile-image-upload" class="hidden" type="file">&ndash;%&gt;--%>
                                        <%--Upload file: <input type="file" name="content">--%>
                                        <%--<input type="submit" value="Upload File">--%>
                                    <%--</form>--%>

                                    <div style="color:#999;" >click here to change profile image</div>
                                    <!--Upload Image Js And Css-->
                                </div>
                                <br>
                                <!-- /input-group -->
                            </div>
                            <div class="col-sm-6">
                                <h4 style="color:#00b1b1;">${user.firstName} ${user.lastName}</h4></span>
                                <span><p>${user.type}</p></span>
                            </div>
                            <div class="clearfix"></div>
                            <hr style="margin:5px 0 5px 0;">


                            <div class="col-sm-5 col-xs-6 title " >First Name:</div><div class="col-sm-7 col-xs-6 ">${user.firstName}</div>
                            <div class="clearfix"></div>
                            <div class="bot-border"></div>

                            <div class="col-sm-5 col-xs-6 title " >Last Name:</div><div class="col-sm-7">${user.lastName}</div>
                            <div class="clearfix"></div>
                            <div class="bot-border"></div>

                            <div class="col-sm-5 col-xs-6 title " >Date Of Birth:</div><div class="col-sm-7">${user.birthday}</div>

                            <div class="clearfix"></div>
                            <div class="bot-border"></div>

                            <div class="col-sm-5 col-xs-6 title " >Address:</div><div class="col-sm-7">${user.addressId}</div>

                            <div class="clearfix"></div>
                            <div class="bot-border"></div>

                            <div class="col-sm-5 col-xs-6 title " >Email:</div><div class="col-sm-7">${user.email}</div>


                            <div class="clearfix"></div>
                            <div class="bot-border"></div>
                            <div class="col-sm-5 col-xs-6 title " >Amount:</div><div class="col-sm-7">${user.amount}$</div>


                            <div class="clearfix"></div>
                            <div class="bot-border"></div>

                            <!-- /.box-body -->
                        </div>
                        <!-- /.box -->

                    </div>
                </div>
            </div>
        </div>
        <script>
            var modalUser = document.getElementById('modalUserWindow'),
            modalSubscriptions = document.getElementById('modalSubscriptions'),
            span = document.getElementsByClassName("close")[0],
            span2 = document.getElementsByClassName("close")[1];
            function showMoreInformation() {
                modalUser.style.display = "block";
                //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
               //Сделать валидатор!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            }

            function showMoreSubscriptionInformation(){
                modalSubscriptions.style.display="block";
            }

            span.onclick = function() {
                modalUser.style.display = "none";
            };

            span2.onclick = function () {
                modalSubscriptions.style.display = "none";
            };

            // When the user clicks anywhere outside of the modal, close it
            window.onclick = function(event) {
                if (event.target === modalUser) {
                    modalUser.style.display = "none";
                }
                if (event.target === modalSubscriptions) {
                    modalSubscriptions.style.display = "none";
                }
            }
        </script>
        <script src='<c:url value="${pageContext.request.contextPath}/resource/js/jquery.js"/>'></script>
        <script src='<c:url value="${pageContext.request.contextPath}/resource/js/lib-carousel.js"/>'></script>
        <script>
            $(function() {
                $('#profile-image1').on('click', function() {
                    $('#profile-image-upload').click();
                });
            });
        </script>
    </div>
</div>
<c:import url="/jsp/common/footer.jsp" />
</body>
</html>
