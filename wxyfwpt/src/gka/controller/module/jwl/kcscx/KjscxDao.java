package gka.controller.module.jwl.kcscx;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import gka.controller.CommonUtil;
import gka.controller.module.jwl.xkqkcx.RqInfo;
import gka.kit.ArrayUtil;

import java.util.*;

/**
 * @Auther ly
 * @Date 2019/4/3
 * @Describe
 */
public class KjscxDao {

    public List<Record> kjscx(String cdlbId, String lhId) {
        String sql = "SELECT KCMC,SKDD,JSXM,KHFS,ZC,XQ,JC  FROM V_WPT_JSKB T WHERE CDLB_ID=? AND CDBH=?";
        List<Record> cdkbList = Db.find(sql, cdlbId, lhId);
        return cdkbList;
    }

    public  List<RqInfo> rq() {
       List<RqInfo> rqInfoList =new ArrayList<RqInfo>();
        for (int i = 0; i < 8; i++) {
            Date date = new Date(System.currentTimeMillis() + i * 24 * 60 * 60 * 1000);
            rqInfoList.add(new RqInfo(CommonUtil.getDate("yyyy-MM-dd", date), CommonUtil.getDateAndWeek("MM月dd日", date)));

        }
        return rqInfoList;
    }

    /**
     * 场地类别查询
     *
     * @return
     */
    public List<Record> cdlb() {
        String sql = "SELECT CDLB_ID,CDLBMC FROM V_WPT_CDLB";
        List<Record> cdLbList = Db.find(sql);
        return cdLbList;
    }

    /**
     * 场地查询
     *
     * @return
     */
    public List<Record> lh(String cdlbId) {
        String sql = "SELECT LH FROM WPT_CDJBXXB WHERE CDLB_ID=?";
        List<Record> cdList = Db.find(sql, cdlbId);
        return cdList;
    }

    /**
     * 获得二位数组课表 插件需要
     *
     * @param cdkbList
     * @param currZc
     * @return
     */
    public String[][] getKbArray(List<Record> cdkbList, String currZc) {
        String[][] cdkb = new String[7][12];
        CommonUtil.initArray(cdkb);
        //循环7天
        for (int i = 1; i <= 7; i++) {
            for (int j = 0; j < cdkbList.size(); j++) {
                Record re = cdkbList.get(j);
                String xq = re.getStr("XQ");
                //星期相同的课
                if (xq.equals(String.valueOf(i))) {
                    //判断周次是否相同
                    String[] zcs = re.getStr("ZC").split(",");
                    //改课周次中是否包含当前周次
                    boolean containsCurrZc = ArrayUtil.contains(zcs, currZc);
                    if (containsCurrZc) {
                        String[] jcs = re.getStr("JC").split("-");
                        String kcmc = re.getStr("KCMC");
                        String skdd = re.getStr("SKDD");
                        for (int k = Integer.parseInt(jcs[0]); k <= Integer.parseInt(jcs[1]); k++) {
                            cdkb[i - 1][k - 1] = kcmc + "@" + skdd + "_n_" + re.getStr("JSXM") + "_n_" + re.getStr("KHFS") + "_n_" + CommonUtil.formatZc(re.getStr("ZC"));
                        }
                    }
                }
            }
        }
        return cdkb;
    }
}
