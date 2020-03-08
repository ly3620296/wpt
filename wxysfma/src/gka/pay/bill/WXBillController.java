package gka.pay.bill;

import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import gka.pay.wxpay.*;

import java.util.HashMap;
import java.util.Map;

@ControllerBind(controllerKey = "/downloadBill")
public class WXBillController extends Controller {
    public void downloadBill() {
        try {
            String aa = WXPayUtil.generateNonceStr();
            System.out.println(aa);
            Map<String, String> map = new HashMap<String, String>();
            WXPayConfig wxPayConfig = new MyWxConfig();
            WXPay wxPay = new WXPay(wxPayConfig);

            map.put("bill_date", "20191103");
            map.put("bill_type", "SUCCESS");
            Map<String, String> mapNew = wxPay.downloadBill(map);
            System.out.println("return_code------" + mapNew.get("return_code"));
            System.out.println("mapNew------" + mapNew);
            System.out.println("mapNewdata------" + mapNew.get("data"));
//          臧建平
//          4367420942960300949
//            System.out.println("WXPayUtil.mapToXml(mapNew)======" + WXPayUtil.mapToXml(mapNew));
            renderJson(mapNew);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
