<%@ page import="java.io.InputStream" %>
<%@ page import="gka.auth2.Auth2Util" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="gka.pay.wxpay.WXPayUtil" %>
<%@ page import="gka.pay.wxpay.WXPay" %>
<%@ page import="gka.pay.wxpay.controller.WxPayTool" %>
<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    WxPayTool wxPayTool = WxPayTool.getInstance();
//    Map<String, String> req = new HashMap<String, String>();
//    req.put("out_trade_no", "1568526879055tulzyyk");
//    Map<String, String> rep = wxPayTool.orderQuery(req);
//    System.out.println("$$$$$$$$$$$$$$" + rep);
//    out.print(rep);


    Map<String, String> req = new HashMap<String, String>();
    req.put("out_trade_no", "1568526879055tulzyyk");

    Map<String, String> rep =    wxPayTool.closeOrder(req);
    System.out.println("$$$$$$$$$$$$$$" + rep);
    out.print(rep);


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
