package gka.pay.wxpay.controller;

import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import gka.kit.IpKit;
import gka.pay.wxpay.*;

import java.util.Map;

@Clear
@ControllerBind(controllerKey = "/pay/wxpay/con/wxPayCon")
public class WxPayController extends Controller {

    public void index() {
        try {
            String order = WXPayUtil.generateOrder();
            String openId = "o1gswuOe-4_AZqm1gipKyZGU2iZ8";
            String cliIp = IpKit.getRealIp(getRequest());
            String totalFee = "1";
            //调用统一下单
            Map<String, String> result = WxPayTool.unifiedOrderJSAPI(new WxPayBean(order, totalFee, cliIp, openId));
            System.out.println(WXPayUtil.mapToXml(result));
            //解析h5所需参数
            Map<String, String> reqData = WxPayTool.reqData(result);
            renderJson(reqData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
