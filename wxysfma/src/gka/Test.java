package gka;

import com.alibaba.fastjson.JSONObject;
import gka.dzfp.EleUtil;
import gka.dzfp.ElectronicInvoiceApi;
import gka.dzfp.bean.InvoiceEBillByCollegeBean;
import gka.dzfp.bean.ItemDetail;
import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    public static void main(String[] args) {
        InvoiceEBillByCollegeBean ie = new InvoiceEBillByCollegeBean();
        Date date = new Date();
        ie.setBusType(EleUtil.appId);
        String orNo = EleUtil.genBusNo();
        System.out.println("=============" + orNo);
        ie.setBusNo(orNo);
        ie.setPlaceCode("001");
//        ie.setBillCode("0101");
        ie.setBillCode("0401");
        ie.setChannel("11");
        ie.setChargePeriod(new SimpleDateFormat("yyyy").format(date));
        ie.setChargeDate(new SimpleDateFormat("yyyy-MM-dd").format(date));
        ie.setPayerType("1");
        ie.setCreditCode("");
        ie.setRecer("校长");
        ie.setIvcDate(new SimpleDateFormat("yyyy-MM-dd").format(date));
        ie.setStuName("李岩");
        ie.setOperator("校长");
        ie.setTotalAmt("100");
        ie.setStuNo("20183519");
        ie.setCollegeCode("xybm");
        ie.setCollegeName("学院名称");
        ie.setDeptCode("xbmc");
        ie.setDeptName("系别名称");
        ie.setProCode("zybm");
        ie.setProName("专业名称");
        ie.setClassCode("bjbm");
        ie.setClassName("班级名称");
        ie.setStuidCard("220403199004273117");
        ie.setPhone("18844092305");
        ie.setEmail("330883793@qq.com");
        ie.setNoticeMode("1");

//        ItemDetail[] itemDetails = new ItemDetail[2];
//        ItemDetail id = new ItemDetail();
//        id.setItemCode("04275702");
//        id.setItemName("研究生学费");
//        id.setItemStdCode("00206");
//        id.setCount("1");
//        id.setStandard("100");
//        id.setAmt("100");
//        itemDetails[0] = id;
//
//        ItemDetail id1 = new ItemDetail();
//        id1.setItemCode("04275713");
//        id1.setItemName("研究生住宿费");
//        id1.setItemStdCode("00003");
//        id1.setCount("1");
//        id1.setStandard("100");
//        id1.setAmt("100");
//        itemDetails[1] = id1;

        //往来
        ItemDetail[] itemDetails = new ItemDetail[1];
        ItemDetail id1 = new ItemDetail();
        id1.setItemCode("wl05");
        id1.setItemName("汪清往来");
        id1.setItemStdCode("00001");
        id1.setCount("1");
        id1.setStandard("100");
        id1.setAmt("100");
        itemDetails[0] = id1;
        ie.setItemDetail(itemDetails);

        ElectronicInvoiceApi.invoiceEBillByCollege(ie);
    }
}