package gka.pay.wxpay.controller;

import java.util.HashMap;
import java.util.Map;

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
    //老师关闭
    public static final String ORDER_STATE_CLOSE_TEACHER = "4";
    //退费
    public static final String ORDER_STATE_TF = "5";
    public static Map<String, String> ORDER_STATE_DESC = new HashMap<String, String>();

    static {
        ORDER_STATE_DESC.put(ORDER_STATE_NOPAY, "未支付");
        ORDER_STATE_DESC.put(ORDER_STATE_CLOSE, "订单关闭（学生）");
        ORDER_STATE_DESC.put(ORDER_STATE_PAY, "已支付");
        ORDER_STATE_DESC.put(ORDER_STATE_CLOSE_TEACHER, "订单关闭（老师）");
        ORDER_STATE_DESC.put(ORDER_STATE_TF, "退费");

    }

    //已经缴费
    public static final String ORDER_YSFY_JF = "1";
    //未缴费
    public static final String ORDER_YSFY_WJF = "0";


    public static final String RETURN_CODE_ERROR = "error";
    public static final String RETURN_CODE_SUCCESS = "success";
    //
    public static final String RESULT_CODE_ILLEGALMONEY = "预支付订单金额与微信回调金额不一致";
    public static final String RESULT_CODE_CLOSE = "订单超时，系统关闭";
    public static final String RESULT_CODE_CLOSE_USER = "用户主动关闭";

    //学杂费对应的项目类型id
    public static final String XMLXID_XZF = "20001";


    //
    public static final String XSSFB_CZLX_LSHTJF = "老师后台缴费";
    public static final String XSSFB_CZLX_LSHTTF = "老师后台退费";
    public static final String XSSFB_CZLX_XSHTJF = "学生后台缴费";
    public static final String XSSFB_CZLX_WPT = "学生微平台缴费";
    public static final String XSSFB_CZLX_PK = "批扣";

}
