<%--
  Created by IntelliJ IDEA.
  User: Максим
  Date: 22.01.2018
  Time: 5:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<%--@elvariable id="userLocale" type="java.lang.String"--%>
<fmt:setLocale value="${userLocale}" />
<fmt:setBundle basename="resource.pagecontent" var="rb"/>
<html>
<head>
    <title><fmt:message key="label.subscriptionS" bundle="${rb}"/></title>
    <link rel='stylesheet prefetch' href='https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.1/css/bootstrap.min.css'>
    <style>
        @import "/resource/css/signin.css" screen;
        @import "/resource/css/main.css" screen;
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
    <div class="content" id="subscriptionTable">
        <table id="sTable" class="table-fill">
            <thead>
            <tr>
                <th class="text-center"><fmt:message key="label.subscriptionS" bundle="${rb}"/> ID</th>
                <th class="text-center"><fmt:message key="label.user" bundle="${rb}"/> ID</th>
                <th class="text-center"><fmt:message key="label.first_name" bundle="${rb}"/></th>
                <th class="text-center"><fmt:message key="label.last_name" bundle="${rb}"/></th>
                <th class="text-center"><fmt:message key="label.registrationDate" bundle="${rb}"/></th>
                <th class="text-center"><fmt:message key="label.price" bundle="${rb}"/></th>
            </tr>
            </thead>
            <tbody class="table-hover">
            <%--@elvariable id="subscriptions" type="java.util.List"--%>
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
    var $table = document.getElementById('sTable'),
        $tablePagination = document.getElementById("pagination"),
        $n = 10,
        $rowCount = $table.rows.length,
        $firstRow = $table.rows[0].firstElementChild.tagName,
        $hasHead = ($firstRow === "TH"),
        $tr = [],
        $i,$ii,$j = ($hasHead)?1:0,
        $th = ($hasHead?$table.rows[(0)].outerHTML:"");
    var $pageCount = Math.ceil($rowCount / $n);
    if ($pageCount > 1) {
        for ($i = $j,$ii = 0; $i < $rowCount; $i++, $ii++)
            $tr[$ii] = $table.rows[$i].outerHTML;
        $tablePagination.insertAdjacentHTML("afterend","<div id='buttons'></div>");
        sort(1);
    }
    function sort($p) {
        var $rows = $th,$s = (($n * $p)-$n);
        for ($i = $s; $i < ($s+$n) && $i < $tr.length; $i++)
            $rows += $tr[$i];
        $table.innerHTML = $rows;
        document.getElementById("buttons").innerHTML = pageButtons($pageCount,$p);
        document.getElementById("id"+$p).setAttribute("class","active");
    }

    function pageButtons($pCount,$cur) {
        var $prevDis = ($cur === 1)?"disabled":"",
            $nextDis = ($cur === $pCount)?"disabled":"",
            $buttons = "<input type='button' value='&lt;&lt; Prev' onclick='sort("+($cur - 1)+")' "+$prevDis+">";
        for ($i=1; $i<=$pCount;$i++)
            $buttons += "<input type='button' id='id"+$i+"' value='"+$i+"' onclick='sort("+$i+")'>";
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