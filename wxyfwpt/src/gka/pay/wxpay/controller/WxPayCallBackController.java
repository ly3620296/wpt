package gka.pay.wxpay.controller;

import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import gka.controller.module.wstyzf.xzf.XzfDao;
import gka.controller.module.wstyzf.xzf.XzfSecondDao;
import gka.dzfp.SendDzfp;
import gka.dzfp.ThreadPoolUtil;
import gka.kit.IpKit;
import gka.pay.wxpay.WXPay;
import gka.pay.wxpay.WXPayConstants;
import gka.pay.wxpay.WXPayUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

@Clear
@ControllerBind(controllerKey = "/pay/wxpay/con/wxPayCallBackController")
public class WxPayCallBackController extends Controller {
    private static final String callBackString = "<xml>" +
            "<return_code><![CDATA[SUCCESS]]></return_code>" +
            "<return_msg><![CDATA[OK]]></return_msg>" +
            "</xml>";
    private XzfSecondDao xzfSecondDao = new XzfSecondDao();

    public void index() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(getRequest().getInputStream(), "UTF-8"));
            StringBuffer sf = new StringBuffer();
            String len;
            while ((len = br.readLine()) != null) {
                sf.append(len);

            }
            Map<String, String> repData = WXPayUtil.xmlToMap(sf.toString());
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
            if (!xzfSecondDao.preMoney(out_trade_no).equals(String.valueOf(Double.parseDouble(repData.get("total_fee")) / 100))) {
                //通知微信
                renderText(callBackString);
                //修改订单为非法订单
//                XzfDao.updateIllegalMoneyOrder(repData);
                if (xzfSecondDao.queryIsBack(out_trade_no)) {
                    xzfSecondDao.updateIllegalMoneyOrder(repData);
                }

                return;
            }

            //通知微信
            renderText(callBackString);

            /**
             * 修改订单状态
             * 1. 修改订单表
             * 2. 修改缴费表
             */
//            XzfDao.updateNormalOrder(repData);
            if (xzfSecondDao.queryIsBack(out_trade_no)) {
                xzfSecondDao.updateNormalOrder(repData);
                //开票
                ThreadPoolUtil.execute(new SendDzfp(out_trade_no));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
