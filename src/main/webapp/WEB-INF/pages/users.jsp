<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>
<html>
<head>
<%--Создаем стиль для нашей таблицы--%>
    <style type="text/css">
        .tg {
            border-collapse: collapse;
            border-spacing: 0;
            border-color: #ccc;
        }

        .tg td {
            font-family: Arial, sans-serif;
            font-size: 14px;
            padding: 10px 5px;
            border-style: solid;
            border-width: 1px;
            overflow: hidden;
            word-break: normal;
            border-color: #ccc;
            color: #333;
            background-color: #fff;
        }

        .tg th {
            font-family: Arial, sans-serif;
            font-size: 14px;
            font-weight: normal;
            padding: 10px 5px;
            border-style: solid;
            border-width: 1px;
            overflow: hidden;
            word-break: normal;
            border-color: #ccc;
            color: #333;
            background-color: #f0f0f0;
        }
    </style>
</head>
<body>

<h2>UserManager</h2>


<table class="tg">
    <%--Формируем форму для ввода параметров нашего фильтра--%>
    <form:form action="/users/filter" commandName="userFilter">
        <tr>
            <th><form:label path="filterName">Filter by Name:</form:label></th>
            <th colspan="3"><form:input path="filterName"/></th>
            <th><form:select path="enabled">
                <form:option value="true" label="Filter enabled" />
                <form:option value="false" label="Filter disabled" />
            </form:select></th>
            <th colspan="2"><input type="submit" value="Save changes" /></th>
        </tr>
    </form:form>
        <%--Формируем шапку для нашей таблицы--%>
        <tr>
            <th width="90">ID</th>
            <th width="120">Name</th>
            <th width="80">Age</th>
            <th width="80">Admin</th>
            <th width="120">Last changes</th>
            <th width="60">Edit</th>
            <th width="60">Delete</th>
        </tr>

        <%--Если БД не пустая заполняем нашу таблицу--%>
        <c:if test="${!empty userList}">
            <c:forEach items="${userList}" var="user">
                <tr>
                    <td>${user.id}</td>
                    <td>${user.name}</td>
                    <td>${user.age}</td>
                    <td>${user.admin}</td>
                    <td>${user.date}</td>
                    <td><a href="<c:url value='/edit/${user.id}'/>">Edit</a></td>
                    <td><a href="<c:url value='/delete/${user.id}'/>">Delete</a></td>
                </tr>
            </c:forEach>
        </c:if>

        <%--в зависимости от значения ID в  поле ввода--%>
        <%--формируем форму для редактированиия или ввода нового пользователья--%>
        <c:if test="${user.id==0||!empty user.id}">
            <c:url var="addAction" value="/users/add"/>
        </c:if>

        <c:if test="${user.id!=0}">
            <c:url var="addAction" value="/users/edit"/>
        </c:if>

        <form:form action="${addAction}" commandName="user">
        <tr>
            <th>
                <%--<form:input path="id" readonly="true" disabled="true" size="6"/>--%>
                User Editor
                <form:hidden path="id"/>
            </th>
            <th><form:input path="name"/></th>
            <th><form:input path="age" size="6"/></th>
            <th><form:select path="admin">
                <form:option value="true" label="true" />
                <form:option value="false" label="false" />
            </form:select></th>
            <th><form:input path="date" readonly="true" disabled="true"/></th>

            <%--В зависимости от действия формируем кнопки в форме --%>
            <th colspan="2">
                <c:if test="${user.id!=0}">
                    <p>
                    <input type="submit" value="<spring:message text="Apply"/>"/>
                    <form:form action="/users" commandName="user">
                        <input type="submit" value="<spring:message text="Cancel"/>"/>
                    </form:form>
                    </p>
                </c:if>
                <c:if test="${user.id==0}">
                    <input type="submit" value="<spring:message text="Add User"/>"/>
                </c:if>
            </th>
        </tr>
        </form:form>
        <%--Отображаем наш пейджинг под таблицей--%>
        <tr>
            <td colspan="4">
                    Current page:< ${currentPage} of  ${countPage} >
                    <c:if test="${countPage > 0}">
                        <c:forEach begin="1" end="${countPage}" var="val">
                            <c:url var="pageURL" value="/users?page=${val}" />
                            <a href="${pageURL}">${val}</a>
                        </c:forEach>
                    </c:if>
            </td>
            <%--Отображаем поле выбора колличества отображаемых строк--%>
            <form:form action="/users/numLineOfPage" commandName="numLineOfPage">
            <td colspan="3" align="right">
                <form:label path="lineOfPage">Rows of page</form:label>
                <form:select path="lineOfPage">
                <form:option value="5" label="5" />
                    <form:option value="10" label="10" />
                    <form:option value="15" label="15" />
                    <form:option value="20" label="20" />
                </form:select>
                <input type="submit" value="Ok" />
            </td>
            </form:form>
        </tr>
    </table>
</body>
</html>
