package gka.controller.xsjf;

import com.alibaba.druid.util.StringUtils;
import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import gka.common.kit.IpKit;
import gka.pay.wxpay.WXPayUtil;
import gka.pay.wxpay.controller.WxPayBean;
import gka.pay.wxpay.controller.WxPayDao;
import gka.pay.wxpay.controller.WxPayOrder;
import gka.pay.wxpay.controller.WxPayTool;
import gka.system.ReturnInfo;
import gka.xsjfgl.login.WptMaXSUserInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther ly
 * @Date 2019/10/2
 * @Describe
 */
@ControllerBind(controllerKey = "/xsjf")
public class XsjfController extends Controller {
    private WxPayDao wxPayDao = new WxPayDao();

    public void index() {
        System.out.println("-----------------");
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            WptMaXSUserInfo wptUserInfo = (WptMaXSUserInfo) getSession().getAttribute("wptMaXSUserInfo");
            String openId = wptUserInfo.getOpenId();
            String xh = wptUserInfo.getZh();
            if (!StringUtils.isEmpty(openId)) {
                String order = WXPayUtil.generateOrder();
                String cliIp = IpKit.getRealIp(getRequest());
                String totalFee = "1";
//                String ids = getPara("arrId");
                //查询是否没缴费
//                boolean pay = wxPayDao.validateIsPay(ids);
                boolean pay = true;
                if (pay) {
                    //查询是否存在未缴费订单
//                    boolean noPayOrder = wxPayDao.noPayOrder(xh);
                    boolean noPayOrder = false;
                    if (!noPayOrder) {
                        //调用统一下单
                        WxPayTool wxPayTool = WxPayTool.getInstance();
                        Map[] arrs = wxPayTool.unifiedOrderNATIVE(new WxPayBean(order, totalFee, cliIp, openId));
                        Map<String, String> unifiedOrder = arrs[0];
                        System.out.println(unifiedOrder);
//                        if (unifiedOrder != null) {
//                            String unifCode = unifiedOrder.get("return_code");
//                            if (unifCode.equals("SUCCESS")) {
//                                String unifResultCode = unifiedOrder.get("result_code");
//                                if (unifResultCode.equals("SUCCESS")) {
//                                    //订单入库
//                                    WxPayOrder wxPayOrder = wxPayTool.fillOrder(arrs[1], ids, IpKit.getRealIp(getRequest()), xh, unifiedOrder.get("prepay_id"));
//                                    wxPayDao.insertOrder(wxPayOrder);
////                                    //加入缓存 过期校验 关闭订单
////                                    OrderCheck.orders.add(wxPayOrder);
//                                    //解析h5所需参数
//                                    Map<String, String> reqData = wxPayTool.reqData(unifiedOrder);
//                                    result.putAll(reqData);
//                                    System.out.println(reqData);
//                                    returnInfo.setReturn_code("0");
//                                    returnInfo.setReturn_msg("success");
//                                } else {
//                                    returnInfo.setReturn_code("-4");
//                                    returnInfo.setReturn_msg(unifiedOrder.get("err_code_des") == null ? "微信支付业务繁忙，请稍后重试！" : unifiedOrder.get("err_code_des"));
//                                }
//                            } else {
//                                returnInfo.setReturn_code("-3");
//                                returnInfo.setReturn_msg(unifiedOrder.get("return_msg"));
//                            }
//
//                        } else {
//                            returnInfo.setReturn_code("-2");
//                            returnInfo.setReturn_msg("微信支付接口异常");
//                        }
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
}
