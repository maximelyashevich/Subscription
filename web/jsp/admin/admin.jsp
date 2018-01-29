<%--
  Created by IntelliJ IDEA.
  User: Максим
  Date: 22.01.2018
  Time: 5:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="en_US" scope="session" />
<html>
<head>
    <title>Admin page</title>
    <link rel='stylesheet prefetch' href='https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.1/css/bootstrap.min.css'>
    <style>
        @import "/resource/css/header-main.css" screen;
        @import "/resource/css/own-login-styling.css" screen;
        @import "/resource/css/footer.css" screen;
        @import "/resource/css/mainstyle.css" screen;
        @import "/resource/css/sidebar-admin.css" screen;
        @import "/resource/css/admin-main.css" screen;
        @import "/resource/font/google-api.css" screen;
    </style>
    <link rel='stylesheet prefetch' href='https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css'>
    <link href='http://fonts.googleapis.com/css?family=Cookie' rel='stylesheet' type='text/css'>
    <script type="text/javascript" src="/resource/js/admin.js"></script>
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
            <div id="parent">
                ADMINISTRATOR
            </div>
            <ul>
                <li><a href="#">Settings</a></li>
                <li><a href="#">Payments</a></li>
                <li><a href="controller?command=logout">Logout</a></li>
            </ul>
        </div>
    </div>
</header>
<main style="padding: 50px 0 722px; background: url('/resource/image/background/3.jpg') no-repeat;">
    <ul class="sidebar-menu">
        <li><span class="nav-section-title"></span></li>
        <li class="have-children"><a href="#"><span class="fa fa-briefcase"></span>Content</a>
            <ul>
                <li><a onclick="showDivBlock('paperTable', 'pagination', 'userTable', 'subscriptionTable')">View papers</a></li>
                <li><a href="#">Add papers</a></li>
                <li><a href="#">Edit papers</a></li>
                <li><a href="#">Delete papers</a></li>
                <li><a href="#">Genres</a></li>
            </ul>
        </li>
        <li class="have-children"><a href="#"><span class="fa fa-user"></span>User</a>
            <ul>
                <li><a href="#" onclick="showDivBlock('userTable', 'pagination', 'paperTable', 'subscriptionTable')">View users</a></li>
                <li><a href="#">Edit users</a></li>
                <li><a href="#">Your profile</a></li>
            </ul>
        </li>
        <li class="have-children"><a href="#"><span class="fa fa-credit-card"></span>Credit</a>
            <ul>
                <li><a href="#">View history</a></li>
                <li><a href="#">Edit</a></li>
            </ul>
        </li>
        <li class="have-children"><a href="#"><span class="fa fa-check-square-o"></span>Subscription</a>
            <ul>
                <li><a href="#" onclick="showDivBlock('subscriptionTable', 'pagination', 'paperTable', 'userTable')">View</a></li>
                <li><a href="#">Edit</a></li>
            </ul>
        </li>
        <li class="have-children"><a href="#"><span class="	fa fa-user-circle"></span>Admin</a>
            <ul>
                <li><a href="#">Options</a></li>
                <li><a href="#">Security</a></li>
            </ul>
        </li>
        <li><a href="#"><span class="fa fa-picture-o"></span>Gallery</a></li>
    </ul>
    <div class="content" id="userTable">
        <table id="uTable" class="table-fill">
            <thead>
            <tr>
                <th class="text-center"><em class="fa fa-cog"></em></th>
                <th class="text-left">User name</th>
                <th class="text-left">Role</th>
                <th class="text-left">Email</th>
                <%--!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!--%>
                <th class="text-left">Availability</th>
                <th class="text-left">Name</th>
                <th class="text-left">Surname</th>
                <th class="text-left">Address (id)</th>
                <th class="text-left">Amount</th>
            </tr>
            </thead>
            <tbody class="table-hover">
            <c:forEach items="${users}" var="user">
                <tr onclick="window.location.href='/jsp/main.jsp'; return false">
                    <td class="text-left"><a class="btn btn-default"><em class="fa fa-pencil"></em></a>
                        <a class="btn btn-danger"><em class="fa fa-trash"></em></a></td>
                    <td class="text-left">${user.userName}</td>
                    <td class="text-left">${user.type}</td>
                    <td class="text-left">${user.email}</td>
                    <td><input type="checkbox" value="${user.availability}" /></td>
                    <td class="text-left">${user.firstName}</td>
                    <td class="text-left">${user.lastName}</td>
                    <td class="text-left">${user.addressId}</td>
                    <td class="text-left">${user.amount}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="content" id="paperTable">
        <table id="pTable" class="table-fill">
            <thead>
            <tr>
                <th class="text-left">Image</th>
                <th class="text-left">Title</th>
                <th class="text-left">Type</th>
                <%--<th class="text-left">Description</th>--%>
                <%--!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!--%>
                <th class="text-left">Periodicity</th>
                <th class="text-left">Age restriction</th>
                <th class="text-left">Availability</th>
                <th class="text-left">Price</th>
            </tr>
            </thead>
            <tbody class="table-hover">
            <c:forEach items="${papers}" var="paper">
                <tr onclick="window.location.href='/jsp/main.jsp'; return false">
                    <td class="text-left">
                        <img src="${paper.imagePath}" width="100" height="75" alt=""></td>
                    <td class="text-left">${paper.title}</td>
                    <td class="text-left">${paper.type}</td>
                    <%--<td class="text-left">${paper.description}</td>--%>
                    <td class="text-left">${paper.publishingPeriodicity}</td>
                    <td class="text-left">${paper.ageRestriction}</td>
                    <td class="text-left">${paper.availability}</td>
                    <td class="text-left">${paper.price}</td>
                </tr>
            </c:forEach>
            </tbody>
            <tbody>
        </table>
    </div>
    <div class="content" id="subscriptionTable">
        <table id="sTable" class="table-fill">
            <thead>
            <tr>
                <th class="text-left">Subscription ID</th>
                <th class="text-left">User ID</th>
                <th class="text-left">User name</th>
                <th class="text-left">User surname</th>
                <th class="text-left">Registration date</th>
                <th class="text-left">Price</th>
            </tr>
            </thead>
            <tbody class="table-hover">
            <c:forEach items="${subscriptions}" var="subscription">
                <tr>
                    <td class="text-left">${subscription.id}</td>
                    <td class="text-left">${subscription.user.id}</td>
                    <td class="text-left">${subscription.user.firstName}</td>
                    <td class="text-left">${subscription.user.lastName}</td>
                    <td class="text-left">${subscription.subscriptionRegistration}</td>
                    <td class="text-left">${subscription.price}$</td>
                </tr>
            </c:forEach>
            </tbody>
            <tbody>
        </table>
    </div>
    <div id="pagination"></div>
    <script>
    var $table = document.getElementById('uTable'),
        $tablePagination = document.getElementById("pagination"),
        $n = 4,
        $rowCount = $table.rows.length,
        $firstRow = $table.rows[0].firstElementChild.tagName,
        $hasHead = ($firstRow === "TH"),
        $tr = [],
        $i,$ii,$j = ($hasHead)?1:0,
// holds the first row if it has a (<TH>) & nothing if (<TD>)
        $th = ($hasHead?$table.rows[(0)].outerHTML:"");
    var $pageCount = Math.ceil($rowCount / $n);
    if ($pageCount > 1) {
        for ($i = $j,$ii = 0; $i < $rowCount; $i++, $ii++)
            $tr[$ii] = $table.rows[$i].outerHTML;
        $tablePagination.insertAdjacentHTML("afterend","<div id='buttons'></div>");
        sort(1);
    }
    function sort($p) {
        /* create ($rows) a variable to hold the group of rows
        ** to be displayed on the selected page,
        ** ($s) the start point .. the first row in each page, Do The Math
        */
        var $rows = $th,$s = (($n * $p)-$n);
        for ($i = $s; $i < ($s+$n) && $i < $tr.length; $i++)
            $rows += $tr[$i];
        $table.innerHTML = $rows;
        // create the pagination buttons
        document.getElementById("buttons").innerHTML = pageButtons($pageCount,$p);
        // CSS Stuff
        document.getElementById("id"+$p).setAttribute("class","active");
    }
    // ($p) is the selected page number. it will be generated when a user clicks a button

    function pageButtons($pCount,$cur) {
        /* this variables will disable the "Prev" button on 1st page
           and "next" button on the last one */
        var $prevDis = ($cur == 1)?"disabled":"",
            $nextDis = ($cur == $pCount)?"disabled":"",
            /* this ($buttons) will hold every single button needed
            ** it will creates each button and sets the onclick attribute
            ** to the "sort" function with a special ($p) number..
            */
            $buttons = "<input type='button' value='&lt;&lt; Prev' onclick='sort("+($cur - 1)+")' "+$prevDis+">";
        for ($i=1; $i<=$pCount;$i++)
            $buttons += "<input type='button' id='id"+$i+"'value='"+$i+"' onclick='sort("+$i+")'>";
        $buttons += "<input type='button' value='Next &gt;&gt;' onclick='sort("+($cur + 1)+")' "+$nextDis+">";
        return $buttons;
    }
    </script>
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
</main>
<c:import url="../common/footer.jsp" />
</body>
</html>