<%@ page import="com.jfinal.plugin.activerecord.Record" %>
<%@ page import="java.util.List" %>
<%@ page import="com.jfinal.plugin.activerecord.Db" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%
    String sql = "SELECT JXBMC,ZC,XQJ,JC,YJSXM,DD,KCMC,THZC,THXQJ,THJC,THDD,XJSXM,JXB_ID,TTKXX_ID,TKSJ,THLSGH FROM V_WPT_TTKXX WHERE SUBSTR(TKSJ,0,13) in (?,?)";
    //拉取得数据
%>
<head>
    <title></title>
</head>
<body>

</body>
</html>
