<%@ page import="com.jfinal.plugin.activerecord.Db" %>
<%@ page import="com.jfinal.plugin.activerecord.Record" %>
<%@ page import="java.util.List" %>
<%@ page import="gka.controller.xsjfgl.fpgl.FpglBean" %>
<%@ page import="gka.dzfp.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String[] bb = new String[]{"1599540961120emieofx"};
    for (String order : bb) {
        ThreadPoolUtil.execute(new SendDzfp(order));
    }

%>
<html>
<head>
    <title></title>
</head>
<body>

</body>
</html>
