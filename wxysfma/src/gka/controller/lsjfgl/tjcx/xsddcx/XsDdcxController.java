package gka.controller.lsjfgl.tjcx.xsddcx;


import com.alibaba.druid.util.StringUtils;
import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import gka.controller.xsjfgl.wyjf.JfInfo;
import gka.controller.xsjfgl.wyjf.WyjfDao;
import gka.xsjfgl.login.WptMaXSUserInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerBind(controllerKey = "/lsjfgl/tjcx/xsddcx")
public class XsDdcxController extends Controller {
    private XsDdcxDao ddcxDao = new XsDdcxDao();
    private WyjfDao wyjfDao = new WyjfDao();

    public void index() {
        Map map = new HashMap();
        try {
            String ddbh = getPara("ddbh");
            String xn = getPara("xn");
            String xm = getPara("xm");
            String ddzt = getPara("ddzt");
            String xh = getPara("xh");
            String sfzh = getPara("sfzh");
            String dateStart = getPara("dateStart");
            String dateEnd = getPara("dateEnd");
            XxddcxSearch search = new XxddcxSearch(ddbh, xn, xm, ddzt, xh, sfzh, dateStart, dateEnd);
            int page = Integer.parseInt(getPara("page"));
            int limit = Integer.parseInt(getPara("limit"));
            Page<Record> paginate = ddcxDao.getOrderInfo(page, limit, search);
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

    public void qxInfo() {
        Map map = new HashMap();
        try {
            String xn = getPara("xn");
            String order_no = getPara("order_no");
            String zh = getPara("zh");
            if (!StringUtils.isEmpty(xn) && !StringUtils.isEmpty(order_no) && !StringUtils.isEmpty(zh)) {
                List<Record> titles = wyjfDao.queryTitle();
                Record re = ddcxDao.getInfo(zh, xn);
                String ids = ddcxDao.getIds(order_no);
                List<JfInfo> jfInfoList = getJfinfo(titles, re, ids);
                Record userInfo = ddcxDao.userInfo(zh);
                map.put("data", jfInfoList);
                map.put("userInfo", userInfo);
                map.put("code", "0");
                map.put("msg", "success");
            } else {
                map.put("code", "-2");
                map.put("msg", "请从正规渠道支付！");
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "-1");
            map.put("msg", "系统繁忙，请稍后重试！");
        }
        renderJson(map);
    }


    public void cgInfo() {
        Map map = new HashMap();
        try {
            String xn = getPara("xn");
            String order_no = getPara("order_no");
            String zh = getPara("zh");
            if (!StringUtils.isEmpty(xn) && !StringUtils.isEmpty(order_no) && !StringUtils.isEmpty(zh)) {
                List<Record> titles = wyjfDao.queryTitle();
                Record re = ddcxDao.getInfo(zh, xn);
                Record re1 = ddcxDao.successOrderInfo(order_no);
                List<JfInfo> jfInfoList = getJfinfo(titles, re, re1.getStr("IDS"));
                Record userInfo = ddcxDao.userInfo(zh);
                map.put("data", jfInfoList);
                map.put("userInfo", userInfo);
                map.put("total_fee", re1.getStr("TOTAL_FEE_CALLBACK"));
                map.put("time_end", re1.getStr("TIME_END"));
                map.put("re", re1.getStr("TIME_END"));
                map.put("code", "0");
                map.put("msg", "success");
            } else {
                map.put("code", "-2");
                map.put("msg", "请从正规渠道支付！");
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "-1");
            map.put("msg", "系统繁忙，请稍后重试！");
        }
        renderJson(map);
    }


    private List<JfInfo> getJfinfo(List<Record> titles, Record re, String ids) {
        List<JfInfo> jfInfos = new ArrayList<JfInfo>();
        String[] xmid = ids.split(",");
        for (Record t : titles) {
            JfInfo jfInfo = new JfInfo();
            String sfjf = t.getStr("SFBX");
            boolean flag = false;
            if (sfjf.equals("1")) {
                jfInfo.setXmmc(t.getStr("JFXMMC"));
                jfInfo.setXmid(t.getStr("JFXMID"));
                jfInfo.setSfbx(sfjf);
                for (int j = 0; j < xmid.length; j++) {
                    if (jfInfo.getXmid().equals(xmid[j])) {
                        jfInfo.setJfje(re.getStr(jfInfo.getXmid()));
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    jfInfo.setJfje("0.00");
                }

            } else {
                jfInfo.setXmmc(t.getStr("JFXMMC") + "（选交）");
                jfInfo.setXmid(t.getStr("JFXMID"));
                jfInfo.setSfbx(sfjf);
                jfInfo.setJfje(re.getStr(jfInfo.getXmid()));
                for (int j = 0; j < xmid.length; j++) {
                    if (jfInfo.getXmid().equals(xmid[j])) {
                        jfInfo.setJfje(re.getStr(jfInfo.getXmid()));
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    jfInfo.setJfje("0.00");
                }

            }
            jfInfos.add(jfInfo);
        }
        return jfInfos;
    }
}
