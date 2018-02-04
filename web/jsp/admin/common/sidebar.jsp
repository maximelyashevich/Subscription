<%--
  Created by IntelliJ IDEA.
  User: Максим
  Date: 04.02.2018
  Time: 3:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<style>
    @import "/resource/css/sidebar.css" screen;
</style>
</head>
<body>
<ul class="sidebar-menu">
    <li><span class="nav-section-title"></span></li>
    <li class="have-children"><a href="#"><span class="fa fa-briefcase"></span>Content</a>
        <ul>
            <li><a href="#" onclick="location.replace('/jsp/admin/contentAdmin.jsp')">View papers</a></li>
            <li><a href="#" onclick="location.replace('/jsp/admin/addPaperAdmin.jsp')">Add papers</a></li>
            <li><a href="#">Edit papers</a></li>
            <li><a href="#">Delete papers</a></li>
            <li><a href="#">Genres</a></li>
        </ul>
    </li>
    <li class="have-children"><a href="#"><span class="fa fa-user"></span>User</a>
        <ul>
            <li><a href="#" onclick="location.replace('/jsp/admin/userAdmin.jsp')">View users</a></li>
            <li><a href="#">Edit users</a></li>
            <li><a href="#">Your profile</a></li>
        </ul>
    </li>
    <li class="have-children"><a href="#"><span class="fa fa-credit-card"></span>Credit</a>
        <ul>
            <li><a href="#" onclick="location.replace('/jsp/admin/creditAdmin.jsp')">View history</a></li>
            <li><a href="#">Edit</a></li>
        </ul>
    </li>
    <li class="have-children"><a href="#"><span class="fa fa-check-square-o"></span>Subscription</a>
        <ul>
            <li><a href="#" onclick="location.replace('/jsp/admin/subscriptionAdmin.jsp')">View</a></li>
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
</body>
</html>
