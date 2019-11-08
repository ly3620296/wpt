package gka.controller.xsjfgl;


import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

@ControllerBind(controllerKey = "/xsjfgl/wheel")
public class WheelController extends Controller {
    public void index() {
        String orderNo = getPara("order_no");
        JSONObject data = new JSONObject();
        if (!StringUtils.isEmpty(orderNo)) {
            String sql = "SELECT ORDER_STATE FROM  WPT_WXZF_SPECIAL_ORDER WHERE ORDER_NO=?";
            Record re = Db.findFirst(sql, orderNo);
            if (re != null) {
                data.put("code", re.getStr("ORDER_STATE"));
            } else {
                data.put("code", "-1");
            }
        }
        renderJson(data);
    }

}
