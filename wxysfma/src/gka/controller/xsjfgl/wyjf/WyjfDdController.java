package gka.controller.xsjfgl.wyjf;

import com.alibaba.druid.util.StringUtils;
import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import gka.common.kit.IpKit;
import gka.common.kit.OrderCodeFactory;
import gka.controller.xsjfgl.MyUtil;
import gka.pay.wxpay.WXPayUtil;
import gka.pay.wxpay.controller.*;
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
            MyUtil.checkDate(returnInfo);
            if (returnInfo.getReturn_code().equals("666666")) {
                WptMaXSUserInfo userInfo = (WptMaXSUserInfo) getSession().getAttribute("wptMaXSUserInfo");
                String xh = userInfo.getZh();
                String order = WXPayUtil.generateOrder();
                String cliIp = IpKit.getRealIp(getRequest());

                String[] idArr = getRequest().getParameterValues("xmid[]");
                String sfxn = getPara("sfxn");
                if (idArr != null && !StringUtils.isEmpty(sfxn)) {
                    String ids = parseIdArr(idArr);
                    // String totalFee = "1";
                    String totalFee = cxTotalFee(idArr, xh, sfxn);
                    //查询是否没缴费
                    Record reVal = wyjfDao.queryTotalWjfByPay(xh, sfxn);

                    String val = genVal(idArr, reVal);
                    boolean pay = wyjfDao.validateIsNoPay(wyjfDao.queryTitle(), ids, val, sfxn, xh);
                    if (pay) {
                        //查询是否存在未缴费订单
                        boolean noPayOrder = wyjfDao.noPayOrder(xh);
                        if (!noPayOrder) {
                            //调用统一下单
                            WxPayTool wxPayTool = WxPayTool.getInstance();
                            Map[] arrs = wxPayTool.unifiedOrderNATIVE(new WxPayBean(order, totalFee, cliIp, ""));
                            Map<String, String> unifiedOrder = arrs[0];
                            if (unifiedOrder != null) {
                                String unifCode = unifiedOrder.get("return_code");
                                if (unifCode.equals("SUCCESS")) {
                                    String unifResultCode = unifiedOrder.get("result_code");
                                    if (unifResultCode.equals("SUCCESS")) {
                                        //订单入库
                                        WxPayOrder wxPayOrder = wxPayTool.fillOrder(arrs[1], ids, IpKit.getRealIp(getRequest()), xh, unifiedOrder.get("prepay_id"));
                                        wxPayOrder.setSfxn(sfxn);
                                        wxPayOrder.setOrderNo(OrderCodeFactory.getD(order));
                                        wxPayOrder.setCode_url(unifiedOrder.get("code_url"));

                                        wxPayDao.insertOrder(wxPayOrder, val);
                                        result.put("code_url", unifiedOrder.get("code_url"));
                                        result.put("oreder_no", wxPayOrder.getOrderNo());
                                        result.put("money", Double.parseDouble(totalFee) / 100);
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
                            returnInfo.setReturn_msg("存在未支付订单，请完成支付或关闭订单！");
                        }
                    } else {
                        returnInfo.setReturn_code("-5");
                        returnInfo.setReturn_msg("已经缴费！");
                    }
                } else {
                    returnInfo.setReturn_code("-7");
                    returnInfo.setReturn_msg("参数有误，请勿篡改订单信息！");
                }
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
    public void closeOrder() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        Map<String, String> reqData = null;
        try {
            String order_no = getPara("order_no");
            if (order_no != null || !"".equals(order_no)) {
                reqData = new HashMap<String, String>();
                Record re = wyjfDao.queryOutTradeNo(order_no);
                String out_trade_no = re.getStr("OUT_TRADE_NO");
                reqData.put("out_trade_no", out_trade_no);
                if (!out_trade_no.equals("")) {
                    String order_state = wyjfDao.queryOrderState(order_no);
                    if (order_state.equals(MyWxpayConstant.ORDER_STATE_NOPAY)) {
                        String pay_type = re.getStr("PAY_TYPE");
                        if (!pay_type.equals("yl")) {
                            WxPayTool wxPayTool = WxPayTool.getInstance();
                            wxPayTool.closeOrder(reqData);
                        }
                        //更改订单状态
                        WxPayDao.closeOrderDb(out_trade_no, "user");
                        returnInfo.setReturn_code("0");
                        returnInfo.setReturn_msg("success");
                    } else {
                        returnInfo.setReturn_code("-3");
                        returnInfo.setReturn_msg("该订单状态目前不可关闭！");
                    }
                } else {
                    returnInfo.setReturn_code("-2");
                    returnInfo.setReturn_msg("非法请求！");
                }

            } else {
                returnInfo.setReturn_code("-1");
                returnInfo.setReturn_msg("非法请求！");
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
     * 查询是否存在未支付订单
     */
    public void noPayOrder() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            WptMaXSUserInfo userInfo = (WptMaXSUserInfo) getSession().getAttribute("wptMaXSUserInfo");
            String xh = userInfo.getZh();
            String noPayOrder = wyjfDao.noPayOrderNo(xh);
            result.put("noPayOrder", noPayOrder);
            returnInfo.setReturn_code("0");
            returnInfo.setReturn_msg("success");

        } catch (Exception e) {
            returnInfo.setReturn_code("-999");
            returnInfo.setReturn_msg("支付服务繁忙，请稍后重试！");
            e.printStackTrace();
        }

        result.put("returnInfo", returnInfo);
        renderJson(result);
    }

    private String parseIdArr(String[] idArr) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < idArr.length; i++) {
            if (i < idArr.length - 1) {
                sb.append(idArr[i]);
                sb.append(",");
            } else {
                sb.append(idArr[i]);
            }
        }
        return sb.toString();
    }

    private String parseIdArrSql(String[] idArr) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < idArr.length; i++) {
            if (i < idArr.length - 1) {
                sb.append(idArr[i]);
                sb.append("+");
            } else {
                sb.append(idArr[i]);
                sb.append(" ZH");
            }
        }
        return sb.toString();
    }

    /**
     * 实际支付金额
     *
     * @param idArr
     * @param xh
     * @param xn
     * @return
     */
    private String cxTotalFee(String[] idArr, String xh, String xn) {
        Record record = wyjfDao.queryXnYjFyxx(wyjfDao.queryTitle(), xh, xn);
        double zh = 0;
        for (int i = 0; i < idArr.length; i++) {
            zh += Double.parseDouble(record.getStr(idArr[i]));
        }
        int zhIn = (int) (zh * 100);
        System.out.println("实际支付金额" + zhIn + "（单位分）");
        return String.valueOf(zhIn);
    }

    private String genVal(String[] id, Record re) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < id.length; i++) {
            if (i < id.length - 1) {
                sb.append(re.getStr(id[i]));
                sb.append(",");
            } else {
                sb.append(re.getStr(id[i]));
            }
        }
        return sb.toString();
    }
}
