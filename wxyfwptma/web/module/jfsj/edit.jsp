<%@ page import="gka.resource.Constant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="<%=Constant.server_name%>js-lib/layui-2.4.5/css/layui.css">
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/jquery/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/layui-2.4.5/layui.js"></script>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/base.js"></script>
</head>
<body>
<jsp:include page="/login/auth.jsp"></jsp:include>
<form class="layui-form" action="" lay-filter="example" style="margin-top: 50px">
    <div class="layui-inline">
        <label class="layui-form-label">缴费时间：</label>

        <div class="layui-input-inline" style="width: 90px !important;margin-right: 10px!important;">
            <input type="text" name="title" placeholder="开始时间" autocomplete="off" class="layui-input"
                   name="dateStart" id="dateStart">
        </div>
        <div class="layui-input-inline" style="width: 10px!important; margin-right: 10px;">
            至
        </div>
        <div class="layui-input-inline" style="width: 90px !important;">
            <input type="text" name="title" placeholder="结束时间" autocomplete="off" class="layui-input"
                   id="dateEnd" name="dateEnd">
        </div>
    </div>
    <div class="layui-form-item" style="margin-top: 35px">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>

<script>
    layui.use(['form', 'layer', 'table', 'laydate'], function () {
        var jQuery = layui.jquery
                , form = layui.form
                , layer = layui.layer
                , table = layui.table
                , laydate = layui.laydate
                , $ = layui.jquery

        var loadIndex;
        form.on('submit(formDemo)', function (data) {
            var dateStart = $("#dateStart").val()
            var dateEnd = $("#dateEnd").val()
            if (dateStart == "") {
                layer.msg("请选择开始时间", {anim: 6, time: 2000});
                return false
            }
            if (dateEnd == "") {
                layer.msg("请选择结束时间", {anim: 6, time: 2000});
                return false
            }
            $.ajax({
                url: wpt_serverName + "jfsj/edit",
                type: 'post',
                dataType: 'json',
                data: {"dateStart":dateStart,"dateEnd":dateEnd},
                timeout: 10000,
                beforeSend: function () {
                    loadIndex = layer.load(0, {shade: [0.2, '#393D49']});
                },
                success: function (data) {
                    if (data.RETURN_STATE == "SUCCESS") {
                        layer.alert('修改成功', function (index) {
                            parent.closeAll()
                        });
                    } else {
                        layer.msg(data.RETURN_MSG, {anim: 6, time: 2000});
                    }
                },
                complete: function () {
                    layer.close(loadIndex);
                }
            })
            return false
        });
        var wpt_grjfxx = {
            query: function () {
                $.ajax({
                    url: wpt_serverName + "jfsj/query",
                    type: 'post',
                    dataType: 'json',
                    data: {},
                    timeout: 10000,
                    beforeSend: function () {
                        loadIndex = layer.load(0, {shade: [0.2, '#393D49']});
                    },
                    success: function (data) {
                        if (data.RETURN_STATE == "SUCCESS") {
                            laydate.render({
                                elem: '#dateStart'
                                , value: data.OUT_DATA.START_TIME // "name": "value"
                                , isInitValue: true //是否允许填充初始值，默认为 true
                            });
                            laydate.render({
                                elem: '#dateEnd'
                                , value: data.OUT_DATA.END_TIME // "name": "value"
                                , isInitValue: true //是否允许填充初始值，默认为 true
                            });
//                            form.render()
                        } else {
                            layer.alert('数据查询失败!', function (index) {
                                parent.closeAll()
                            });
                        }
                    },
                    complete: function () {
                        layer.close(loadIndex);
                    }
                })
            },
            initDate: function () {
                //初始化开始日期控件
                var start = laydate.render({
                    elem: '#dateStart',
                    done: function (value, date, endDate) {
                        end.config.min = {
                            year: date.year,
                            month: date.month - 1,
                            date: date.date
                        }; //重置结束日期最小值
                    }
                });
                //初始化结束日期控件
                var end = laydate.render({
                    elem: '#dateEnd',
                    done: function (value, date, endDate) {
                        start.config.max = {
                            year: date.year,
                            month: date.month - 1,
                            date: date.date
                        };
                    }
                });
            }
        }
        wpt_grjfxx.initDate();
        wpt_grjfxx.query();
    });
</script>
</body>
</html>