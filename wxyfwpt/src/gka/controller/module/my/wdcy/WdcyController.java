package gka.controller.module.my.wdcy;

import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Record;
import gka.controller.login.WptUserInfo;
import gka.system.ReturnInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 成绩查询
 */

@ControllerBind(controllerKey = "/my/wdcy")
public class WdcyController extends Controller {
    private WdcyDao wdcyDao = new WdcyDao();

    public void index() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            WptUserInfo wptUserInfo = (WptUserInfo) getSession().getAttribute("wptUserInfo");
            String xh = wptUserInfo.getZh();
            String qx = wptUserInfo.getJsdm();
            List allMenu = wdcyDao.allMenu(qx);
            List myMenu = wdcyDao.myMenu(xh);
            result.put("allMenu", allMenu);
            result.put("myMenu", myMenu);
            returnInfo.setReturn_code("0");
            returnInfo.setReturn_msg("success");
        } catch (Exception e) {
            returnInfo.setReturn_code("-999");
            returnInfo.setReturn_msg("服务繁忙，请稍后重试！");
            e.printStackTrace();
        }
        result.put("returnInfo", returnInfo);
        renderJson(result);
    }

    public void addMyMenu() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            WptUserInfo wptUserInfo = (WptUserInfo) getSession().getAttribute("wptUserInfo");
            String xh = wptUserInfo.getZh();
            String menuId = getPara("id");
            Record rs = wdcyDao.checkMyMenu(xh, menuId);
            if (rs == null) {
                List list = wdcyDao.checkMyMenuSum(xh);
                if (list.size() < 9) {
                    Boolean bool = wdcyDao.addMyMenu(xh, menuId);
                    result.put("bool", bool);
                    returnInfo.setReturn_code("0");
                    returnInfo.setReturn_msg("success");
                } else {
                    returnInfo.setReturn_code("0002");
                    returnInfo.setReturn_msg("每位用户只能设置9个常用菜单!");
                }
            } else {
                returnInfo.setReturn_code("0001");
                returnInfo.setReturn_msg("此菜单已经被您设置成常用了!");
            }
        } catch (Exception e) {
            returnInfo.setReturn_code("-999");
            returnInfo.setReturn_msg("服务繁忙，请稍后重试！");
            e.printStackTrace();
        }
        result.put("returnInfo", returnInfo);
        renderJson(result);

    }

    public void reMyMenu() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {

            WptUserInfo wptUserInfo = (WptUserInfo) getSession().getAttribute("wptUserInfo");
            String xh = wptUserInfo.getZh();
            String menuId = getPara("id");
            List list = wdcyDao.checkMyMenuSum(xh);
            if (list.size() <= 1) {
                returnInfo.setReturn_code("0001");
                returnInfo.setReturn_msg("常用按钮至少要保留一个菜单!");
            } else {
                wdcyDao.reMyMenu(xh, menuId);
                returnInfo.setReturn_code("0");
                returnInfo.setReturn_msg("success");
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
