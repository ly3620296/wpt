package gka.controller.jfxmdm;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

/**
 * @Auther ly
 * @Date 2019/4/19
 * @Describe
 */
public class JfxmDmDao {

    public static List<Record> list(Integer start, Integer end, String jfxmmc) {
        String sql = "SELECT ID,JFXMID,JFXMMC,SFBX FROM (SELECT ROWNUM AS ROWNO,T.*  FROM JFXMDM T WHERE ROWNUM <= ?) A WHERE A.ROWNO >= ? ";
        if (jfxmmc != null && !"".equals(jfxmmc)) {
            sql += " AND JFXMMC LIKE '%" + jfxmmc + "%'";
        }
        sql+=" ORDER BY JFXMID ";

        List<Record> re = Db.find(sql, end, start);
        return re;
    }

    public static Integer searchCount(String jfxmmc) {
        String sql = "select count(1) from JFXMDM";
        if (jfxmmc != null && !"".equals(jfxmmc)) {
            sql += " where JFXMMC like '%" + jfxmmc + "%'";
        }
        Record re = Db.findFirst(sql);
        return re.getInt("count(1)");
    }

    public static Integer add(String jfxmid, String jfxmmc, String sfbx) {
        int i = 0;
        try {
            String sql = "INSERT INTO JFXMDM (ID,JFXMID,JFXMMC,SFBX) VALUES(SEQ_JFXMDM.NEXTVAL,?,?,?)";
            i = Db.update(sql, jfxmid, jfxmmc, sfbx);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    public static Integer edit(String id,String jfxmid, String jfxmmc, String sfbx) {
        String sql = "UPDATE JFXMDM SET JFXMID=?,JFXMMC=?,SFBX=? WHERE ID=?";
        int i = Db.update(sql, jfxmid, jfxmmc, sfbx, id);
        return i;
    }


}
