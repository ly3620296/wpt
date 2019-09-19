<%@ page import="gka.resource.Constant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/reset.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/title.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/zf.css"/>
    <script src="<%=Constant.server_name%>js-lib/base.js" type="text/javascript"
            charset="utf-8"></script>
    <title></title>
</head>
<body>
<jsp:include page="/common/auth.jsp"></jsp:include>
<div class="titledddiv">
    <img class="fh-icon" src="<%=Constant.server_name%>img/fh-icon.png"
         onclick="javascript:window.location.replace('/wpt/module/fwzx/fwzxapp.jsp')"/>

    <p class="titleName">学杂费</p>
</div>

<div class="list" id="sfxmList">

</div>

<div class="tanchudd" style="display: none" id="tanchuddId">
    <div class="cont list">
        <div class="tablediv" id="noPayOrderTable">
            <%--<p class="tablename">2016-2017年度</p>--%>
            <%--<table border="" cellspacing="" cellpadding="">--%>
            <%--<tbody>--%>
            <%--<tr>--%>
            <%--&lt;%&ndash;<th>选择</th>&ndash;%&gt;--%>
            <%--<th>费用名称</th>--%>
            <%--<th>应交金额</th>--%>
            <%--<th>已缴金额</th>--%>
            <%--<th>欠费金额</th>--%>
            <%--</tr>--%>
            <%--<tr id="checkId_2016-2017_4">--%>
            <%--&lt;%&ndash;<td>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<div style="cursor:pointer" id="checkJF_2016-2017_4" class="check bixuan active"&ndash;%&gt;--%>
            <%--&lt;%&ndash;value="1000"></div>&ndash;%&gt;--%>
            <%--&lt;%&ndash;</td>&ndash;%&gt;--%>
            <%--<td>教材费</td>--%>
            <%--<td>1000</td>--%>
            <%--<td>0</td>--%>
            <%--<td>1000</td>--%>
            <%--</tr>--%>
            <%--<tr id="checkId_2016-2017_3">--%>
            <%--&lt;%&ndash;<td>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<div style="cursor:pointer" id="_3" class="WJF" value="1300"></div>&ndash;%&gt;--%>
            <%--&lt;%&ndash;</td>&ndash;%&gt;--%>
            <%--<td>备品</td>--%>
            <%--<td>1300</td>--%>
            <%--<td>0</td>--%>
            <%--<td>1300</td>--%>
            <%--</tr>--%>
            <%--<tr id="checkId_2016-2017_2">--%>
            <%--&lt;%&ndash;<td>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<div style="cursor:pointer" id="_2" class="WJF" value="1200"></div>&ndash;%&gt;--%>
            <%--&lt;%&ndash;</td>&ndash;%&gt;--%>
            <%--<td>住宿费</td>--%>
            <%--<td>1200</td>--%>
            <%--<td>0</td>--%>
            <%--<td>1200</td>--%>
            <%--</tr>--%>
            <%--<tr id="checkId_2016-2017_1">--%>
            <%--&lt;%&ndash;<td>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<div style="cursor:pointer" id="_1" class="WJF" value="4000"></div>&ndash;%&gt;--%>
            <%--&lt;%&ndash;</td>&ndash;%&gt;--%>
            <%--<td>学费</td>--%>
            <%--<td>4000</td>--%>
            <%--<td>0</td>--%>
            <%--<td>4000</td>--%>
            <%--</tr>--%>
            <%--<tr id="checkId_2016-2017_5">--%>
            <%--&lt;%&ndash;<td>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<div style="cursor:pointer" id="checkJF_2016-2017_5" class="check bixuan active"&ndash;%&gt;--%>
            <%--&lt;%&ndash;value="55"></div>&ndash;%&gt;--%>
            <%--&lt;%&ndash;</td>&ndash;%&gt;--%>
            <%--<td>体检费</td>--%>
            <%--<td>55</td>--%>
            <%--<td>0</td>--%>
            <%--<td>55</td>--%>
            <%--</tr>--%>
            <%--</tbody>--%>
            <%--</table>--%>
            <%--<div class="bottom">--%>
            <%--<p id="checkJF_2016-2017_total" value="7555">合计：7555元</p>--%>
            <%--</div>--%>
        </div>

        <div class="foot">
            <a class="btnno" href="javascript:void(0)" id="closeOrder">关闭订单</a>
            <a class="btnyes" href="javascript:void(0)" id="finishOrder">继续支付</a>
        </div>
    </div>
</div>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/layui/layui.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=Constant.server_name%>module/fwzx/wstyzf/xzf/js/xzf.js" charset="utf-8"></script>

</body>
</html>
