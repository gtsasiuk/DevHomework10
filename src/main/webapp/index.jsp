<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Current Time</title>
</head>
<body>
<h1>Current Time</h1>
<p>Time: <%= request.getAttribute("currentTime") %></p>
<p>Timezone: <%= request.getAttribute("timezone") %></p>
</body>
</html>