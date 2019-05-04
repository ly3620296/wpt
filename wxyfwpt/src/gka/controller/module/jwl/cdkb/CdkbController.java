package gka.controller.module.jwl.cdkb;

import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Record;
import gka.controller.CommonDao;
import gka.controller.login.WptUserInfo;
import gka.kit.DateUtil;
import gka.system.ReturnInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 场地课表查询
 */

@ControllerBind(controllerKey = "/jwl/cdkb")
public class CdkbController extends Controller {
    private CdkbDao cdkbDao = new CdkbDao();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 周次和场地类别
     */
    public void index() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            String currXnxq = (String) getSession().getAttribute("currXnxq");
            WptUserInfo wptUserInfo = (WptUserInfo) getSession().getAttribute("wptUserInfo");
            //当前日期是星期几
            int currXq = DateUtil.getCurrXq();
            //当前日期对应的周次
            String currZc = CommonDao.currXnxqZc(simpleDateFormat.format(new Date()), currXnxq);
            //当前学年学期一共有多少周次
            String[] zcs = CommonDao.currXnxqZcs(currXnxq);
            //场地类别
            List<Record> cdlbList = cdkbDao.cdlb();
            //查询学生当前学期所有课表并缓存
//            List<Record> cdkbList = cdkbDao.cdkb(wptUserInfo.getZh(), currXnxq, getSession());
//            String[][] cdkbArry = cdkbDao.getKbArray(cdkbList, currZc);
            returnInfo.setReturn_code("0");
            returnInfo.setReturn_msg("success");
            result.put("currZc", currZc);
            result.put("currXq", currXq);
            result.put("zcs", zcs);
            result.put("cdlbList", cdlbList);
//            result.put("xskbArry", cdkbArry);
        } catch (Exception e) {
            returnInfo.setReturn_code("-999");
            returnInfo.setReturn_msg("服务繁忙，请稍后重试！");
            e.printStackTrace();
        }
        result.put("returnInfo", returnInfo);
        renderJson(result);
    }

    /**
     * 场地
     */
    public void cd() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            String cdlbId = getPara("cdlbId");
            List<Record> cdList = cdkbDao.cd(cdlbId);
            returnInfo.setReturn_code("0");
            returnInfo.setReturn_msg("success");
            int currXq = DateUtil.getCurrXq();
            result.put("currXq", currXq);
            result.put("cdList", cdList);
        } catch (Exception e) {
            returnInfo.setReturn_code("-999");
            returnInfo.setReturn_msg("服务繁忙，请稍后重试！");
            e.printStackTrace();
        }
        result.put("returnInfo", returnInfo);
        renderJson(result);
    }

    /**
     * 场地课表
     */
    public void cdkb() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            String zc = getPara("zc");
            String cdlbId = getPara("cdlbId");
            String cdId = getPara("cdId");
            List<Record> cdkbList = cdkbDao.cdkb(cdlbId,cdId);
            String[][] cdkbArry = cdkbDao.getKbArray(cdkbList, zc);
            returnInfo.setReturn_code("0");
            returnInfo.setReturn_msg("success");
            int currXq = DateUtil.getCurrXq();
            result.put("currXq", currXq);
            result.put("cdkbArry", cdkbArry);
        } catch (Exception e) {
            returnInfo.setReturn_code("-999");
            returnInfo.setReturn_msg("服务繁忙，请稍后重试！");
            e.printStackTrace();
        }
        result.put("returnInfo", returnInfo);
        renderJson(result);
    }


}
