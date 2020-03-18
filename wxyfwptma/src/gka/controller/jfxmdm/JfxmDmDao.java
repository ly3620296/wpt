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
//        String sql = "SELECT ID,JFXMID,JFXMMC,SFBX,SFQY,JFTJ FROM (SELECT ROWNUM AS ROWNO,T.*  FROM JFXMDM T WHERE ROWNUM <= ?) A WHERE A.ROWNO >= ? ";
        String sql = "select * from ( select row_.*, rownum rownum_ from (  SELECT ID,JFXMID,JFXMMC,SFBX,SFQY,JFTJ FROM JFXMDM ";
        if (jfxmmc != null && !"".equals(jfxmmc)) {
            sql += " WHERE JFXMMC LIKE '%" + jfxmmc + "%'";
        }
        sql += " ORDER BY JFXMID  ) row_ where rownum <= ?) table_alias where table_alias.rownum_ >= ?";
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

    public static Integer add(String jfxmid, String jfxmmc) {
        int i = 0;
        try {
            String sql = "INSERT INTO JFXMDM (ID,JFXMID,JFXMMC) VALUES(SEQ_JFXMDM.NEXTVAL,?,?)";
            i = Db.update(sql, jfxmid, jfxmmc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    public static Integer edit(String id, String jfxmid, String jfxmmc) {
        String sql = "UPDATE JFXMDM SET JFXMID=?,JFXMMC=?WHERE ID=?";
        int i = Db.update(sql, jfxmid, jfxmmc, id);
        return i;
    }


    public int updateStatus(String my_id, String my_type, String my_status) {
        int result = 0;
        System.out.println(my_id);
        System.out.println(my_status);
        System.out.println(my_type);
        try {
            String sql = "";
            if (my_type.equals("SFQY")) {
                sql = "UPDATE JFXMDM SET SFQY=? WHERE ID=?";
            } else if (my_type.equals("JFTJ")) {
                sql = "UPDATE JFXMDM SET JFTJ=? WHERE ID=?";
            } else if (my_type.equals("SFBX")) {
                sql = "UPDATE JFXMDM SET SFBX=? WHERE ID=?";
            }
            result = Db.update(sql, my_status, my_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
