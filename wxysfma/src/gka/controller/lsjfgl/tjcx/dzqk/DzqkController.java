package gka.controller.lsjfgl.tjcx.dzqk;


import com.alibaba.druid.util.StringUtils;
import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import gka.common.kit.ExcelExportUtil;
import gka.controller.lsjfgl.tjcx.xsddcx.XsDdcxDao;
import gka.controller.lsjfgl.tjcx.xsddcx.XxddcxSearch;
import gka.controller.xsjfgl.wyjf.JfInfo;
import gka.controller.xsjfgl.wyjf.WyjfDao;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerBind(controllerKey = "/lsjfgl/tjcx/dzqk")
public class DzqkController extends Controller {
    private DzqkDao dzqkDao = new DzqkDao();


    public void index() {
        Map map = new HashMap();
        try {
            int page = Integer.parseInt(getPara("page"));
            int limit = Integer.parseInt(getPara("limit"));
            Page<Record> paginate = dzqkDao.getOrderInfo(page, limit);
            map.put("code", "0");
            map.put("msg", "success");
            map.put("data", paginate.getList());
            map.put("count", paginate.getTotalRow());
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "-1");
            map.put("msg", "系统繁忙，请稍后重试！");
        }
        renderJson(map);
    }

    public void export() {
        try {
            String dzsj=getPara("dzsj");
            String sql = "SELECT o.*,y.XM,y.XH from wpt_wxzf_special_order o left join yhsjb y on y.xh=o.xh and y.xn=o.sfxn where o.return_code='success' " +
                    " and pay_type in ('NATIVE','JSAPI') AND o.TIME_START like '"+dzsj+"%'"+"";
            Map<String, String> titleData = new HashMap<String, String>();//标题，后面用到
            titleData.put("ORDER_NO", "订单编号");//
            titleData.put("SFXN", "交费学年");//
            titleData.put("XM", "姓名");
            titleData.put("XH", "学号");
            titleData.put("TOTAL_FEE", "订单合计");
            titleData.put("TIME_END", "支付时间");//
            File file = new File(ExcelExportUtil.getTitle(dzsj+"对账数据"));
            file = ExcelExportUtil.saveFile(titleData, sql, file);
            this.renderFile(file);
        } catch (Exception e) {
            e.printStackTrace();
            setAttr("errorMessage", "导出失败请稍后再试，错误信息：" + e.toString());
            render("/error/400.html");
        }
    }
}
