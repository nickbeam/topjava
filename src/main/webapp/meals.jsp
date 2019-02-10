<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>

<table>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo" scope="request"/>
    <c:forEach var="meal" items="${meal}">
        <tr>
            <td colspan="2">
                <%=meal.toString()%><br/>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>