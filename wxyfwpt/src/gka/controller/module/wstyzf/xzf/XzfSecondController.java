package gka.controller.module.wstyzf.xzf;

import com.alibaba.druid.util.StringUtils;
import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import gka.controller.login.WptUserInfo;
import gka.kit.IpKit;
import gka.kit.OrderCodeFactory;
import gka.kit.ReKit;
import gka.pay.wxpay.WXPayUtil;
import gka.pay.wxpay.controller.*;
import gka.resource.properties.ProFactory;
import gka.system.ReturnInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ControllerBind(controllerKey = "/wstyzf/xzfSecond")
public class XzfSecondController extends Controller {
    private XzfSecondDao xzfDao = new XzfSecondDao();
    private WxPayDao wxPayDao = new WxPayDao();

    public void index() {
        ReturnInfo returnInfo = new ReturnInfo();
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            WptUserInfo userInfo = (WptUserInfo) getSession().getAttribute("wptUserInfo");
            String xh = userInfo.getZh();
            List<Record> titles = xzfDao.queryTitle();

            //未支付订单
            Record re1 = xzfDao.queryJxzf(xh);
            if (re1 != null) {
                Record re = xzfDao.jf(xh, re1.getStr("SFXN"), titles);
                Record reNormal = xzfDao.queryTotalNormalState(xh, re1.getStr("SFXN"), titles);
                List<JfInfo> jfInfoList = getJfinfo(reNormal, titles, re, re1.getStr("IDS"), re1.getStr("SFXN"));
                result.put("orderInfo", jfInfoList);
                result.put("payType", re1.getStr("PAY_TYPE"));
                result.put("noPayOrder", "yes");
                result.put("prepay_id", re1.getStr("PREPAY_ID"));
            } else {
                result.put("noPayOrder", "no");
            }

            //缴费记录
            List<Record> jfjl = getJfjl(titles, xh);
            List<Record> dataNormal = xzfDao.queryTotalNormal(xh, titles);
            result.put("titles", titles);
            result.put("data", jfjl);
            result.put("dataNormal", dataNormal);
            returnInfo.setReturn_code("0");
            returnInfo.setReturn_msg("success");

        } catch (Exception e) {
            e.printStackTrace();
            returnInfo.setReturn_code("-999");
            returnInfo.setReturn_msg("系统繁忙，请稍后重试");
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
                String newIds = newIds(ids);
                String sfxn = ids.split(",")[0].split("wow")[0];
//                String totalFee = cxTotalFee(parseIdArrSql(ids), xh, sfxn);
                if (!StringUtils.isEmpty(ids)) {
                    //查询是否没缴费
                    boolean pay = xzfDao.validateIsNoPay(newIds, sfxn, xh);
                    if (pay) {
                        //查询是否存在未缴费订单
                        boolean noPayOrder = xzfDao.noPayOrder(xh);
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
                                        WxPayOrder wxPayOrder = wxPayTool.fillOrder(arrs[1], newIds, IpKit.getRealIp(getRequest()), xh, unifiedOrder.get("prepay_id"), sfxn);
                                        wxPayOrder.setOrderNo(OrderCodeFactory.getD(order));
                                        wxPayDao.insertOrderSpecial(wxPayOrder);
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
                            returnInfo.setReturn_msg("存在未支付订单！");
                        }
                    } else {
                        returnInfo.setReturn_code("-5");
                        returnInfo.setReturn_msg("已经缴费！");
                    }
                } else {
                    returnInfo.setReturn_code("-7");
                    returnInfo.setReturn_msg("参数有误，请勿篡改订单信息！");
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


    //缴费记录
    private List<Record> getJfjl(List<Record> titles, String xh) {
        List<Record> totals = xzfDao.queryTotal(titles, xh);
        return totals;
    }

    private String newIds(String ids) {
        String[] temp = ids.split(",");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < temp.length; i++) {
            if (i < temp.length - 1) {
                sb.append(temp[i].split("wow")[1]);
                sb.append(",");
            } else {
                sb.append(temp[i].split("wow")[1]);
            }

        }
        return sb.toString();
    }

    private List<JfInfo> getJfinfo(Record normal, List<Record> titles, Record re, String ids, String xn) {
        List<JfInfo> jfInfos = new ArrayList<JfInfo>();
        String[] xmid = ids.split(",");
        for (Record t : titles) {
            boolean flag = false;
            JfInfo jfInfo = new JfInfo();
            String sfjf = t.getStr("SFBX");
            jfInfo.setXnxq(xn);
            if (sfjf.equals("1")) {
                jfInfo.setXmmc(t.getStr("JFXMMC"));
                jfInfo.setXmid(t.getStr("JFXMID"));
                jfInfo.setSfbx(sfjf);
                for (int j = 0; j < xmid.length; j++) {
                    if (jfInfo.getXmid().equals(xmid[j])) {
                        jfInfo.setJfje(re.getStr(jfInfo.getXmid()));
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    jfInfo.setJfje("0");
                } else {
                    jfInfos.add(jfInfo);
                }
            } else {
                jfInfo.setXmmc(t.getStr("JFXMMC") + "（选交）");
                jfInfo.setXmid(t.getStr("JFXMID"));
                jfInfo.setSfbx(sfjf);
                for (int j = 0; j < xmid.length; j++) {
                    if (jfInfo.getXmid().equals(xmid[j])) {
                        jfInfo.setJfje(re.getStr(jfInfo.getXmid()));
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    jfInfo.setJfje("0");
                } else {
                    jfInfos.add(jfInfo);
                }
            }
        }
        return jfInfos;
    }

    /**
     * 重新支付未支付订单
     */
    public void rezfXzf() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        Map<String, String> reqData = null;
        try {
            String prepay_id = getPara("prepay_id");
            if (prepay_id != null || !"".equals(prepay_id)) {
                reqData = new HashMap<String, String>();
                reqData.put("appId", ProFactory.use("gkean.properties").getStr("appId"));
                reqData.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
                reqData.put("nonceStr", WXPayUtil.generateNonceStr());
                reqData.put("package", "prepay_id=" + prepay_id);
                reqData.put("signType", WxPayTool.getWxPay().getSignType().toString());
                String sign = WXPayUtil.generateSignature(reqData, ProFactory.use("gkean.properties").getStr("apiKey"), WxPayTool.getWxPay().getSignType());
                reqData.put("paySign", sign);
                result.putAll(reqData);
                returnInfo.setReturn_code("0");
                returnInfo.setReturn_msg("success");
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
     * 主动关闭订单
     */
    public void closeOrder() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        Map<String, String> reqData = null;
        try {
            String prepay_id = getPara("prepay_id");
            if (prepay_id != null || !"".equals(prepay_id)) {
                reqData = new HashMap<String, String>();
                String out_trade_no = xzfDao.queryOutTradeNo(prepay_id);
                if (!out_trade_no.equals("")) {
                    reqData.put("out_trade_no", out_trade_no);
                    String order_state = xzfDao.queryOrderState(prepay_id);
                    if (order_state.equals(MyWxpayConstant.ORDER_STATE_NOPAY)) {
                        WxPayTool wxPayTool = WxPayTool.getInstance();
                        Map<String, String> map = wxPayTool.closeOrder(reqData);
                        System.out.println("result" + map);
                        //更改订单状态
                        WxPayDao.closeOrderSpecialDb(out_trade_no, "user");
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

    private String parseIdArrSql(String ids) {
        String sql = ids.replaceAll(",", "+");
        sql += " ZH";
        return sql;
    }

    /**
     * 实际支付金额
     *
     * @param idSql
     * @param xh
     * @param xn
     * @return
     */
    private String cxTotalFee(String idSql, String xh, String xn) {
        String zh = "0";
        String sql = "SELECT " + idSql + " FROM XSSFB WHERE XH=? AND XN=?";
        Record re = Db.findFirst(sql, xh, xn);
        if (re != null) {
            zh = re.getStr("ZH");
            double zhD = Double.parseDouble(zh);
            zh = String.valueOf((int) (zhD * 100));
        }
        System.out.println("实际支付金额" + zh + "（单位分）");
        return zh;
    }
}
