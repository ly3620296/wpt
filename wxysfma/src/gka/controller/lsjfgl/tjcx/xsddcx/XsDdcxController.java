package gka.controller.lsjfgl.tjcx.xsddcx;


import com.alibaba.druid.util.StringUtils;
import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import gka.common.kit.ExcelExportUtil;
import gka.controller.lsjfgl.tjcx.qftj.SearchBean;
import gka.controller.xsjfgl.wyjf.JfInfo;
import gka.controller.xsjfgl.wyjf.WyjfDao;

import java.io.File;
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
            List<Record> xnList = ddcxDao.queryXn();
            map.put("xnList", xnList);
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

            Map<String, String> titleData = new HashMap<String, String>();//标题，后面用到
            titleData.put("ORDER_NO", "订单编号");
            titleData.put("SFXN", "交费学年");
            titleData.put("XM", "姓名");
            titleData.put("ZH", "学号");
            titleData.put("TIME_START", "下单时间");
            titleData.put("TOTAL_FEE", "订单合计");
            titleData.put("TIME_END", "支付时间");
            titleData.put("TOTAL_FEE_CALLBACK", "支付金额");
            titleData.put("JGMC", "学院名称");
            titleData.put("ZYMC", "专业名称");
            titleData.put("BJMC", "班级名称");
            titleData.put("ORDER_STATE", "状态");
            File file = new File(ExcelExportUtil.getTitle("学生订单数据"));

            String ddbh = getPara("ddbh");
            String xn = getPara("xn");
            String xm = getPara("xm");
            String ddzt = getPara("ddzt");
            String xh = getPara("xh");
            String sfzh = getPara("sfzh");
            String dateStart = getPara("dateStart");
            String dateEnd = getPara("dateEnd");
            SearchBeanDd searchBean = new SearchBeanDd(ddbh, xn, xh, xm, sfzh, ddzt, dateStart, dateEnd);
            file = ExcelExportUtil.saveFile(titleData, genSql(searchBean), file);
            this.renderFile(file);
        } catch (Exception e) {
            e.printStackTrace();
            setAttr("errorMessage", "导出失败请稍后再试，错误信息：" + e.toString());
            render("/error/400.html");
        }
    }

    private String genSql(SearchBeanDd searchBean) {
        String selectSql = "SELECT T1.ORDER_NO,T1.SFXN, to_char(to_date(T1.TIME_START,'yyyymmddhh24miss'),'yyyy-mm-dd hh24:mi:ss') TIME_START,to_char(to_date(T1.TIME_END,'yyyymmddhh24miss'),'yyyy-mm-dd hh24:mi:ss') TIME_END,T1.TOTAL_FEE,NVL(T1.TOTAL_FEE_CALLBACK,0) TOTAL_FEE_CALLBACK,decode(T1.ORDER_STATE,'0','待支付','1','已取消','2','支付成功','3','问题订单','4','教师取消') ORDER_STATE,T2.ZYMC,T2.JGMC,T2.BJMC,T2.XM,T2.ZH,T2.ZJHM ";
        String fromSql = " FROM WPT_WXZF_SPECIAL_ORDER T1 LEFT JOIN  WPT_YH T2 ON T1.XH=T2.ZH  WHERE 1=1 ";
        if (!StringUtils.isEmpty(searchBean.getDdbh())) {
            fromSql += " AND T1.ORDER_NO='" + searchBean.getDdbh() + "'";
        }
        if (!StringUtils.isEmpty(searchBean.getXn())) {
            fromSql += " AND T1.SFXN='" + searchBean.getXn() + "'";
        }
        if (!StringUtils.isEmpty(searchBean.getXm())) {
            fromSql += " AND T2.XM like '%" + searchBean.getXm() + "%'";
        }
        if (!StringUtils.isEmpty(searchBean.getDdzt())) {
            fromSql += " AND T1.ORDER_STATE='" + searchBean.getDdzt() + "'";
        }
        if (!StringUtils.isEmpty(searchBean.getXh())) {
            fromSql += " AND T2.ZH='" + searchBean.getXh() + "'";
        }
        if (!StringUtils.isEmpty(searchBean.getSfzh())) {
            fromSql += " AND T2.ZJHM='" + searchBean.getSfzh() + "'";
        }
        if (!StringUtils.isEmpty(searchBean.getZfst())) {
            fromSql += " AND  to_date(TIME_END,'yyyymmddhh24miss')>=to_date('" + searchBean.getZfst() + "','yyyy-mm-dd') ";
        }
        if (!StringUtils.isEmpty(searchBean.getZfed())) {
            fromSql += " AND  to_date(TIME_END,'yyyymmddhh24miss')<=to_date('" + searchBean.getZfed() + "','yyyy-mm-dd')+1 ";
        }
        if (fromSql.contains("to_date")) {
            fromSql += " AND TIME_END IS NOT NULL";
        }
        fromSql += "  ORDER BY T1.TIME_START DESC";
        return selectSql + fromSql;
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
