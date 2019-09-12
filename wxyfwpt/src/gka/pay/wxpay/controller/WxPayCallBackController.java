package gka.pay.wxpay.controller;

import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import gka.kit.IpKit;
import gka.pay.wxpay.WXPayUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

@Clear
@ControllerBind(controllerKey = "/pay/wxpay/con/wxPayCallBackController")
public class WxPayCallBackController extends Controller {

    public void index() {
        try {
            System.out.println("$$$$$$$$$$$$$$$$$$$$$$###########");
            BufferedReader br = new BufferedReader(new InputStreamReader(getRequest().getInputStream(), "UTF-8"));
            StringBuffer sf = new StringBuffer();
            String len;
            while ((len = br.readLine()) != null) {
                sf.append(len);

            }
            Map<String, String> map = WXPayUtil.xmlToMap(sf.toString());
            System.out.println("$$$$$$$$$$$$$$$$$##$$%^^" + map);
            renderText("<xml>" +
                    "<return_code><![CDATA[SUCCESS]]></return_code>" +
                    "<return_msg><![CDATA[OK]]></return_msg>" +
                    "</xml>");



        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
