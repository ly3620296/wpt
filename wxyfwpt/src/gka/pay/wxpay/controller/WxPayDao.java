package gka.pay.wxpay.controller;

import com.jfinal.plugin.activerecord.Db;

/**
 * @Auther ly
 * @Date 2019/9/10
 * @Describe
 */
public class WxPayDao {

    public void insertOrder(WxPayOrder wxPayOrder) {
        String sql = "insert into wpt_wxzf_order (out_trade_no,ids,pay_type,total_fee,appid,mch_id,openid,payIp,time_start,isCallBack) values (?,?,?,?,?,?,?,?,?,?)";
        Db.update(sql, wxPayOrder.getOut_trade_no(), wxPayOrder.getIds(), wxPayOrder.getPay_type(), wxPayOrder.getTotal_fee(), wxPayOrder.getAppid(),
                wxPayOrder.getMch_id(), wxPayOrder.getOpenid(), wxPayOrder.getPayIp(), wxPayOrder.getTime_start(), "0");
    }
}
