<%--
  Created by IntelliJ IDEA.
  User: kuzmi
  Date: 05.04.2020
  Time: 13:47
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo" scope="request"/>
    <title>MealEdit</title>
</head>
<body>
<section>
    <form method="post" action="mealsEdit?mealId=${meal.id}&action=edit" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="mealId" value="${meal.id}">
        <h3>Добавление еды</h3>
        <p>
        <dl>
            <dt>Дата</dt>
            <dd><input type="datetime-local" name="dateTime" size="30" value="${meal.dateTime}"></dd>
        </dl>
        <dl>
            <dt>Тип еды</dt>
            <dd><input type="text" name="description" size="30" value="${meal.description}"></dd>
        </dl>
        <dl>
            <dt>Калории</dt>
            <dd><input type="text" name="calories" size="30" value="${meal.calories}"></dd>
        </dl>
        </p>
        <br>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
</body>
</html>
