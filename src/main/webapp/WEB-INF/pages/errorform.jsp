<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
<h1>Type error</h1>
<h3>
    <pre>
    You entered incorrect data. We remind you that
    the name field must not be empty and must contain
    at least one character, and the age field must
    contain non-negative integers
    </pre>
</h3>
<br/>
<a href="<c:url value="/users"/>">Back to UserManager</a>
<br/>


</body>
</html>
