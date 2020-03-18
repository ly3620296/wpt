package gka.controller.xsjfgl.wyjf;

import com.alibaba.druid.util.StringUtils;
import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Record;
import gka.common.kit.ReKit;
import gka.pay.wxpay.controller.MyWxpayConstant;
import gka.xsjfgl.login.WptMaXSUserInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@ControllerBind(controllerKey = "/xsjfgl/wyjf")
public class WyjfController_bakk extends Controller {
    WyjfDao wyjfDao = new WyjfDao();

    public void index() {
        Map map = new HashMap();
        try {
            WptMaXSUserInfo userInfo = (WptMaXSUserInfo) getSession().getAttribute("wptMaXSUserInfo");
            String xh = userInfo.getZh();
            List<Record> titles = wyjfDao.queryTitle();
            List<Record> jfjl = getWjfjl(titles, xh);
            NoPayOrderInfo noPayOrderInfo = wyjfDao.getNoPayOrderInfo(xh);
            map.put("noPayOrderInfo", noPayOrderInfo);
            map.put("code", "0");
            map.put("msg", "success");
            map.put("data", jfjl);
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
            WptMaXSUserInfo userInfo = (WptMaXSUserInfo) getSession().getAttribute("wptMaXSUserInfo");
            String xh = userInfo.getZh();
            List<Record> titles = wyjfDao.queryTitle();
            NoPayOrderInfo noPayOrderInfo = wyjfDao.getNoPayOrderInfo(xh);
            map.put("noPayOrderInfo", noPayOrderInfo);
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

    //查询缴费明细
    public void wjfjl() {
        Map map = new HashMap();
        try {
            String xn = getPara("xn");
            if (!StringUtils.isEmpty(xn)) {
                WptMaXSUserInfo userInfo = (WptMaXSUserInfo) getSession().getAttribute("wptMaXSUserInfo");
                String xh = userInfo.getZh();
                List<Record> titles = wyjfDao.queryTitle();
                //是否有未支付订单
                boolean noPay = noPay();
                if (!noPay) {
                    //是否未缴费
                    boolean isPay = validate(titles, xh, xn);
                    if (!isPay) {
                        Record re = wyjfDao.jf(xh, xn, titles);
                        List<JfInfo> jfInfoList = getJfinfo(titles, re);
                        map = ReKit.toMap(jfInfoList.size(), jfInfoList);
                    } else {
                        map.put("code", "-4");
                        map.put("msg", "已经支付过，请误重新支付！");
                    }
                } else {
                    map.put("code", "-3");
                    map.put("msg", "您有未支付订单，请先关闭订单或完成待支付订单！");
                }
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


    //继续缴费
    public void jxWjfjl() {
        Map map = new HashMap();
        try {
            String xn = getPara("xn");
            String order_no = getPara("order_no");
            if (!StringUtils.isEmpty(xn) && !StringUtils.isEmpty(order_no)) {
                WptMaXSUserInfo userInfo = (WptMaXSUserInfo) getSession().getAttribute("wptMaXSUserInfo");
                String xh = userInfo.getZh();
                List<Record> titles = wyjfDao.queryTitle();
                //是否未缴费
                boolean isPay = validate(titles, xh, xn);
                if (!isPay) {
                    Record re1 = wyjfDao.queryJxzf(order_no);
                    if (re1 != null) {
                        Record re = wyjfDao.jf(xh, xn, titles);
                        List<JfInfo> jfInfoList = getJfinfo(titles, re, re1.getStr("IDS"));
                        map = ReKit.toMap(jfInfoList.size(), jfInfoList);
                        map.put("code_url", re1.getStr("CODE_URL"));
                        map.put("order_no", order_no);
                        map.put("total_fee", re1.getStr("TOTAL_FEE"));
                        map.put("order_state", MyWxpayConstant.ORDER_STATE_DESC.get(re1.getStr("ORDER_STATE")));
                    } else {
                        map.put("code", "-5");
                        map.put("msg", "目前不存在未支付订单！");
                    }
                } else {
                    map.put("code", "-4");
                    map.put("msg", "已经支付过，请误重新支付！");
                }
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

    private List<JfInfo> getJfinfo(List<Record> titles, Record re) {
        List<JfInfo> jfInfos = new ArrayList<JfInfo>();
        for (Record t : titles) {
            JfInfo jfInfo = new JfInfo();
            String sfjf = t.getStr("SFBX");
            if (sfjf.equals("1")) {
                jfInfo.setXmmc(t.getStr("JFXMMC"));
                jfInfo.setXmid(t.getStr("JFXMID"));
                jfInfo.setSfbx(sfjf);
                jfInfo.setJfje(re.getStr(jfInfo.getXmid()));
            } else {
                jfInfo.setXmmc(t.getStr("JFXMMC") + "（选交）");
                jfInfo.setXmid(t.getStr("JFXMID"));
                jfInfo.setSfbx(sfjf);
                jfInfo.setJfje(re.getStr(jfInfo.getXmid()));
            }
            jfInfos.add(jfInfo);
        }
        return jfInfos;
    }


    private List<JfInfo> getJfinfo(List<Record> titles, Record re, String ids) {
        List<JfInfo> jfInfos = new ArrayList<JfInfo>();
        String[] xmid = ids.split(",");
        for (Record t : titles) {
            boolean flag = false;
            JfInfo jfInfo = new JfInfo();
            String sfjf = t.getStr("SFBX");
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

    //未缴费记录
    private List<Record> getWjfjl(List<Record> titles, String xh) {
        //应收项目合计 已交和未交
        List<Record> totals = wyjfDao.queryTotalWjf(titles, xh);
        List<Record> wj = new ArrayList<Record>();
        for (Record total : totals) {
            boolean flag = true;
            for (Record title : titles) {
                String val = total.getStr(title.getStr("JFXMID"));
                if (!val.equals("0")) {
                    flag = false;
                    break;
                }
            }
            if (!flag) {
                wj.add(total);
            }
        }
        return wj;
    }

    //验证缴费学年是否未缴费 true缴费了 false未缴费
    private boolean validate(List<Record> titles, String xh, String xn) {
        //应收项目合计 已交和未交
        List<Record> totals = wyjfDao.queryTotal(titles, xh);
        List<Record> wj = new ArrayList<Record>();
        boolean isPay = true;
        for (Record total : totals) {
            boolean flag = true;
            for (Record title : titles) {
                String val = total.getStr(title.getStr("JFXMID"));
                if (val.equals("0")) {
                    flag = false;
                    break;
                }
            }
            if (!flag) {
                if (total.getStr("XN").equals(xn)) {
                    isPay = false;
                    break;
                }
            }
        }
        return isPay;
    }

    //是否有未支付订单， true有 false 没有
    private boolean noPay() {

        return false;
    }
}
