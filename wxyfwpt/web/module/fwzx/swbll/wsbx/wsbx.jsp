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
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/title.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/gzcx.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>js-lib/layui/css/layui.css"/>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>font-awesome-4.7.0/css/font-awesome.css"/>
    <title></title>

</head>
<body style="background: #f3f3f3;">
<jsp:include page="/common/auth.jsp"></jsp:include>
<%
    WptUserInfo wptUser = (WptUserInfo) session.getAttribute("wptUserInfo");
    if (wptUser == null) {
        wptUser = new WptUserInfo();
    }
%>
<div class="gzcx">
    <div class="titledddiv">
        <img class="fh-icon" src="<%=Constant.server_name%>img/fh-icon.png"
             onclick="javascript:window.location.replace(document.referrer)"/>

        <p class="titleName">报修</p>
    </div>

    <div class="divname">
        <img class="leftimg" src="<%=Constant.server_name%>img/bx-newicon.png"/>

        <div class="right">
            <p>姓名：<%=wptUser.getXm()%>
            </p>

            <p>学号：<%=wptUser.getZh()%>
            </p>
        </div>
    </div>
    <ul class="qjlist">
        <li>
            <div class="left bxleft">

                <span class="fa fa-podcast fa-2x"></span>
            </div>
            <div class="right">
                <input class="bxtext" type="text" id="" value="南山区 1号教学楼" readonly/>
            </div>
        </li>
        <li>
            <div class="left bxleft">

                <span class="fa fa-map-marker fa-2x"></span>
            </div>
            <div class="right">
                <input class="bxtext" type="text" id="" value="" placeholder="请输入报修地点 如101"/>
            </div>
        </li>

        <li>
            <div class="left  bxleft">
                <span class="fa fa-calendar-minus-o fa-2x"></span>
            </div>
            <div class="right">
                <input id="demo1" class="bxtext" placeholder="<预约上门时间>"/>
            </div>
        </li>

        <li>
            <div class="left  bxleft">
                <span class="fa fa-th-large fa-2x"></span>
            </div>
            <div class="right">
                <input class="bxtext" type="text" id="" value="" placeholder="<选择报修项目>"/>
                <%--<select class="bxselect" placeholder="" name="">--%>
                <%--<option value="">报修项目</option>--%>
                <%--</select>--%>
            </div>
        </li>

        <li>
            <div class="left  bxleft" style="vertical-align: top;">
                <span class="fa fa-comments fa-2x" style="line-height: 14px;"></span>
            </div>
            <div class="right">
                <textarea class='bxtextarea' placeholder="请一句话描述保修内容"></textarea>
            </div>
        </li>
    </ul>
    <a class="tijiao" href="javascript:void(0)">提交</a>
</div>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/jquery/jquery-3.3.1.min.js"></script>
<script src="<%=Constant.server_name%>js-lib/date/datePicker.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/layer/layer.js"></script>

<script>
    $(".tijiao").on("click", function () {
        layer.msg('功能建设中......', {shade: [0.2, '#393D49'], time: 2000});
    })

    $("#demo1").on("focus", function () {
        document.activeElement.blur();//屏蔽默认键盘弹出；
    });
    //    $form.on("focus","input[name=qrCode]",function(){
    //        document.activeElement.blur();//屏蔽默认键盘弹出；
    //    });

    var calendar = new datePicker();
    calendar.init({
        'trigger': '#demo1', /*按钮选择器，用于触发弹出插件*/
        'type': 'date', /*模式：date日期；datetime日期时间；time时间；ym年月；*/
        'minDate': '1900-1-1', /*最小日期*/
        'maxDate': '2100-12-31', /*最大日期*/
        'onSubmit': function () {/*确认时触发事件*/
            var theSelectData = calendar.value;
        },
        'onClose': function () {/*取消时触发事件*/
        }
    });
</script>
</body>

</html>





















