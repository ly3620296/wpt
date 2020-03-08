package gka.controller.lsjfgl.tjcx.dnkp;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import gka.common.kit.IpKit;
import gka.common.kit.OrderCodeFactory;
import gka.controller.xsjfgl.wyjf.WyjfDao;
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

    public void save() {
        Map map = new HashMap();
        try {
            String json = getPara("json");
            JSONObject jsonObject = JSONObject.parseObject(json);
            System.out.println(jsonObject.get("jffs"));
            System.out.println(jsonObject.get("xh"));
            System.out.println(jsonObject.get("xn"));
            String jffs = jsonObject.getString("jffs");
            String xh = jsonObject.getString("xh");
            String ze = jsonObject.getString("ze");
            String xn = jsonObject.getString("xn");

            List<Record> titles = wyjfDao.queryTitle();
            String ids = "";
            for (int i = 0; i < titles.size(); i++) {
                String JFXMID = titles.get(i).getStr("JFXMID");
                if (!jsonObject.getString(JFXMID).equals("0")) {
                    ids += JFXMID;
                    ids += ",";
                }
            }
            ids = ids.endsWith(",") ? ids.substring(0, ids.length() - 1) : ids;
            System.out.println(ids);
            //查询是否没缴费
            boolean pay = wyjfDao.validateIsNoPay(ids, xn, xh);
            if (pay) {
                String order = WXPayUtil.generateOrder();//订单号
                String orderNO = OrderCodeFactory.getD(order);
                System.out.println(ids);
                String ip = IpKit.getRealIp(getRequest());

                DnkpDao.insertOrder(xh, order, ids, jffs, ze, ip, xn, orderNO);
                String sql = "INSERT INTO YHSJB  SELECT * FROM XSSFB WHERE XH=? AND XN=?";
                Db.update(sql, xh, xn);
                String upSql = "update YHSJB SET yshj='"+ze+"'," + getSql(titles, jsonObject) + " WHERE XH=? AND XN=?";
                Db.update(upSql, xh, xn);
                map.put("code", "0");
                map.put("msg", "success");
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
