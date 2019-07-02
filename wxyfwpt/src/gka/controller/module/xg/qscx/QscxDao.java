package gka.controller.module.xg.qscx;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import gka.controller.CommonUtil;
import gka.controller.module.jwl.kjscx.KjscxInfo;
import gka.controller.module.jwl.xkqkcx.RqInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther ly
 * @Date 2019/4/3
 * @Describe
 */
public class QscxDao {


    public List<Record> qscx(String ldId, String qshId) {
        String sql = "SELECT T1.CWH,T1.XH,T1.RZYYMC,T1.RZSJ,T2.LXDH,T2.XM,T2.ZYMC,T2.JGMC,T2.BJMC,T2.NJMC,T2.XB FROM V_WPT_QSCX  T1 LEFT JOIN " +
                "WPT_YH T2 ON T1.XH=T2.ZH  WHERE LDDM=? AND QSH=?";
        List<Record> xsList = Db.find(sql, ldId, qshId);
        return xsList;
    }

    public List<Record> qscxByXh(String ldId, String qshId, String xh) {
        String sql = "";
        List<Record> xsList = null;
        if (ldId.equals("")) {
            sql = "SELECT T1.CWH,T1.XH,T1.RZYYMC,T1.RZSJ,T2.LXDH,T2.XM,T2.ZYMC,T2.JGMC,T2.BJMC,T2.NJMC,T2.XB FROM V_WPT_QSCX  T1 LEFT JOIN " +
                    "WPT_YH T2 ON T1.XH=T2.ZH  WHERE T1.XH=?";
            xsList = Db.find(sql, xh);
        } else if (!ldId.equals("") && qshId.equals("")) {
            sql = "SELECT T1.CWH,T1.XH,T1.RZYYMC,T1.RZSJ,T2.LXDH,T2.XM,T2.ZYMC,T2.JGMC,T2.BJMC,T2.NJMC,T2.XB FROM V_WPT_QSCX  T1 LEFT JOIN " +
                    "WPT_YH T2 ON T1.XH=T2.ZH   WHERE LDDM=? AND  T1.XH=?";
            xsList = Db.find(sql, ldId, xh);
        } else if (!ldId.equals("") && !qshId.equals("")) {
            sql = "SELECT T1.CWH,T1.XH,T1.RZYYMC,T1.RZSJ,T2.LXDH,T2.XM,T2.ZYMC,T2.JGMC,T2.BJMC,T2.NJMC,T2.XB FROM V_WPT_QSCX  T1 LEFT JOIN " +
                    "WPT_YH T2 ON T1.XH=T2.ZH  WHERE LDDM=? AND QSH=? AND T1.XH=?";
            xsList = Db.find(sql, ldId, qshId, xh);
        }

        return xsList;
    }

    public List<RqInfo> rq() {
        List<RqInfo> rqInfoList = new ArrayList<RqInfo>();
        for (int i = 0; i < 8; i++) {
            Date date = new Date(System.currentTimeMillis() + i * 24 * 60 * 60 * 1000);
            rqInfoList.add(new RqInfo(CommonUtil.getDate("yyyy-MM-dd", date), CommonUtil.getDateAndWeek("MM月dd日", date), CommonUtil.getWeekNum(date)));

        }
        return rqInfoList;
    }

    /**
     * 楼栋查询
     *
     * @return
     */
    public List<Record> ld() {
        String sql = "SELECT LDDM,LDMC FROM V_WPT_LDXX";
        List<Record> ldList = Db.find(sql);
        return ldList;
    }

    /**
     * 寝室号查询
     *
     * @return
     */
    public List<Record> qsh(String ldId) {
        String sql = "SELECT QSH FROM V_WPT_QSXX WHERE LDDM=?";
        List<Record> qsList = Db.find(sql, ldId);
        return qsList;
    }


}
