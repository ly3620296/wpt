package gka.pay.wxpay.controller;

/**
 * @Auther ly
 * @Date 2019/9/15
 * @Describe
 */
public class MyWxpayConstant {
    //未支付
    public static final String ORDER_STATE_NOPAY = "0";
    //订单关闭
    public static final String ORDER_STATE_CLOSE = "1";
    //已支付
    public static final String ORDER_STATE_PAY = "2";
    //异常订单，total_fee不匹配
    public static final String ORDER_STATE_ILLEGALMONEY = "3";


    public static final String RETURN_CODE_ERROR = "error";
    public static final String RETURN_CODE_SUCCESS = "success";
    //
    public static final String RESULT_CODE_ILLEGALMONEY = "预支付订单金额与微信回调金额不一致";

    //学杂费对应的项目类型id
    public static final String XMLXID_XZF="20001";
}
