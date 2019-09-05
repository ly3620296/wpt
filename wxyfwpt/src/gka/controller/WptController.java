package gka.controller;

import com.alibaba.druid.util.StringUtils;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Record;
import gka.auth2.AccessToken;
import gka.controller.login.WptUserInfo;
import gka.resource.Constant;
import gka.system.ReturnInfo;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Clear
@ControllerBind(controllerKey = "/")
public class WptController extends Controller {

    private WptDao wptDao = new WptDao();

    public void index() {
        WptUserInfo wptUserInfo = (WptUserInfo) getSession().getAttribute("wptUserInfo");
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%");
        if (wptUserInfo == null) {
            renderJsp("/WEB-INF/index.jsp");
        } else {
            renderJsp("/module/main/main.jsp");
        }
    }

    public void auth() {
        String code = getPara("code");
        WptUserInfo wptUserInfo = (WptUserInfo) getSession().getAttribute("wptUserInfo");
        if (wptUserInfo != null) {
            renderJsp("/module/main/main.jsp");
            return;
        }

        if (StringUtils.isEmpty(code)) {
            renderJsp("/error.jsp");
            return;
        }

        String openId = AccessToken.getOpenId(code);

        if (openId == null) {
            renderJsp("/error.jsp");
            return;
        }

        boolean isBind = wptDao.isBindOpenId(openId);

        if (!isBind) {
            //未绑定过
            getSession().setAttribute("bindOpenId", openId);
            getSession().setAttribute("channel", "wx");
            redirect("/");
        } else {
            try {
                Record re = wptDao.loginByOpenId(openId);
                CommonUtil.setSession(re, getSession());
                getSession().setAttribute("channel", "wx");
                renderJsp("/module/main/main.jsp");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void unbind() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            String channel = (String) getSession().getAttribute("channel");
            if (channel != null && "wx".equals(channel)) {
                WptUserInfo wptUserInfo = (WptUserInfo) getSession().getAttribute("wptUserInfo");
                String zh = wptUserInfo.getZh();
                String currOpenId = (String) getSession().getAttribute("bindOpenId");
                if (currOpenId == null) {
                    wptDao.unbind(zh);
                    getSession().setAttribute("bindOpenId", wptUserInfo.getOpenId());
                    wptUserInfo.setOpenId("");
                    returnInfo.setReturn_code("0");
                    returnInfo.setReturn_msg("success");
                } else {
                    returnInfo.setReturn_code("-1");
                    returnInfo.setReturn_msg("非绑定账号微信禁止解绑，请用原微信解除绑定！");
                }
            } else {
                returnInfo.setReturn_code("-2");
                returnInfo.setReturn_msg("非微信渠道，禁止解绑！");
            }

        } catch (Exception e) {
            returnInfo.setReturn_code("-999");
            returnInfo.setReturn_msg("服务繁忙，请稍后重试！");
            e.printStackTrace();
        }
        result.put("returnInfo", returnInfo);
        renderJson(result);
    }

    public void bind() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            String channel = (String) getSession().getAttribute("channel");
            if (channel != null && "wx".equals(channel)) {
                WptUserInfo wptUserInfo = (WptUserInfo) getSession().getAttribute("wptUserInfo");
                String zh = wptUserInfo.getZh();
                String bindOpenId = (String) getSession().getAttribute("bindOpenId");
                if (!StringUtils.isEmpty(bindOpenId)) {
                    wptDao.bindOpenId(bindOpenId, zh);
                    wptUserInfo.setOpenId(bindOpenId);
                    getSession().removeAttribute("bindOpenId");
                    returnInfo.setReturn_code("0");
                    returnInfo.setReturn_msg("success");
                } else {
                    returnInfo.setReturn_code("-1");
                    returnInfo.setReturn_msg("请从微信端登录!");
                }
            } else {
                returnInfo.setReturn_code("-2");
                returnInfo.setReturn_msg("非微信渠道，禁止解绑！");
            }
        } catch (Exception e) {
            returnInfo.setReturn_code("-999");
            returnInfo.setReturn_msg("服务繁忙，请稍后重试！");
            e.printStackTrace();
        }
        result.put("returnInfo", returnInfo);
        renderJson(result);
    }

}
