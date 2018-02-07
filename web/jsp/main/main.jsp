<%--@elvariable id="subscription" type="com.elyashevich.subscription.entity.Subscription"--%>
<%--@elvariable id="user" type="com.elyashevich.subscription.entity.User"--%>
<%--@elvariable id="finalPrice" type="java.math.BigDecimal"--%>
<%--@elvariable id="titleMessageS" type="java.lang.String"--%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<%--@elvariable id="userLocale" type="java.lang.String"--%>
<fmt:setLocale value="${userLocale}" />
<fmt:setBundle basename="resource.pagecontent" var="rb"/>
<html>
<head>
    <link rel='stylesheet prefetch' href='<c:url value="/resource/css/stylecdnj.css"/>'>
    <title><fmt:message key="label.mainPage" bundle="${rb}"/></title>
    <style>
        @import "/resource/css/signin.css" screen;
        @import "/resource/css/footer.css" screen;
        @import "/resource/css/main.css" screen;
        @import "/resource/css/popup.css" screen;
    </style>
    <link href='<c:url value="/resource/font/1.css"/>' rel='stylesheet' type='text/css'>
    <link rel='stylesheet prefetch' href='https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css'>
    <link href='http://fonts.googleapis.com/css?family=Cookie' rel='stylesheet' type='text/css'>
    <script>
        window.console = window.console || function(t) {};
        if (document.location.search.match(/type=embed/gi)) {
            window.parent.postMessage("resize", "*");
        }
    </script>
</head>
<body>
<c:import url="/jsp/main/common/headerMain.jsp" />
<main style="background-color: #D5DDE5; padding: 50px 55px; margin-top: -18px; margin-bottom: 25px; float: left;">
    <div id="myModal" class="modal">
        <div class="modal-content">
            <span class="close">&times;</span>
            <span><fmt:message key="label.orderBasket" bundle="${rb}"/></span>
            <span>${titleMessageS}</span>
            <div id="empty_basket">
            </div>
            <div id="cart_content">
                <table id="basketTable" class="shopping_list" style="width:100%;">
                    <tr>
                        <th><fmt:message key="label.name" bundle="${rb}"/></th>
                        <th><fmt:message key="label.price" bundle="${rb}"/></th>
                        <th><fmt:message key="label.delete" bundle="${rb}"/></th>
                    </tr>
                    <%--@elvariable id="basketSet" type="java.util.HashSet"--%>
                        <c:forEach items="${basketSet}" var="basketElement">
                        <tr>
                        <td>${basketElement.title}</td>
                        <td class="price">${basketElement.price}</td>
                        <td>
                            <form id="basketForm" name="basketForm" method="post" action="${pageContext.request.contextPath}/controller">
                                <input type="hidden" name="command" value="delete_paper_item"/>
                                <input type="hidden" name="paperEditionId" value="${basketElement.id}"/>
                            <a href="#" onClick="document.forms['basketForm'].submit();" style="color: #c14340f5; font-weight: 800">
                                X
                                </a>
                            </form>
                        </td>
                    </tr>
                        </c:forEach>
                    <tr>
                    <td><fmt:message key="label.finalPrice" bundle="${rb}"/></td>
                    <td id="totalPrice" colspan="2">${finalPrice}$</td>
                    </tr>
                </table>
                <form id="subscriptionForm" name="subscriptionForm" method="post" action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="command" value="subscription"/>
                    <input type="hidden" name="userID" value="${user.id}"/>
                    <button id="paySubscription"><fmt:message key="label.payOrder" bundle="${rb}"/></button>
                </form>
            </div>
        </div>
    </div>
<div id="successOrder" class="modal">
    <div class="modal-content">
        <span class="close">&times;</span>
        <p>${subscription.user.firstName}, <fmt:message key="label.yourOrder" bundle="${rb}"/> #${subscription.id}!</p>
        <p><fmt:message key="label.dr" bundle="${rb}"/> ${subscription.subscriptionRegistration}</p>
        <p><fmt:message key="label.rhc" bundle="${rb}"/> </p>
        <p><b><fmt:message key="label.country" bundle="${rb}"/> </b><i> ${subscription.user.address.country};</i></p>
        <p><b><fmt:message key="label.city" bundle="${rb}"/> </b><i> ${subscription.user.address.city}</i></p>
        <p><b><fmt:message key="label.detailAddress" bundle="${rb}"/> </b><i> ${subscription.user.address.detailAddress}</i></p>
        <p><b><fmt:message key="label.postIndex" bundle="${rb}"/> </b><i> ${subscription.user.address.postIndex}</i></p>
        <p><fmt:message key="label.checkEmail" bundle="${rb}"/> </p>
        <p><fmt:message key="label.totalPrice" bundle="${rb}"/> ${subscription.price}$</p>
        <span></span>
    </div>
</div>
    <div id="modalPaperWindow" class="modal">
        <div class="modal-content-order" style="width: 50%; height: 80%;">
            <span class="close">&times;</span>
            <div id="paperInformation"></div>
        </div>
    </div>
    <%--@elvariable id="flagOrder" type="java.lang.String"--%>
    <c:if test="${flagOrder=='READY'}">
        <script>
            document.getElementById('successOrder').style.display="block";
        </script>
    </c:if>
    <div id="right_container">
        <input type="button" id="basketButton" style="
background-color: #f1f1f1a6;
    border: 1px solid #ccc;
    color: #62666a;
    width: 100%;
    height: 45px;
    font: 16px Arial, Helvetica, sans-serif;" value="<fmt:message key="label.basket" bundle="${ rb }"/> (${quantity})">
        <fieldset class="group">
            <legend><fmt:message key="label.genres" bundle="${rb}"/></legend>
            <ul class="checkbox">
                <%--@elvariable id="genres" type="java.util.ArrayList"--%>
                <c:forEach items="${genres}" var="genre">
                    <li>
                        <label for="${ctg:notnull(genre).name}" style=" position: unset;
                        transform: none;
                        color: rgba(25, 25, 25, 0.75);
                        pointer-events: auto;
     font-size: 14px;">
                            <input type="checkbox" class="checkbox" id="${ctg:notnull(genre).name}" value="${ctg:notnull(genre).name}" style="height: auto;"/>
                                ${ctg:notnull(genre).name}
                        </label>
                    </li>
                </c:forEach>
            </ul>
        </fieldset>
    </div>
    <div class="content" id="userTable" style="width: 80%; margin-left: 0">
        <div id="search">
            <form class="example" method="post" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="search"/>
                <input type="hidden" name="criteria" id="criteria">
                <input type="text" placeholder="Search.." name="searchData" id="searchData">
                <button type="submit" onclick="doIT()"><i class="fa fa-search"></i></button>
            </form>
        </div>
        <table id="uTable">
            <tbody>
            <%--@elvariable id="rPapers" type="java.util.ArrayList"--%>
            <c:forEach begin="0" end="${rPapers.size()/3}" varStatus="loop">
                <tr>
                    <c:forEach items="${rPapers}" var="paper" varStatus="status" begin="${loop.index*3}" end="${loop.index*3+2}" step="1">
                        <td class="item_box text-center">
                            <img id="${status.index}" class="text-center" src="${paper.imagePath}">
                            <a href="#" onclick="showMoreInformation(
                                    '<c:out value="${paper.type}"/>',
                                    '<c:out value="${paper.title}"/>',
                                    '<c:out value="${paper.description}"/>',
                                    '<c:out value="${paper.price}"/>',
                                    '<c:url value="${status.index}"/>',
                                    '<c:out value="${paper.publishingPeriodicity}"/>',
                                    '<c:out value="${paper.ageRestriction}"/>',
                                    '<c:out value="${paper.availability}"/>')"
                               style="color: #466a7b;"><h4 class="item_title">${paper.title}</h4></a>
                            <p class="text-left">${paper.description}</p>
                            <p class="text-left"><fmt:message key="label.priceFor" bundle="${rb}"/> <span class="item_price">${paper.price}</span>$</p>
                            <form method="post" action="${pageContext.request.contextPath}/controller">
                                <p class="text-left"><fmt:message key="label.choose" bundle="${rb}"/>
                                    <select id="select${status.index}" onchange="defineDuration('select${status.index}', 'output${status.index}')" title="<fmt:message key="label.choose" bundle="${rb}"/>">
                                        <option value="3">3</option>
                                        <option value="6">6</option>
                                        <option value="9">9</option>
                                        <option value="12">12</option>
                                    </select>
                                </p>
                                <input type="hidden" name="command" value="basket"/>
                                <input type="hidden" id="output${status.index}" name="durationQuantity" value="3">
                                <input type="hidden" name="paperEditionId" value="${paper.id}"/>
                            <input type="submit" style="    width: 100%;
    background-color: #18865ee0;
    color: #dae2e9;
    height: 45px;
    border-width: 1px;" value="Add to basket"/>
                            </form>
                        </td>
                    </c:forEach>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div id="pagination" style="display:block;"></div>
    </div>

    <script src='<c:url value="${pageContext.request.contextPath}/resource/js/jquery.js"/>'></script>

    <script>
        var modal = document.getElementById('myModal');
        var modalOrder = document.getElementById('successOrder');
        var modalPaper = document.getElementById('modalPaperWindow');
        var btn = document.getElementById("basketButton");
        var span = document.getElementsByClassName("close")[0];
        var spanPaper = document.getElementsByClassName("close")[2];
        var spanOrder = document.getElementsByClassName("close")[1];
        btn.onclick = function() {
            modal.style.display = "block";
        };
        function defineDuration(idSelect, idOutput) {
            document.getElementById(idOutput).value = document.getElementById(idSelect).value;
        }

        function showMoreInformation(type, title, description, price, image, periodicity, age_restr, is_available) {
            var totalPaperItem='';
            modalPaper.style.display = "block";
            totalPaperItem+='<div><img src="'+
                document.getElementById(image).src+'" style="height: 80%; width: 40%; float:left; margin: 10px 10px 10px 0;" />' +
                '<h3><fmt:message key="label.name" bundle="${rb}"/> '+title+'</h3>' +
                '<br><fmt:message key="label.type" bundle="${rb}"/> '+type+'</br>'+
                '<br><fmt:message key="label.ageR" bundle="${rb}"/> '+age_restr+'+</br>'+
                '<br><fmt:message key="label.description" bundle="${rb}"/> '+description+'</br>'+
                '<br><fmt:message key="label.periodicity" bundle="${rb}"/> '+periodicity+' <fmt:message key="label.perMonth" bundle="${rb}"/></br>'+
                '<h3><fmt:message key="label.price" bundle="${rb}"/>: '+price+'$</h3></div>';
            document.getElementById("paperInformation").innerHTML = totalPaperItem;
        }
        span.onclick = function() {
            modal.style.display = "none";
            document.getElementById("paperInformation").innerText='';
        };
        spanPaper.onclick = function() {
            modalPaper.style.display = "none";
        };

        spanOrder.onclick = function() {
            modalOrder.style.display = "none";
        };
        window.onclick = function(event) {
            if (event.target === modal) {
                modal.style.display = "none";
            }
            if (event.target === modalPaper) {
                modalPaper.style.display = "none";
            }
            if (event.target === modalOrder) {
                modalOrder.style.display = "none";
            }
        }
    </script>
    <script>
        function getCheckedCheckBoxes() {
            var checkboxes = document.getElementsByClassName('checkbox');
            var checkboxesChecked = []; 
            for (var index = 0; index < checkboxes.length; index++) {
                if (checkboxes[index].checked) {
                    checkboxesChecked.push(checkboxes[index].value); 
                }
            }
            return checkboxesChecked;
        }
        function doIT() {
            var opts = getCheckedCheckBoxes();
            var criteriaField = document.getElementById('criteria');
            criteriaField.value=opts;
        }
    </script>
    <script>
    var $table = document.getElementById("uTable"),
    $tablePagination = document.getElementById("pagination"),
    $n = 2,
    $rowCount = $table.rows.length,
    $firstRow = $table.rows[1].firstElementChild.tagName,
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
    <script src='<c:url value="${pageContext.request.contextPath}/resource/js/jquery.js"/>'></script>

</main>
<c:import url="../common/footer.jsp" />
</body>
</html>