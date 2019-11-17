package gka.kit;

import com.jfinal.json.Json;
import gka.system.Constant;

import java.net.SocketException;
import java.util.LinkedHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: 杨亮
 * Date: 2017/7/22
 * Time: 14:06
 * DESCRIPTION: JSON类型返回工具
 * To change this template use File | Settings | File Templates.;
 */
public class ReturnKit extends LinkedHashMap {
    private static final String RETURN_STATE = "RETURN_STATE";
    private static final String RETURN_CODE = "RETURN_CODE";
    private static final String RETURN_MSG = "RETURN_MSG";
    private static final String RETURN_IP = "RETURN_IP";
    private static final String OUT_DATA = "OUT_DATA";

    /**
     * 默认返回成功
     */
    public static String retOk() {
        return retKit(Constant.STATE_SUCCESS, Constant.CODE_SUCCESS, Constant.STATE_SUCCESS_MSG).toJson();
    }

    /**
     * 自定义消息返回成功
     */
    public static String retOk(Object out_data) {
        return retKit(Constant.STATE_SUCCESS, Constant.CODE_SUCCESS, Constant.STATE_SUCCESS_MSG, out_data).toJson();
    }

    /**
     * 默认返回失败
     */
    public static String retFail() {
        return retKit(Constant.STATE_FAIL, Constant.CODE_FAIL, Constant.STATE_FAIL_MSG).toJson();
    }

    /**
     * 自定义消息返回失败
     */
    public static String retFail(Object return_msg) {
        return retKit(Constant.STATE_FAIL, Constant.CODE_FAIL, return_msg).toJson();
    }

    /**
     * 自定义消息、code的返回失败
     */
    public static String retFail(Object return_msg, Object return_code) {
        return retKit(Constant.STATE_FAIL, return_code, return_msg).toJson();
    }

    /**
     * 自定义消息、code的返回失败
     */
    public static String retFail(Object return_msg, Object return_code, Object out_data) {
        return retKit(Constant.STATE_FAIL, return_code, return_msg, out_data).toJson();
    }

    /**
     * 自定义返回值、消息返回
     */
    public static String ret(Object return_state, Object return_code) {
        return retKit(return_state, return_code).toJson();
    }

    /**
     * 自定义返回值、消息返回
     */
    public static String ret(Object return_state, Object return_code, Object return_msg) {
        return retKit(return_state, return_code, return_msg).toJson();
    }

    /**
     * 自定义返回值、消息、其他参数返回
     */
    public static String ret(Object return_state, Object return_code, Object return_msg, Object out_data) {
        return retKit(return_state, return_code, return_msg, out_data).toJson();
    }

    private static ReturnKit retKit(Object return_state, Object return_code) {
        return new ReturnKit().setReturn(return_state, return_code);
    }

    private static ReturnKit retKit(Object return_state, Object return_code, Object return_msg) {
        return new ReturnKit().setReturn(return_state, return_code, return_msg);
    }

    private static ReturnKit retKit(Object return_state, Object return_code, Object return_msg, Object out_data) {
        return new ReturnKit().setReturn(return_state, return_code, return_msg, out_data);
    }

    private ReturnKit setReturn(Object return_state, Object return_code) {
        try {
            super.put(RETURN_STATE, return_state);
            super.put(RETURN_CODE, return_code);
            super.put(RETURN_IP, IpKit.getLocalIp());
            return this;
        } catch (SocketException e) {
            super.put(RETURN_STATE, return_state);
            super.put(RETURN_CODE, return_code);
            return this;
        }
    }

    private ReturnKit setReturn(Object return_state, Object return_code, Object return_msg) {
        try {
            super.put(RETURN_STATE, return_state);
            super.put(RETURN_CODE, return_code);
            super.put(RETURN_MSG, return_msg);
            super.put(RETURN_IP, IpKit.getLocalIp());
            return this;
        } catch (SocketException e) {
            super.put(RETURN_STATE, return_state);
            super.put(RETURN_CODE, return_code);
            super.put(RETURN_MSG, return_msg);
            return this;
        }
    }

    private ReturnKit setReturn(Object return_state, Object return_code, Object return_msg, Object out_data) {
        try {
            super.put(RETURN_STATE, return_state);
            super.put(RETURN_CODE, return_code);
            super.put(RETURN_MSG, return_msg);
            super.put(RETURN_IP, IpKit.getLocalIp());
            super.put(OUT_DATA, out_data);
            return this;
        } catch (SocketException e) {
            super.put(RETURN_STATE, return_state);
            super.put(RETURN_CODE, return_code);
            super.put(RETURN_MSG, return_msg);
            super.put(OUT_DATA, out_data);
            return this;
        }
    }

    private String toJson() {
        return Json.getJson().toJson(this);
    }
}
