package gka.controller.ygtxl;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

/**
 * @Auther ly
 * @Date 2019/4/19
 * @Describe
 */
public class YgtxlDao {


    public static List<Record> list(Integer start, Integer end, String u_id, String xm) {
        String sql = "SELECT ID,xm,dh,x.x_name as xyxm FROM " +
                "(SELECT ROWNUM AS rowno,t.* FROM wptma_ygtxl t WHERE ROWNUM <= ?) a,wptma_xygl x WHERE x.x_code=a.xyid and a.rowno >= ?";
        if (xm != null && !"".equals(xm)) {
            sql += " and xm like '%" + xm + "%'";
        }
        List<Record> re = Db.find(sql, end, start);
        return re;
    }

    public static Integer searchCount(String u_id, String xm) {
        String sql = "select count(1) from wptma_ygtxl";
        if (xm != null && !"".equals(xm)) {
            sql += " where xm like '%" + xm + "%'";
        }
        Record re = Db.findFirst(sql);
        return re.getInt("count(1)");
    }

    public static void main(String[] args) {
        char[] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
                'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
                'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        boolean[] flags = new boolean[letters.length];
        String str = "";
        for (int i = 0; i < 10; i++) {
            int index;
            do {
                index = (int) (Math.random() * (letters.length));
            } while (flags[index]);// 判断生成的字符是否重复

            str += letters[index];
            flags[index] = true;
        }
        System.out.println((int) ((Math.random() * 9 + 1) * 100000));
        String a = "" + (int) ((Math.random() * 9 + 1) * 100000);
        System.out.println(a);
        System.out.println(str.toString());
        System.out.println(str + a);
    }

    public static String createXmId() {
        char[] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
                'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
                'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        boolean[] flags = new boolean[letters.length];
        String str = "";
        for (int i = 0; i < 10; i++) {
            int index;
            do {
                index = (int) (Math.random() * (letters.length));
            } while (flags[index]);// 判断生成的字符是否重复

            str += letters[index];
            flags[index] = true;
        }
        String a = "" + (int) ((Math.random() * 9 + 1) * 100000);
        String result = str + a;
        return result;
    }

    public static Integer add(String xm, String dh, String xyid) {
        String sql = "insert into wptma_ygtxl (id,xm, dh, xyid) values(SEQ_WPTMA_YGTXL.NEXTVAL,?,?,?)";
        int i = Db.update(sql,xm, dh, xyid);
        return i;
    }

    public static Integer edit(String id, String xm, String dh, String xyid) {
        String sql = "update wptma_ygtxl set xm=?,dh=?,xyid=? where id=?";
        int i = Db.update(sql, xm, dh, xyid, id);
        return i;
    }

}
