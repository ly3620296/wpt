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
    private static WxPayTool wxPayTool;
    private static WXPayConfig wxPayConfig;
    private static WXPay wxPay;

    private WxPayTool(WXPayConfig wxPayConfig) {
        try {
            this.wxPayConfig = wxPayConfig;
            wxPay = new WXPay(this.wxPayConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static WxPayTool getInstance() {
        if (wxPayTool == null) {
            synchronized (WxPayTool.class) {
                if (wxPayTool == null) {
                    wxPayTool = new WxPayTool(new MyWxConfig());
                }
            }
        }
        return wxPayTool;
    }

    /**
     * 统一下单
     *
     * @param wxPayBean
     * @return
     * @throws Exception
     */
    public Map[] unifiedOrderJSAPI(WxPayBean wxPayBean) throws Exception {
        Map[] mapArr = new Map[2];
        Map<String, String> map = new HashMap<String, String>();
        map.put("body", PayConstant.BODY);
        map.put("out_trade_no", wxPayBean.getOrderNo());
        map.put("total_fee", wxPayBean.getTotal_fee());
        map.put("spbill_create_ip", wxPayBean.getSpbill_create_ip());
        map.put("notify_url", PayConstant.NOTIFY_URL);
        map.put("trade_type", PayConstant.JSAPI);
        map.put("openid", wxPayBean.getOpenId());
        Map<String, String> result = null;
        try {
            result = wxPay.unifiedOrder(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public Map<String, String> reqData(Map<String, String> result) {
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
    public WxPayOrder fillOrder(Map<String, String> unifiedOrder, String ids, String ip, String zh, String prepay_id) {
        WxPayOrder wxPayOrder = new WxPayOrder();
        wxPayOrder.setOut_trade_no(unifiedOrder.get("out_trade_no"));
        wxPayOrder.setIds(ids);
        wxPayOrder.setPay_type(unifiedOrder.get("trade_type"));
        wxPayOrder.setTotal_fee(unifiedOrder.get("total_fee"));
        wxPayOrder.setAppid(unifiedOrder.get("appid"));
        wxPayOrder.setMch_id(unifiedOrder.get("mch_id"));
        wxPayOrder.setOpenid(unifiedOrder.get("openid"));
        wxPayOrder.setPREPAY_ID(prepay_id);
        wxPayOrder.setPayIp(ip);
        wxPayOrder.setTime_start(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        wxPayOrder.setXh(zh);
        return wxPayOrder;
    }

    /**
     * @param reqData
     * @return 订单查询
     * @throws Exception
     */

    public Map<String, String> orderQuery(Map<String, String> reqData) throws Exception {
        return wxPay.orderQuery(reqData);
    }

    /**
     * @param reqData
     * @return 关闭订单
     * @throws Exception
     */

    public Map<String, String> closeOrder(Map<String, String> reqData) throws Exception {
        return wxPay.closeOrder(reqData);
    }


    public static WXPay getWxPay() {
        return wxPay;
    }

    static class PayConstant {
        //
        static final String BODY = "科安";
        //支付异步回调地址
        static final String NOTIFY_URL = ProFactory.use("gkean.properties").getStr("domain_name") + "/wpt/pay/wxpay/con/wxPayCallBackController";
        //交易类型
        static final String JSAPI = "JSAPI";
    }

    static enum OrderState {
        //支付成功
        SUCCESS,
        //转入退款
        REFUND,
        //未支付
        NOTPAY,
        //已关闭
        CLOSED,
        //已撤销（付款码支付）
        REVOKED,
        //用户支付中（付款码支付）
        USERPAYING,
        //支付失败(其他原因，如银行返回失败)
        PAYERROR
    }
}
