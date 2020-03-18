package gka.controller;

import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import gka.controller.login.WptMaUserInfo;
import gka.interceptor.LoginInterceptor;

/**
 * Created by Administrator on 2019/4/22 0022.
 */
@ControllerBind(controllerKey = "/")
@Clear({LoginInterceptor.class})
public class WptMaController extends Controller {
    public void index() {
        System.out.println("aaaaaaaaa");
        WptMaUserInfo wptUserInfo = (WptMaUserInfo) getSession().getAttribute("wptMaUserInfo");
        if (wptUserInfo == null) {
            renderJsp("/login/xslogin.jsp");
        } else {
            renderJsp("/main/xsindex.jsp");
        }
    }

    public void ls() {
        System.out.println("aaaaaaaaa");
        WptMaUserInfo wptUserInfo = (WptMaUserInfo) getSession().getAttribute("wptMaUserInfo");
        if (wptUserInfo == null) {
            renderJsp("/login/lslogin.jsp");
        } else {
            renderJsp("/main/lsindex.jsp");
        }
    }
}
