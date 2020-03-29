package gka.controller.xsjfgl;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import gka.system.ReturnInfo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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



    public static Long changeDate(String date) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date));
        return calendar.getTimeInMillis();
    }

    public static void checkDate(ReturnInfo info) throws Exception {
        Record re = Db.findFirst("select * from wptma_jfsj");
        if (re != null) {
            String startDate = re.getStr("start_time") == null ? "" : re.getStr("start_time").toString();
            String endDate = re.getStr("end_time") == null ? "" : re.getStr("end_time").toString();
            if (!startDate.equals("") && !endDate.equals("")) {
                long nowDate = System.currentTimeMillis();
                long start = changeDate(startDate);
                long end = changeDate(endDate);
                if (nowDate < start) {
                    info.setReturn_code("-0003");
                    info.setReturn_msg("缴费尚未开始，请于" + startDate + "后再来缴费!");
                } else if (nowDate > end) {
                    info.setReturn_code("-0004");
                    info.setReturn_msg("网上缴费时间已过,请联系老师进行线下缴费!");
                }else {
                    info.setReturn_code("666666");
                    info.setReturn_msg("");
                }
            } else {
                info.setReturn_code("-0002");
                info.setReturn_msg("获取缴费日期失败，请联系管理员!");
            }
        } else {
            info.setReturn_code("-0001");
            info.setReturn_msg("获取缴费日期失败，请联系管理员!");
        }

    }
}
