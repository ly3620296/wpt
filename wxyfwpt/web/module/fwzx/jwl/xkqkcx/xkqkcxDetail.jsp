<%@ page import="gka.resource.Constant" %>
<%@ page import="java.net.URLDecoder" %>
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
    <script src="<%=Constant.server_name%>js-lib/base.js" type="text/javascript"
            charset="utf-8"></script>
    <title></title>

</head>
<body style="background:#f3f3f3">
<jsp:include page="/common/auth.jsp"></jsp:include>
<%
    String jxb_id = request.getParameter("jxb_id");
    String jxb_mc = URLDecoder.decode(request.getParameter("jxbmc"), "UTF-8");
%>
<div class="ccj">


    <div class="titledddiv">
        <img class="fh-icon" src="<%=Constant.server_name%>img/fh-icon.png"
             onclick="javascript:window.location.replace(document.referrer)"/>

        <p class="titleName">
            <%=jxb_mc%>
        </p>
    </div>

    <div class="titleccj" id="ttt">
    </div>
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
<script src="<%=Constant.server_name%>js-lib/layui/layui.js" charset="utf-8"></script>
<script>
    layui.use('form', function () {
        var form = layui.form,
                $ = layui.jquery,
                xkqkcx_loadIndex;
        var _xkqkcx = {
            initXk: function () {
                $.ajax({
                    url: wpt_serverName + "jwl/xkqkcx/xkxs",
                    type: 'post',
                    dataType: 'json',
                    data: {jxb_id: "<%=jxb_id%>"},
                    timeout: 10000,
                    beforeSend: function () {
                        layer.ready(function () {
                            xkqkcx_loadIndex = layer.load(0, {shade: [0.2, '#393D49']})
                        })

                    },
                    success: function (data) {
                        if (data) {
                            var code = data.returnInfo.return_code;
                            var msg = data.returnInfo.return_msg;
                            if (code == "0") {
                                //加载成绩
                                _xkqkcx.initXsxs(data.xkxsList);
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
            },
            initXsxs: function (xkxsList) {
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
        }
        _xkqkcx.initXk();
    })
</script>
</html>

