package gka.dzfp;

import com.alibaba.fastjson.JSONObject;
import gka.dzfp.bean.InvoiceEBillByCollegeBean;
import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

/**
 * 电子发票API
 */
public class ElectronicInvoiceApi {
    //服务地址
//    private final static String dzfp_domian = "http://112.49.34.4:18001/colleges-proxy/api/college/";
    private final static String dzfp_domian = "http://112.49.34.4:8001/colleges-proxy/api/college/";
    //单张票据开具接口名
    private final static String invoiceEBillByCollege_url = "invoiceEBillByCollege";

    private static EleUtil eleUtil = new EleUtil();

    private static FpglDao fpglDao = new FpglDao();

    /**
     * @param invoiceEBillByCollegeBean
     * @desc 单张票据开具接口
     */
    public static void invoiceEBillByCollege(InvoiceEBillByCollegeBean invoiceEBillByCollegeBean, FpglBean fpglBean) {
        JSONObject requestBody = eleUtil.genRequestBody(invoiceEBillByCollegeBean);
        String rcbwMw = requestBody.getString("data");
        Base64 base64 = new Base64();
        String rMsg = "";
        String returnCode = "";
        try {
            String rc = new String(base64.decode(rcbwMw), "utf-8");
            if (rc != null && rc.getBytes("UTF-8").length > 4000) {
                rc = rc.substring(0, 2500);
            }
            fpglBean.setRCBW(rc);

            String s = HttpUtil.doPost(dzfp_domian + invoiceEBillByCollege_url, requestBody.toJSONString());
            JSONObject jsonObject = JSONObject.parseObject(s);

            byte[] datas = base64.decode(jsonObject.getString("data"));

            String mess = new String(datas, "UTF-8");
            JSONObject cc = JSONObject.parseObject(mess);

            if (cc != null) {
                returnCode = cc.getString("result");
                byte[] messages = base64.decode(cc.getString("message"));
                String returnMsg = new String(messages, "UTF-8");
                JSONObject jsRms = JSONObject.parseObject(returnMsg);
                if ("S0000".equals(returnCode)) {
                    //电子票据代码
                    String eBillCode = jsRms.getString("eBillCode");
                    //电子票据号码
                    String eBillNo = jsRms.getString("eBillNo");
                    //电子校验码
                    String checkCode = jsRms.getString("checkCode");

                    if (returnMsg != null && returnMsg.getBytes("UTF-8").length > 4000) {
                        returnMsg = returnMsg.substring(0, 2500);
                    }

                    fpglBean.setCCBW(returnMsg);
                    fpglBean.setEBILLCODE(eBillCode);
                    fpglBean.setEBILLNO(eBillNo);
                    fpglBean.setCHECKCODE(checkCode);
                    rMsg = "success";
                } else {
                    if (returnMsg.getBytes("UTF-8").length > 2000) {
                        returnMsg = returnMsg.substring(0, 600);
                    }
                    rMsg = returnMsg;
                }
            }
        } catch (Exception e) {
            returnCode = "-999";
            rMsg = e.getMessage();
            e.printStackTrace();
        }
        fpglBean.setCODE(returnCode);
        fpglBean.setMSG(rMsg);
        fpglDao.insertSendDzfp(fpglBean);
    }


}
