package gka.controller.module.xsfwl.bmtxl;

import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Record;
import gka.controller.login.WptUserInfo;
import gka.system.ReturnInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门通讯录
 */

@ControllerBind(controllerKey = "/xsfwl/bmtxl")
public class BmtxlController extends Controller {
    private BmtxlDao bmtxlDao = new BmtxlDao();

    /**
     * 部门通讯录初始化
     */
    public void index() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {

            //查询所有部门
            List<Record> bmList = bmtxlDao.queryBm();
            System.out.println(bmList.get(0).getStr("X_CODE"));
            System.out.println(bmList.get(0).getStr("X_NAME"));

            //查询所有通讯录
            List<Record> txlList = bmtxlDao.queryYgtxlByBm(bmList.get(0).getStr("X_CODE"));
            result.put("bmList", bmList);
            result.put("txlList", txlList);
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
     * 根据部门查询对应通讯录
     */
    public void bmtxtByBm() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            String bmCode = getPara("bmCode");
            //查询所有通讯录
            List<Record> txlList  = bmtxlDao.queryYgtxlByBm(bmCode);
            result.put("txlList", txlList);
            returnInfo.setReturn_code("0");
            returnInfo.setReturn_msg("success");
        } catch (Exception e) {
            returnInfo.setReturn_code("-999");
            returnInfo.setReturn_msg("服务繁忙，请稍后重试！");
            e.printStackTrace();
        }
        result.put("returnInfo", returnInfo);
        renderJson(result);
        ;

    }
}
