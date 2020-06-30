<%@ page import="gka.resource.Constant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="<%=Constant.server_name%>js-lib/layui-v2.5.4/css/layui.css">
    <style>
        html {
            background-color: #fbfbfb !important;
            color: #333;
        }

        xm-select .scroll-body {
            padding-left: 10px !important;
            overflow: hidden !important;
        }

        xm-select .layui-transfer-data {
            overflow: hidden !important;
        }
    </style>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/jquery/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/layui-v2.5.4/layui.js"></script>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/base.js"></script>
</head>
<body>
<jsp:include page="/login/auth.jsp"></jsp:include>
<form class="layui-form" action="" lay-filter="example">
    <input type="text" name="ID" style="display: none">

    <div class="layui-form-item" style="margin-top:  5%">
        <label class="layui-form-label" style="width: 120px;">发票名称</label>

        <div class="layui-input-inline">
            <input type="text" name="FPMC" required lay-verify="required" placeholder="请输入发票名称"
                   autocomplete="off"
                   class="layui-input" style="width: 320px;">
        </div>
    </div>
    <div class="layui-form-item ">
        <label class="layui-form-label" style="width: 120px;">发票类型</label>

        <div class="layui-input-inline">
            <input type="radio" name="FPLX" value="1" title="通用" checked>
            <input type="radio" name="FPLX" value="2" title="往来">
        </div>
    </div>
    <div class="layui-form-item ">
        <label class="layui-form-label" style="width: 120px;">收费项目</label>

        <div class="layui-input-inline" id="sfxm" style="width:502px;">
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>

<script>
    layui.use(['transfer', 'util', 'form'], function () {
        var form = layui.form;
        var transfer = layui.transfer;

        //按钮事件
        var loadIndex;
        var id = getURLParameter("id");
        if (id == undefined || id == "" || id == null) {
            layer.alert('查询数据失败!', function (index) {
                parent.closeAll()
            });
            return false;
        }
        $.ajax({
            url: wpt_serverName + "fpgl/query",
            type: 'post',
            dataType: 'json',
            data: {id: id},
            timeout: 10000,
            beforeSend: function () {
                loadIndex = layer.load(0, {shade: [0.2, '#393D49']});
            },
            success: function (data) {
                if (data.RETURN_STATE == "SUCCESS") {
                    form.val('example', {
                        "ID": data.OUT_DATA.re.ID,
                        "FPMC": data.OUT_DATA.re.FPMC,
                        "FPLX": data.OUT_DATA.re.FPLX
                    })
                    form.render();

                    var dataTr = data.OUT_DATA.sfxm;
                    var valAr = data.OUT_DATA.re.XMID.split(",");
                    for (var i = 0; i < valAr.length; i++) {
                        for (var j = 0; j < dataTr.length; j++) {
                            if (dataTr[j].JFXMID == valAr[i]) {
                                dataTr[j].disabled = false;
                                break;
                            }
                        }
                    }

                    transfer.render({
                        elem: '#sfxm'
                        , id: 'sfxm_tr'
                        , data: dataTr
                        , title: ['收费项目', '已选收费项目']
                        , showSearch: true
                        , value: valAr
                        , onchange: function (data, index) {
                        }
                        , parseData: function (res) {
                            return {
                                "value": res.JFXMID //数据值
                                , "title": res.JFXMMC //数据标题
                                , "disabled": res.disabled  //是否禁用
                                , "checked": res.checked //是否选中
                            }
                        }
                    })

                } else {
                    layer.alert('数据查询失败!', function (index) {
                        window.parent.location.reload();
                    });
                }
            },
            complete: function () {
                layer.close(loadIndex);
            }
        })

        //监听提交
        form.on('submit(formDemo)', function (data) {
            var my_data = transfer.getData('sfxm_tr');
            if (my_data.length <= 0) {
                layer.msg("请填写收费项目", {anim: 6, time: 2000});
                return false;
            }
            var my_chek = "";
            var my_chek_mc = "";
            for (var i = 0; i < my_data.length; i++) {
                if (i < my_data.length - 1) {
                    my_chek += my_data[i].value + ",";
                    my_chek_mc += my_data[i].title + "，";
                } else {
                    my_chek += my_data[i].value;
                    my_chek_mc += my_data[i].title;
                }
            }
            data.field.SFXM = my_chek;
            data.field.XMMC = encodeURIComponent(my_chek_mc);

            $.ajax({
                url: wpt_serverName + "fpgl/edit",
                type: 'post',
                dataType: 'json',
                data: data.field,
                timeout: 10000,
                beforeSend: function () {
                    loadIndex = layer.load(0, {shade: [0.2, '#393D49']});
                },
                success: function (data) {
                    if (data.RETURN_STATE == "SUCCESS") {
                        layer.alert('修改成功', function (index) {
                            window.parent.location.reload();
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
    });
</script>
</body>
</html>