<%@ page import="com.jfinal.plugin.activerecord.Db" %>
<%@ page import="com.jfinal.plugin.activerecord.Record" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String u = request.getParameter("u");
    String p = request.getParameter("p");

    if (u != null && p != null && u.equals("qc") && p.equals("zsy20200315")) {
        List<Record> records = Db.find("select ZH from wpt_yh where jsdm='02' and zh not in(select distinct(xh)  from wpt_wdcy)");
        for (int i = 0; i < records.size(); i++) {
            Db.update("INSERT INTO WPT_WDCY(XH,MENUID,TIME,ID) VALUES (?,'22',SYSDATE,SEQ_WPT_WDCY.NEXTVAL)", records.get(i).getStr("ZH"));
            Db.update("INSERT INTO WPT_WDCY(XH,MENUID,TIME,ID) VALUES (?,'25',SYSDATE,SEQ_WPT_WDCY.NEXTVAL)", records.get(i).getStr("ZH"));
            Db.update("INSERT INTO WPT_WDCY(XH,MENUID,TIME,ID) VALUES (?,'26',SYSDATE,SEQ_WPT_WDCY.NEXTVAL)", records.get(i).getStr("ZH"));
            Db.update("INSERT INTO WPT_WDCY(XH,MENUID,TIME,ID) VALUES (?,'34',SYSDATE,SEQ_WPT_WDCY.NEXTVAL)", records.get(i).getStr("ZH"));
            Db.update("INSERT INTO WPT_WDCY(XH,MENUID,TIME,ID) VALUES (?,'37',SYSDATE,SEQ_WPT_WDCY.NEXTVAL)", records.get(i).getStr("ZH"));
            Db.update("INSERT INTO WPT_WDCY(XH,MENUID,TIME,ID) VALUES (?,'41',SYSDATE,SEQ_WPT_WDCY.NEXTVAL)", records.get(i).getStr("ZH"));
        }
    } else {
        out.print("滚 没权限!!!!");
    }
%>
<html>
<head>
    <title></title>
</head>
<body>

</body>
</html>
