package gka.controller.module.jwl.jxjh;

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

@ControllerBind(controllerKey = "/jwl/jxjh")
public class JxjhController extends Controller {
    private JxjhDao jxjhDao = new JxjhDao();

    /**
     * 教学计划页初始化
     */
    public void index() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            WptUserInfo wptUserInfo = (WptUserInfo) getSession().getAttribute("wptUserInfo");
            String nj = wptUserInfo.getNjdm();
            String zydm = wptUserInfo.getZydm();
            String zyfxdm = wptUserInfo.getZyfxdm();
            String xh = wptUserInfo.getZh();
            //当前学年学期
            String currXnxq = (String) getSession().getAttribute("currXnxq");
            //当前学年学期所有教学计划
            List<Record> jxjhList = jxjhDao.jxjh(nj, zydm, zyfxdm, currXnxq);
            //该学生所有学年学期
            List<Record> xnxqList = jxjhDao.xnxq(nj, zydm);
            result.put("jxjhList", jxjhList);
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
     * 根据学年学期查询对教学计划
     */
    public void jxjhByXnxq() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            WptUserInfo wptUserInfo = (WptUserInfo) getSession().getAttribute("wptUserInfo");
            String nj = wptUserInfo.getNjdm();
            String zydm = wptUserInfo.getZydm();
            String zyfxdm = wptUserInfo.getZyfxdm();
            //学年学期
            String currXnxq = getRequest().getParameter("currXnxq");
            if (currXnxq != null && !"".equals(currXnxq)) {
                //当前学年学期教学计划
                List<Record> jxjhList = jxjhDao.jxjh(nj, zydm, zyfxdm, currXnxq);
                result.put("jxjhList", jxjhList);
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
