package gka.controller.fpgl;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther ly
 * @Date 2019/4/19
 * @Describe
 */
public class FpglDao {

    public List<Record> list(Integer start, Integer end, String fpmc) {
//        String sql = "SELECT ID,JFXMID,JFXMMC,SFBX,SFQY,JFTJ FROM (SELECT ROWNUM AS ROWNO,T.*  FROM JFXMDM T WHERE ROWNUM <= ?) A WHERE A.ROWNO >= ? ";
        String sql = "select * from ( select row_.*, rownum rownum_ from ( SELECT ID,FPMC,FPLX,XGSJ,QYZT,XMID,XMMC,CJSJ FROM WPTMA_FPXX";
        if (fpmc != null && !"".equals(fpmc)) {
            sql += " WHERE FPMC LIKE '%" + fpmc + "%'";
        }
        sql += " ORDER BY ID  ) row_ where rownum <= ?) table_alias where table_alias.rownum_ >= ?";
        List<Record> re = Db.find(sql, end, start);
        return re;
    }

    public Integer searchCount(String jfxmmc) {
        String sql = "select count(1) from JFXMDM";
        if (jfxmmc != null && !"".equals(jfxmmc)) {
            sql += " where JFXMMC like '%" + jfxmmc + "%'";
        }
        Record re = Db.findFirst(sql);
        return re.getInt("count(1)");
    }


    public List<Record> sfxm() {
        String sql = "SELECT JFXMID,JFXMMC FROM JFXMDM";

        List<Record> reAll = Db.find(sql);
        sql = "SELECT XMID FROM WPTMA_FPXX";
        List<Record> reYx = Db.find(sql);

        List<String> xmidYx = new ArrayList<String>();

        if (reYx != null) {
            for (int i = 0; i < reYx.size(); i++) {
                String[] xmidYxArr = reYx.get(i).getStr("XMID").split(",");
                for (String x : xmidYxArr) {
                    xmidYx.add(x);
                }
            }
        }

        for (int i = 0; i < reAll.size(); i++) {
            Record re = reAll.get(i);
            String idA = re.getStr("JFXMID");
            for (int j = 0; j < xmidYx.size(); j++) {
                String idYx = xmidYx.get(j);
                if (idA.equals(idYx)) {
                    re.set("disabled", true);
                    break;
                }
            }
        }
        return reAll;
    }



    public Integer add(String FPMC, String FPLX, String SFXM, String XMMC) {
        int i = 0;
        try {
            String sql = "INSERT INTO WPTMA_FPXX (ID,FPMC,FPLX,CJSJ,QYZT,XMID,XMMC) VALUES(SEQ_WPTMA_FPXX.NEXTVAL,?,?,SYSDATE,?,?,?)";
            i = Db.update(sql, FPMC, FPLX, "1", SFXM, XMMC);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    public Integer edit(String id, String FPMC, String FPLX, String SFXM, String XMMC) {
        String sql = "UPDATE WPTMA_FPXX SET FPMC=?,FPLX=?,XMID=?,XMMC=? WHERE ID=?";
        int i = Db.update(sql, FPMC, FPLX, SFXM, XMMC, id);
        return i;
    }


    public int updateStatus(String my_id, String my_type, String my_status) {
        int result = 0;
        System.out.println(my_id);
        System.out.println(my_status);
        System.out.println(my_type);
        try {
            String sql = "";
            if (my_type.equals("QYZT")) {
                sql = "UPDATE WPTMA_FPXX SET QYZT=? WHERE ID=?";
            }

//            else if (my_type.equals("JFTJ")) {
//                sql = "UPDATE JFXMDM SET JFTJ=? WHERE ID=?";
//            } else if (my_type.equals("SFBX")) {
//                sql = "UPDATE JFXMDM SET SFBX=? WHERE ID=?";
//            }
            result = Db.update(sql, my_status, my_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
