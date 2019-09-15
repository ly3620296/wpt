package gka.pay.wxpay.controller;

import com.jfinal.plugin.activerecord.Db;

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
}
