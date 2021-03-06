package gka.thread;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 小鹏鹏 on 2020/2/6.
 */
public class Bill {
    private String date;//﻿交易时间
    private String appId;//公众账号ID
    private String mch_id;//商户号
    private String subMch_id;//子商户号 特约商户号
    private String device_info;//设备号
    private String weiXinOrderNo;//微信订单号
    private String mchOrderNo;//商户订单号
    private String userId;//用户标识
    private String type;//交易类型
    private String status;//交易状态
    private String bank;//付款银行
    private String currency;//货币种类
    private String amount;//总金额
    private String envelopeAmount;//企业红包金额 代金券金额
    private String name;//商品名称
    private String packet;//商户数据包
    private String poundage;//手续费
    private String rate;//费率
    private String orderAmount; //订单金额

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getSubMch_id() {
        return subMch_id;
    }

    public void setSubMch_id(String subMch_id) {
        this.subMch_id = subMch_id;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getWeiXinOrderNo() {
        return weiXinOrderNo;
    }

    public void setWeiXinOrderNo(String weiXinOrderNo) {
        this.weiXinOrderNo = weiXinOrderNo;
    }

    public String getMchOrderNo() {
        return mchOrderNo;
    }

    public void setMchOrderNo(String mchOrderNo) {
        this.mchOrderNo = mchOrderNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getEnvelopeAmount() {
        return envelopeAmount;
    }

    public void setEnvelopeAmount(String envelopeAmount) {
        this.envelopeAmount = envelopeAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPacket() {
        return packet;
    }

    public void setPacket(String packet) {
        this.packet = packet;
    }

    public String getPoundage() {
        return poundage;
    }

    public void setPoundage(String poundage) {
        this.poundage = poundage;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }


    //date 为账单时间 格式 2018-12-25
    //result  接口返回数据
    public static List<Bill> analyze(String result, String date) {
        List<Bill> bills = new ArrayList<Bill>();
        String tradeMsg = result.substring(result.indexOf("`")); //去标题
        String tradeInfo = tradeMsg.substring(0, tradeMsg.indexOf("总")).replaceFirst(date, "").replaceAll("`", "");
        ;// 去掉汇总数据
        String[] tradeArray = tradeInfo.split(date); //通过交易时间分隔   订单数
        for (String trade : tradeArray) {
            String[] order = trade.split(",");
            Bill bill = new Bill();
            bill.setDate(date + order[0]);
            bill.setAppId(order[1]);
            bill.setMch_id(order[2]);
            bill.setSubMch_id(order[3]);
            bill.setDevice_info(order[4]);
            bill.setWeiXinOrderNo(order[5]);
            bill.setMchOrderNo(order[6]);
            bill.setUserId(order[7]);
            bill.setType(order[8]);
            bill.setStatus(order[9]);
            bill.setBank(order[10]);
            bill.setCurrency(order[11]);
            bill.setAmount(order[12]);
            bill.setEnvelopeAmount(order[13]);
            bill.setName(order[14]);
            bill.setPacket(order[15]);
            bill.setPoundage(order[16]);
            bill.setRate(order[17]);
            bill.setOrderAmount(order[18]);
            bills.add(bill);
        }
        return bills;
    }

    public static void main(String[] args) {
        String bill = "{\n" +
                "    \"data\": \"?交易时间,公众账号ID,商户号,特约商户号,设备号,微信订单号,商户订单号,用户标识,交易类型,交易状态,付款银行,货币种类,应结订单金额,代金券金额,商品名称,商户数据包,手续费,费率,订单金额,费率备注\\r\\n`2019-11-04 18:52:59,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000422201911044383886395,`1572864513058gxaivpd,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n`2019-11-04 19:57:05,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000423201911046892119860,`1572868323023nkfdudu,`o1gswuJ_mUWc0bQ8eLkBYPBtHpR8,`NATIVE,`SUCCESS,`GDB_CREDIT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n`2019-11-04 19:10:42,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000428201911047831786620,`1572865579667loevqgw,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n`2019-11-04 19:15:51,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000438201911041032765762,`1572865890364jryczgn,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n`2019-11-04 19:24:03,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000447201911041288502636,`1572866378743unbkuor,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n总交易单数,应结订单总金额,退款总金额,充值券退款总金额,手续费总金额,订单总金额,申请退款总金额\\r\\n`5,`0.05,`0.00,`0.00,`0.00000,`0.05,`0.00\",\n" +
                "    \"return_msg\": \"ok\",\n" +
                "    \"return_code\": \"SUCCESS\"\n" +
                "}";
        bill= JSONObject.parseObject(bill).getString("data");
//        List<Bill> bills = Bill.analyze(bill, "2019-11-04");
        String tradeRecords = bill.replace("`", "");
        String[] data = tradeRecords.split(",");
        System.out.println(data[data.length - 2]);

    }
//    public static void main(String[] args) throws Exception {
//        String bill = "{\"data\":\"?交易时间,公众账号ID,商户号,特约商户号,设备号,微信订单号,商户订单号,用户标识,交易类型,交易状态,付款银行,货币种类,应结订单金额,代金券金额,商品名称,商户数据包,手续费,费率,订单金额,费率备注\\r\\n`2019-11-03 19:12:02,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000451201911032334502019,`1572779359488bfffqlj,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n`2019-11-03 19:54:24,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000438201911035408473990,`1572781802656bezthwq,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n`2019-11-03 13:16:39,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000424201911036747682838,`1572757964994sihzssv,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n`2019-11-03 19:13:38,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000428201911033904916624,`1572779456441njdpfjt,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n`2019-11-03 19:11:26,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000436201911032648215072,`1572779319691kbhmiyl,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n`2019-11-03 16:08:31,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000450201911030986775722,`1572768334142edddrtb,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n`2019-11-03 19:38:32,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000422201911036492621733,`1572780844897ryfinnu,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n`2019-11-03 19:32:55,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000447201911033582479277,`1572780486748jnqgptn,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n`2019-11-03 13:19:48,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000428201911036577176110,`1572758213015utessiv,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n`2019-11-03 19:41:51,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000444201911031335872945,`1572781045045rsrdpol,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n`2019-11-03 14:40:56,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000444201911039519361336,`1572762946905lliqtzn,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n`2019-11-03 19:43:02,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000448201911037207725385,`1572781118181lhjuwvs,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n`2019-11-03 19:55:47,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000448201911039779022415,`1572781885332gxovrik,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n`2019-11-03 19:12:52,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000431201911035458064465,`1572779408633psihmhd,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n`2019-11-03 19:35:52,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000435201911032803163219,`1572780686701iplnckv,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n`2019-11-03 19:10:56,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000436201911030714109414,`1572779286073fbwqfwo,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n`2019-11-03 19:54:54,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000426201911033037577837,`1572781833049avgjhgh,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n`2019-11-03 16:07:09,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000449201911030600420721,`1572768258784aywavgs,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n`2019-11-03 19:39:28,`wxdf9968af00e458e0,`1393091302,`0,`,`4200000440201911032289264383,`1572780905283qvvqzap,`o1gswuOe-4_AZqm1gipKyZGU2iZ8,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`长春工业大学收费,`,`0.00000,`0.60%,`0.01,`\\r\\n总交易单数,应结订单总金额,退款总金额,充值券退款总金额,手续费总金额,订单总金额,申请退款总金额\\r\\n`19,`0.19,`0.00,`0.00,`0.00000,`0.19,`0.00\",\"return_msg\":\"ok\",\"return_code\":\"SUCCESS\"}";
//        List list = analyze(bill,"2019-11-03");
//        System.out.println(list.size());
//    }

}
