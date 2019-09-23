package gka.controller.xxts.ttk.thread;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 推送主线程
 */
public class TtkThread implements Runnable {

    public TtkThread() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                //调用调停课查询
                checkTtk(getTime());
                TimeUnit.HOURS.sleep(2);
//                TimeUnit.MINUTES.sleep(1);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public String[] getTime() {
        String[] time = new String[2];
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
        time[0] = simpleDateFormat.format(new Date());
        time[1] = simpleDateFormat.format(new Date(System.currentTimeMillis() - 1 * 60 * 60 * 1000));
        return time;
    }

    //
    private void checkTtk(String[] time) {
        String sql = "SELECT JXBMC,ZC,XQJ,JC,YJSXM,DD,KCMC,THZC,THXQJ,THJC,THDD,XJSXM,JXB_ID,TTKXX_ID,TKSJ,THLSGH FROM V_WPT_TTKXX WHERE SUBSTR(TKSJ,0,13) in (?,?)";
        //拉取得数据
        List<Record> v_ttk = Db.find(sql, time[0], time[1]);
        if (v_ttk != null && v_ttk.size() > 0) {
            sql = "SELECT V_ID FROM T_TTK_LOG  WHERE V_STATUS=?";
            //wpt记录的数据
            List<Record> t_ttk = Db.find(sql, "0");
            //和现有推送消息表作对比
            if (t_ttk != null && t_ttk.size() > 0) {
                for (int i = 0; i < v_ttk.size(); i++) {
                    boolean flag = true;
                    Record v = v_ttk.get(i);
                    String v_ttkxx_id = v.getStr("TTKXX_ID");
                    for (int j = 0; j < t_ttk.size(); j++) {
                        Record t = t_ttk.get(j);
                        String t_ttkxx_id = t.getStr("V_ID");
                        if (v_ttkxx_id.equals(t_ttkxx_id)) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        //插入wpt记录表
                        String tksj = v.getStr("TKSJ");
                        insertWpt(v_ttkxx_id, tksj);
                        //插入总的推送记录表为未读取
                        insertTtkLog(v);
                    }

                }
            } else {
                //插入wpt记录表
                for (int j = 0; j < v_ttk.size(); j++) {
                    Record v = v_ttk.get(j);
                    String tksj = v.getStr("TKSJ");
                    String v_ttkxx_id = v.getStr("TTKXX_ID");
                    insertWpt(v_ttkxx_id, tksj);
                    //插入总的推送记录表为未读取
                    insertTtkLog(v);
                }

            }

        }
    }

    private void insertWpt(String id, String date) {
        String sql = "INSERT INTO t_ttk_log t (V_ID,V_DATE,V_STATUS) VALUES (?,?,?)";
        Db.update(sql, id, date, "0");
    }

    private void insertTtkLog(Record record) {
        String sql = "SELECT XH FROM V_WPT_XSXKB T WHERE JXB_ID=?";
        List<Record> list = Db.find(sql, record.getStr("JXB_ID"));
        String JXBMC = record.getStr("JXBMC");
        String ZC = record.getStr("ZC");
        String XQJ = record.getStr("XQJ");
        String JC = record.getStr("JC");
        String YJSXM = record.getStr("YJSXM");
        String DD = record.getStr("DD");
        String KCMC = record.getStr("KCMC");
        String THZC = record.getStr("THZC");
        String THXQJ = record.getStr("THXQJ");
        String THJC = record.getStr("THJC");
        String THDD = record.getStr("THDD");
        String XJSXM = record.getStr("XJSXM");
        String JXB_ID = record.getStr("JXB_ID");
        String TTKXX_ID = record.getStr("TTKXX_ID");
        String TKSJ = record.getStr("TKSJ");
        for (int i = 0; i < list.size(); i++) {
            Record xh = list.get(i);
            sql = "INSERT INTO T_TTK_USER_LOG (JXBMC,ZC,XQJ,JC,YJSXM,DD,KCMC,THZC,THXQJ,THJC,THDD,XJSXM,JXB_ID,TTKXX_ID,TKSJ,V_STATUS,V_XH,D_TIPS_DATE,V_TYPE) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE,?)";
            Db.update(sql, JXBMC, ZC, XQJ, JC, YJSXM, DD, KCMC, THZC, THXQJ, THJC, THDD, XJSXM, JXB_ID, TTKXX_ID, TKSJ, "0", xh.getStr("XH"),"0");
        }
        sql = "INSERT INTO T_TTK_USER_LOG (JXBMC,ZC,XQJ,JC,YJSXM,DD,KCMC,THZC,THXQJ,THJC,THDD,XJSXM,JXB_ID,TTKXX_ID,TKSJ,V_STATUS,V_XH,D_TIPS_DATE,V_TYPE) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE,?)";
        Db.update(sql, JXBMC, ZC, XQJ, JC, YJSXM, DD, KCMC, THZC, THXQJ, THJC, THDD, XJSXM, JXB_ID, TTKXX_ID, TKSJ, "0", record.getStr("THLSGH"),"0");
    }
}
