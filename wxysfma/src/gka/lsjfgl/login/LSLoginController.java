package gka.lsjfgl.login;

import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Record;
import gka.interceptor.LoginInterceptor;
import gka.system.ReturnInfo;

/**
 * @Auther ly
 * @Date 2019/4/19
 * @Describe
 */
@ControllerBind(controllerKey = "/LSlogin")
public class LSLoginController extends Controller {
    private LSLoginDao LxloginDao = new LSLoginDao();
    @Before(LSLoginValidator.class)
    public void loginValidate() {
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            String account = getPara("account");
            String password = getPara("password");
            Record record = LxloginDao.loginValidate(account, password);
            if (record != null) {
                LxloginDao.setSession(record, getSession());
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

    public void loginOut() {
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            getSession().removeAttribute("wptMaLSUserInfo");
            returnInfo.setReturn_code("0");
            returnInfo.setReturn_msg("success");
        } catch (Exception e) {
            e.printStackTrace();
            returnInfo.setReturn_code("-999");
            returnInfo.setReturn_msg("系统异常，请稍后重试");
        }
        renderJson(returnInfo);
    }
    @Clear
    public void captcha() {
        renderCaptcha();
    }
}
