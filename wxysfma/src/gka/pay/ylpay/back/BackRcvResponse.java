package gka.pay.ylpay.back;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import gka.controller.xsjfgl.wyjf.WyjfDao;
import gka.pay.ylpay.DemoBase;
import gka.pay.ylpay.sdk.AcpService;
import gka.pay.ylpay.sdk.LogUtil;
import gka.pay.ylpay.sdk.SDKConstants;
import org.apache.commons.io.IOUtils;


/**
 * 重要：联调测试时请仔细阅读注释！
 * <p/>
 * 产品：跳转网关支付产品<br>
 * 功能：后台通知接收处理示例 <br>
 * 日期： 2015-09<br>
 * <p/>
 * 版权： 中国银联<br>
 * 声明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己需要，按照技术文档编写。该代码仅供参考，不提供编码，性能，规范性等方面的保障<br>
 * 该接口参考文档位置：open.unionpay.com帮助中心 下载  产品接口规范  《网关支付产品接口规范》，<br>
 * 《平台接入接口规范-第5部分-附录》（内包含应答码接口规范，全渠道平台银行名称-简码对照表），
 * 测试过程中的如果遇到疑问或问题您可以：1）优先在open平台中查找答案：
 * 调试过程中的问题或其他问题请在 https://open.unionpay.com/ajweb/help/faq/list 帮助中心 FAQ 搜索解决方案
 * 测试过程中产生的6位应答码问题疑问请在https://open.unionpay.com/ajweb/help/respCode/respCodeList 输入应答码搜索解决方案
 * 2） 咨询在线人工支持： open.unionpay.com注册一个用户并登陆在右上角点击“在线客服”，咨询人工QQ测试支持。
 * 交易说明：	前台类交易成功才会发送后台通知。后台类交易（有后台通知的接口）交易结束之后成功失败都会发通知。
 * 为保证安全，涉及资金类的交易，收到通知后请再发起查询接口确认交易成功。不涉及资金的交易可以以通知接口respCode=00判断成功。
 * 未收到通知时，查询接口调用时间点请参照此FAQ：https://open.unionpay.com/ajweb/help/faq/list?id=77&level=0&from=0
 */

@ControllerBind(controllerKey = "/notice")
public class BackRcvResponse extends Controller {
    WyjfDao wyjfDao = new WyjfDao();

    public void backRcvResponse() {
        HttpServletRequest req = getRequest();

        LogUtil.writeLog("BackRcvResponse接收后台通知开始");
        String encoding = req.getParameter(SDKConstants.param_encoding);
        // 获取银联通知服务器发送的后台通知参数
        Map<String, String> reqParam = getAllRequestParam(req);
        LogUtil.printRequestLog(reqParam);

        //重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
        if (!AcpService.validate(reqParam, encoding)) {
            LogUtil.writeLog("验证签名结果[失败].");
            //验签失败，需解决验签问题

        } else {
            LogUtil.writeLog("验证签名结果[成功].");
            //【注：为了安全验签成功才应该写商户的成功处理逻辑】交易成功，更新商户订单状态
            String respCode = reqParam.get("respCode");
            System.out.println("====respCode====" + respCode);
            if (respCode.equals("00")) {
                //返回给银联服务器http 200  状态码
//                    resp.getWriter().print("ok");
                renderJson("ok");
                //记录数据库
                reqParam.put("time_end", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                String orderId = reqParam.get("orderId");
                //验证重复收到通知
                if (wyjfDao.queryIsBack(orderId)) {
                    wyjfDao.updateNormalOrderYl(reqParam);
                }
            }
        }
        LogUtil.writeLog("BackRcvResponse接收后台通知结束");

    }


    public void frontRcvResponse() {
        HttpServletRequest req = getRequest();
        HttpServletResponse resp = getResponse();
        LogUtil.writeLog("FrontRcvResponse前台接收报文返回开始");

        String encoding = req.getParameter(SDKConstants.param_encoding);
        LogUtil.writeLog("返回报文中encoding=[" + encoding + "]");
        String pageResult = "";
        String code = "";
        String msg = "";
        String orderId = "";
        String txnAmt = "";
        if (DemoBase.encoding.equalsIgnoreCase(encoding)) {
            pageResult = "/utf8_result.jsp";
        } else {
            pageResult = "/gbk_result.jsp";
        }
        Map<String, String> respParam = getAllRequestParam(req);

        // 打印请求报文
        LogUtil.printRequestLog(respParam);

        Map<String, String> valideData = null;
        if (null != respParam && !respParam.isEmpty()) {
            Iterator<Map.Entry<String, String>> it = respParam.entrySet()
                    .iterator();
            valideData = new HashMap<String, String>(respParam.size());
            while (it.hasNext()) {
                Map.Entry<String, String> e = it.next();
                String key = (String) e.getKey();
                String value = (String) e.getValue();
                try {
                    value = new String(value.getBytes(encoding), encoding);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
//                page.append("<tr><td width=\"30%\" align=\"right\">" + key
//                        + "(" + key + ")</td><td>" + value + "</td></tr>");
                valideData.put(key, value);
            }
        }
        if (!AcpService.validate(valideData, encoding)) {
//            page.append("<tr><td width=\"30%\" align=\"right\">验证签名结果</td><td>失败</td></tr>");
            code = "-1";
            msg = "验证签名失败";
            LogUtil.writeLog("验证签名结果[失败].");
        } else {
//            page.append("<tr><td width=\"30%\" align=\"right\">验证签名结果</td><td>成功</td></tr>");
            LogUtil.writeLog("验证签名结果[成功].");
            System.out.println(valideData.get("orderId")); //其他字段也可用类似方式获取
            code = valideData.get("respCode");
            msg = valideData.get("respMsg");
            orderId = valideData.get("orderId");
            txnAmt = String.valueOf(Double.parseDouble(valideData.get("txnAmt")) / 100);
            //判断respCode=00、A6后，对涉及资金类的交易，请再发起查询接口查询，确定交易成功后更新数据库。
        }
        req.setAttribute("code", code);
        req.setAttribute("msg", msg);
        req.setAttribute("orderId", orderId);
        req.setAttribute("txnAmt", txnAmt);
        try {
            req.getRequestDispatcher(pageResult).forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LogUtil.writeLog("FrontRcvResponse前台接收报文返回结束");
    }


    /**
     * 获取请求参数中所有的信息
     * 当商户上送frontUrl或backUrl地址中带有参数信息的时候，
     * 这种方式会将url地址中的参数读到map中，会导多出来这些信息从而致验签失败，这个时候可以自行修改过滤掉url中的参数或者使用getAllRequestParamStream方法。
     *
     * @param request
     * @return
     */
    public static Map<String, String> getAllRequestParam(
            final HttpServletRequest request) {
        Map<String, String> res = new HashMap<String, String>();
        Enumeration<?> temp = request.getParameterNames();
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                String value = request.getParameter(en);
                res.put(en, value);
                // 在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
                if (res.get(en) == null || "".equals(res.get(en))) {
                    // System.out.println("======为空的字段名===="+en);
                    res.remove(en);
                }
            }
        }
        return res;
    }

    /**
     * 获取请求参数中所有的信息。
     * 非struts可以改用此方法获取，好处是可以过滤掉request.getParameter方法过滤不掉的url中的参数。
     * struts可能对某些content-type会提前读取参数导致从inputstream读不到信息，所以可能用不了这个方法。理论应该可以调整struts配置使不影响，但请自己去研究。
     * 调用本方法之前不能调用req.getParameter("key");这种方法，否则会导致request取不到输入流。
     *
     * @param request
     * @return
     */
    public static Map<String, String> getAllRequestParamStream(
            final HttpServletRequest request) {
        Map<String, String> res = new HashMap<String, String>();
        try {
            String notifyStr = new String(IOUtils.toByteArray(request.getInputStream()), DemoBase.encoding);
            LogUtil.writeLog("收到通知报文：" + notifyStr);
            String[] kvs = notifyStr.split("&");
            for (String kv : kvs) {
                String[] tmp = kv.split("=");
                if (tmp.length >= 2) {
                    String key = tmp[0];
                    String value = URLDecoder.decode(tmp[1], DemoBase.encoding);
                    res.put(key, value);
                }
            }
        } catch (UnsupportedEncodingException e) {
            LogUtil.writeLog("getAllRequestParamStream.UnsupportedEncodingException error: " + e.getClass() + ":" + e.getMessage());
        } catch (IOException e) {
            LogUtil.writeLog("getAllRequestParamStream.IOException error: " + e.getClass() + ":" + e.getMessage());
        }
        return res;
    }
}