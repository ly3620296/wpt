<%@ page import="gka.resource.Constant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
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
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/layui-v2.5.4/layui.js"></script>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/layui-v2.5.4/plugin/xm-select.js"></script>
    <script type="text/javascript" src="<%=Constant.server_name%>js-lib/base.js"></script>
</head>
<body>
<jsp:include page="/login/auth.jsp"></jsp:include>

<form class="layui-form" action="">
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
</div>
<script>
    var data1 = [
        {"value": "1", "title": "李白"}
        , {"value": "2", "title": "杜甫"}
        , {"value": "3", "title": "苏轼"}
        , {"value": "4", "title": "李清照"}
        , {"value": "5", "title": "鲁迅", "disabled": true}
        , {"value": "6", "title": "巴金"}
        , {"value": "7", "title": "冰心"}
        , {"value": "8", "title": "矛盾"}
        , {"value": "9", "title": "贤心"}
    ]
    layui.use(['transfer', 'form', 'element', 'laydate'], function () {
        var $ = layui.jquery
                , transfer = layui.transfer
                , layer = layui.layer
                , form = layui.form
                , element = layui.element;
        var loadIndex;


        $.ajax({
            url: wpt_serverName + "fpgl/sfxmList",
            type: 'post',
            dataType: 'json',
            timeout: 10000,
            beforeSend: function () {
                loadIndex = layer.load(0, {shade: [0.2, '#393D49']});
            },
            success: function (data) {
                transfer.render({
                    elem: '#sfxm'
                    , id: 'sfxm_tr'
                    , data: data
                    , title: ['收费项目', '已选收费项目']
                    , showSearch: true
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
                url: wpt_serverName + "fpgl/add",
                type: 'post',
                dataType: 'json',
                data: data.field,
                timeout: 10000,
                beforeSend: function () {
                    loadIndex = layer.load(0, {shade: [0.2, '#393D49']});
                },
                success: function (data) {
                    if (data.RETURN_STATE == "SUCCESS") {
                        layer.alert('添加成功!', function (index) {
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