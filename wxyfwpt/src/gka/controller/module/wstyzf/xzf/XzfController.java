package gka.controller.module.wstyzf.xzf;

import com.alibaba.druid.util.StringUtils;
import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Record;
import gka.controller.login.WptUserInfo;
import gka.kit.IpKit;
import gka.pay.wxpay.WXPayUtil;
import gka.pay.wxpay.controller.WxPayBean;
import gka.pay.wxpay.controller.WxPayDao;
import gka.pay.wxpay.controller.WxPayOrder;
import gka.pay.wxpay.controller.WxPayTool;
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
            if (!StringUtils.isEmpty(openId)) {
                String order = WXPayUtil.generateOrder();
                String cliIp = IpKit.getRealIp(getRequest());
                String totalFee = "1";
                String ids = getPara("arrId");
                //调用统一下单
//
                Map[] arrs = WxPayTool.unifiedOrderJSAPI(new WxPayBean(order, totalFee, cliIp, openId));
                Map<String, String> unifiedOrder = arrs[0];
                if (unifiedOrder != null) {
                    String unifCode = unifiedOrder.get("return_code");
                    if (unifCode.equals("SUCCESS")) {
                        String unifResultCode = unifiedOrder.get("result_code");
                        if (unifResultCode.equals("SUCCESS")) {
                            //订单入库
                            WxPayOrder wxPayOrder = WxPayTool.fillOrder(arrs[1], ids, IpKit.getRealIp(getRequest()));
                            wxPayDao.insertOrder(wxPayOrder);
                            //解析h5所需参数
                            Map<String, String> reqData = WxPayTool.reqData(unifiedOrder);
                            result.putAll(reqData);

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
}
