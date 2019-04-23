package gka.controller;

import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import gka.controller.login.WptUserInfo;
import gka.resource.Constant;

/**
 * Created by Administrator on 2019/4/22 0022.
 */
@ControllerBind(controllerKey = "/")
public class WptController extends Controller {
    public void index() {
        WptUserInfo wptUserInfo = (WptUserInfo) getSession().getAttribute("wptUserInfo");
        if (wptUserInfo == null) {
            renderJsp("/WEB-INF/index.jsp");
        } else {
            renderJsp("/module/main/main.jsp");
        }
    }
}
