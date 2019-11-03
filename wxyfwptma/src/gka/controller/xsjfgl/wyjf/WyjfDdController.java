package gka.controller.xsjfgl.wyjf;

import com.alibaba.druid.util.StringUtils;
import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Record;
import gka.common.kit.IpKit;
import gka.pay.wxpay.WXPayUtil;
import gka.pay.wxpay.controller.WxPayBean;
import gka.pay.wxpay.controller.WxPayDao;
import gka.pay.wxpay.controller.WxPayOrder;
import gka.pay.wxpay.controller.WxPayTool;
import gka.resource.properties.ProFactory;
import gka.system.ReturnInfo;
import gka.xsjfgl.login.WptMaXSUserInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ControllerBind(controllerKey = "/xsjfgl/wyjfDd")
public class WyjfDdController extends Controller {
    private WxPayDao wxPayDao = new WxPayDao();
    private WyjfDao wyjfDao = new WyjfDao();


    /**
     * 调用统一下单
     */
    public void zfXzf() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            WptMaXSUserInfo userInfo = (WptMaXSUserInfo) getSession().getAttribute("wptMaXSUserInfo");
            String openId = userInfo.getOpenId();
            String xh = userInfo.getZh();
            if (!StringUtils.isEmpty(openId)) {
                String order = WXPayUtil.generateOrder();
                String cliIp = IpKit.getRealIp(getRequest());
                String totalFee = "1";
                String ids = getPara("sfxmId");
                //查询是否没缴费
                boolean pay = wyjfDao.validateIsPay(ids);
                if (pay) {
                    //查询是否存在未缴费订单
                    boolean noPayOrder = wyjfDao.noPayOrder(xh);
                    if (!noPayOrder) {
                        //调用统一下单
                        WxPayTool wxPayTool = WxPayTool.getInstance();
                        Map[] arrs = wxPayTool.unifiedOrderJSAPI(new WxPayBean(order, totalFee, cliIp, openId));
                        Map<String, String> unifiedOrder = arrs[0];
                        if (unifiedOrder != null) {
                            String unifCode = unifiedOrder.get("return_code");
                            if (unifCode.equals("SUCCESS")) {
                                String unifResultCode = unifiedOrder.get("result_code");
                                if (unifResultCode.equals("SUCCESS")) {
                                    //订单入库
                                    WxPayOrder wxPayOrder = wxPayTool.fillOrder(arrs[1], ids, IpKit.getRealIp(getRequest()), xh, unifiedOrder.get("prepay_id"));
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
                        returnInfo.setReturn_code("-6");
                        returnInfo.setReturn_msg("存在未支付订单，请刷新页面！");
                    }
                } else {
                    returnInfo.setReturn_code("-5");
                    returnInfo.setReturn_msg("已经缴费！");
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
//    public void rezfXzf() {
//        Map<String, Object> result = new HashMap<String, Object>();
//        ReturnInfo returnInfo = new ReturnInfo();
//        Map<String, String> reqData = null;
//        try {
//            String prepay_id = getPara("prepay_id");
//            if (prepay_id != null || !"".equals(prepay_id)) {
//                reqData = new HashMap<String, String>();
//                reqData.put("appId", ProFactory.use("gkean.properties").getStr("appId"));
//                reqData.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
//                reqData.put("nonceStr", WXPayUtil.generateNonceStr());
//                reqData.put("package", "prepay_id=" + prepay_id);
//                reqData.put("signType", WxPayTool.getWxPay().getSignType().toString());
//                String sign = WXPayUtil.generateSignature(reqData, ProFactory.use("gkean.properties").getStr("apiKey"), WxPayTool.getWxPay().getSignType());
//                reqData.put("paySign", sign);
//                result.putAll(reqData);
//                returnInfo.setReturn_code("0");
//                returnInfo.setReturn_msg("success");
//            } else {
//                returnInfo.setReturn_code("-1");
//                returnInfo.setReturn_msg("非法请求！");
//            }
//
//        } catch (Exception e) {
//            returnInfo.setReturn_code("-999");
//            returnInfo.setReturn_msg("支付服务繁忙，请稍后重试！");
//            e.printStackTrace();
//        }
//
//        result.put("returnInfo", returnInfo);
//        renderJson(result);
//    }

    /**
     * 主动关闭订单
     */
//    public void closeOrder() {
//        Map<String, Object> result = new HashMap<String, Object>();
//        ReturnInfo returnInfo = new ReturnInfo();
//        Map<String, String> reqData = null;
//        try {
//            String prepay_id = getPara("prepay_id");
//            if (prepay_id != null || !"".equals(prepay_id)) {
//                reqData = new HashMap<String, String>();
//                String out_trade_no = xzfDao.queryOutTradeNo(prepay_id);
//                reqData.put("out_trade_no", out_trade_no);
//                if (!out_trade_no.equals("")) {
//                    WxPayTool wxPayTool = WxPayTool.getInstance();
//                    wxPayTool.closeOrder(reqData);
//                    //更改订单状态
//                    WxPayDao.closeOrderDb(out_trade_no, "user");
//                }
//
//                returnInfo.setReturn_code("0");
//                returnInfo.setReturn_msg("success");
//            } else {
//                returnInfo.setReturn_code("-1");
//                returnInfo.setReturn_msg("非法请求！");
//            }
//
//        } catch (Exception e) {
//            returnInfo.setReturn_code("-999");
//            returnInfo.setReturn_msg("支付服务繁忙，请稍后重试！");
//            e.printStackTrace();
//        }
//
//        result.put("returnInfo", returnInfo);
//        renderJson(result);
//    }
}