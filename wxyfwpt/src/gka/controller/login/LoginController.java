package gka.controller.login;

import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import gka.system.ReturnInfo;

/**
 * @Auther ly
 * @Date 2019/4/19
 * @Describe
 */
@ControllerBind(controllerKey = "/login")
public class LoginController extends Controller {
    private LoginDao loginDao = new LoginDao();

    public void loginValidate() {
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            String account = getPara("account");
            String password = getPara("password");
            boolean loginValidate = loginDao.loginValidate(account, password);
            if (loginValidate) {
                returnInfo.setReturn_code("0");
                returnInfo.setReturn_msg("success");
            } else {
                returnInfo.setReturn_code("-1");
                returnInfo.setReturn_msg("用户名或密码错误");
            }

        } catch (Exception e) {
            e.printStackTrace();
            returnInfo.setReturn_code("-999");
            returnInfo.setReturn_msg("系统异常，请稍后重试");
        }
        renderJson(returnInfo);

    }
}
