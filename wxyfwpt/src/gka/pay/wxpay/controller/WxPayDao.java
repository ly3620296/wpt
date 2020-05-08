package gka.pay.wxpay.controller;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Auther ly
 * @Date 2019/9/10
 * @Describe
 */
public class WxPayDao {

    public void insertOrder(WxPayOrder wxPayOrder) {
        String sql = "INSERT INTO WPT_WXZF_ORDER (XH,OUT_TRADE_NO,IDS,PAY_TYPE,TOTAL_FEE,APPID,MCH_ID,OPENID,PAYIP,TIME_START,ORDER_STATE,PREPAY_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        Db.update(sql, wxPayOrder.getXh(), wxPayOrder.getOut_trade_no(), wxPayOrder.getIds(), wxPayOrder.getPay_type(), wxPayOrder.getTotal_fee(), wxPayOrder.getAppid(),
                wxPayOrder.getMch_id(), wxPayOrder.getOpenid(), wxPayOrder.getPayIp(), wxPayOrder.getTime_start(), MyWxpayConstant.ORDER_STATE_NOPAY, wxPayOrder.getPREPAY_ID());
    }

    public void insertOrderSpecial(WxPayOrder wxPayOrder,String val) {
        String sql = "INSERT INTO WPT_WXZF_SPECIAL_ORDER (XH,OUT_TRADE_NO,IDS,PAY_TYPE,TOTAL_FEE,APPID,MCH_ID,OPENID,PAYIP,TIME_START,ORDER_STATE,PREPAY_ID,SFXN,ORDER_NO,CODE_URL,PAY_VAL) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Db.update(sql, wxPayOrder.getXh(), wxPayOrder.getOut_trade_no(), wxPayOrder.getIds(), wxPayOrder.getPay_type(), wxPayOrder.getTotal_fee(), wxPayOrder.getAppid(),
                wxPayOrder.getMch_id(), wxPayOrder.getOpenid(), wxPayOrder.getPayIp(), wxPayOrder.getTime_start(), MyWxpayConstant.ORDER_STATE_NOPAY, wxPayOrder.getPREPAY_ID(), wxPayOrder.getSfxn(), wxPayOrder.getOrderNo(), "",val);
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





    public static void closeOrderSpecialDb(String out_trade_no, String who) {
        String state = orderSpecialState(out_trade_no);
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

    public static String orderSpecialState(String out_trade_no) {
        String sql = "SELECT ORDER_STATE FROM WPT_WXZF_SPECIAL_ORDER WHERE OUT_TRADE_NO=?";
        Record re = Db.findFirst(sql, out_trade_no);
        return re == null ? "" : re.getStr("ORDER_STATE");
    }
    
    public static String orderState(String out_trade_no) {
        String sql = "SELECT ORDER_STATE FROM WPT_WXZF_ORDER WHERE OUT_TRADE_NO=?";
        Record re = Db.findFirst(sql, out_trade_no);
        return re == null ? "" : re.getStr("ORDER_STATE");
    }
}
