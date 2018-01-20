<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><title>Welcome</title></head>
<body>
<h3>Welcome</h3>
<hr/>
 ${user}, hello!
<hr/>
<div>
    <c:forEach items="${papers}" var="paper">
        <tr>
            <td>${paper.type}</td>
            <td>
                <img src="${paper.imagePath}" border="2px" height="450" width="350">
            </td>
        </tr>
    </c:forEach>
</div>
<a href="controller?command=logout">Logout</a>
</body>
</html>