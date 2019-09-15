package gka.controller.module.wstyzf.xzf;

import com.alibaba.druid.util.StringUtils;
import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Record;
import gka.controller.login.WptUserInfo;
import gka.kit.IpKit;
import gka.pay.wxpay.WXPayConstants;
import gka.pay.wxpay.WXPayUtil;
import gka.pay.wxpay.controller.WxPayBean;
import gka.pay.wxpay.controller.WxPayDao;
import gka.pay.wxpay.controller.WxPayOrder;
import gka.pay.wxpay.controller.WxPayTool;
import gka.resource.properties.ProFactory;
import gka.system.ReturnInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ControllerBind(controllerKey = "/wstyzf/xzf")
public class XzfController extends Controller {
    private XzfDao xzfDao = new XzfDao();
    private WxPayDao wxPayDao = new WxPayDao();

    /**
     * 学杂费
     */
    public void index() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            WptUserInfo wptUserInfo = (WptUserInfo) getSession().getAttribute("wptUserInfo");
            String xh = wptUserInfo.getZh();
            //学杂费
            List<Record> xzfList = xzfDao.xzf(xh);
            List<Record> xzfXnxqList = xzfDao.xzfXnxq(xh);
            result.put("xzfList", xzfList);
            result.put("xzfXnxqList", xzfXnxqList);
            returnInfo.setReturn_code("0");
            returnInfo.setReturn_msg("success");
        } catch (Exception e) {
            returnInfo.setReturn_code("-999");
            returnInfo.setReturn_msg("服务繁忙，请稍后重试！");
            e.printStackTrace();
        }
        result.put("returnInfo", returnInfo);
        renderJson(result);
    }

    /**
     * 调用统一下单
     */
    public void zfXzf() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            WptUserInfo wptUserInfo = (WptUserInfo) getSession().getAttribute("wptUserInfo");
            String openId = wptUserInfo.getOpenId();
            String xh = wptUserInfo.getZh();
            if (!StringUtils.isEmpty(openId)) {
                String order = WXPayUtil.generateOrder();
                String cliIp = IpKit.getRealIp(getRequest());
                String totalFee = "1";
                String ids = getPara("arrId");
                //调用统一下单
                WxPayTool wxPayTool = WxPayTool.getInstance();
                Map[] arrs = wxPayTool.unifiedOrderJSAPI(new WxPayBean(order, totalFee, cliIp, openId));
                Map<String, String> unifiedOrder = arrs[0];
                if (unifiedOrder != null) {
                    String unifCode = unifiedOrder.get("return_code");
                    if (unifCode.equals("SUCCESS")) {
                        String unifResultCode = unifiedOrder.get("result_code");
                        if (unifResultCode.equals("SUCCESS")) {
                            System.out.println("%$##$#"+unifiedOrder);
                            //订单入库
                            WxPayOrder wxPayOrder = wxPayTool.fillOrder(arrs[1], ids, IpKit.getRealIp(getRequest()), xh,unifiedOrder.get("prepay_id"));
                            wxPayDao.insertOrder(wxPayOrder);
                            //解析h5所需参数
                            Map<String, String> reqData = wxPayTool.reqData(unifiedOrder);
                            result.putAll(reqData);
                            System.out.println(reqData);
                            returnInfo.setReturn_code("0");
                            returnInfo.setReturn_msg("success");
                        } else {
                            returnInfo.setReturn_code("-4");
                            returnInfo.setReturn_msg(unifiedOrder.get("err_code_des") == null ? "微信支付业务繁忙，请稍后重试！" : unifiedOrder.get("err_code_des"));
                        }
                    } else {
                        returnInfo.setReturn_code("-3");
                        returnInfo.setReturn_msg(unifiedOrder.get("return_msg"));
                    }

                } else {
                    returnInfo.setReturn_code("-2");
                    returnInfo.setReturn_msg("微信支付接口异常");
                }
            } else {
                returnInfo.setReturn_code("-1");
                returnInfo.setReturn_msg("请先绑定微信");
            }
        } catch (Exception e) {
            returnInfo.setReturn_code("-999");
            returnInfo.setReturn_msg("支付服务繁忙，请稍后重试！");
            e.printStackTrace();
        }
        result.put("returnInfo", returnInfo);
        renderJson(result);
    }

    /**
     * 重新支付未支付订单
     */
    public void rezfXzf() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        Map<String, String> reqData = null;
        try {
            reqData = new HashMap<String, String>();
            reqData.put("appId", "wxdf9968af00e458e0");
            reqData.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
            reqData.put("nonceStr", WXPayUtil.generateNonceStr());
            reqData.put("package", "prepay_id=wx151406053867561220b867831943408700");
            reqData.put("signType", WxPayTool.getWxPay().getSignType().toString());
            String sign = WXPayUtil.generateSignature(reqData, ProFactory.use("gkean.properties").getStr("apiKey"), WxPayTool.getWxPay().getSignType());
            reqData.put("paySign", sign);
            result.putAll(reqData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        returnInfo.setReturn_code("0");


        result.put("returnInfo", returnInfo);
        renderJson(result);
    }

}
