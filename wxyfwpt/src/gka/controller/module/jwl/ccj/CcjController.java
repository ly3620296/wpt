package gka.controller.module.jwl.ccj;

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

@ControllerBind(controllerKey = "/jwl/ccj")
public class CcjController extends Controller {
    private gka.controller.module.jwl.ccj.ccjDao ccjDao = new ccjDao();

    /**
     * 成绩页初始化
     */
    public void index() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            WptUserInfo wptUserInfo = (WptUserInfo) getSession().getAttribute("wptUserInfo");
            String xh = wptUserInfo.getZh();
            //当前学年学期
            String currXnxq = (String) getSession().getAttribute("currXnxq");
            //当前学年学期所有成绩
            List<Record> cjList = ccjDao.ccj(xh, currXnxq);
            //该学生所有学年学期
            List<Record> xnxqList = ccjDao.xnxq(xh);
            result.put("cjList", cjList);
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
     * 根据学年学期查询对应成绩
     */
    public void ccjByXnxq() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            WptUserInfo wptUserInfo = (WptUserInfo) getSession().getAttribute("wptUserInfo");
            String xh = wptUserInfo.getZh();
            //学年学期
            String currXnxq = getRequest().getParameter("currXnxq");
            if (currXnxq != null && !"".equals(currXnxq)) {
                //当前学年学期所有成绩
                List<Record> cjList = ccjDao.ccj(xh, currXnxq);
                result.put("cjList", cjList);
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
}
