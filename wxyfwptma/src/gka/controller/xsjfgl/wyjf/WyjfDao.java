package gka.controller.xsjfgl.wyjf;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

public class WyjfDao {

    public List<Record> queryTitle() {
        String sql = "SELECT JFXMID,JFXMMC,SFBX FROM JFXMDM ORDER BY JFXMID";
        List<Record> list = Db.find(sql);
        return list;
    }

    public List<Record> queryTotal(List<Record> title, String xh) {
        String sql = "SELECT T1.XN,T1.YSHJ," + getSql(title) +
                " FROM XSSFB T1 LEFT JOIN  YHSJB T2 ON T1.XH =T2.XH AND T1.XN=T2.XN  WHERE T1.XH =? ORDER BY T1.XN";
        List<Record> list = Db.find(sql, xh);
        return list;
    }

    public List<Record> queryTotalWjf(List<Record> title, String xh) {
        String sql = "SELECT T1.XN," + getSqlWjf(title) +
                " FROM XSSFB T1 LEFT JOIN  YHSJB T2 ON T1.XH =T2.XH AND T1.XN=T2.XN  WHERE T1.XH =? ORDER BY T1.XN";
        List<Record> list = Db.find(sql, xh);
        return list;
    }

    public Record jf(String xh, String xn, List<Record> title) {
        String sql = "SELECT " + getSqlForJf(title) +
                " FROM XSSFB T1 LEFT JOIN  YHSJB T2 ON T1.XH =T2.XH AND T1.XN=T2.XN  WHERE T1.XH =? AND T1.XN=?";
        Record re = Db.findFirst(sql, xh, xn);
        return re;

    }

    private String getSql(List<Record> title) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < title.size(); i++) {
            Record re = title.get(i);

            String jfxmId = re.getStr("JFXMID");
            if (i < title.size() - 1) {
                sb.append("NVL(T2." + jfxmId + ",0) " + jfxmId + ",");
            } else {
                sb.append("NVL(T2." + jfxmId + ",0) " + jfxmId);
            }
        }
        return sb.toString();
    }

    private String getSqlWjf(List<Record> title) {
        StringBuffer sb = new StringBuffer();
        StringBuffer hj = new StringBuffer("(T1.YSHJ");
        for (int i = 0; i < title.size(); i++) {
            Record re = title.get(i);

            String jfxmId = re.getStr("JFXMID");
            if (i < title.size() - 1) {
                sb.append("NVL(T2." + jfxmId + ",0) " + jfxmId + ",");
                hj.append("-" + "NVL(T2." + jfxmId + ",0)");
            } else {
                sb.append("NVL(T2." + jfxmId + ",0) " + jfxmId + ",");
                hj.append("-" + "NVL(T2." + jfxmId + ",0)) YSHJ");
            }
        }
        sb.append(hj);
        return sb.toString();
    }

    private String getSqlForJf(List<Record> title) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < title.size(); i++) {
            Record re = title.get(i);

            String jfxmId = re.getStr("JFXMID");
            if (i < title.size() - 1) {
                sb.append("(NVL(T1." + jfxmId + ",0)-NVL(T2." + jfxmId + ",0)) " + jfxmId + ",");
            } else {
                sb.append("(NVL(T1." + jfxmId + ",0)-NVL(T2." + jfxmId + ",0)) " + jfxmId);
            }
        }
        return sb.toString();
    }


    public boolean validateIsPay(String ids) {
        return false;
    }

    public boolean noPayOrder(String xh) {
        return true;
    }
}
