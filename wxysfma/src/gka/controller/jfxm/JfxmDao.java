package gka.controller.jfxm;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * @Auther ly
 * @Date 2019/4/19
 * @Describe
 */
public class JfxmDao {

    public static List<Record> list(Integer start, Integer end, String u_id, String title) {
        String sql = "SELECT XMID,XMMC,XMJE,XMLXMC,decode(SFBX,'1','是','否') as SFBX FROM " +
                "(SELECT ROWNUM AS rowno,t.* FROM wpt_jfxm t WHERE ROWNUM <= ?) a,wpt_jfxmlx x WHERE x.XMLXID=a.XMLXID and a.rowno >= ?";
        if (title != null && !"".equals(title)) {
            sql += " and XMMC like '%" + title + "%'";
        }
        List<Record> re = Db.find(sql, end, start);
        return re;
    }

    public static Integer searchCount(String u_id, String title) {
        String sql = "select count(1) from wpt_jfxm";
        if (title != null && !"".equals(title)) {
            sql += " where XMMC like '%" + title + "%'";
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

    public static Integer add(String id, String xmmc, String xmje, String xmlxid, String sfbx) {
        String sql = "insert into wpt_jfxm (XMID,xmmc,xmje,xmlxid,sfbx) values(?,?,?,?,?)";
        int i = Db.update(sql,id, xmmc, xmje, xmlxid, sfbx);
        return i;
    }

    public static Integer edit(String xmid, String xmmc, String xmje, String xmlxid, String sfbx) {
        String sql = "update wpt_jfxm set xmmc=?,xmje=?,xmlxid=?,sfbx=? where xmid=?";
        int i = Db.update(sql, xmmc, xmje, xmlxid, sfbx, xmid);
        return i;
    }


}
