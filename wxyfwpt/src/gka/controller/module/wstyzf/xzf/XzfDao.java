package gka.controller.module.wstyzf.xzf;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;
import gka.pay.wxpay.controller.MyWxpayConstant;
import gka.system.ReturnInfo;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @Auther ly
 * @Date 2019/9/4
 * @Describe
 */
public class XzfDao {

    public List<Record> xzf(String xh) {
        String sql = "SELECT T1.ID,T1.XH,T1.SFJF,T1.XNXQ,T2.XMMC,T2.XMJE,T2.SFBX FROM WPT_YSFY T1 LEFT JOIN WPT_JFXM T2 " +
                "ON T1.XMID = T2.XMID  WHERE T1.XH=? AND T2.XMLXID=? ORDER BY T1.XNXQ DESC";
        List<Record> sfzhList = Db.find(sql, xh, MyWxpayConstant.XMLXID_XZF);
        return sfzhList;
    }

    public List<Record> xzfXnxq(String xh) {
        String sql = "SELECT DISTINCT(T1.XNXQ) FROM WPT_YSFY T1 LEFT JOIN WPT_JFXM T2 " +
                "ON T1.XMID = T2.XMID  WHERE T1.XH=? AND T2.XMLXID=? ORDER BY T1.XNXQ DESC";
        List<Record> xzfXnxq = Db.find(sql, xh, MyWxpayConstant.XMLXID_XZF);
        return xzfXnxq;
    }

    public boolean validateIsPay(String ids) {
        boolean flag = true;
        String sql = "SELECT SFJF FROM  WPT_YSFY  WHERE ID IN(" + ids + ")";
        List<Record> list = Db.find(sql);
        for (Record re : list) {
            if (re.getStr("SFJF").equals(MyWxpayConstant.ORDER_YSFY_JF)) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    public static String preMoney(String orderNo) {
        String preMoney = "";
        String sql = "SELECT TOTAL_FEE FROM  WPT_WXZF_ORDER WHERE OUT_TRADE_NO=?";
        Record re = Db.findFirst(sql, orderNo);
        if (re != null) {
            preMoney = re.getStr("TOTAL_FEE");
        }
        return preMoney;
    }

    public static void updateIllegalMoneyOrder(final Map<String, String> repData) {
        Db.tx(new IAtom() {
            @Override
            public boolean run() throws SQLException {
                String sql = "UPDATE WPT_WXZF_ORDER SET TIME_END=?,ORDER_STATE=?,RETURN_CODE=?,RESULT_CODE=?,TRANSACTION_ID=? WHERE OUT_TRADE_NO=?";
                int upOrder = Db.update(sql, repData.get("time_end"), MyWxpayConstant.ORDER_STATE_ILLEGALMONEY, MyWxpayConstant.RETURN_CODE_ERROR, MyWxpayConstant.RESULT_CODE_ILLEGALMONEY, repData.get("transaction_id"), repData.get("out_trade_no"));
                sql = "";
                int upYsf = Db.update(sql);
                return upOrder * upYsf >= 1;
            }
        });
    }

    public static void updateNormalOrder(final Map<String, String> repData) {
        Db.tx(new IAtom() {
            @Override
            public boolean run() throws SQLException {
                String sql = "UPDATE WPT_WXZF_ORDER SET TIME_END=?,ORDER_STATE=?,RETURN_CODE=?,RESULT_CODE=?,TRANSACTION_ID=? WHERE OUT_TRADE_NO=?";
                int upOrder = Db.update(sql, repData.get("time_end"), MyWxpayConstant.ORDER_STATE_PAY, MyWxpayConstant.RETURN_CODE_SUCCESS, repData.get("result_code"), repData.get("transaction_id"), repData.get("out_trade_no"));
                String ids = getOrderIds(repData.get("out_trade_no"));
                int upYsf = 0;
                if (ids != null && !"".equals(ids)) {
                    sql = "UPDATE WPT_YSFY SET SFJF=? WHERE ID IN(" + ids + ")";
                    upYsf = Db.update(sql, MyWxpayConstant.ORDER_YSFY_JF);
                }
                return upOrder * upYsf >= 1;
            }
        });
    }

    public static String getOrderIds(String out_trade_no) {
        String sql = "SELECT IDS FROM WPT_WXZF_ORDER WHERE OUT_TRADE_NO=?";
        Record re = Db.findFirst(sql, out_trade_no);
        String ids = "";
        if (re != null) {
            ids = re.getStr("IDS");
        }
        return ids;

    }

    public Object[] queryNoPayOrder(String zh) {
        Object[] arr = null;
        String sql = "SELECT IDS,PREPAY_ID FROM WPT_WXZF_ORDER WHERE XH=? AND ORDER_STATE=?";
        Record re = Db.findFirst(sql, zh, MyWxpayConstant.ORDER_STATE_NOPAY);
        if (re != null) {
            arr = new Object[2];
            String ids = re.getStr("IDS");
            sql = "SELECT  T3.XNXQ,T3.XMMC,T3.XMJE,T4.XMLXMC FROM (SELECT T1.ID,T1.XH,T1.SFJF,T1.XNXQ,T2.XMMC,T2.XMJE,T2.SFBX,T2.XMLXID " +
                    "FROM WPT_YSFY  T1 LEFT JOIN WPT_JFXM T2 " +
                    "ON T1.XMID = T2.XMID  WHERE T1.XH=? AND T2.XMLXID=? AND ID IN(" + ids + ")) T3 LEFT JOIN WPT_JFXMLX T4 " +
                    "ON T3.XMLXID=T4.XMLXID";
            List<Record> list = Db.find(sql, zh, MyWxpayConstant.XMLXID_XZF);
            arr[0] = re.getStr("PREPAY_ID");
            arr[1] = list;
        }
        return arr;
    }

    public boolean noPayOrder(String zh) {
        String sql = "SELECT IDS,PREPAY_ID FROM WPT_WXZF_ORDER WHERE XH=? AND ORDER_STATE=?";
        Record re = Db.findFirst(sql, zh, MyWxpayConstant.ORDER_STATE_NOPAY);
        return re != null;
    }
    public String queryOutTradeNo(String prepay_id) {
        String outTradeNo="";
        String sql = "SELECT OUT_TRADE_NO FROM WPT_WXZF_ORDER WHERE PREPAY_ID=?";
        Record re = Db.findFirst(sql, prepay_id);
        if(re!=null){
            outTradeNo=re.getStr("OUT_TRADE_NO");
        }
        return outTradeNo;
    }



    public static Long changeDate(String date) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date));
        return calendar.getTimeInMillis();
    }

    public static void checkDate(ReturnInfo info) throws Exception {
        Record re = Db.findFirst("select * from wptma_jfsj");
        if (re != null) {
            String startDate = re.getStr("start_time") == null ? "" : re.getStr("start_time").toString();
            String endDate = re.getStr("end_time") == null ? "" : re.getStr("end_time").toString();
            if (!startDate.equals("") && !endDate.equals("")) {
                long nowDate = System.currentTimeMillis();
                long start = changeDate(startDate);
                long end = changeDate(endDate);
                if (nowDate < start) {
                    info.setReturn_code("-0003");
                    info.setReturn_msg("缴费尚未开始，请于" + startDate + "后再来缴费!");
                } else if (nowDate > end) {
                    info.setReturn_code("-0004");
                    info.setReturn_msg("网上缴费时间已过,请联系老师进行线下缴费!");
                }else {
                    info.setReturn_code("666666");
                    info.setReturn_msg("");
                }
            } else {
                info.setReturn_code("-0002");
                info.setReturn_msg("获取缴费日期失败，请联系管理员!");
            }
        } else {
            info.setReturn_code("-0001");
            info.setReturn_msg("获取缴费日期失败，请联系管理员!");
        }
    }

}
