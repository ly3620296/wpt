<%@ page import="gka.resource.Constant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/reset.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>font-awesome-4.7.0/css/font-awesome.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>js-lib/layui/css/layui.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/table_ts.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/kjscx.css"/>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/jquery/jquery-3.3.1.min.js"></script>
    <script src="<%=Constant.server_name%>js-lib/base.js" type="text/javascript"
            charset="utf-8"></script>
    <title></title>
</head>
<body style="background: #e4e4e4;">
<jsp:include page="/common/auth.jsp"></jsp:include>
<div class="kjscx">
    <div class="titledddiv">
        <!--<img class="fh-icon" src="img/fh-icon.png"/>-->
        <p class="titleName">空教室查询</p>
    </div>

    <div class="layui-form" lay-filter="kjscx_cdlb">
        <label class="layui-form-label">教室类别</label>

        <div class="layui-input-block">
            <select name="interest" id="kjscx_cdlb" lay-filter="kjscx_cdlb_se">
                <option value=""></option>
            </select>
        </div>
    </div>

    <div class="layui-form" lay-filter="kjscx_lh">
        <label class="layui-form-label">楼号</label>

        <div class="layui-input-block">
            <select name="interest" id="kjscx_lh" lay-filter="kjscx_lh_se">
                <option value=''>请选择（楼号）</option>
            </select>
        </div>
    </div>

    <div class="layui-form" lay-filter="kjscx_rq">
        <label class="layui-form-label">日期</label>

        <div class="layui-input-block">
            <select name="interest" id="kjscx_rq" lay-filter="kjscx_rq_se">
            </select>
        </div>
    </div>

    <div class="kjscx-title">
        <img src="<%=Constant.server_name%>img/kjscx-icontext.jpg"/>
        <span>空教室查询</span>
    </div>

    <table class="kjscxtable">
        <!-- <caption>我是标题</caption>-->
        <thead>
        <tr>
            <!-- 模拟表头分割线的部分 -->
            <th>教室</th>
            <th>1-2</th>
            <th>3-4</th>
            <th>5-6</th>
            <th>7-8</th>
            <th>9-10</th>
            <th>11-12</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>B03</td>
            <td><i class="fa fa-home fa-lg kjsactive"></i></td>
            <td><i class="fa fa-home fa-lg"></i></td>
            <td><i class="fa fa-home fa-lg"></i></td>
            <td><i class="fa fa-home fa-lg"></i></td>
            <td><i class="fa fa-home fa-lg"></i></td>
            <td><i class="fa fa-home fa-lg"></i></td>

        </tr>
        <tr>
            <td>B03</td>
            <td><i class="fa fa-home fa-lg  kjsactive"></i></td>
            <td><i class="fa fa-home fa-lg"></i></td>
            <td><i class="fa fa-home fa-lg"></i></td>
            <td><i class="fa fa-home fa-lg"></i></td>
            <td><i class="fa fa-home fa-lg"></i></td>
            <td><i class="fa fa-home fa-lg"></i></td>
        </tr>
        <tr>
            <td>B03</td>
            <td><i class="fa fa-home fa-lg"></i></td>
            <td><i class="fa fa-home fa-lg"></i></td>
            <td><i class="fa fa-home fa-lg"></i></td>
            <td><i class="fa fa-home fa-lg"></i></td>
            <td><i class="fa fa-home fa-lg"></i></td>
            <td><i class="fa fa-home fa-lg"></i></td>
        </tr>
        <tr>
            <td>B03</td>
            <td><i class="fa fa-home fa-lg"></i></td>
            <td><i class="fa fa-home fa-lg"></i></td>
            <td><i class="fa fa-home fa-lg"></i></td>
            <td><i class="fa fa-home fa-lg"></i></td>
            <td><i class="fa fa-home fa-lg"></i></td>
            <td><i class="fa fa-home fa-lg"></i></td>
        </tr>
        </tbody>
    </table>


</div>
</body>
<script src="<%=Constant.server_name%>js-lib/layui/layui.js" charset="utf-8"></script>
<script src="<%=Constant.server_name%>module/fwzx/jwl/kjscx/js/kjscx.js" charset="utf-8"></script>
<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->
</html>

