<%@ page import="gka.resource.Constant" %>
<%@ page import="gka.controller.login.WptUserInfo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/reset.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/app.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/seclect.css"/>
    <script src="<%=Constant.server_name%>js-lib/jquery/jquery-3.3.1.min.js" type="text/javascript"
            charset="utf-8"></script>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/layer/layer.js"></script>
    <script src="<%=Constant.server_name%>js-lib/base.js" type="text/javascript"
            charset="utf-8"></script>
    <title></title>

</head>
<body style="background:#f3f3f3">
<jsp:include page="/common/auth.jsp"></jsp:include>
<%
    WptUserInfo wptUser = (WptUserInfo) session.getAttribute("wptUserInfo");
    if (wptUser == null) {
        wptUser = new WptUserInfo();
    }
%>
<div class="ccj">
    <div class="titledddiv">
        <p class="titleName">选课情况查询</p>
    </div>

    <%--<div class="titleccj" id="ttt">--%>
    <%--<img class="img" src="<%=Constant.server_name%>img/ccd-top.jpg"/>--%>

    <%--&lt;%&ndash;<p><span>姓名：<%=wptUser.getXm()%></span> <span style="margin-left: 5%;">教工号：<%=wptUser.getZh()%></span></p>&ndash;%&gt;--%>
    <%--</div>--%>
    <table border="0" cellspacing="" cellpadding="">
        <thead>
        <tr>
            <th colspan="2">学号</th>
            <th>姓名</th>
            <th>班级</th>
            <th>重修标记</th>
            <th colspan="2">联系电话</th>
        </tr>
        </thead>
        <tbody id="wpt_cj_table">

        </tbody>
    </table>
</div>

</body>
<%
    String jxb_id = request.getParameter("jxb_id");
%>
<script>
    $(function () {
        var xkqkcx_loadIndex;
        $.ajax({
            url: wpt_serverName + "jwl/xkqkcx/xkxs",
            type: 'post',
            dataType: 'json',
            data: {jxb_id: "<%=jxb_id%>"},
            timeout: 10000,
            beforeSend: function () {
                xkqkcx_loadIndex = layer.load(0, {shade: [0.2, '#393D49']})
            },
            success: function (data) {
                if (data) {
                    var code = data.returnInfo.return_code;
                    var msg = data.returnInfo.return_msg;
                    if (code == "0") {
                        //加载成绩
                        initXsxs(data.xkxsList);
                    } else {
                        layer.msg(msg, {anim: 6, time: 2000});
                    }
                }
            }
            ,
            complete: function () {
                layer.close(xkqkcx_loadIndex);
            }
        })
    })

    function initXsxs(xkxsList) {
        var xkxss = "";
        if (xkxsList) {
            for (var index in xkxsList) {
                var xkxs = xkxsList[index];
                var lxdh = xkxs.LXDH || '';
                var cxbj = xkxs.CJXZ == 1 ? "重修" : "";
                xkxss += '<tr>' +
                '<td class="cjj_td"></td>' +
                '<td>' + xkxs.XH + '</td>' +
                '<td>' + xkxs.XM + '</td>' +
                '<td>' + xkxs.BJMC + '</td>' +
                '<td>' + cxbj + '</td>' +
                '<td>' + lxdh + '</td>' +
                '<td class="cjj_td"></td>' +
                '</tr>';
            }
            $("#wpt_cj_table").html(xkxss);

        }
    }
</script>
</html>

