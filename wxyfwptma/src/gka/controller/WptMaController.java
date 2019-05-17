package gka.controller;

import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import gka.controller.login.WptMaUserInfo;

/**
 * Created by Administrator on 2019/4/22 0022.
 */
@ControllerBind(controllerKey = "/")
public class WptMaController extends Controller {
    public void index() {
        WptMaUserInfo wptUserInfo = (WptMaUserInfo) getSession().getAttribute("wptUserInfo");
        if (wptUserInfo == null) {
            renderJsp("/login/login.jsp");
        } else {
            renderJsp("/main/main.jsp");
        }
    }
}
