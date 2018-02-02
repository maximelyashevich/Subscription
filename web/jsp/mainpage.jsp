<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="en_US" scope="session" />
<html>
<head>
    <link rel='stylesheet prefetch' href='<c:url value="${pageContext.request.contextPath}/resource/css/stylecdnj.css"/>'>
    <title>Welcome, user</title>
    <style>
        @import "/resource/css/header-main.css" screen;
        @import "/resource/css/own-login-styling.css" screen;
        @import "/resource/css/footer.css" screen;
        @import "/resource/css/mainstyle.css" screen;
        @import "/resource/css/popup.css" screen;
    </style>
    <link href='<c:url value="${pageContext.request.contextPath}/resource/font/1.css"/>' rel='stylesheet' type='text/css'>
    <link rel='stylesheet prefetch' href='https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css'>
    <link href='http://fonts.googleapis.com/css?family=Cookie' rel='stylesheet' type='text/css'>
    <script type="text/javascript" src="/resource/js/adminStyle.js"></script>
    <script>
        window.console = window.console || function(t) {};
        if (document.location.search.match(/type=embed/gi)) {
            window.parent.postMessage("resize", "*");
        }
    </script>
</head>
<body>
<header class="header-main">
    <div class="header-limiter">
        <h1><a href="#"><span>Subscription</span></a></h1>
        <nav>
            <a href="/jsp/user.jsp">Моя страница</a>
            <a href="#">Surveys</a>
            <a href="#">Reports</a>
            <a href="#">Roles</a>
        </nav>
        <div class="header-user-menu">
            <div id="parent">
                ${user.firstName}
            </div>
            <ul>
                <li>
                    <a href="#">Settings</a>
                </li>
                <li>
                    <form method="post" action="/controller">
                        <input type="hidden" name="command" value="money"/>
                        <input type="hidden" name="currentUserId" value="${user.id}">
                   <button type="submit">Top up the balance</button>
                    </form>
                </li>
                <li><a href="/controller?command=logout">Logout</a></li>
            </ul>
        </div>
    </div>
</header>
<main style="background-color: #D5DDE5; padding: 50px 55px;">
    <!-- The Modal -->
    <div id="myModal" class="modal">
        <!-- Modal content -->
        <div class="modal-content">
            <span class="close">&times;</span>
            <span>Your order basket, dear user!</span>
            <div id="empty_basket">
            </div>
            <div id="cart_content">
                <table id="basketTable" class="shopping_list" style="width:100%;">
                    <tr>
                        <th>Наименование</th>
                        <th>Цена</th>
                        <th>Удалить</th>
                    </tr>
                        <c:forEach items="${basketSet}" var="basketElement">
                        <tr>
                        <td>${basketElement.title}</td>
                        <td class="price">${basketElement.price}</td>
                        <td>
                            <form id="basketForm" name="basketForm" method="post" action="/controller">
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
                    <td>Итого: </td>
                    <td id="totalPrice" colspan="2">${finalPrice}$</td>
                    </tr>
                </table>
                <form id="subscriptionForm" name="subscriptionForm" method="post" action="/controller">
                    <input type="hidden" name="command" value="subscription"/>
                    <input type="hidden" name="userId" value="${user.id}"/>
                    <button id="paySubscription">Оплатить подписку</button>
                </form>
                <button id="getCredit">Взять в кредит</button>
            </div>
        </div>
    </div>
    <div id="modalPaperWindow" class="modal">
        <!-- Modal content -->
        <div class="modal-content-order">
            <span class="close">&times;</span>
            <div id="paperInformation"></div>
        </div>
    </div>
    <div id="right_container">
        <input type="button" id="basketButton" style="
background-color: #f1f1f1a6;
    border: 1px solid #ccc;
    color: #62666a;
    width: 100%;
    height: 45px;
    font: 16px Arial, Helvetica, sans-serif;" value="Корзина ${quantity}">
        </input>
        <fieldset class="group">
            <legend>Select genres for searching</legend>
            <ul class="checkbox">
                <c:forEach items="${genres}" var="genre">
                    <li>
                        <label for="${genre.name}" style=" position: unset;
                        transform: none;
                        color: rgba(25, 25, 25, 0.75);
                        pointer-events: auto;
     font-size: 14px;">
                            <input type="checkbox" class="checkbox" id="${genre.name}" value="${genre.name}" style="height: auto;"/>
                                ${genre.name}
                        </label>
                    </li>
                </c:forEach>
            </ul>
        </fieldset>
    </div>
    <div class="content" id="userTable" style="width: 80%; margin-left: 0px">
        <div id="search">
            <form class="example" method="post" action="/controller">
                <input type="hidden" name="command" value="search"/>
                <input type="hidden" name="criteria" id="criteria">
                <input type="text" placeholder="Search.." name="searchData" id="searchData">
                <button type="submit" onclick="doIT()"><i class="fa fa-search"></i></button>
            </form>
        </div>
        <table id="uTable">
            <tbody>
            <c:forEach begin="0" end="${papers.size()/4}" varStatus="loop">
                <tr>
                    <c:forEach items="${papers}" var="paper" varStatus="status" begin="${loop.index*4}" end="${loop.index*4+3}" step="1">
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
                            <p class="text-left">Price for 3 subscriptions month:  <span class="item_price">${paper.price}</span>$</p>
                            <form method="post" action="/controller">
                                <p class="text-left">Choose subscription duration:
                                    <select id="select${status.index}" onchange="defineDuration('select${status.index}', 'output${status.index}')">
                                        <option value="3">3 months</option>
                                        <option value="6">6 months</option>
                                        <option value="9">9 months</option>
                                        <option value="12">12 months</option>
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
    <script>
        var modal = document.getElementById('myModal');
        var modalPaper = document.getElementById('modalPaperWindow');
        var btn = document.getElementById("basketButton");
        var span = document.getElementsByClassName("close")[0];
        var spanPaper = document.getElementsByClassName("close")[1];
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
                document.getElementById(image).src+'" style="height: 390px; width: 280px; float:left; margin: 10px 10px 10px 0;" />' +
                '<h3>Title: '+title+'</h3>' +
                '<br>Type: '+type+'</br>'+
                '<br>Title: '+title+'</br>'+
                '<br>Age restriction: '+age_restr+'+</br>'+
                '<br>Description: '+description+'</br>'+
                '<br>Periodicity: '+periodicity+'per month</br>'+
                '<br>Availability: '+is_available+'</br>'+
                '<h3>Price: '+price+'$</h3></div>';
            document.getElementById("paperInformation").innerHTML = totalPaperItem;
        }
        span.onclick = function() {
            modal.style.display = "none";
            document.getElementById("paperInformation").innerText='';
        };
        spanPaper.onclick = function() {
            modalPaper.style.display = "none";
        };
        // When the user clicks anywhere outside of the modal, close it
        window.onclick = function(event) {
            if (event.target === modal) {
                modal.style.display = "none";
            }
            if (event.target === modalPaper) {
                modalPaper.style.display = "none";
            }
        }

    </script>
    <script>
        function getCheckedCheckBoxes() {
            var checkboxes = document.getElementsByClassName('checkbox');
            var checkboxesChecked = []; // можно в массиве их хранить, если нужно использовать
            for (var index = 0; index < checkboxes.length; index++) {
                if (checkboxes[index].checked) {
                    checkboxesChecked.push(checkboxes[index].value); // положим в массив выбранный
                }
            }
            return checkboxesChecked;
        }
        function doIT() {
            // reference to select list using this keyword and form elements collection
            // no callback function used this time
            var opts = getCheckedCheckBoxes();
            var criteriaField = document.getElementById('criteria');
            criteriaField.value=opts;
        }
    </script>
    <%--<script>--%>
    <%--var $table = document.getElementById("uTable"),--%>
    <%--$tablePagination = document.getElementById("pagination"),--%>
    <%--$n = 2,--%>
    <%--$rowCount = $table.rows.length,--%>
    <%--$firstRow = $table.rows[1].firstElementChild.tagName,--%>
    <%--$hasHead = ($firstRow === "TH"),--%>
    <%--$tr = [],--%>
    <%--$i,$ii,$j = ($hasHead)?1:0,--%>
    <%--// holds the first row if it has a (<TH>) & nothing if (<TD>)--%>
    <%--$th = ($hasHead?$table.rows[(0)].outerHTML:"");--%>
    <%--var $pageCount = Math.ceil($rowCount / $n);--%>
    <%--if ($pageCount > 1) {--%>
    <%--for ($i = $j,$ii = 0; $i < $rowCount; $i++, $ii++)--%>
    <%--$tr[$ii] = $table.rows[$i].outerHTML;--%>
    <%--$tablePagination.insertAdjacentHTML("afterend","<div id='buttons'></div>");--%>
    <%--sort(1);--%>
    <%--}--%>
    <%--// ($p) is the selected page number. it will be generated when a user clicks a button--%>
    <%--function sort($p) {--%>
    <%--/* create ($rows) a variable to hold the group of rows--%>
    <%--** to be displayed on the selected page,--%>
    <%--** ($s) the start point .. the first row in each page, Do The Math--%>
    <%--*/--%>
    <%--var $rows = $th,$s = (($n * $p)-$n);--%>
    <%--for ($i = $s; $i < ($s+$n) && $i < $tr.length; $i++)--%>
    <%--$rows += $tr[$i];--%>
    <%--$table.innerHTML = $rows;--%>
    <%--// create the pagination buttons--%>
    <%--document.getElementById("buttons").innerHTML = pageButtons($pageCount,$p);--%>
    <%--// CSS Stuff--%>
    <%--document.getElementById("id"+$p).setAttribute("class","active");--%>
    <%--}--%>
    <%--function pageButtons($pCount,$cur) {--%>
    <%--/* this variables will disable the "Prev" button on 1st page--%>
    <%--and "next" button on the last one */--%>
    <%--var $prevDis = ($cur == 1)?"disabled":"",--%>
    <%--$nextDis = ($cur == $pCount)?"disabled":"",--%>
    <%--/* this ($buttons) will hold every single button needed--%>
    <%--** it will creates each button and sets the onclick attribute--%>
    <%--** to the "sort" function with a special ($p) number..--%>
    <%--*/--%>
    <%--$buttons = "<input type='button' value='&lt;&lt; Prev' onclick='sort("+($cur - 1)+")' "+$prevDis+">";--%>
    <%--for ($i=1; $i<=$pCount;$i++)--%>
    <%--$buttons += "<input type='button' id='id"+$i+"'value='"+$i+"' onclick='sort("+$i+")'>";--%>
    <%--$buttons += "<input type='button' value='Next &gt;&gt;' onclick='sort("+($cur + 1)+")' "+$nextDis+">";--%>
    <%--return $buttons;--%>
    <%--}--%>
    <%--</script>--%>
    <script src='<c:url value="${pageContext.request.contextPath}/resource/js/jquery.js"/>'></script>
    <script src='<c:url value="${pageContext.request.contextPath}/resource/js/lib-carousel.js"/>'></script>
</main>
<c:import url="../jsp/common/footer.jsp" />
</body>
</html>