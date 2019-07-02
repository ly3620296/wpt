package gka.controller.module.jwl.xl;

import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Record;
import gka.controller.CommonDao;
import gka.system.ReturnInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ControllerBind(controllerKey = "/jwl/xl")
public class XlController extends Controller {
    private XlDao xlDao = new XlDao();
    private  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public void index() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            String currXnxq = CommonDao.currXnxq();
            List<Record> list = xlDao.queryXl(currXnxq);
            result.put("currXnxq", currXnxq);
            result.put("xlList", list);
            result.put("currDate", simpleDateFormat.format(new Date()));
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
