package gka.controller.module.yktlcx;

import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Record;
import gka.controller.login.WptUserInfo;
import gka.system.ReturnInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 一卡通查询
 */

@ControllerBind(controllerKey = "/yktlcx/xfjl")
public class XfjlController extends Controller {
    private XfjlDao xfjl = new XfjlDao();

    /**
     * 一卡通查询
     */
    public void index() {

        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            WptUserInfo wptUserInfo = (WptUserInfo) getSession().getAttribute("wptUserInfo");
            String zjhm = wptUserInfo.getZjhm();
            //当前学年学期所有成绩1
            List<Record> xfjLList = xfjl.xfjl(zjhm);
            result.put("xfjLList", xfjLList);
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

}
