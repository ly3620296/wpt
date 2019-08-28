package gka.controller.xxts.tsgh.thred;

import com.jfinal.plugin.activerecord.Db;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 更新昨日数据为不可用哦
 * 不然推送表数据越来越多
 */
public class TsghUpdate implements Runnable {

    public TsghUpdate() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        SimpleDateFormat simp = new SimpleDateFormat("HH");
        SimpleDateFormat simpYes = new SimpleDateFormat("yyyyMMdd");

        while (true) {
            try {
                String currHour = simp.format(new Date());
                if (currHour.equals("3")) {
                    flushYesterday(simpYes.format(new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000)));
                }
                TimeUnit.HOURS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void flushYesterday(String yesterday) {
        String sql = "UPDATE T_TSGH_LOG SET V_STATUS=? WHERE TO_CHAR(D_DATE,'yyyymmdd')=? AND V_STATS=?";
        Db.update(sql, "1", yesterday, "0");
    }
}
