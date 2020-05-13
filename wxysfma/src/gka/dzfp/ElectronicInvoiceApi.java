package gka.dzfp;

import com.alibaba.fastjson.JSONObject;
import gka.dzfp.bean.InvoiceEBillByCollegeBean;
import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

/**
 * 电子发票API
 */
public class ElectronicInvoiceApi {
    //单张票据开具接口地址
    private final static String invoiceEBillByCollege_url = "http://112.49.34.4:18001/colleges-proxy/api/college/invoiceEBillByCollege";
    private static EleUtil eleUtil = new EleUtil();

    /**
     * @param invoiceEBillByCollegeBean
     * @desc 单张票据开具接口
     */
    public static void invoiceEBillByCollege(InvoiceEBillByCollegeBean invoiceEBillByCollegeBean) {
        JSONObject requestBody = eleUtil.genRequestBody(invoiceEBillByCollegeBean);
        String s = HttpUtil.doPost(invoiceEBillByCollege_url, requestBody.toJSONString());
        JSONObject jsonObject = JSONObject.parseObject(s);
        Base64 base64 = new Base64();
        byte[] datas = base64.decode(jsonObject.getString("data"));
        try {
            String mess = new String(datas, "UTF-8");
            JSONObject js = JSONObject.parseObject(mess);
            System.out.println(js);
            byte[] messages = base64.decode(js.getString("message"));
            System.out.println(new String(messages, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
