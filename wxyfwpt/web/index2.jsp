<%@ page import="java.util.Enumeration" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Thread.sleep(1000000);
    String a = request.getParameter("a");
    String b = request.getParameter("b");
    String qur = request.getQueryString();
    System.out.println("qur-----" + qur);
    System.out.println("a----" + a);
    System.out.println("b----" + b);
//    Enumeration<String> headerNames = request.getHeaderNames();
//    while (headerNames.hasMoreElements()) {//判断是否还有下一个元素
//        String nextElement = headerNames.nextElement();//获取headerNames集合中的请求头
//        String header2 = request.getHeader(nextElement);//通过请求头得到请求内容
//        System.out.println(nextElement + ":" + header2);
//    }
%>
<html>
<head>
    <title></title>
</head>
<body>
<h1>哈哈哈</h1>

</body>
</html>