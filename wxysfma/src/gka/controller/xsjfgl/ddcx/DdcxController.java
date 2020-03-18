package gka.controller.xsjfgl.ddcx;


import com.alibaba.druid.util.StringUtils;
import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import gka.common.kit.ReKit;
import gka.controller.xsjfgl.wyjf.JfInfo;
import gka.controller.xsjfgl.wyjf.WyjfDao;
import gka.pay.wxpay.controller.MyWxpayConstant;
import gka.xsjfgl.login.WptMaXSUserInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerBind(controllerKey = "/xsjfgl/ddcx")
public class DdcxController extends Controller {
    private DdcxDao ddcxDao = new DdcxDao();
    private WyjfDao wyjfDao = new WyjfDao();

    /**
     * 订单查询
     */
    public void index() {
        Map map = new HashMap();
        try {
            WptMaXSUserInfo userInfo = (WptMaXSUserInfo) getSession().getAttribute("wptMaXSUserInfo");
            String xh = userInfo.getZh();
            int page = Integer.parseInt(getPara("page"));
            int limit = Integer.parseInt(getPara("limit"));
            Page<Record> paginate = ddcxDao.getOrderInfo(page, limit, xh);
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


    /**
     * 订单详情
     */
    public void qxInfo() {
        Map map = new HashMap();
        try {
            String xn = getPara("xn");
            String order_no = getPara("order_no");
            if (!StringUtils.isEmpty(xn) && !StringUtils.isEmpty(order_no)) {
                WptMaXSUserInfo userInfo = (WptMaXSUserInfo) getSession().getAttribute("wptMaXSUserInfo");
                String xh = userInfo.getZh();
                List<Record> titles = wyjfDao.queryTitle();
                Record re = ddcxDao.getInfo(xh, xn);
                String ids = ddcxDao.getIds(order_no);
                List<JfInfo> jfInfoList = getJfinfo(titles, re, ids);
                map.put("data", jfInfoList);
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
            if (!StringUtils.isEmpty(xn) && !StringUtils.isEmpty(order_no)) {
                WptMaXSUserInfo userInfo = (WptMaXSUserInfo) getSession().getAttribute("wptMaXSUserInfo");
                String xh = userInfo.getZh();
                List<Record> titles = wyjfDao.queryTitle();
                Record re = ddcxDao.getInfo(xh, xn);
                Record re1 = ddcxDao.successOrderInfo(order_no);
                List<JfInfo> jfInfoList = getJfinfo(titles, re, re1.getStr("IDS"));
                map.put("data", jfInfoList);
                map.put("total_fee", re1.getStr("TOTAL_FEE_CALLBACK"));
                map.put("time_end", re1.getStr("TIME_END"));
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
