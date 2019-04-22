package gka.pay;

import gka.pay.wxpay.MyWxConfig;
import gka.pay.wxpay.WXPay;
import gka.pay.wxpay.WXPayConfig;
import gka.pay.wxpay.WXPayUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther ly
 * @Date 2019/4/15
 * @Describe
 */
public class aa {
    public static void main(String[] args) throws Exception {
//        String aa = WXPayUtil.generateNonceStr();
//        System.out.println(aa);
        Map<String, String> map = new HashMap<String, String>();
        WXPayConfig wxPayConfig = new MyWxConfig();
        WXPay wxPay = new WXPay(wxPayConfig);
        map.put("body", "科安");
        map.put("out_trade_no", WXPayUtil.generateOrder());
        map.put("total_fee", "1");
        map.put("spbill_create_ip", "222.168.8.114");
        map.put("notify_url", "http://www.kean.com.cn/wpt/test.jsp");
        map.put("trade_type", "JSAPI");
        map.put("openid", "o1gswuOe-4_AZqm1gipKyZGU2iZ8");
        Map<String, String> mapNew = wxPay.unifiedOrder(map);
        System.out.println(WXPayUtil.mapToXml(mapNew));
    }
}
