package gka.controller.gzwh;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class GzwhDao {
    public static List<Record> list(Integer start, Integer end, String u_id) {
        String sql = "SELECT id,bt,TIME FROM (SELECT ROWNUM AS rowno,t.*  FROM wptma_gzbtb t WHERE ROWNUM <= ?) a WHERE a.rowno >= ?";
        List<Record> re = Db.find(sql, end, start);
        return re;
    }

    public static Integer searchCount(String u_id) {
        String sql = "select count(1) from wptma_gzbtb";
        Record re = Db.findFirst(sql);
        return re.getInt("count(1)");
    }

    public static void main(String[] args) {
        String sql = "insert into wptma_gzsjb (id,yhm,xm [aaaaa]) values(?,?,? [bbbbb])";
        String asssssss = "";
        String bbbbb = "";
        for (int c = 0; c < 5; c++) {
            asssssss += "," + "zd" + (c + 1);
            System.out.println(asssssss);
            bbbbb += ",'" + (c * 100) + "'";
        }
        System.out.println(asssssss);
        sql = sql.replace("[aaaaa]", asssssss);
        sql = sql.replace("[bbbbb]", bbbbb);
        System.out.println("sql===========" + sql);
    }
}
