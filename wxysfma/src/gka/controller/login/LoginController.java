package gka.controller.login;

import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import gka.interceptor.LoginInterceptor;
import gka.system.ReturnInfo;

import java.util.List;

/**
 * @Auther ly
 * @Date 2019/4/19
 * @Describe
 */
@Clear({LoginInterceptor.class})
@ControllerBind(controllerKey = "/login")
public class LoginController extends Controller {
    private LoginDao loginDao = new LoginDao();
    @Before(LoginValidator.class)
    public void loginValidate() {
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            String account = getPara("account");
            String password = getPara("password");
            Record record = loginDao.loginValidate(account, password);
            if (record != null) {
                setSession(record);
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
            getSession().removeAttribute("wptMaUserInfo");
            returnInfo.setReturn_code("0");
            returnInfo.setReturn_msg("success");
        } catch (Exception e) {
            e.printStackTrace();
            returnInfo.setReturn_code("-999");
            returnInfo.setReturn_msg("系统异常，请稍后重试");
        }
        renderJson(returnInfo);
    }
    private void setSession(Record record) {
        WptMaUserInfo userInfo = new WptMaUserInfo();
        userInfo.setM_id(record.getStr("m_id") == null ? "" : record.getStr("m_id"));
        userInfo.setM_name(record.getStr("m_name") == null ? "" : record.getStr("m_name"));
        userInfo.setM_zh(record.getStr("m_zh") == null ? "" : record.getStr("m_zh"));
        userInfo.setM_mm(record.getStr("m_mm") == null ? "" : record.getStr("m_mm"));
        userInfo.setM_qx(record.getStr("m_qx") == null ? "" : record.getStr("m_qx"));
        userInfo.setMenu(LoginDao.getMenuTree(userInfo.getM_id()));
        getSession().setAttribute("wptMaUserInfo", userInfo);
    }
    @Clear
    public void captcha() {
        renderCaptcha();
    }
}
