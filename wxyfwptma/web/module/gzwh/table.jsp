<%@ page import="gka.resource.Constant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="<%=Constant.server_name%>js-lib/layui-2.4.5/css/layui.css">
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/jquery/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/layui-2.4.5/layui.js"></script>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/base.js"></script>
    <style type="text/css">
        .tablelist {
            width: 100%;
        }

        .tablelist td {
            font-size: 14px;
            text-align: center;
            padding: 8px 0;
            background: #ffffff;
            border: 1px solid #e4eff5;
        }

        .tablelist tr th {
            padding: 8px 0;
            font-size: 14px;
            text-align: center;
            color: #ffffff;
            background: #009688;
            border: 1px solid #fff;
        }
    </style>
</head>
<body>

<table class="tablelist" border="1" cellspacing="1" cellpadding="1" id="table">

</table>
<script type="application/javascript">
    layui.use(['tree', 'util', 'form'], function () {
        var loadIndex;
        var id = getURLParameter("id");
        if (id == undefined || id == "" || id == null) {
            layer.alert('未获取到ID!', function (index) {
                parent.closeAll()
            });
            return false;
        }
        $.ajax({
            url: wpt_serverName + "gzwh/table",
            type: 'post',
            dataType: 'json',
            data: {id: id},
            timeout: 10000,
            beforeSend: function () {
                loadIndex = layer.load(0, {shade: [0.2, '#393D49']});
            },
            success: function (data) {
                if (data.RETURN_STATE == "SUCCESS") {
                    var headList = data.OUT_DATA.headList;
                    var bodyList = data.OUT_DATA.bodyList;
                    var html = '<tr><th>工号</th>';
                    if (headList != null) {
                        for (var i = 0; i < headList.length; i++) {
                            var zdms = headList[i].ZDMS == null ? "" : headList[i].ZDMS
                            html += '<th>' + zdms + '</th>'
                        }
                        html += '</tr>';
                    } else {
                        layer.alert('工资表头数据查询失败!', function (index) {
                            parent.closeAll()
                        });
                    }
                    if (bodyList != null) {
                        for (var i = 0; i < bodyList.length; i++) {
                            html += '<tr><td>' + bodyList[i].YHM + '</td>'
//                            html += '<td>' + bodyList[i].XM + '</td>'
                            for (var a = 0; a < headList.length; a++) {
                                var body = bodyList[i]
                                html += '<td>' + (body[(headList[a].ZD).toUpperCase()] == null ? "" : body[(headList[a].ZD).toUpperCase()]) + '</td>'
                            }
                            html += '</tr>';
                        }
                    } else {
                        layer.alert('员工数据查询失败!', function (index) {
                            parent.closeAll()
                        });
                    }
                    $("#table").html(html)
                } else {
                    layer.alert(data.RETURN_MSG, function (index) {
                        parent.closeAll()
                    });
                }
            },
            complete: function () {
                layer.close(loadIndex);
            }
        })
    });

    function getURLParameter(name) {
        return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search) || [, ""])[1].replace(/\+/g, '%20')) || null;
    }
</script>
</body>
</html>