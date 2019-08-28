package gka.controller.xxts.ttk.thread;

import com.jfinal.plugin.activerecord.Db;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 更新昨日数据为不可用哦
 * 不然推送表数据越来越多
 */
public class TtkUpdate implements Runnable {

    public TtkUpdate() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        SimpleDateFormat simp = new SimpleDateFormat("HH");
        SimpleDateFormat simpYes = new SimpleDateFormat("yyyyMMdd");

        while (true) {
            try {
                TimeUnit.HOURS.sleep(1);
                String currHour = simp.format(new Date());
                if (currHour.equals("3")) {
                    flushYesterday(simpYes.format(new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000)));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void flushYesterday(String yesterday) {
        String sql = "UPDATE T_TTK_LOG SET V_STATUS=? WHERE SUBSTR(V_DATE,0,10) = ? AND V_STATUS = ?";
        Db.update(sql, "1", yesterday, "0");
    }
}
