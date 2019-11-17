package gka.system;

import com.gka.pub.util.Util;
import com.gka.pub.util.WriteLog;

import java.util.Hashtable;

/**
 * Created with IntelliJ IDEA.
 * User: 杨亮
 * Date: 2017/7/22
 * Time: 9:42
 * DESCRIPTION:
 * To change this template use File | Settings | File Templates.;
 */
public class Constant {
    public static final String Tomcat_Path = System.getProperty("catalina.base");
    public static final String SysPath = System.getProperty("user.dir");
    public static final String separator = System.getProperty("file.separator");

    /**
     * 日志
     */
    public static WriteLog errorLog = null;
    public static WriteLog renderLog = null;
    /**
     * 错误信息列表
     */
    private static Hashtable<String, String> ErrorList = new Hashtable<String, String>();
    public static boolean APP_STOP = false;

    /**
     * 检查错误信息在1分钟内是否存在重复
     * (用于while中异常导致错误日志重复写入文件)
     *
     * @param errorMessage ；
     * @return ；
     */
    public static boolean CheckRepeatError(String errorMessage) {
        if (ErrorList.containsKey(errorMessage))
            if (ErrorList.get(errorMessage).equals(Util.getDate("yyyyMMddHHmm") + errorMessage))
                return true;
        ErrorList.put(errorMessage, Util.getDate("yyyyMMddHHmm") + errorMessage);
        return false;
    }

    public static void initLogs(String Path) {
        errorLog = new WriteLog(Path + separator + "logs/error", true, WriteLog.WL_WRITE | WriteLog.WL_FOLDER);
        renderLog = new WriteLog(Path + separator + "logs/debug", true, WriteLog.WL_WRITE | WriteLog.WL_FOLDER);
    }

    /**
     * 操作成功状态
     */
    public static final String STATE_SUCCESS = "SUCCESS";
    public static final String CODE_SUCCESS = "0";
    public static final String STATE_SUCCESS_MSG = "调用成功";
    public static final String STATE_FAIL = "FAIL";
    public static final String CODE_FAIL = "-1";
    public static final String STATE_FAIL_MSG = "调用失败";
    public static final String FORBIDDEN_IP = "FORBIDDEN_IP";
    public static final String FORBIDDEN_IP_MSG = "无效IP授权";
}
