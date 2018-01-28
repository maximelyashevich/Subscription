<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="en_US" scope="session" />
<html>
<head>
    <link rel='stylesheet prefetch' href='<c:url value="${pageContext.request.contextPath}/resource/css/stylecdnjm.css"/>'>
    <title>Welcome, user</title>
    <style>
        @import "/resource/css/header-main.css" screen;
        @import "/resource/css/own-login-styling.css" screen;
        @import "/resource/css/footer.css" screen;
        @import "/resource/css/mainstyle.css" screen;
        @import "/resource/font/google-api.css" screen;
    </style>
    <link href='<c:url value="${pageContext.request.contextPath}/resource/font/1.css"/>' rel='stylesheet' type='text/css'>
    <link rel='stylesheet prefetch' href='https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css'>
    <link href='http://fonts.googleapis.com/css?family=Cookie' rel='stylesheet' type='text/css'>
    <script type="text/javascript" src="/resource/js/admin-main.js"></script>
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
            <a href="#">Overview</a>
            <a href="#">Surveys</a>
            <a href="#">Reports</a>
            <a href="#">Roles</a>
        </nav>
        <div class="header-user-menu">
            <div id="parent">
                ${user.firstName}
            </div>
            <ul>
                <li><a href="#">Settings</a></li>
                <li><a href="#">Payments</a></li>
                <li><a href="controller?command=logout">Logout</a></li>
            </ul>
        </div>
    </div>
</header>
<main style="background-color: #D5DDE5; padding: 50px 55px;">

    <div id="cart_content">
    </div>
    <button id="clear_cart">Очистить корзину</button>
    <div id="search">
        <form class="example" method="post" action="/controller">
            <input type="hidden" name="command" value="search"/>
            <input type="hidden" name="criteria" id="criteria">
            <input type="text" placeholder="Search.." name="searchData" id="searchData">
            <button type="submit" onclick="doIT()"><i class="fa fa-search"></i></button>
        </form>
    </div>
    <div id="right_container">
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
        <table id="uTable">
            <tbody>
            <c:forEach begin="0" end="${papers.size()/4}" varStatus="loop">
                <tr>
                    <c:forEach items="${papers}" var="paper" varStatus="status" begin="${loop.index*4}" end="${loop.index*4+3}" step="1">
                        <td class="item_box text-center">
                            <img src="${paper.imagePath}">
                            <h4 class="item_title">${paper.title}</h4>
                            <p>${paper.description}</p>
                            <p>Price:  <span class="item_price">${paper.price}</span>$</p>
                            <button id="checkout" class="add_item" data-id="${status.index}">Add to basket</button>
                        </td>
                    </c:forEach>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div id="pagination" style="display:block;"></div>
    </div>
    <script>
        var d = document,
            itemBox = d.querySelectorAll('.item_box'), // блок каждого товара
            cartCont = d.getElementById('cart_content'); // блок вывода данных корзины
        // Функция кроссбраузерной установка обработчика событий
        function addEvent(elem, type, handler){
            if(elem.addEventListener){
                elem.addEventListener(type, handler, false);
            } else {
                elem.attachEvent('on'+type, function(){ handler.call( elem ); });
            }
            return false;
        }
        // Получаем данные из LocalStorage
        function getCartData(){
            return JSON.parse(localStorage.getItem('cart'));
        }
        // Записываем данные в LocalStorage
        function setCartData(o){
            localStorage.setItem('cart', JSON.stringify(o));
            return false;
        }
        // Добавляем товар в корзину
        function addToCart(e){
            this.disabled = true; // блокируем кнопку на время операции с корзиной
            var cartData = getCartData() || {}, // получаем данные корзины или создаём новый объект, если данных еще нет
                parentBox = this.parentNode, // родительский элемент кнопки "Добавить в корзину",
                itemId = this.getAttribute('data-id'), // ID товара
                itemTitle = parentBox.querySelector('.item_title').innerHTML, // название товара
                itemPrice = parentBox.querySelector('.item_price').innerHTML; // стоимость товара
            cartData[itemId] = [itemTitle, itemPrice];
            if(!setCartData(cartData)){ // Обновляем данные в LocalStorage
                this.disabled = false; // разблокируем кнопку после обновления LS
            }
            openCart();
            return false;
        }
        // Устанавливаем обработчик события на каждую кнопку "Добавить в корзину"
        for(var i = 0; i < itemBox.length; i++){
            addEvent(itemBox[i].querySelector('.add_item'), 'click', addToCart);
        }
        // Открываем корзину со списком добавленных товаров
        function openCart(){
            var cartData = getCartData(), // вытаскиваем все данные корзины
                totalItems = '';
            // если что-то в корзине уже есть, начинаем формировать данные для вывода
            if(cartData !== null){
                totalItems = '<table id="basketTable" class="shopping_list"><tr><th>Наименование</th><th>Цена</th><th>Удалить</th></tr>';
                for(var items in cartData){
                    totalItems += '<tr>';
                    totalItems += '<td>'+cartData[items][0]+'</td>'+'<td class="price">'+cartData[items][1]+'</td>';
                    totalItems += '<td><a href="#" onclick="someDeleteRowFunction(this)">X</a></td></tr>';
                }

                totalItems += '<td>Итого: </td>'+'<td id="totalPrice" value="">'+
                    '</td>'+'<td></td></table>';
                cartCont.innerHTML = totalItems;
                defineFinalPrice();
            } else {
                // если в корзине пусто, то сигнализируем об этом
                cartCont.innerHTML = 'В корзине пусто!';
            }
            return false;
        }
        /* Очистить корзину */
        addEvent(d.getElementById('clear_cart'), 'click', function(e){
            localStorage.removeItem('cart');
            cartCont.innerHTML = 'Корзина очишена.';
        });
        function someDeleteRowFunction(o) {
            //no clue what to put here?
            var p=o.parentNode.parentNode;
            p.parentNode.removeChild(p);
            defineFinalPrice();
        }

        function defineFinalPrice(){
            var cls = document.getElementById("basketTable").getElementsByTagName("td");
            var sum = 0;
            for (var i = 0; i < cls.length; i++){
                if(cls[i].className === "price"){
                    sum += isNaN(cls[i].innerHTML) ? 0 : parseFloat(cls[i].innerHTML);
                }
            }
            document.getElementById("totalPrice").innerText=sum.toFixed(5);
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
            alert( 'The number of options selected is: ' + opts.length ); //  number of selected options
        };
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
    <script>
        $('i.glyphicon-remove').on('click', function(e){
            $(e.target).closest("tr").remove();
        });
    </script>
    <script src='<c:url value="${pageContext.request.contextPath}/resource/js/lib-carousel.js"/>'></script>
</main>
<c:import url="../jsp/common/footer.jsp" />
</body>
</html>