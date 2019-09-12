package gka.pay.wxpay.controller;

import gka.pay.wxpay.*;
import gka.resource.properties.ProFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther ly
 * @Date 2019/9/3
 * @Describe
 */
public class WxPayTool {
    private static WXPayConfig wxPayConfig;
    private static WXPay wxPay;

    public WxPayTool(WXPayConfig wxPayConfig) {
        try {
            this.wxPayConfig = wxPayConfig;
            wxPay = new WXPay(this.wxPayConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 统一下单
     *
     * @param wxPayBean
     * @return
     * @throws Exception
     */
    public static Map[] unifiedOrderJSAPI(WxPayBean wxPayBean) throws Exception {
        Map[] mapArr = new Map[2];
        Map<String, String> map = new HashMap<String, String>();
        map.put("body", PayConstant.BODY);
        map.put("out_trade_no", wxPayBean.getOrderNo());
        map.put("total_fee", wxPayBean.getTotal_fee());
        map.put("spbill_create_ip", wxPayBean.getSpbill_create_ip());
        map.put("notify_url", PayConstant.NOTIFY_URL);
        map.put("trade_type", PayConstant.JSAPI);
        map.put("openid", wxPayBean.getOpenId());
        Map<String, String> result = wxPay.unifiedOrder(map);
        System.out.println(map);
        mapArr[0] = result;
        mapArr[1] = map;
        return mapArr;
    }

    /**
     * 提取下单结果报文，前端jsapi接口需要参数
     *
     * @param result
     * @return
     */
    public static Map<String, String> reqData(Map<String, String> result) {
        Map<String, String> reqData = null;
        try {
            reqData = new HashMap<String, String>();
            reqData.put("appId", result.get("appid"));
            reqData.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
            reqData.put("nonceStr", WXPayUtil.generateNonceStr());
            reqData.put("package", "prepay_id=" + result.get("prepay_id"));
            reqData.put("signType", WXPayConstants.HMACSHA256);
            String sign = WXPayUtil.generateSignature(reqData, ProFactory.use("gkean.properties").getStr("apiKey"), WXPayConstants.SignType.HMACSHA256);
            reqData.put("paySign", sign);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reqData;
    }

    /**
     * 提取订单记录数据
     */
    public static WxPayOrder fillOrder(Map<String, String> unifiedOrder, String ids, String ip) {
        WxPayOrder wxPayOrder = new WxPayOrder();
        wxPayOrder.setOut_trade_no(unifiedOrder.get("out_trade_no"));
        wxPayOrder.setIds(ids);
        wxPayOrder.setPay_type(unifiedOrder.get("trade_type"));
        wxPayOrder.setTotal_fee(unifiedOrder.get("total_fee"));
        wxPayOrder.setAppid(unifiedOrder.get("appid"));
        wxPayOrder.setMch_id(unifiedOrder.get("mch_id"));
        wxPayOrder.setOpenid(unifiedOrder.get("openid"));
        wxPayOrder.setPayIp(ip);
        wxPayOrder.setTime_start(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

        return wxPayOrder;
    }

    static class PayConstant {
        //
        static final String BODY = "科安";
        //支付异步回调地址
        static final String NOTIFY_URL = "http://www.kean.com.cn/wpt/pay/wxpay/con/wxPayCallBackController";
        //交易类型
        static final String JSAPI = "JSAPI";
    }
}
