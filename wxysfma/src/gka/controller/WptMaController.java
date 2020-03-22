package gka.controller;

import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import gka.controller.login.WptMaUserInfo;
import gka.interceptor.LoginInterceptor;
import gka.lsjfgl.login.WptMaLSUserInfo;
import gka.xsjfgl.login.WptMaXSUserInfo;

/**
 * Created by Administrator on 2019/4/22 0022.
 */
@ControllerBind(controllerKey = "/")
public class WptMaController extends Controller {
    public void index() {
        WptMaXSUserInfo wptMaXSUserInfo = (WptMaXSUserInfo) getSession().getAttribute("wptMaXSUserInfo");
        if (wptMaXSUserInfo == null) {
            renderJsp("/login/xslogin.jsp");
        } else {
            renderJsp("/main/xsindex.jsp");
        }
    }

    public void ls() {
        WptMaLSUserInfo wptMaLSUserInfo = (WptMaLSUserInfo) getSession().getAttribute("wptMaLSUserInfo");
        if (wptMaLSUserInfo == null) {
            renderJsp("/login/lslogin.jsp");
        } else {
            renderJsp("/main/lsindex.jsp");
        }
    }
}
