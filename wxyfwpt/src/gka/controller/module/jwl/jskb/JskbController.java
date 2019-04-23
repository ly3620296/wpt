package gka.controller.module.jwl.jskb;

import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Record;
import gka.controller.CommonDao;
import gka.controller.login.WptUserInfo;
import gka.controller.module.jwl.jskb.JskbDao;
import gka.kit.DateUtil;
import gka.system.ReturnInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 学生课表查询
 */

@ControllerBind(controllerKey = "/jwl/jskb")
public class JskbController extends Controller {
    private JskbDao jskbDao = new JskbDao();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public void index() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            String currXnxq = (String) getSession().getAttribute("currXnxq");
            WptUserInfo wptUserInfo = (WptUserInfo) getSession().getAttribute("wptUserInfo");
            int currXq = DateUtil.getCurrXq();
            //当前日期对应的周次
            String currZc = CommonDao.currXnxqZc(simpleDateFormat.format(new Date()), currXnxq);
            //当前学年学期一共有多少周次
            String[] zcs = CommonDao.currXnxqZcs(currXnxq);
            //查询学生当前学期所有课表并缓存
            List<Record> jskbList = jskbDao.jskb(wptUserInfo.getZh(), currXnxq, getSession());
            //课表
            String[][] jskbArry = jskbDao.getKbArray(jskbList, currZc);
            returnInfo.setReturn_code("0");
            returnInfo.setReturn_msg("success");
            result.put("currZc", currZc);
            result.put("currXq", currXq);
            result.put("zcs", zcs);
            result.put("jskbArry", jskbArry);
        } catch (Exception e) {
            returnInfo.setReturn_code("-999");
            returnInfo.setReturn_msg("服务繁忙，请稍后重试！");
            e.printStackTrace();
        }
        result.put("returnInfo", returnInfo);
        renderJson(result);
    }

    public void jskbByzc() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            String currXnxq = (String) getSession().getAttribute("currXnxq");
            WptUserInfo wptUserInfo = (WptUserInfo) getSession().getAttribute("wptUserInfo");
            String currZc = getPara("currZc");
            //查询学生当前学期所有课表并缓存
            List<Record> jskbList = jskbDao.jskb(wptUserInfo.getZh(), currXnxq, getSession());
            String[][] jskbArry = jskbDao.getKbArray(jskbList, currZc);
            returnInfo.setReturn_code("0");
            returnInfo.setReturn_msg("success");
            int currXq = DateUtil.getCurrXq();
            result.put("currXq", currXq);
            result.put("jskbArry", jskbArry);
        } catch (Exception e) {
            returnInfo.setReturn_code("-999");
            returnInfo.setReturn_msg("服务繁忙，请稍后重试！");
            e.printStackTrace();
        }
        result.put("returnInfo", returnInfo);
        renderJson(result);
    }


}
