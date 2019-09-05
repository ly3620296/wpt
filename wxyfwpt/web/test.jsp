<%@ page import="java.io.InputStream" %>
<%@ page import="gka.auth2.Auth2Util" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="gka.pay.wxpay.WXPayUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    System.out.println("----------------");
    System.out.println("------------------");
    BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
    StringBuffer sf = new StringBuffer();
    String len;
    while ((len = br.readLine()) != null) {
        sf.append(len);

    }
    Map<String, String> map = WXPayUtil.xmlToMap(sf.toString());
    System.out.println("$$$$$$$$$$$$$$$$$"+map);
    PrintWriter pw = response.getWriter();
    pw.write("<xml>" +
            "<return_code><![CDATA[SUCCESS]]></return_code>" +
            "<return_msg><![CDATA[OK]]></return_msg>" +
            "</xml>");
    pw.flush();
    System.out.println("-------end------------");
//    Map<Thread, StackTraceElement[]> map = Thread.getAllStackTraces();
//    Set<Map.Entry<Thread, StackTraceElement[]>> entrySet = map.entrySet();
//    Iterator<Map.Entry<Thread, StackTraceElement[]>> iterator = entrySet.iterator();
//    while (iterator.hasNext()) {
//        Map.Entry<Thread, StackTraceElement[]> entry = iterator.next();
//        Thread t = entry.getKey();
//        StackTraceElement[] stackTraceElements = entry.getValue();
//        out.println("线程:" + t.getName() + "</br>");
//        out.println("线程状态:" + t.getState());
//        for (StackTraceElement element : stackTraceElements) {
//            out.println("\t" + element + "</br>");
//        }
//    }
%>
<html>
<head>
    <title></title>
</head>
<body>

</body>
</html>
