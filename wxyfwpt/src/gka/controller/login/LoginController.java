package gka.controller.login;

import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Record;
import gka.controller.CommonDao;
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
            Record record = loginDao.loginValidate(account, password);
            if (record != null) {
                setSession(record);
                getSession().setAttribute("currXnxq", CommonDao.currXnxq());
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

    private void setSession(Record record) {
        WptUserInfo userInfo = new WptUserInfo();
        userInfo.setZh(record.getStr("ZH"));
        userInfo.setXm(record.getStr("XM"));
        userInfo.setXb(record.getStr("XB"));
        userInfo.setMz(record.getStr("MZ"));
        userInfo.setZzmm(record.getStr("ZZMM"));
        userInfo.setCsrq(record.getStr("CSRQ"));
        userInfo.setZjlx(record.getStr("ZJLX"));
        userInfo.setZjhm(record.getStr("ZJHM"));
        userInfo.setJgdm(record.getStr("JGDM"));
        userInfo.setJgmc(record.getStr("JGMC"));
        userInfo.setZydm(record.getStr("ZYDM"));
        userInfo.setZymc(record.getStr("ZYMC"));
        userInfo.setBjdm(record.getStr("BJDM"));
        userInfo.setBjmc(record.getStr("BJMC"));
        userInfo.setNjdm(record.getStr("NJDM"));
        userInfo.setNjmc(record.getStr("NJMC"));
        userInfo.setXz(record.getStr("XZ"));
        userInfo.setSfzx(record.getStr("SFZX"));
        userInfo.setXjzt(record.getStr("XJZT"));
        userInfo.setBdzc(record.getStr("BDZC"));
        userInfo.setLxdh(record.getStr("LXDH"));
        userInfo.setYx(record.getStr(" YX"));
        userInfo.setJsdm(record.getStr("JSDM"));
        userInfo.setJsmc(record.getStr("JSMC"));
        userInfo.setZyfxdm(record.getStr("ZYFXDM"));
        userInfo.setZyfxmc(record.getStr("ZYFXMC"));
        getSession().setAttribute("wptUserInfo", userInfo);
    }
}
