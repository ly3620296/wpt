package gka.pay.ylpay.api;

import com.alibaba.druid.util.StringUtils;
import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import gka.common.kit.IpKit;
import gka.common.kit.OrderCodeFactory;
import gka.controller.xsjfgl.MyUtil;
import gka.controller.xsjfgl.wyjf.WyjfDao;
import gka.pay.wxpay.WXPayUtil;
import gka.pay.wxpay.controller.*;
import gka.pay.ylpay.DemoBase;
import gka.pay.ylpay.sdk.AcpService;
import gka.pay.ylpay.sdk.LogUtil;
import gka.pay.ylpay.sdk.SDKConfig;
import gka.resource.properties.ProKit;
import gka.system.ReturnInfo;
import gka.xsjfgl.login.WptMaXSUserInfo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerBind(controllerKey = "/pay/ylpay/api")
public class Form_6_2_FrontConsume extends Controller {
    private WxPayDao wxPayDao = new WxPayDao();
    private WyjfDao wyjfDao = new WyjfDao();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

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

                String cliIp = IpKit.getRealIp(getRequest());

                String[] idArr = getPara("xmid").split(",");
                String sfxn = getPara("sfxn");
                if (idArr != null && !StringUtils.isEmpty(sfxn)) {
                    String ids = parseIdArr(idArr);
                    String totalFee = "";
                    if (ProKit.use("gkean.properties").getBoolean("testPay")) {
                        totalFee = "1";
                    } else {
                        totalFee = cxTotalFee(idArr, xh, sfxn);
                    }
//
//                    //查询是否没缴费
                    Record reVal = wyjfDao.queryTotalWjfByPay(xh, sfxn);
                    String val = genVal(idArr, reVal);
                    boolean pay = wyjfDao.validateIsNoPay(wyjfDao.queryTitle(), ids, val, sfxn, xh);
                    if (pay) {
                        //查询是否存在未缴费订单
                        boolean noPayOrder = wyjfDao.noPayOrder(xh);
                        if (!noPayOrder) {
                            String order = OrderCodeFactory.getDYl(WXPayUtil.generateOrder());
                            String orderId = WXPayUtil.generateOrder();
                            String timeStart = simpleDateFormat.format(new Date());
                            //订单入库
                            WxPayOrder wxPayOrder = new WxPayOrder();
                            wxPayOrder.setXh(xh);
                            wxPayOrder.setOut_trade_no(order);
                            wxPayOrder.setIds(ids);
                            wxPayOrder.setPay_type("yl");
                            wxPayOrder.setTotal_fee(totalFee);
                            wxPayOrder.setMch_id(SDKConfig.getConfig().getMerId());
                            wxPayOrder.setPayIp(cliIp);
                            wxPayOrder.setTime_start(timeStart);
                            wxPayOrder.setSfxn(sfxn);
                            wxPayOrder.setOrderNo(OrderCodeFactory.getD(orderId));
                            wxPayDao.insertOrderYl(wxPayOrder, val);
                            //调用统一下单
                            frontConsume(order, timeStart, totalFee);
                            return;

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
            e.printStackTrace();
            returnInfo.setReturn_code("-999");
            returnInfo.setReturn_msg("支付服务繁忙，请稍后重试！");
        }
        result.put("returnInfo", returnInfo);
        renderJson(result);
    }


    /**
     * 调用统一下单
     */
    public void jxjf() {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            MyUtil.checkDate(returnInfo);
            if (returnInfo.getReturn_code().equals("666666")) {
                WptMaXSUserInfo userInfo = (WptMaXSUserInfo) getSession().getAttribute("wptMaXSUserInfo");
                String xh = userInfo.getZh();
                String order_no = getPara("order_no");
                String[] idArr = getPara("xmid").split(",");
                String sfxn = getPara("sfxn");
                if (idArr != null && !StringUtils.isEmpty(sfxn)) {
                    String ids = parseIdArr(idArr);
                    String totalFee = "";
                    if (ProKit.use("gkean.properties").getBoolean("testPay")) {
                        totalFee = "1";
                    } else {
                        totalFee = String.valueOf((int) (Double.parseDouble(wyjfDao.queryTotalFee(order_no)) * 100));
                    }
//                    //查询是否没缴费
                    Record reVal = wyjfDao.queryTotalWjfByPay(xh, sfxn);
                    String val = genVal(idArr, reVal);
                    boolean pay = wyjfDao.validateIsNoPay(wyjfDao.queryTitle(), ids, val, sfxn, xh);
                    if (pay) {
                        String orderId = getOrderId(order_no);
                        String timeStart = simpleDateFormat.format(new Date());
                        //调用统一下单
                        frontConsume(orderId, timeStart, totalFee);
                        return;
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
            e.printStackTrace();
            returnInfo.setReturn_code("-999");
            returnInfo.setReturn_msg("支付服务繁忙，请稍后重试！");
        }
        result.put("returnInfo", returnInfo);
        renderJson(result);
    }

    public void frontConsume(String oId, String timeStart, String totalFee) throws IOException {
        HttpServletResponse resp = getResponse();
        resp.setContentType("text/html; charset=" + DemoBase.encoding);
        //前台页面传过来的
        //交易金额
        String txnAmt = totalFee;
//        20200427224024
        //订单ID
        String orderId = oId;
        //下单时间
        String txnTime = timeStart;

        Map<String, String> requestData = new HashMap<String, String>();

        /***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
        requestData.put("version", DemoBase.version);              //版本号，全渠道默认值
        requestData.put("encoding", DemoBase.encoding);              //字符集编码，可以使用UTF-8,GBK两种方式
        requestData.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
        requestData.put("txnType", "01");                          //交易类型 ，01：消费
        requestData.put("txnSubType", "01");                          //交易子类型， 01：自助消费
        requestData.put("bizType", "000201");                      //业务类型，B2C网关支付，手机wap支付
        requestData.put("channelType", "07");                      //渠道类型，这个字段区分B2C网关支付和手机wap支付；07：PC,平板  08：手机

        /***商户接入参数***/
        requestData.put("merId", SDKConfig.getConfig().getMerId());                              //商户号码，请改成自己申请的正式商户号或者open上注册得来的777测试商户号
        requestData.put("accessType", "0");                          //接入类型，0：直连商户
        requestData.put("orderId", orderId);             //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则
        requestData.put("txnTime", txnTime);        //订单发送时间，取系统时间，格式为yyyyMMddHHmmss，必须取当前时间，否则会报txnTime无效
        requestData.put("currencyCode", "156");                      //交易币种（境内商户一般是156 人民币）
        requestData.put("txnAmt", txnAmt);                              //交易金额，单位分，不要带小数点
        //requestData.put("reqReserved", "透传字段");        		      //请求方保留域，如需使用请启用即可；透传字段（可以实现商户自定义参数的追踪）本交易的后台通知,对本交易的交易状态查询交易、对账文件中均会原样返回，商户可以按需上传，长度为1-1024个字节。出现&={}[]符号时可能导致查询接口应答报文解析失败，建议尽量只传字母数字并使用|分割，或者可以最外层做一次base64编码(base64编码之后出现的等号不会导致解析失败可以不用管)。
        requestData.put("riskRateInfo", "{commodityName=计划财务处缴费}");

        //前台通知地址 （需设置为外网能访问 http https均可），支付成功后的页面 点击“返回商户”按钮的时候将异步通知报文post到该地址
        //如果想要实现过几秒中自动跳转回商户页面权限，需联系银联业务申请开通自动返回商户权限
        //异步通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 消费交易 商户通知
        requestData.put("frontUrl", DemoBase.frontUrl);
        //后台通知地址（需设置为【外网】能访问 http https均可），支付成功后银联会自动将异步通知报文post到商户上送的该地址，失败的交易银联不会发送后台通知
        //后台通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 消费交易 商户通知
        //注意:1.需设置为外网能访问，否则收不到通知    2.http https均可  3.收单后台通知后需要10秒内返回http200或302状态码
        //    4.如果银联通知服务器发送通知后10秒内未收到返回状态码或者应答码非http200，那么银联会间隔一段时间再次发送。总共发送5次，每次的间隔时间为0,1,2,4分钟。
        //    5.后台通知地址如果上送了带有？的参数，例如：http://abc/web?a=b&c=d 在后台通知处理程序验证签名之前需要编写逻辑将这些字段去掉再验签，否则将会验签失败
        requestData.put("backUrl", DemoBase.backUrl);
        // 订单超时时间。
        // 超过此时间后，除网银交易外，其他交易银联系统会拒绝受理，提示超时。 跳转银行网银交易如果超时后交易成功，会自动退款，大约5个工作日金额返还到持卡人账户。
        // 此时间建议取支付时的北京时间加15分钟。
        // 超过超时时间调查询接口应答origRespCode不是A6或者00的就可以判断为失败。
//        requestData.put("payTimeout", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date().getTime() + 15 * 60 * 1000));

        /**请求参数设置完毕，以下对请求参数进行签名并生成html表单，将表单写入浏览器跳转打开银联页面**/
        Map<String, String> submitFromData = AcpService.sign(requestData, DemoBase.encoding);  //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。

        String requestFrontUrl = SDKConfig.getConfig().getFrontRequestUrl();  //获取请求银联的前台地址：对应属性文件acp_sdk.properties文件中的acpsdk.frontTransUrl
        String html = AcpService.createAutoFormHtml(requestFrontUrl, submitFromData, DemoBase.encoding);   //生成自动跳转的Html表单

        LogUtil.writeLog("打印请求HTML，此为请求报文，为联调排查问题的依据：" + html);
        //将生成的html写到浏览器中完成自动跳转打开银联支付页面；这里调用signData之后，将html写到浏览器跳转到银联页面之前均不能对html中的表单项的名称和值进行修改，如果修改会导致验签不通过
        resp.getWriter().write(html);
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

    /**
     * 查询订单号
     *
     * @param order_no
     * @return
     */
    private String getOrderId(String order_no) {
        Record first = Db.findFirst("SELECT OUT_TRADE_NO FROM WPT_WXZF_SPECIAL_ORDER WHERE ORDER_NO=?", order_no);
        String str = first.getStr("OUT_TRADE_NO");
        return str;
    }
}
