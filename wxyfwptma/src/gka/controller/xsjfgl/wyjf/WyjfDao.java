package gka.controller.xsjfgl.wyjf;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import gka.controller.xsjfgl.MyUtil;
import gka.controller.xsjfgl.grjfxx.MyConstant;
import gka.pay.wxpay.WXPayConstants;
import gka.pay.wxpay.controller.MyWxpayConstant;

import java.text.SimpleDateFormat;
import java.util.List;

public class WyjfDao {

    public List<Record> queryTitle() {
        String sql = "SELECT JFXMID,JFXMMC,SFBX FROM JFXMDM ORDER BY JFXMID";
        List<Record> list = Db.find(sql);
        return list;
    }
    public Page<Record> queryTotalPage(List<Record> title, String xh, int pageNum, int pageSize) {
        String selectSql = "SELECT T1.XN,T1.YSHJ," + getSql(title);
        String fromSql = " FROM XSSFB T1 LEFT JOIN  YHSJB T2 ON T1.XH =T2.XH AND T1.XN=T2.XN  WHERE T1.XH =? ORDER BY T1.XN";
        Page<Record> paginate = Db.paginate(pageNum, pageSize, selectSql, fromSql, xh);
        return paginate;
    }
    public List<Record> queryTotal(List<Record> title, String xh) {
        String sql = "SELECT T1.XN,T1.YSHJ," + getSql(title) +
                " FROM XSSFB T1 LEFT JOIN  YHSJB T2 ON T1.XH =T2.XH AND T1.XN=T2.XN  WHERE T1.XH =? ORDER BY T1.XN";
        List<Record> list = Db.find(sql, xh);
        return list;
    }

    public List<Record> queryTotalWjf(List<Record> title, String xh) {
        System.out.println("--------------########-----------");
        String sql = "SELECT T1.XN," + getSqlWjf(title) +
                " FROM XSSFB T1 LEFT JOIN  YHSJB T2 ON T1.XH =T2.XH AND T1.XN=T2.XN  WHERE T1.XH =? ORDER BY T1.XN";
        List<Record> list = Db.find(sql, xh);
        return list;
    }

    public Record jf(String xh, String xn, List<Record> title) {
        String sql = "SELECT " + getSqlForJf(title) +
                " FROM XSSFB T1 LEFT JOIN  YHSJB T2 ON T1.XH =T2.XH AND T1.XN=T2.XN  WHERE T1.XH =? AND T1.XN=?";
        Record re = Db.findFirst(sql, xh, xn);
        return re;

    }

    private String getSql(List<Record> title) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < title.size(); i++) {
            Record re = title.get(i);

            String jfxmId = re.getStr("JFXMID");
            if (i < title.size() - 1) {
                sb.append("NVL(T2." + jfxmId + ",0) " + jfxmId + ",");
            } else {
                sb.append("NVL(T2." + jfxmId + ",0) " + jfxmId);
            }
        }
        return sb.toString();
    }

    private String getSqlWjf(List<Record> title) {
        StringBuffer sb = new StringBuffer();
        StringBuffer hj = new StringBuffer("(T1.YSHJ");
        for (int i = 0; i < title.size(); i++) {
            Record re = title.get(i);

            String jfxmId = re.getStr("JFXMID");
            if (i < title.size() - 1) {
                sb.append("(NVL(T1." + jfxmId + ",0)-");
                sb.append("NVL(T2." + jfxmId + ",0)) " + jfxmId + ",");
                hj.append("-" + "NVL(T2." + jfxmId + ",0)");
            } else {
                sb.append("(NVL(T1." + jfxmId + ",0)-");
                sb.append("NVL(T2." + jfxmId + ",0)) " + jfxmId + ",");
                hj.append("-" + "NVL(T2." + jfxmId + ",0)) YSHJ");
            }
        }
        sb.append(hj);
        return sb.toString();
    }

    private String getSqlForJf(List<Record> title) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < title.size(); i++) {
            Record re = title.get(i);

            String jfxmId = re.getStr("JFXMID");
            if (i < title.size() - 1) {
                sb.append("(NVL(T1." + jfxmId + ",0)-NVL(T2." + jfxmId + ",0)) " + jfxmId + ",");
            } else {
                sb.append("(NVL(T1." + jfxmId + ",0)-NVL(T2." + jfxmId + ",0)) " + jfxmId);
            }
        }
        return sb.toString();
    }

    public String getMoney(String ids, String xn, String zh) {
        String sql = "SELECT " + ids + " FROM XSSFB WHERE XN=? AND XH=?";
        Record re = Db.findFirst(sql, xn, zh);
        int sum = 0;
        String[] idArr = ids.split(",");
        for (int i = 0; i < idArr.length; i++) {
            sum += Float.parseFloat(re.getStr(idArr[i]));
        }
        return String.valueOf(sum);
    }

    //查询是否未缴费
    public boolean validateIsNoPay(String ids, String xn, String zh) {
        String sql = "SELECT " + ids + " FROM YHSJB WHERE XN=? AND XH=?";
        Record re = Db.findFirst(sql, xn, zh);
        boolean isNoPay = true;
        if (re != null) {
            String[] idArr = ids.split(",");
            for (int i = 0; i < idArr.length; i++) {
                if (!re.getStr(idArr[i]).equals("0"))
                    isNoPay = false;
                break;
            }
        }
        return isNoPay;
    }

    //查询是否存在未支付订单
    public boolean noPayOrder(String xh) {
        String sql = "SELECT IDS FROM WPT_WXZF_SPECIAL_ORDER WHERE XH=? AND ORDER_STATE=?";
        Record re = Db.findFirst(sql, xh, MyWxpayConstant.ORDER_STATE_NOPAY);
        return re != null;
    }

    //查询是否存在未支付订单号
    public String noPayOrderNo(String xh) {
        String sql = "SELECT ORDER_NO FROM WPT_WXZF_SPECIAL_ORDER WHERE XH=? AND ORDER_STATE=?";
        Record re = Db.findFirst(sql, xh, MyWxpayConstant.ORDER_STATE_NOPAY);
        return re != null ? re.getStr("ORDER_NO") : "-1";
    }

    //查询关闭订单参数
    public String queryOutTradeNo(String order_no) {
        String outTradeNo = "";
        String sql = "SELECT OUT_TRADE_NO FROM WPT_WXZF_SPECIAL_ORDER WHERE ORDER_NO=?";
        Record re = Db.findFirst(sql, order_no);
        if (re != null) {
            outTradeNo = re.getStr("OUT_TRADE_NO");
        }
        return outTradeNo;
    }

    public NoPayOrderInfo getNoPayOrderInfo(String zh) {
        String sql = "SELECT T1.ORDER_NO,T1.SFXN,T1.TIME_START,T1.TOTAL_FEE,T1.ORDER_STATE,T2.ZYMC,T2.JGMC,T2.BJMC,T2.XM,T2.ZH,T2.ZJHM FROM WPT_WXZF_SPECIAL_ORDER T1 LEFT JOIN  WPT_YH T2 ON T1.XH=T2.ZH WHERE T1.ORDER_STATE=? AND T2.ZH=?";
        Record re = Db.findFirst(sql, MyWxpayConstant.ORDER_STATE_NOPAY, zh);
        NoPayOrderInfo noPayOrderInfo = null;
        if (re != null) {
            noPayOrderInfo = new NoPayOrderInfo();
            noPayOrderInfo.setBjmc(re.getStr("BJMC"));
            noPayOrderInfo.setDdbh(re.getStr("ORDER_NO"));
            noPayOrderInfo.setDdhj(re.getStr("TOTAL_FEE"));
            noPayOrderInfo.setJfxn(re.getStr("SFXN"));
            noPayOrderInfo.setSfzh(re.getStr("ZJHM"));
            noPayOrderInfo.setXdsj(MyUtil.getMyDate(re.getStr("TIME_START")));
            noPayOrderInfo.setXh(re.getStr("ZH"));
            noPayOrderInfo.setXm(re.getStr("XM"));
            noPayOrderInfo.setXymc(re.getStr("JGMC"));
            noPayOrderInfo.setZt(re.getStr("ORDER_STATE").equals(String.valueOf(MyWxpayConstant.ORDER_STATE_NOPAY)) ? "待支付" : "");
            noPayOrderInfo.setZymc(re.getStr("ZYMC"));
        }
        return noPayOrderInfo;
    }



    public Record queryJxzf(String order_no) {
        Record re = Db.findFirst("SELECT TOTAL_FEE,CODE_URL,ORDER_STATE FROM WPT_WXZF_SPECIAL_ORDER WHERE ORDER_NO=? AND ORDER_STATE=?", order_no, MyWxpayConstant.ORDER_STATE_NOPAY);
        return re;
    }
}
