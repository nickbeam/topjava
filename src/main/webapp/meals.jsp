<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="css/style.css">
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>

<table align="center" border="1" cellpadding="8" cellspacing="0">
    <thead>
    <tr>
        <th width="25%">Date</th>
        <th width="25%">Description</th>
        <th width="25%">Calories</th>
        <th width="25%">Excess</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${meals}" var="meal">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
        <c:choose>
            <c:when test="${meal.excess == true}">
                <tr align="center" class="text-red">
                    <td>${DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(meal.dateTime)}</td>
                    <td>${meal.description}</td>
                    <td>${meal.calories}</td>
                    <td>${meal.excess}</td>
                </tr>
            </c:when>
            <c:otherwise>
                <tr align="center" class="text-green">
                    <td>${DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(meal.dateTime)}</td>
                    <td>${meal.description}</td>
                    <td>${meal.calories}</td>
                    <td>${meal.excess}</td>
                </tr>
            </c:otherwise>
        </c:choose>
    </c:forEach>
    </tbody>
</table>

</body>
</html>