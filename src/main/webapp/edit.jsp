<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="ru.javawebinar.topjava.model.MealTo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Edit meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Edit meal</h2>
<%--<jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>--%>
<form method="post" action="meals" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="id" value="${meal.id}">

        <label for="datetime">Date:</label>
        <input type="datetime-local" id="datetime" name="dateTime" value="${meal.dateTime}">

        <label for="description">Description:</label>
        <input type="text" id="description" name="description" value="${meal.description}">

        <label for="calories">Calories:</label>
        <input type="number" step="1" id="calories" name="calories" value="${meal.calories}">
    <hr>
    <button type="submit">Save</button>
    <button onclick="window.history.back()">Cancel</button>
</form>
</body>
</html>