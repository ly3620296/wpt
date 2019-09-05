package gka.pay.wxpay.controller;

import gka.pay.wxpay.*;
import gka.resource.properties.ProFactory;

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

    public static Map<String, String> unifiedOrderJSAPI(WxPayBean wxPayBean) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("body", PayConstant.BODY);
        map.put("out_trade_no", wxPayBean.getOrderNo());
        map.put("total_fee", wxPayBean.getTotal_fee());
        map.put("spbill_create_ip", wxPayBean.getSpbill_create_ip());
        map.put("notify_url", PayConstant.NOTIFY_URL);
        map.put("trade_type", PayConstant.JSAPI);
        map.put("openid", wxPayBean.getOpenId());
        System.out.println(wxPay);
        Map<String, String> result = wxPay.unifiedOrder(map);
        return result;
    }

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

    static class PayConstant {
        //
        static final String BODY = "科安";
        //支付异步回调地址
        static final String NOTIFY_URL = "http://www.kean.com.cn/wpt/test.jsp";
        //交易类型
        static final String JSAPI = "JSAPI";
    }
}
