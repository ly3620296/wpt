<%@ page import="gka.resource.Constant" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/11/3
  Time: 10:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title></title>
      <link rel="stylesheet" href="<%=Constant.server_name%>js-lib/layui-2.4.5/css/layui.css">
      <link rel="stylesheet" href="<%=Constant.server_name%>js-lib/layui-2.4.5/css/modules/laydate/default/laydate.css">
      <script type="text/javascript" src="<%=Constant.server_name%>js-lib/layui-2.4.5/lay/modules/laydate.js"></script>

  </head>
  <body>
  <input type="text" class="form-control" id="LogdateStart" />
  <input type="text" class="form-control" id="LogdateEnd" />
  </body>
</html>
<script>
    //初始化开始日期控件
    var start = laydate.render({
        elem: '#LogdateStart',
        max: new Date().toLocaleDateString(),
        done: function(value, date, endDate) {
            end.config.min = {
                year: date.year,
                month: date.month - 1,
                date: date.date
            }; //重置结束日期最小值
        }
    });
    //初始化结束日期控件
    var end = laydate.render({
        elem: '#LogdateEnd',
        max: new Date().toLocaleDateString(),
        done: function(value, date, endDate) {
            start.config.max = {
                year: date.year,
                month: date.month - 1,
                date: date.date
            }; //重置开始日期最大值
        }
    });
</script>