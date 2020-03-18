<%@ page import="gka.resource.Constant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Expires" content="0">
    <title>后台管理</title>
    <link rel="stylesheet" type="text/css" href="<%=Constant.server_name%>css/login.css"/>
    <link rel="stylesheet" href="<%=Constant.server_name%>js-lib/layui-2.4.5/css/layui.css">
</head>
<body>
<div class="divcont">


    <div class="headtitle">
        <img src="<%=Constant.server_name%>img/headtitle.jpg"/>
        <a class="textout2" href="http://mh.jlsfjy.cn/portal.do">吉林司法门户网站</a>
        <a class="textout1" href="javascript:void(0)">院办电话：0431－83311051</a>

    </div>


    <div class="login_box">
        <div class="login_l_img"><img src="<%=Constant.server_name%>img/login-img.png"></div>
        <div class="login">
            <div class="login_logo"><img src="<%=Constant.server_name%>img/login_logo.png"></div>
            <div class="login_name">
                <p>缴费管理系统</p>
            </div>
            <form method="post">
                <ul>
                    <li>
                        <input type="text" name="account" id="account" placeholder="用户名" value="20183519">
                    </li>
                    <li>
                        <input type="text" name="password" id="password" placeholder="密码" value="123123.">
                    </li>
                    <li>
                        <input style="width: 45%;" type="text" placeholder="图片验证码" id="LAY-user-login-vercode"/>
                        <img id="mobileImage" src="" onclick="updateCaptcha();" style="width: 40%;margin-left: 10px"/>
                        <%--<img style="width: 30%;vertical-align: middle;" class="imgPassword" src="<%=Constant.server_name%>img/imgpassword.png"/>--%>
                    </li>
                    <li>
                        <input value="登录" style="width:100%;" type="button" id="login">
                    </li>
                </ul>
            </form>
        </div>
    </div>


    <div class="login-about-bottom">
        <div class="media">
            <img src="<%=Constant.server_name%>img/login-about-1.png"/>

            <div class="media-body">
                <h4 class="media-heading">吉林司法警官职业学院</h4>

                <p>诚、智、律、毅 ; 进取、勤奋、严谨、文明</p>
            </div>
        </div>


        <div class="media">
            <img src="<%=Constant.server_name%>img/login-about-2.png"/>

            <div class="media-body">
                <h4 class="media-heading">统一身份</h4>

                <p>学号/教工号、手机号、邮箱 一键登录一次登录，畅通IMUST</p>
            </div>
        </div>

        <div class="media">
            <img src="<%=Constant.server_name%>img/login-about-3.png"/>

            <div class="media-body">
                <h4 class="media-heading">应用 &amp; 服务</h4>

                <p>办公、邮件、教务、科研全方位应用选课、查询、通知、下载个性化服务</p>
            </div>
        </div>

    </div>
</div>
</body>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/jquery/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/layer/layer.js"></script>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/base.js"></script>
<script type="text/javascript" src="<%=Constant.server_name%>js-lib/wptma/xslogin.js"></script>
</html>