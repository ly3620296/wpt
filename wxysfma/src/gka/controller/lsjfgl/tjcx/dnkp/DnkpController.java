package gka.controller.lsjfgl.tjcx.dnkp;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import gka.common.kit.IpKit;
import gka.common.kit.OrderCodeFactory;
import gka.controller.lsjfgl.tjcx.xsddcx.XsDdcxDao;
import gka.controller.xsjfgl.wyjf.WyjfDao;
import gka.lsjfgl.login.WptMaLSUserInfo;
import gka.pay.wxpay.WXPayUtil;
import gka.pay.wxpay.controller.WxPayDao;
import gka.pay.wxpay.controller.WxPayOrder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerBind(controllerKey = "/lsjfgl/dnkp")
public class DnkpController extends Controller {
    WyjfDao wyjfDao = new WyjfDao();
    private WxPayDao wxPayDao = new WxPayDao();
    private XsDdcxDao ddcxDao = new XsDdcxDao();
    private DnkpDao dnkpDao = new DnkpDao();

    public void index() {
        Map map = new HashMap();
        try {
            String sfxn = getPara("sfxn"); //缴费学年
            String xm = getPara("xm"); //姓名
            String xh = getPara("xh"); //学号
            String sfzh = getPara("sfzh");  //身份证
            List<Record> list = DnkpDao.getOrderInfo(sfxn, xm, xh, sfzh);
            map.put("code", "0");
            map.put("msg", "success");
            map.put("list", list);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "-1");
            map.put("msg", "系统繁忙，请稍后重试！");
        }
        renderJson(map);
    }


    public void userInfo() {
        Map map = new HashMap();
        try {
            String xm = getPara("xm"); //姓名
            String xn = getPara("xn"); //姓名
            String sfzh = getPara("sfzh");  //身份证
            String xymc = getPara("xymc");  //学院名称
            String zymc = getPara("zymc");  //专业名称
            String bjmc = getPara("bjmc");  //班级名称
            int page = Integer.parseInt(getPara("page"));
            int limit = Integer.parseInt(getPara("limit"));
            Page<Record> pageUserInfo = dnkpDao.userInfo(page, limit, xn, xm, sfzh, xymc, zymc, bjmc);
            map.put("data", pageUserInfo.getList());
            map.put("count", pageUserInfo.getTotalRow());
            map.put("code", "0");
            map.put("msg", "success");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "-1");
            map.put("msg", "系统繁忙，请稍后重试！");
        }
        renderJson(map);
    }

    public void queryByXh() {
        Map map = new HashMap();
        try {
            String xn = getPara("xn"); //缴费学年
            String xh = getPara("xh"); //学号
            Record userInfo = dnkpDao.getUserInfo(xh, xn);
            List<Record> titles = wyjfDao.queryTitle();
            List<Record> yjfList = wyjfDao.queryYjFyxx(titles, xh, xn);
            Record wjfjl = wyjfDao.queryTotalWjf(titles, xh, xn);
            map.put("titles", titles);
            map.put("wjfjl", wjfjl);
            map.put("userInfo", userInfo);
            map.put("yjfList", yjfList);
            map.put("code", "0");
            map.put("msg", "success");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "-1");
            map.put("msg", "系统繁忙，请稍后重试！");
        }
        renderJson(map);
    }


    public void tf() {
        Map map = new HashMap();
        try {
            String xn = getPara("xn"); //缴费学年
            String xh = getPara("xh"); //学号
            String ddh = getPara("ddh"); //学号
            WptMaLSUserInfo userInfo = (WptMaLSUserInfo) getSession().getAttribute("wptMaLSUserInfo");
            dnkpDao.tf(ddh, xn, xh, userInfo.getM_zh());
            map.put("code", "0");
            map.put("msg", "success");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "-1");
            map.put("msg", "系统繁忙，请稍后重试！");
        }
        renderJson(map);
    }

    public void save() {
        Map map = new HashMap();
        try {
            String json = getPara("json");
            JSONObject jsonObject = JSONObject.parseObject(json);
            String pay_type = jsonObject.getString("pay_type");
            String xh = jsonObject.getString("xh");
            String xn = jsonObject.getString("xn");
            String ze = jsonObject.getString("ze");
            List<Record> titles = wyjfDao.queryTitle();
            String ids = "";
            String values = "";
            for (int i = 0; i < titles.size(); i++) {
                String JFXMID = titles.get(i).getStr("JFXMID");
                if (!jsonObject.getString(JFXMID).equals("0")) {
                    ids += JFXMID;
                    ids += ",";
                    values += jsonObject.getString(JFXMID);
                    values += ",";
                }
            }
            ids = ids.endsWith(",") ? ids.substring(0, ids.length() - 1) : ids;
            values = values.endsWith(",") ? values.substring(0, values.length() - 1) : values;
            //查询是否没缴费
            boolean pay = wyjfDao.validateIsNoPay(titles, ids, values, xn, xh);
            if (pay) {
                boolean noPayOrder = wyjfDao.noPayOrder(xh);
                if (!noPayOrder) {
                    String order = WXPayUtil.generateOrder();//订单号
                    String orderNO = OrderCodeFactory.getD(order);
                    String ip = IpKit.getRealIp(getRequest());
                    WptMaLSUserInfo userInfo = (WptMaLSUserInfo) getSession().getAttribute("wptMaLSUserInfo");
                    dnkpDao.insertOrder(xh, order, ids, pay_type, ze, ip, xn, orderNO, values, userInfo.getM_zh());
                    map.put("code", "0");
                    map.put("msg", "success");
                } else {
                    map.put("code", "-6");
                    map.put("msg", "存在未支付订单，请完成支付或关闭订单！");
                }
            } else {
                map.put("code", "-5");
                map.put("msg", "该学生已缴费");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "-1");
            map.put("msg", "系统繁忙，请稍后重试！");
        }
        renderJson(map);
    }

    public static String getSql(List<Record> titles, JSONObject jsonObject) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < titles.size(); i++) {
            if (i < titles.size() - 1) {
                sb.append(titles.get(i).getStr("JFXMID") + "='" + jsonObject.getString(titles.get(i).getStr("JFXMID")) + "',");
            } else {
                sb.append(titles.get(i).getStr("JFXMID") + "='" + jsonObject.getString(titles.get(i).getStr("JFXMID")) + "'");
            }
        }
        return sb.toString();
    }


    public void checkPay() {
        Map map = new HashMap();
        try {
            String xn = getPara("xn"); //缴费学年
            String xh = getPara("xh"); //学号
            List<Record> list = Db.find("select * from wpt_wxzf_special_order where return_code='success' and xh=? and sfxn=?", xh, xn);
            if (list.size() > 0) {
                map.put("code", "-2");
                map.put("msg", "该学生在[" + xn + "]学年已经缴费!");
            } else {
                Record record = Db.findFirst("select * from xssfb  where xh=? and xn=?", xh, xn);
                List<Record> titles = wyjfDao.queryTitle();
                map.put("code", "0");
                map.put("msg", "success");
                map.put("para", record);
                map.put("titles", titles);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        renderJson(map);
    }

    public void title() {
        Map map = new HashMap();
        try {
            List<Record> titles = wyjfDao.queryTitle();
            List<Record> xnList = ddcxDao.queryXn();
            map.put("xnList", xnList);
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
