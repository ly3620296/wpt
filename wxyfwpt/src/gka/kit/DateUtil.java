package gka.kit;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2019/4/21 0021.
 */
public class DateUtil {
    /**
     * 获取当前日期是星期几
     *
     * @return
     */
    public static int getCurrXq() {
        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        int weekday = c.get(Calendar.DAY_OF_WEEK);
        if (weekday >= 2 && weekday <= 7) {
            weekday = weekday - 1;
        } else {
            weekday = 7;
        }
        return weekday;
    }

    public static void main(String[] args) {
        System.out.println(getCurrXq());
    }
}
