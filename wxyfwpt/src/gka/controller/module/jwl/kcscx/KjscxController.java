package gka.controller.module.jwl.kcscx;

import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Record;
import gka.controller.CommonDao;
import gka.controller.login.WptUserInfo;
import gka.controller.module.jwl.xkqkcx.RqInfo;
import gka.kit.DateUtil;
import gka.system.ReturnInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 空教室查询
 */

@ControllerBind(controllerKey = "/jwl/kjscx")
public class KjscxController extends Controller {
    private KjscxDao kjscxDao = new KjscxDao();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");


    public void index() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            //场地类别
            List<Record> cdlbList = kjscxDao.cdlb();
            List<RqInfo> rqList = kjscxDao.rq();
            returnInfo.setReturn_code("0");
            returnInfo.setReturn_msg("success");
            result.put("cdlbList", cdlbList);
            result.put("rqList", rqList);
        } catch (Exception e) {
            returnInfo.setReturn_code("-999");
            returnInfo.setReturn_msg("服务繁忙，请稍后重试！");
            e.printStackTrace();
        }
        result.put("returnInfo", returnInfo);
        renderJson(result);
    }

    /**
     * 楼号
     */
    public void lh() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            String cdlbId = getPara("cdlbId");
            List<Record> lhList = kjscxDao.lh(cdlbId);
            returnInfo.setReturn_code("0");
            returnInfo.setReturn_msg("success");
            result.put("lhList", lhList);
        } catch (Exception e) {
            returnInfo.setReturn_code("-999");
            returnInfo.setReturn_msg("服务繁忙，请稍后重试！");
            e.printStackTrace();
        }
        result.put("returnInfo", returnInfo);
        renderJson(result);
    }

    /**
     * 空教室查询
     */
    public void kjscxByCon() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            String rq = getPara("rq");
            String cdlbId = getPara("cdlbId");
            String lhId = getPara("lhId");
            String currXnxq = (String) getSession().getAttribute("currXnxq");
            String currZc = CommonDao.currXnxqZc(rq, currXnxq);
            List<Record> kjscxList = kjscxDao.kjscx(cdlbId, lhId, currZc);
            returnInfo.setReturn_code("0");
            returnInfo.setReturn_msg("success");
            result.put("kjscxList", kjscxList);
        } catch (Exception e) {
            returnInfo.setReturn_code("-999");
            returnInfo.setReturn_msg("服务繁忙，请稍后重试！");
            e.printStackTrace();
        }
        result.put("returnInfo", returnInfo);
        renderJson(result);
    }



}
