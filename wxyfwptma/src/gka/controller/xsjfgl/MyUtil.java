package gka.controller.xsjfgl;

/**
 * Created by Administrator on 2019/11/1 0001.
 */
public class MyUtil {
    public static String getMyDate(String time) {
        StringBuffer sb = new StringBuffer();
        if (time != null && time.length() == 14) {
            sb.append(time.substring(0, 4));
            sb.append("-");
            sb.append(time.substring(4, 6));
            sb.append("-");
            sb.append(time.substring(6, 8));
            sb.append(" ");
            sb.append(time.substring(8, 10));
            sb.append(":");
            sb.append(time.substring(10, 12));
            sb.append(":");
            sb.append(time.substring(12, 14));
            return sb.toString();
        }
        return time;
    }
}
