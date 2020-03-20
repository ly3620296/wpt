package gka.pay.wxpay.controller;

import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import gka.controller.xsjfgl.wyjf.WyjfDao;
import gka.pay.wxpay.WXPay;
import gka.pay.wxpay.WXPayUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Clear
@ControllerBind(controllerKey = "/pay/wxpay/con/wxPayCallBackController")
public class WxPayCallBackController extends Controller {
    private static final String callBackString = "<xml>" +
            "<return_code><![CDATA[SUCCESS]]></return_code>" +
            "<return_msg><![CDATA[OK]]></return_msg>" +
            "</xml>";
    private WyjfDao wyjfDao = new WyjfDao();

    public void index() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(getRequest().getInputStream(), "UTF-8"));
            StringBuffer sf = new StringBuffer();
            String len;
            while ((len = br.readLine()) != null) {
                sf.append(len);
            }
            Map<String, String> repData = WXPayUtil.xmlToMap(sf.toString());
//            Map<String, String> repData = getTest();
            System.out.println(repData);
            WXPay wxPay = WxPayTool.getWxPay();
            //判断签名是否有效
            boolean signValid = wxPay.isResponseSignatureValid(repData);
            //签名无效 直接返回
            if (!signValid) {
                //通知微信
                renderText(callBackString);
                return;
            }
            //判断下单时候的金额是否和回调时候的金额一致 否则视为非法订单 直接返回
            String out_trade_no = repData.get("out_trade_no");
            if (!wyjfDao.preMoney(out_trade_no).equals(repData.get("total_fee"))) {
                //通知微信
                renderText(callBackString);
                //修改订单为非法订单
                wyjfDao.updateIllegalMoneyOrder(repData);
                return;
            }

            //通知微信
            renderText(callBackString);

            /**
             * 修改订单状态
             * 1. 修改订单表
             * 2. 修改缴费表
             */
            wyjfDao.updateNormalOrder(repData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Map<String, String> getTest() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("transaction_id", "4200000444201911039519361336");
        map.put("nonce_str", "oDBnvufbhwvJTa8P08l3YNesmOh3FXfD");
        map.put("bank_type", "CFT");
        map.put("openid", "o1gswuOe-4_AZqm1gipKyZGU2iZ8");
        map.put("sign", "F3A83A3CA48C25FC29E9B8A2CB80DBE7497C8217431A206BE7EE9A3A033E869C");
        map.put("fee_type", "CNY");
        map.put("mch_id", "1393091302");
        map.put("cash_fee", "1");
        map.put("out_trade_no", "1572762946905lliqtzn");
        map.put("appid", "wxdf9968af00e458e0");
        map.put("total_fee", "1");
        map.put("trade_type", "NATIVE");
        map.put("result_code", "SUCCESS");
        map.put("time_end", "20191103144056");
        map.put("is_subscribe", "Y");
        map.put("return_code", "SUCCESS");
        return map;
    }

}
