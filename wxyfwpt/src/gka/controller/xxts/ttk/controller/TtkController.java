package gka.controller.xxts.ttk.controller;

import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Record;
import gka.controller.login.WptUserInfo;
import gka.controller.xxts.oa.OaDao;
import gka.controller.xxts.tsgh.controller.TsjyDao;
import gka.system.ReturnInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerBind(controllerKey = "/xxts/ttk")
public class TtkController extends Controller {
    //调停课
    private TtkDao ttkDao = new TtkDao();
    //图书借阅
    private TsjyDao tsjyDao = new TsjyDao();
    //oa
    private OaDao oaDao = new OaDao();

    public void index() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            WptUserInfo wptUserInfo = (WptUserInfo) getSession().getAttribute("wptUserInfo");
            String zh = wptUserInfo.getZh();
            String sfzh = wptUserInfo.getZjhm();
            //调停课消息 图书借阅消息
            List<Record> ttkXxList = ttkDao.ttkxx(zh, sfzh);
            result.put("ttkXxList", ttkXxList);
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
     * 调停课详细信息
     */
    public void yd() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            WptUserInfo wptUserInfo = (WptUserInfo) getSession().getAttribute("wptUserInfo");
            String zh = wptUserInfo.getZh();
            String ttkxxid = getPara("ttkxxid");
            Record ttkXx = ttkDao.xx(ttkxxid, zh);
            result.put("ttkXx", ttkXx);
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
     * 首次 标记调停课未读信息为已读
     */
    public void wd() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            WptUserInfo wptUserInfo = (WptUserInfo) getSession().getAttribute("wptUserInfo");
            String zh = wptUserInfo.getZh();
            String ttkxxid = getPara("ttkxxid");
            ttkDao.upYd(ttkxxid, zh);
            Record ttkXx = ttkDao.xx(ttkxxid, zh);
            result.put("ttkXx", ttkXx);
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
     * 图书借阅详细信息
     */
    public void ydTsjy() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            WptUserInfo wptUserInfo = (WptUserInfo) getSession().getAttribute("wptUserInfo");
            String sfzh = wptUserInfo.getZjhm();
            String tsjyxxid = getPara("tsjyxxid");
            Record tsjyXx = tsjyDao.xx(tsjyxxid, sfzh);
            result.put("ttkXx", tsjyXx);
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
     * 首次 标记图书借阅信息为已读
     */
    public void wdTsjy() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            WptUserInfo wptUserInfo = (WptUserInfo) getSession().getAttribute("wptUserInfo");
            String sfzh = wptUserInfo.getZjhm();
            String tsjyxxid = getPara("tsjyxxid");

            tsjyDao.upYd(tsjyxxid, sfzh);
            Record ttkXx = tsjyDao.xx(tsjyxxid, sfzh);
            result.put("ttkXx", ttkXx);
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
     *
     */
    public void ydOa() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            WptUserInfo wptUserInfo = (WptUserInfo) getSession().getAttribute("wptUserInfo");
            String zh = wptUserInfo.getZh();
            String xxid = getPara("xxid");
            Record tsjyXx = oaDao.xx(xxid, zh);
            result.put("ttkXx", tsjyXx);
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
     *
     */
    public void wdOa() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            WptUserInfo wptUserInfo = (WptUserInfo) getSession().getAttribute("wptUserInfo");
            String zh = wptUserInfo.getZh();
            String xxid = getPara("xxid");
            oaDao.upYd(xxid, zh);
            Record ttkXx = oaDao.xx(xxid, zh);
            result.put("ttkXx", ttkXx);
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
