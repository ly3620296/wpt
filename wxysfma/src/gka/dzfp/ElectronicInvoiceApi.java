package gka.dzfp;

import com.alibaba.fastjson.JSONObject;
import gka.dzfp.bean.InvoiceEBillByCollegeBean;

/**
 * 电子发票API
 */
public class ElectronicInvoiceApi {
    //单张票据开具接口地址
    private String invoiceEBillByCollege_url = "http://[ip]:[port]/[service]/api/college/[接口服务标识]";
    EleUtil eleUtil = new EleUtil();

    /**
     * @param invoiceEBillByCollegeBean
     * @desc 单张票据开具接口
     */
    public void invoiceEBillByCollege(InvoiceEBillByCollegeBean invoiceEBillByCollegeBean) {
        JSONObject requestBody = eleUtil.genRequestBody(invoiceEBillByCollegeBean);
    }
}
