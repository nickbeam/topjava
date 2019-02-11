<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="css/style.css">
    <title>Edit meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Edit meal</h2>

<jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
<form>
    <label for="datetime">Date:</label>
    <input type="datetime-local" id="datetime" name="dateTime" value="${meal.dateTime}">
</form>

</body>
</html>