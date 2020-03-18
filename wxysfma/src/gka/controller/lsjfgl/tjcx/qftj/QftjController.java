package gka.controller.lsjfgl.tjcx.qftj;

import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import gka.common.kit.ExcelExportUtil;
import gka.controller.lsjfgl.tjcx.xsddcx.XsDdcxDao;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerBind(controllerKey = "/lsjfgl/tjcx/qftj")
public class QftjController extends Controller {
    private QftjDao qftjDao = new QftjDao();
    private XsDdcxDao ddcxDao = new XsDdcxDao();

    public void index() {
        Map map = new HashMap();
        try {
            String xn = getPara("xn"); //学年
            String nj = getPara("nj"); //入学年级
            String xh = getPara("xh"); //学号
            String xm = getPara("xm"); //姓名
            String xymc = getPara("xymc");  //学院名称
            String zymc = getPara("zymc");  //专业名称
            String bjmc = getPara("bjmc");  //班级名称
            QftjSearch search = new QftjSearch(xn, nj, xh, xm, xymc, zymc, bjmc);
            int page = Integer.parseInt(getPara("page"));
            int limit = Integer.parseInt(getPara("limit"));

            Page<Record> paginate = qftjDao.getOrderInfo(page, limit, search);
            List<Record> xnList = ddcxDao.queryXn();
            List<Record> rxnjList = ddcxDao.queryRxnj();
            map.put("xnList", xnList);
            map.put("rxnjList", rxnjList);
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


            File file = new File(ExcelExportUtil.getTitle("欠费学生数据"));
            file = ExcelExportUtil.saveFilepQftj(file);
            this.renderFile(file);
        } catch (Exception e) {
            e.printStackTrace();
            setAttr("errorMessage", "导出失败请稍后再试，错误信息：" + e.toString());
            render("/error/400.html");
        }
    }
    public void title() {
        Map map = new HashMap();
        try {
            List<Record> titles = qftjDao.queryTitle();
            map.put("titles", titles);
            map.put("code", "0");
            map.put("msg", "success");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "-1");
            map.put("msg", "系统繁忙，请稍后重试！");
        }
        renderJson(map);
    }

}
