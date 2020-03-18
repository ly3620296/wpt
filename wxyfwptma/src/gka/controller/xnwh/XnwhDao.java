package gka.controller.xnwh;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

/**
 * @Auther ly
 * @Date 2019/4/19
 * @Describe
 */
public class XnwhDao {

    public static List<Record> list(Integer start, Integer end) {
//        String sql = "SELECT XNID,XNMC FROM (SELECT ROWNUM AS ROWNO,T.*  FROM XN T WHERE ROWNUM <= ? ) A " +
//                "WHERE A.ROWNO >= ? ORDER BY TO_NUMBER(SUBSTR(XNMC,0,4)) DESC";
        String sql="select * from ( select row_.*, rownum rownum_ from (  select XNID,XNMC from XN order by TO_NUMBER(SUBSTR(XNMC,0,4)) DESC ) row_ where rownum <= ?) table_alias where table_alias.rownum_ >= ?";
        List<Record> re = Db.find(sql, end, start);
        return re;
    }

    public static Integer searchCount() {
        String sql = "SELECT COUNT(1) CC FROM XN";
        Record re = Db.findFirst(sql);
        return re.getInt("CC");
    }

    public static Integer add(String xnmc) {
        int i = 0;
        try {
            String sql = "INSERT INTO XN (XNID,XNMC) VALUES(SEQ_XN.NEXTVAL,?)";
            i = Db.update(sql, xnmc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    public static Integer edit(String id, String xnmc) {
        String sql = "UPDATE XN SET XNMC=? WHERE XNID=?";
        int i = Db.update(sql, xnmc, id);
        return i;
    }


}
