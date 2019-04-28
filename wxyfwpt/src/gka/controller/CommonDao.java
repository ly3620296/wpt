package gka.controller;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2019/4/20 0020.
 */
public class CommonDao {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 查询当前学年学期
     *
     * @return
     */
    public static String currXnxq() {
        String currXnxq = "";
        String sql = "SELECT V_ZDZ FROM V_WPT_DQXNXQ";
        List<Record> re = Db.find(sql);
        if (re != null) {
            if (re.size() == 1) {
                currXnxq = re.get(0).getStr("V_ZDZ");
            } else if (re.size() == 2) {
                Record re1 = re.get(0);
                String val1 = re1.getStr("V_ZDZ");
                Record re2 = re.get(1);
                String val2 = re2.getStr("V_ZDZ");
                if (val1.length() == 4) {
                    currXnxq = val1 + "-" + String.valueOf(Integer.parseInt(val1) + 1) + "-" + getXq(val2);
                } else {
                    currXnxq = val2 + "-" + String.valueOf(Integer.parseInt(val2) + 1) + "-" + getXq(val1);
                }
            }
        }
        return currXnxq;
    }

    /**
     * @param currXnxq 当前学年学期
     * @return 查询当前学年学期一共有多少周次
     */
    public static String[] currXnxqZcs(String currXnxq) {
        String sql = "SELECT DISTINCT(ZC) ZC FROM V_WPT_XL  WHERE XNXQ=? ORDER BY ZC ASC";
        List<Record> re = Db.find(sql, currXnxq);
        String[] zcs = null;
        if (re != null) {
            zcs = new String[re.size()];
            for (int i = 0; i < re.size(); i++) {
                zcs[i] = re.get(i).getStr("ZC");
            }
        }
        return zcs;
    }


    /**
     * @param currDate 当前日期
     * @param currXnxq 当前学年学期
     * @return 查询当前学日期对应的周次
     */
    public static String currXnxqZc(String currDate, String currXnxq) {
        String currZc = "";
        String sql = "SELECT ZC FROM V_WPT_XL WHERE RQ=? AND XNXQ=?";
        Record re = Db.findFirst(sql, currDate, currXnxq);
        if (re != null) {
            currZc = re.getStr("ZC");
        } else {
            sql = "SELECT  MAX(RQ) MAXRQ,MIN(RQ),MAX(ZC) MAXZC,MIN(ZC) MINZC  FROM  V_WPT_XL WHERE XNXQ=?";
            re = Db.findFirst(sql, currXnxq);
            //当前学年学期最校历表中最后一天
            String maxRq = re.getStr("MAXRQ");
            //当前学年学期最校历表中第一天
            String minRq = re.getStr("MINRQ");
            try {
                if (simpleDateFormat.parse(currDate).getTime() > simpleDateFormat.parse(maxRq).getTime()) {
                    //当前日期比最后一天大 当前周为最后一周
                    currZc = re.getStr("MAXZC");
                } else if (simpleDateFormat.parse(currDate).getTime() < simpleDateFormat.parse(minRq).getTime()) {
                    //当前日期比最小一天小 当前周为第一周
                    currZc = re.getStr("MINZC");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return currZc;
    }

    private static String getXq(String xq) {
        if (xq.equals("12")) {
            xq = "1";
        } else if (xq.equals("3")) {
            xq = "2";
        } else {
            xq = "0";
        }
        return xq;
    }

    public static void main(String[] args) throws Exception {
        Date date = simpleDateFormat.parse("2019-04-21");
        System.out.println(date.getTime());
        System.out.println(date);
    }
}
