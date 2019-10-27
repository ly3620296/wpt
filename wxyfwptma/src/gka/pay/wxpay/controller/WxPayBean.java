package gka.pay.wxpay.controller;

/**
 * @Auther ly
 * @Date 2019/9/3
 * @Describe
 */
public class WxPayBean {
    //订单号
    private String orderNo;
    //金额
    private String total_fee;
    //客户ip
    private String spbill_create_ip;
    //客户标识
    private String openId;

    public WxPayBean(String orderNo, String total_fee, String spbill_create_ip, String openId) {
        this.orderNo = orderNo;
        this.total_fee = total_fee;
        this.spbill_create_ip = spbill_create_ip;
        this.openId = openId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
