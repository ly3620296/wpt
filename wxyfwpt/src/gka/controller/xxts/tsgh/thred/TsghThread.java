package gka.controller.xxts.tsgh.thred;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 推送主线程
 */
public class TsghThread implements Runnable {

    public TsghThread() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                //图书借阅记录查询
                checkTsgwz(getTime());
//                TimeUnit.HOURS.sleep(3);
                TimeUnit.MINUTES.sleep(1);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public String[] getTime() {
        String[] time = new String[2];
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
        time[0] = simpleDateFormat.format(new Date(System.currentTimeMillis() + 2 * 24 * 60 * 60 * 1000));
        time[1] = simpleDateFormat.format(new Date(System.currentTimeMillis() + 2 * 24 * 60 * 60 * 1000 - 1 * 60 * 60 * 1000));
        return time;
    }

    //图书借阅记录查询
    private void checkTsgwz(String[] time) {
        String sql = "SELECT DZZH,TXM,ZTM,JCSK,XHSK,XJCS,LTLX,WXLX,SQH,BB,CBZ,CBRQ,YJS,SFGH,GHSJ,ZHGXSJ,SFZH,ID FROM V_WPT_TSJYCX WHERE SUBSTR(XHSK,0,13) in (?,?) AND SFGH = ?";
        //拉取得数据
        List<Record> v_tsgh = Db.find(sql, time[0], time[1], "n");
        if (v_tsgh != null && v_tsgh.size() > 0) {
            sql = "SELECT V_ID FROM T_TSGH_LOG  WHERE V_STATUS=?";
            //wpt记录的数据
            List<Record> t_tsgh = Db.find(sql, "0");
            //和现有推送消息表作对比
            if (t_tsgh != null && t_tsgh.size() > 0) {
                for (int i = 0; i < v_tsgh.size(); i++) {
                    boolean flag = true;
                    Record v = v_tsgh.get(i);
                    String v_tsgh_id = v.getStr("ID");
                    for (int j = 0; j < t_tsgh.size(); j++) {
                        Record t = t_tsgh.get(j);
                        String t_tsgh_id = t.getStr("V_ID");
                        if (v_tsgh_id.equals(t_tsgh_id)) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        //插入wpt记录表
                        insertWpt(v_tsgh_id);
                        //插入总的推送记录表为未读取
                        insertTswxLog(v);
                    }

                }
            } else {
                //插入wpt记录表
                for (int j = 0; j < v_tsgh.size(); j++) {
                    Record v = v_tsgh.get(j);
                    String v_tsgh_id = v.getStr("ID");
                    //插入wpt记录表
                    insertWpt(v_tsgh_id);
                    //插入总的推送记录表为未读取
                    insertTswxLog(v);
                }

            }

        }
    }

    private void insertWpt(String id) {
        String sql = "INSERT INTO T_TSGH_LOG T (V_ID,D_DATE,V_STATUS) VALUES (?,SYSDATE,?)";
        Db.update(sql, id, "0");
    }

    private void insertTswxLog(Record record) {
        String DZZH = record.getStr("DZZH");
        String TXM = record.getStr("TXM");
        String ZTM = record.getStr("ZTM");
        String JCSK = record.getStr("JCSK");
        String XHSK = record.getStr("XHSK");
        String XJCS = record.getStr("XJCS");
        String LTLX = record.getStr("LTLX");
        String WXLX = record.getStr("WXLX");
        String SQH = record.getStr("SQH");
        String BB = record.getStr("BB");
        String CBZ = record.getStr("CBZ");
        String CBRQ = record.getStr("CBRQ");
        String YJS = record.getStr("YJS");
        String SFGH = record.getStr("SFGH");
        String GHSJ = record.getStr("GHSJ");
        String ZHGXSJ = record.getStr("ZHGXSJ");
        String SFZH = record.getStr("SFZH");
        String ID = record.getStr("ID");
        String sql = "INSERT INTO T_TSGH_USER_LOG (DZZH,TXM,ZTM,JCSK,XHSK,XJCS,LTLX,WXLX,SQH,BB,CBZ,CBRQ,YJS,SFGH,GHSJ,ZHGXSJ,SFZH,ID,V_STATUS,D_TIPS_DATE,V_TYPE) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE,?)";
        Db.update(sql, DZZH, TXM, ZTM, JCSK, XHSK, XJCS, LTLX, WXLX, SQH, BB, CBZ, CBRQ, YJS, SFGH, GHSJ, ZHGXSJ, SFZH, ID, "0", "1");
    }
}
