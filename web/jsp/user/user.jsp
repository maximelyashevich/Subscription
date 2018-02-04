<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setLocale value="en_US" scope="session" />
<html>
<head>
    <title>Welcome, user</title>
    <style>
        @import "/resource/css/header-main.css" screen;
        @import "/resource/css/footer.css" screen;
        @import "/resource/css/style-main.css" screen;
        @import "/resource/css/user-style.css" screen;
        @import "/resource/css/popup.css" screen;
    </style>
    <link href='<c:url value="/resource/font/1.css"/>' rel='stylesheet' type='text/css'>
    <link rel='stylesheet prefetch' href='https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css'>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resource/css/bootstrap.css"/>">
    <script type="text/javascript" src="/resource/js/adminStyle.js"></script>
</head>
<body style="   background: url('/resource/image/background/3.jpg') no-repeat;">
<header class="header-main" style="margin-bottom: 25px">
    <div class="header-limiter">
        <h1><a href="#"><span>Subscription</span></a></h1>
        <nav>
            <a href="#" onclick="showDivBlock('userProfile', 'userProfile', 'sSubscriptions', 'cCredit', 'cCredit')">My profile</a>
            <a href="#" onclick="showDivBlock('sSubscriptions', 'sSubscriptions', 'userProfile', 'cCredit', 'cCredit')">My subscriptions</a>
            <a href="#" onclick="showDivBlock('cCredit', 'cCredit', 'userProfile', 'sSubscriptions',  'sSubscriptions')">My credit history</a>
            <a href="/jsp/mainpage.jsp">Main page</a>
            <a href="#">Roles</a>
        </nav>
        <div class="header-user-menu">
            <div id="parent">
                <ctg:userRole user="${user}"/>
            </div>
            <ul>
                <li><a href="#" onclick="showMoreInformation()">Edit profile</a></li>
                <li>
                    <form method="post" action="/controller">
                        <input type="hidden" name="command" value="money"/>
                        <input type="hidden" name="UserID" value="${user.id}">
                        <button type="submit">Top up the balance</button>
                    </form>
                </li>
                <li>
                    <form action="/controller" method="post">
                        <a href="#" onclick="parentNode.submit();">Logout</a>
                        <input type="hidden" name="command" value="logout"/>
                    </form>
            </ul>
        </div>
    </div>
</header>
<div class="container" style="min-height: 75%">
    <div id="modalUserWindow" class="modal" style="background-color: rgba(0,0,0,0.35);">
        <!-- Modal content -->
        <div id="photoModal" class="modal-content" style="margin-top: 150px;">
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
                  <input type="text" name="first_name" value="${user.firstName}"/>
                   </div>
                 <div class="field-wrap">
                 <label>Last name:</label>
                   <input type="text" name="last_name" value="${user.lastName}"/>
                  </div>
                <div><label>Birthday:</label>
                    <input type="date" name="dob" min="1905-01-01" max="2018-01-01" value="${user.birthday}"/></div>
                    <div class="field-wrap">
                        <label>Country:</label>
                        <input type="text" name="countryC" value="${user.address.country}"/>
                    </div>
                    <div class="field-wrap">
                        <label>City:</label>
                        <input type="text" name="city" value="${user.address.city}"/>
                    </div>
                    <div class="field-wrap">
                        <label>Post index:</label>
                        <input type="text" name="postIndex" value="${user.address.postIndex}"/>
                    </div>
                    <div class="field-wrap">
                        <label>Detail address:</label>
                        <input type="text" name="detailAddress" value="${user.address.detailAddress}"/>
                    </div>
        <button>Save changes</button></form>
            <div id="userInformation"></div>
        </div>
    </div>
    <div id="sSubscriptions" class="componentUserBlock" style="display: none">
        <!-- Modal content -->
        <div class="modal-content" style="height: 75%; width: 65%;">
            <div class="componentContent" id="subscriptionsTable"
                 style="width: 100%; height: 85%; margin-left: 0;  margin-top: 7%;  overflow: auto;">

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
                        </table>
                    <%--</c:otherwise>--%>
                <%--</c:choose>--%>
            </div>
        </div>
    </div>
    <div id="cCredit" class="componentUserBlock" style="display: none">
        <!-- Modal content -->
        <div class="modal-content" style="height: 75%; width: 65%;">
            <div class="componentContent" id="creditsTable"
                 style="width: 100%; height: 100%; margin-left: 0;  margin-top: 7%;  overflow: auto;">
                <c:choose>
                    <c:when test="${empty creditsForUser}">
                        <br>
                        ${user.firstName}, your credit history is empty.
                        <br/>
                    </c:when>
                    <c:otherwise>
                        <table id="creditTable" class="table-fill">
                            <thead>
                            <tr>
                                <th class="text-center">Credit ID</th>
                                <th class="text-center">Debt</th>
                                <th class="text-center">Interest rate (%)</th>
                                <th class="text-center">Payoff (days)</th>
                                <th class="text-center">Is active</th>
                            </tr>
                            </thead>
                            <tbody class="table-hover">
                            <c:forEach items="${creditsForUser}" var="credit">
                                <tr>
                                    <td class="text-center">${credit.id}</td>
                                    <td class="text-center">${credit.debt}$</td>
                                    <td class="text-center">${credit.interestRate}%</td>
                                    <td class="text-center">${credit.payoff}</td>
                                    <td class="text-center">${credit.availability}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
    <div class="row" id="userProfile" style="margin-right: -40px;
    margin-left: 250px;">
        <div class="col-md-7 ">
            <div class="panel panel-default">
                <div class="panel-heading">  <h4 >User Profile</h4></div>
                <div class="panel-body">
                    <div class="box box-info">
                        <div class="box-body">
                            <div class="col-sm-6">
                                <div align="center">

                                    <form action="/upload" enctype="multipart/form-data" method="POST">
                                        <label for="image">
                                            <input type="file" name="image" onchange="this.form.submit()" id="image" style="display:none;" accept="image/*" required/>
                                            <img src="${user.imagePath}" class="img-circle img-responsive" style="width: 152px; height:138px" id="contactImage"/>
                                        </label>
                                        <input type="hidden" name="userID" value="${user.id}"/>
                                        <div style="color:#999;" >click here to change profile image</div>
                                    </form>
                                </div>
                                <br>
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

                            <div class="col-sm-5 col-xs-6 title " >County:</div><div class="col-sm-7">${user.address.country}</div>
                            <div class="clearfix"></div>
                            <div class="bot-border"></div>

                            <div class="col-sm-5 col-xs-6 title " >City:</div><div class="col-sm-7">${user.address.city}</div>
                            <div class="clearfix"></div>
                            <div class="bot-border"></div>

                            <div class="col-sm-5 col-xs-6 title " >Post index:</div><div class="col-sm-7">${user.address.postIndex}</div>
                            <div class="clearfix"></div>
                            <div class="bot-border"></div>

                            <div class="col-sm-5 col-xs-6 title " >Detail address:</div><div class="col-sm-7">${user.address.detailAddress}</div>
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
            span = document.getElementsByClassName("close")[0];
            function showMoreInformation() {
                modalUser.style.display = "block";
                //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                //Сделать валидатор!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            }

            span.onclick = function() {
                modalUser.style.display = "none";
            };

            // When the user clicks anywhere outside of the modal, close it
            window.onclick = function(event) {
                if (event.target === modalUser) {
                    modalUser.style.display = "none";
                }
            }
        </script>
        <script src='<c:url value="${pageContext.request.contextPath}/resource/js/jquery.js"/>'></script>
        <script>
            $(function() {
                $('#profile-image1').on('click', function() {
                    $('#profile-image-upload').click();
                });
            });
        </script>
    </div>
</div>
<script type="text/javascript" src="/resource/js/photo.js"></script>
<c:import url="/jsp/common/footer.jsp" />
</body>
</html>
