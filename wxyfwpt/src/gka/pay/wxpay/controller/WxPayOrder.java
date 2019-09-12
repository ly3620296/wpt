package gka.pay.wxpay.controller;

/**
 * @Auther ly
 * @Date 2019/9/10
 * @Describe
 */
public class WxPayOrder {
    //商户订单号
    private String out_trade_no;
    //支付项目id集合
    private String ids;
    //支付渠道
    private String pay_type;
    //支付金额(单位为分)
    private String total_fee;
    //公众账号ID
    private String appid;
    //商户号
    private String mch_id;
    //客户标识
    private String openid;
    //客户端地址
    private String payIp;
    //调用支付时间
    private String time_start;
    //支付完成时间
    private String time_end;
    //是否接受到回调 0:否 1:是
    private String isCallBack = "0";
    //业务结果
    private String return_code;
    //结果详情
    private String result_code;

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getPayIp() {
        return payIp;
    }

    public void setPayIp(String payIp) {
        this.payIp = payIp;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public String getIsCallBack() {
        return isCallBack;
    }

    public void setIsCallBack(String isCallBack) {
        this.isCallBack = isCallBack;
    }

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    @Override
    public String toString() {
        return "WxPayOrder{" +
                "out_trade_no='" + out_trade_no + '\'' +
                ", ids='" + ids + '\'' +
                ", pay_type='" + pay_type + '\'' +
                ", total_fee='" + total_fee + '\'' +
                ", appid='" + appid + '\'' +
                ", mch_id='" + mch_id + '\'' +
                ", openid='" + openid + '\'' +
                ", payIp='" + payIp + '\'' +
                ", time_start='" + time_start + '\'' +
                ", time_end='" + time_end + '\'' +
                ", isCallBack='" + isCallBack + '\'' +
                ", return_code='" + return_code + '\'' +
                ", result_code='" + result_code + '\'' +
                '}';
    }
}
