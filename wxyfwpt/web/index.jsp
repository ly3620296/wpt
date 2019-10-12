<%@ page import="gka.auth2.Auth2Util" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String url = Auth2Util.createAuth2Url();
    out.print(url);
%>
<html>
<head>
    <title></title>
</head>
<body>

</body>
</html>
