package gka.controller.module.jwl.xkqkcx;

import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Record;
import gka.controller.login.WptUserInfo;
import gka.system.ReturnInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 选课情况查询
 */

@ControllerBind(controllerKey = "/jwl/xkqkcx")
public class XkqkcxController extends Controller {
    private XkqkcxjDao xkqkcxjDao = new XkqkcxjDao();

    /**
     * 选课情况查询页初始化
     */
    public void index() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            WptUserInfo wptUserInfo = (WptUserInfo) getSession().getAttribute("wptUserInfo");
            String jgh = wptUserInfo.getZh();
            //当前学年学期
            String currXnxq = (String) getSession().getAttribute("currXnxq");
            //当前学年学期课表
            List<Record> xkqkcxList = xkqkcxjDao.xkqkcx(jgh, currXnxq);
            //所有学年学期
            List<Record> xnxqList = xkqkcxjDao.xnxq(jgh);
            result.put("xkqkcxList", xkqkcxList);
            result.put("xnxqList", xnxqList);
            result.put("currXnxq", currXnxq);
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

    /**
     * 根据学年学期查询对应课表
     */
    public void xkqkcxByXnxq() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            WptUserInfo wptUserInfo = (WptUserInfo) getSession().getAttribute("wptUserInfo");
            String jgh = wptUserInfo.getZh();
            //学年学期
            String currXnxq = getRequest().getParameter("currXnxq");
            if (currXnxq != null && !"".equals(currXnxq)) {
                //当前学年学期所有成绩
                List<Record> xkqkcxList = xkqkcxjDao.xkqkcx(jgh, currXnxq);
                result.put("xkqkcxList", xkqkcxList);
                returnInfo.setReturn_code("0");
                returnInfo.setReturn_msg("success");
            } else {
                returnInfo.setReturn_code("-1");
                returnInfo.setReturn_msg("请选择合法的学年学期!");
            }
        } catch (Exception e) {
            returnInfo.setReturn_code("-999");
            returnInfo.setReturn_msg("服务繁忙，请稍后重试！");
            e.printStackTrace();
        }
        result.put("returnInfo", returnInfo);
        renderJson(result);

    }

    /**
     * 该课程所有选课的学生
     */
    public void xkxs() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            String jxb_id = getPara("jxb_id");
            List<Record> xkxsList = xkqkcxjDao.xkxs(jxb_id);
            result.put("xkxsList", xkxsList);
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
