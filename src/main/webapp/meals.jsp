<%@ page import="ru.javawebinar.topjava.util.MealsUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: kuzmi
  Date: 05.04.2020
  Time: 11:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<section>
    <table>
        <tr class="tab_head">
            <th>Время</th>
            <th>Еда</th>
            <th>Калории</th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach items="${meals}" var="mealTo">
            <jsp:useBean id="mealTo" type="ru.javawebinar.topjava.model.MealTo"/>
            <tr <%= mealTo.isExcess() ? "style=\"color: red\"" : "style=\"color: green\"" %>>
                <td><fmt:parseDate value="${ mealTo.dateTime }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime"
                                   type="both"/>
                    <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }"/></td>
                <td>${mealTo.description}</td>
                <td>${mealTo.calories}</td>
                <td><a href="meals?mealId=${mealTo.id}&action=delete"><img src="img/delete.png"></a></td>
                <td><a href="meals?mealId=${mealTo.id}&action=edit"><img src="img/pencil.png"></a></td>
            </tr>
        </c:forEach>
    </table>
    <a href="meals?action=add">Добавить новую еду <img src="img/add.png"></a>
</section>
</body>
</html>
