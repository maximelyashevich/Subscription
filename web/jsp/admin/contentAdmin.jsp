<%--@elvariable id="uPaper" type="java.lang.String"--%>
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
<%--@elvariable id="userLocale" type="String"--%>
<fmt:setLocale value="${userLocale}" />
<fmt:setBundle basename="resource.pagecontent" var="rb"/>
<html>
<head>
    <title><fmt:message key="label.adminContent" bundle="${rb}"/></title>
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
    <div id="modalPaperWindow" class="modal" style="display:none;">
        <div class="modal-content-order">
            <span class="close">&times;</span>
            <div id="paperInformation"></div>
        </div>
    </div>
    <div class="content" id="paperTable">
        <table id="pTable" class="table-fill">
            <thead>
            <tr>
                <th class="text-center"><em class="fa fa-cog"></em></th>
                <th class="text-left"><fmt:message key="label.option" bundle="${rb}"/></th>
                <th class="text-left"><fmt:message key="label.image" bundle="${rb}"/></th>
                <th class="text-left"><fmt:message key="label.name" bundle="${rb}"/></th>
                <th class="text-left"><fmt:message key="label.typePaper" bundle="${rb}"/></th>
                <th class="text-left"><fmt:message key="label.periodicityPaper" bundle="${rb}"/></th>
                <th class="text-left"><fmt:message key="label.ageRestriction" bundle="${rb}"/></th>
                <th class="text-left"><fmt:message key="label.availability" bundle="${rb}"/></th>
                <th class="text-left"><fmt:message key="label.price" bundle="${rb}"/></th>
            </tr>
            </thead>
            <tbody class="table-hover">
            <%--@elvariable id="papers" type="java.util.ArrayList"--%>
            <c:forEach items="${papers}" var="paper" varStatus="status">
                <tr>
                    <td class="text-left"><a class="btn btn-default" href="#" onclick="showMoreInformation(
                            '<c:out value="${paper.type}"/>',
                            '<c:out value="${paper.title}"/>',
                            '<c:out value="${paper.description}"/>',
                            '<c:out value="${paper.price}"/>',
                            '<c:out value="${paper.publishingPeriodicity}"/>',
                            '<c:out value="${paper.ageRestriction}"/>',
                            '<c:out value="${paper.id}"/>')"><em class="fa fa-pencil"></em></a>
                    </td>
                      <td>
                          <form action="${pageContext.request.contextPath}/upload" style="width: 150px; margin-right: -30px;" enctype="multipart/form-data" method="POST">
                              <input type="file" name="image" id="image" style="width: 80%; font-size: 12px;" accept="image/*" required/>
                              <input type="hidden" name="secret" value="paper">
                              <input type="hidden" name="paperID" value="${paper.id}"/>
                              <button style="width: 80%; margin-top: 5px; margin-left: -20%;"><fmt:message key="label.send" bundle="${rb}"/></button>
                          </form>
                      </td>
                       <td>
                         <img src="${paper.imagePath}" style="position: inherit;width: 145px; height:115px;" id="contactImage"/>
                    </td>
                    <td class="text-left">${paper.title}</td>
                    <td class="text-left">${paper.type}</td>
                    <%--<td class="text-left">${paper.description}</td>--%>
                    <td class="text-left">${paper.publishingPeriodicity}</td>
                    <td class="text-left">${paper.ageRestriction}</td>
                    <td class="text-center">
                        <form method="post" action="${pageContext.request.contextPath}/controller">
                            <input type="hidden" name="command" value="hide_paper">
                            <input type="hidden" name="paperID" value="${paper.id}"/>
                            <c:choose>
                                <c:when test="${paper.availability=='true'}">
                                    <input type="submit" style="width: 100%; background-color:rgba(42,51,64,0.78);color: #dae2e9;font-size: 12px;border-width: 1px;" value="<fmt:message key="label.hide" bundle='${ rb }' />"/>
                                </c:when>
                                <c:otherwise>
                                    <input type="submit" style="width: 100%; background-color:rgba(42,51,64,0.78);color: #dae2e9;font-size: 12px;border-width: 1px;" value="<fmt:message key="label.show" bundle='${ rb }' />"/>
                                </c:otherwise>
                            </c:choose>
                        </form>
                    </td>
                    <td class="text-left">${paper.price}</td>
                </tr>
            </c:forEach>
            </tbody>
            <tbody>
        </table>
    </div>
    <div id="pagination"></div>
    <script>
        var modalPaper = document.getElementById('modalPaperWindow');
        var spanPaper = document.getElementsByClassName("close")[0];
        function showMoreInformation(type, title, description, price, periodicity, age_restr, paperID) {
            var totalPaperItem='';
            modalPaper.style.display = "block";
            totalPaperItem+=' <form method="POST" action="/controller">' +
                '            <input type="hidden" name="command" value="update_paper">' +
                '<input type="hidden" name="paperID" value="'+paperID+'">'+
                    ' <h2>${uPaper}</h2>' +
                '<div style="color: black">' +
               '<div class="field-wrap" style="margin: 10px; height: 20px;">' +
                '<label style="color:black"><fmt:message key="label.name" bundle="${rb}"/>:</label>'+
            '<input type="text" name="paperTitle" style="float: left;margin-left: 150px;width: 70%;" value="'+title.replace(/"/g,"")+'" required title="<fmt:message key="label.paperTitle" bundle='${ rb }' />"/></div>'+
               '<div class="field-wrap" style="margin: 10px; height: 20px;">'+
                '<label style="color:black"><fmt:message key="label.typePaper" bundle="${rb}"/>:</label>'+
            '<input type="text" name="type" style="float: left;margin-left: 150px;width: 70%; margin-top: 5px;" value="'+type+'" required pattern="^(magazine|newspaper|book)$" title="<fmt:message key="label.paperTypeIn" bundle='${ rb }' />"/></div>'+
                '<div class="field-wrap" style="margin: 10px;">' +
                '<textarea name="description" rows="4" cols="50" style="color:darkslategray; font-size:18px; margin-top: 0px; margin-bottom: 0px;  height: 100px; border: 1px solid #a0b3b0;" required ' +
                ' title="<fmt:message key="label.descriptionD" bundle='${ rb }' />">'+
                description+'</textarea></div>'+
                '<div class="field-wrap" style="margin: 10px; height: 20px;">' +
                '<label style="color:black"><fmt:message key="label.ageRestriction" bundle="${rb}"/>:</label>'+
                '<input type="text" name="restriction" style="float: left; margin-left: 175px; margin-top: 5px; width: 65%;" value="'+age_restr+'" ' +
                'required pattern="[0-9]{1,2}" title="<fmt:message key="label.restrictionIn" bundle='${ rb }' />"/></div>'+
                '<div class="field-wrap" style="margin: 10px; height: 20px;">' +
                '<label style="color:black"><fmt:message key="label.periodicityPaper" bundle="${rb}"/>:</label>'+
                '<input type="text" name="period" style="float: left;margin-left: 200px; width: 60%; margin-top: 5px;" value="'+periodicity+'" ' +
                'required pattern="[1-5]{1}" title="<fmt:message key="label.periodicityIn" bundle='${ rb }' />"/></div>'+
                '<div class="field-wrap" style="margin: 10px; height: 20px;">' +
                '<label style="color:black"><fmt:message key="label.price" bundle="${rb}"/> ($):</label>'+
            '<input type="text" name="price" style="float: left;margin-left: 150px;width: 70%; margin-top: 5px;" value="'+price+'" ' +
                'required pattern="-?[0-9]+(?:\\.[0-9]{1,5})?" title="<fmt:message key="label.moneyIn" bundle='${ rb }' />"/></div>'+
            '<div class="field-wrap" style="margin: 10px;">' +
                '<button>Save changes</button></div></form>';
            document.getElementById("paperInformation").innerHTML = totalPaperItem;
        }
        spanPaper.onclick = function() {
            modalPaper.style.display = "none";
        };
        window.onclick = function(event) {
            if (event.target === modalPaper) {
                modalPaper.style.display = "none";
            }
        };
        var $table = document.getElementById('pTable'),
        $tablePagination = document.getElementById("pagination"),
        $n = 4,
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