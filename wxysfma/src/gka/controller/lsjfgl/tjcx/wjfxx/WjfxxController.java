package gka.controller.lsjfgl.tjcx.wjfxx;

import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import gka.controller.xsjfgl.wyjf.WyjfDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerBind(controllerKey = "/lsjfgl/tjcx/wjfxx")
public class WjfxxController extends Controller {
    private WjfxxDao wjfxxDao = new WjfxxDao();
    private WyjfDao wyjfDao = new WyjfDao();

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
            WjfxxSearch search = new WjfxxSearch(xn,nj, xh, xm, xymc, zymc, bjmc);
            int page = Integer.parseInt(getPara("page"));
            int limit = Integer.parseInt(getPara("limit"));
            List<Record> titles = wyjfDao.queryTitle();
            Page<Record> paginate = wjfxxDao.getOrderInfo(titles, page, limit, search);
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

    public void title() {
        Map map = new HashMap();
        try {
            List<Record> titles = wyjfDao.queryTitle();
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