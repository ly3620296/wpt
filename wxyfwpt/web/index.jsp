<%@ page import="gka.auth2.Auth2Util" %>
<%@ page import="java.io.IOException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    try {
        String url = Auth2Util.createAuth2Url();
        out.print(url);
    } catch (IOException e) {
        e.printStackTrace();
    }
%>
<html>
<head>
    <title></title>
</head>
<body>

</body>
</html>
