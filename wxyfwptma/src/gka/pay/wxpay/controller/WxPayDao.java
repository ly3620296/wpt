package gka.pay.wxpay.controller;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther ly
 * @Date 2019/9/10
 * @Describe
 */
public class WxPayDao {

    public void insertOrder(WxPayOrder wxPayOrder) {
        String sql = "INSERT INTO WPT_WXZF_SPECIAL_ORDER (XH,OUT_TRADE_NO,IDS,PAY_TYPE,TOTAL_FEE,APPID,MCH_ID,OPENID,PAYIP,TIME_START,ORDER_STATE,PREPAY_ID,SFXN,ORDER_NO,CODE_URL) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Db.update(sql, wxPayOrder.getXh(), wxPayOrder.getOut_trade_no(), wxPayOrder.getIds(), wxPayOrder.getPay_type(), wxPayOrder.getTotal_fee(), wxPayOrder.getAppid(),
                wxPayOrder.getMch_id(), wxPayOrder.getOpenid(), wxPayOrder.getPayIp(), wxPayOrder.getTime_start(), MyWxpayConstant.ORDER_STATE_NOPAY, wxPayOrder.getPREPAY_ID(), wxPayOrder.getSfxn(), wxPayOrder.getOrderNo(), wxPayOrder.getCode_url());
    }


    public static void closeOrderDb(String out_trade_no, String who) {
        String state = orderState(out_trade_no);
        //未支付 或异常订单 将被关闭
        if (state.equals(MyWxpayConstant.ORDER_STATE_NOPAY) || state.equals(MyWxpayConstant.ORDER_STATE_ILLEGALMONEY)) {
            SimpleDateFormat sp = new SimpleDateFormat("yyyyMMddHHmmss");
            String sql = "UPDATE WPT_WXZF_SPECIAL_ORDER SET TIME_END=?,ORDER_STATE=?,RETURN_CODE=?,RESULT_CODE=? WHERE OUT_TRADE_NO=? ";
            if (who.equals("sys")) {
                Db.update(sql, sp.format(new Date()), MyWxpayConstant.ORDER_STATE_CLOSE, MyWxpayConstant.RETURN_CODE_ERROR, MyWxpayConstant.RESULT_CODE_CLOSE, out_trade_no);
            } else if (who.equals("user")) {
                Db.update(sql, sp.format(new Date()), MyWxpayConstant.ORDER_STATE_CLOSE, MyWxpayConstant.RETURN_CODE_ERROR, MyWxpayConstant.RESULT_CODE_CLOSE_USER, out_trade_no);
            }

        }
    }

    public static String orderState(String out_trade_no) {
        String sql = "SELECT ORDER_STATE FROM WPT_WXZF_SPECIAL_ORDER WHERE OUT_TRADE_NO=?";
        Record re = Db.findFirst(sql, out_trade_no);
        return re == null ? "" : re.getStr("ORDER_STATE");
    }

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


}
