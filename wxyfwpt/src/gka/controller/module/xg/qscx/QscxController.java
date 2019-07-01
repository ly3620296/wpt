package gka.controller.module.xg.qscx;

import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Record;
import gka.controller.CommonDao;
import gka.controller.module.jwl.kjscx.KjscxInfo;
import gka.system.ReturnInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 寝室查询
 */

@ControllerBind(controllerKey = "/xg/qscx")
public class QscxController extends Controller {
    private QscxDao qscxDao = new QscxDao();

    /**
     * 寝室查询
     */
    public void index() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            String ldId = getPara("ldId");
            String qshId = getPara("qshId");
            List<Record> xsList = qscxDao.qscx(ldId, qshId);
            returnInfo.setReturn_code("0");
            returnInfo.setReturn_msg("success");
            result.put("xsList", xsList);
        } catch (Exception e) {
            returnInfo.setReturn_code("-999");
            returnInfo.setReturn_msg("服务繁忙，请稍后重试！");
            e.printStackTrace();
        }
        result.put("returnInfo", returnInfo);
        renderJson(result);
    }

    /**
     * 楼栋
     */
    public void ld() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            //楼栋类别
            List<Record> ldList = qscxDao.ld();
            returnInfo.setReturn_code("0");
            returnInfo.setReturn_msg("success");
            result.put("ldList", ldList);
        } catch (Exception e) {
            returnInfo.setReturn_code("-999");
            returnInfo.setReturn_msg("服务繁忙，请稍后重试！");
            e.printStackTrace();
        }
        result.put("returnInfo", returnInfo);
        renderJson(result);
    }

    /**
     * 寝室号
     */
    public void qsh() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            String ldId = getPara("ldId");
            List<Record> qshList = qscxDao.qsh(ldId);
            returnInfo.setReturn_code("0");
            returnInfo.setReturn_msg("success");
            result.put("qshList", qshList);
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
    public void qscxByXh() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            String ldId = getPara("ldId");
            String qshId = getPara("qshId");
            String xh = getPara("xh");
            List<Record> xsList = qscxDao.qscxByXh(ldId, qshId, xh);
            returnInfo.setReturn_code("0");
            returnInfo.setReturn_msg("success");
            result.put("xsList", xsList);
        } catch (Exception e) {
            returnInfo.setReturn_code("-999");
            returnInfo.setReturn_msg("服务繁忙，请稍后重试！");
            e.printStackTrace();
        }
        result.put("returnInfo", returnInfo);
        renderJson(result);
    }


}
