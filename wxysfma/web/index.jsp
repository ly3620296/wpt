<%@ page import="gka.resource.Constant" %>
<%@ page import="com.jfinal.plugin.activerecord.Db" %>
<%@ page import="java.io.File" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.awt.image.BufferedImage" %>
<%@ page import="javax.imageio.ImageIO" %>
<%@ page import="java.io.ByteArrayOutputStream" %>
<%@ page import="com.jfinal.plugin.activerecord.Record" %>
<%@ page import="java.io.OutputStream" %>
<%@ page import="gka.dzfp.ThreadPoolUtil" %>
<%@ page import="gka.dzfp.SendDzfp" %>
<%@ page import="gka.Test" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    //    System.out.println("============");
//    String orderId = "1590909008009nrtargb";
//    ThreadPoolUtil.execute(new SendDzfp(orderId));
    Test.main(null);
%>
<%--<%--%>


<%--File f = new File("D://QC.png");--%>
<%--BufferedImage bi;--%>
<%--try {--%>
<%--bi = ImageIO.read(f);--%>
<%--ByteArrayOutputStream baos = new ByteArrayOutputStream();--%>
<%--ImageIO.write(bi, "jpg", baos);--%>
<%--byte[] bytes = baos.toByteArray();--%>
<%--Db.update("UPDATE XSZPB SET ZP=? WHERE XH='20183519'", bytes);--%>
<%--} catch (Exception e) {--%>
<%--e.printStackTrace();--%>
<%--}--%>
<%--%>--%>
<%--<html>--%>
<%--<head>--%>
<%--<title></title>--%>
<%--<link rel="stylesheet" href="<%=Constant.server_name%>js-lib/layui-2.4.5/css/layui.css">--%>
<%--<link rel="stylesheet" href="<%=Constant.server_name%>js-lib/layui-2.4.5/css/modules/laydate/default/laydate.css">--%>
<%--<script type="text/javascript" src="<%=Constant.server_name%>js-lib/layui-2.4.5/lay/modules/laydate.js"></script>--%>

<%--</head>--%>
<%--<body>--%>
<%--<input type="text" class="form-control" id="LogdateStart"/>--%>
<%--<input type="text" class="form-control" id="LogdateEnd"/>--%>
<%--</body>--%>
<%--</html>--%>
<%--<script>--%>
<%--//初始化开始日期控件--%>
<%--var start = laydate.render({--%>
<%--elem: '#LogdateStart',--%>
<%--max: new Date().toLocaleDateString(),--%>
<%--done: function (value, date, endDate) {--%>
<%--end.config.min = {--%>
<%--year: date.year,--%>
<%--month: date.month - 1,--%>
<%--date: date.date--%>
<%--}; //重置结束日期最小值--%>
<%--}--%>
<%--});--%>
<%--//初始化结束日期控件--%>
<%--var end = laydate.render({--%>
<%--elem: '#LogdateEnd',--%>
<%--max: new Date().toLocaleDateString(),--%>
<%--done: function (value, date, endDate) {--%>
<%--start.config.max = {--%>
<%--year: date.year,--%>
<%--month: date.month - 1,--%>
<%--date: date.date--%>
<%--}; //重置开始日期最大值--%>
<%--}--%>
<%--});--%>
<%--</script>--%>