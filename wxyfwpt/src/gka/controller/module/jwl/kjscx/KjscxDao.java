package gka.controller.module.jwl.kjscx;

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
    private String[] baseArray = new String[]{"1-2", "3-4", "5-6", "7-8", "9-10", "11-12"};

    public List<Record> kjscx(String cdlbId, String lhId, String currZc, String currXnxq) {
        String sql;
        List<Record> kjsList;
        if (lhId.equals("")) {
            sql = "SELECT KCMC,SKDD,JSXM,KHFS,ZC,XQ,JC FROM V_WPT_JSKB T WHERE CDLB_ID=? AND INSTR( ','||ZC||',', '," + currZc + ",')!=0 AND XNXQ=?";
            kjsList = Db.find(sql, cdlbId, currXnxq);
        } else {
            sql = "SELECT KCMC,SKDD,JSXM,KHFS,ZC,XQ,JC FROM V_WPT_JSKB T WHERE CDLB_ID=? AND SKCDLH=? AND INSTR( ','||ZC||',', '," + currZc + ",')!=0 AND XNXQ=?";
            kjsList = Db.find(sql, cdlbId, lhId, currXnxq);
        }

        return kjsList;
    }

    public List<Record> kjscxEmptlist(String cdlbId, String lhId, String currZc, String currXnxq) {
        String sql;
        List<Record> kjsList;
        List<Record> emptyList;
        String skdd_ids = "(";
        if (lhId.equals("")) {
            sql = "SELECT SKDD FROM V_WPT_JSKB T WHERE CDLB_ID=? AND INSTR( ','||ZC||',', '," + currZc + ",')!=0 AND XNXQ=?";
            kjsList = Db.find(sql, cdlbId, currXnxq);
            for (int i = 0; i < kjsList.size(); i++) {
                if (i < kjsList.size() - 1) {
                    skdd_ids += "'" + kjsList.get(i).getStr("SKDD") + "',";
                } else {
                    skdd_ids += "'" + kjsList.get(i).getStr("SKDD") + "')";
                }
            }
            if (kjsList.size() > 0) {
                sql = "SELECT CDMC FROM V_WPT_CDJBXX T WHERE CDLB_ID=? AND  CDMC NOT IN" + skdd_ids;
            } else {
                sql = "SELECT CDMC FROM V_WPT_CDJBXX T WHERE CDLB_ID=?";
            }
            emptyList = Db.find(sql, cdlbId);
        } else {
            sql = "SELECT KCMC,SKDD,JSXM,KHFS,ZC,XQ,JC FROM V_WPT_JSKB T WHERE CDLB_ID=? AND SKCDLH=? AND INSTR( ','||ZC||',', '," + currZc + ",')!=0 AND XNXQ=?";
            kjsList = Db.find(sql, cdlbId, lhId, currXnxq);
            for (int i = 0; i < kjsList.size(); i++) {
                if (i < kjsList.size() - 1) {
                    skdd_ids += "'" + kjsList.get(i).getStr("SKDD") + "',";
                } else {
                    skdd_ids += "'" + kjsList.get(i).getStr("SKDD") + "')";
                }
            }
            if (kjsList.size() > 0) {
                sql = "SELECT CDMC FROM V_WPT_CDJBXX T WHERE CDLB_ID=? AND LH=? AND CDMC NOT IN" + skdd_ids;
            } else {
                sql = "SELECT CDMC FROM V_WPT_CDJBXX T WHERE CDLB_ID=? AND LH=?";
            }

            emptyList = Db.find(sql, cdlbId, lhId);
        }

        return emptyList;
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
     * 场地类别查询
     *
     * @return
     */
    public List<Record> cdlb() {
        String sql = "SELECT CDLB_ID,CDLBMC FROM V_WPT_CDLBDM";
        List<Record> cdLbList = Db.find(sql);
        return cdLbList;
    }

    /**
     * 场地查询
     *
     * @return
     */
    public List<Record> lh(String cdlbId) {
        String sql = "SELECT DISTINCT(LH) LH FROM V_WPT_CDJBXX WHERE CDLB_ID=?";
        List<Record> cdList = Db.find(sql, cdlbId);
        return cdList;
    }

    /**
     * @param kjsList
     * @return
     */
    public List<KjscxInfo> getKbList(List<Record> kjsList, String currXq) {
        List<KjscxInfo> kjscxList = new ArrayList<KjscxInfo>();
        for (int i = 0; i < kjsList.size(); i++) {
            Record re = kjsList.get(i);
            String skdd = re.getStr("SKDD");
            String kcmc = re.getStr("KCMC");
            String currJc = re.getStr("JC");
            String xq = re.getStr("XQ");
            boolean flag = false;
            for (int j = 0; j < kjscxList.size(); j++) {
                KjscxInfo kjscxInfo = kjscxList.get(j);
                String skddH = kjscxInfo.getSkdd();
                if (skdd.equals(skddH)) {
                    initJcArray(kjscxInfo, currJc, xq, currXq);
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                KjscxInfo kjscxInfo = new KjscxInfo(skdd, kcmc);
                initJcArray(kjscxInfo, currJc, xq, currXq);
                kjscxList.add(kjscxInfo);
            }

        }
        return kjscxList;
    }

    private void initJcArray(KjscxInfo kjscxInfo, String jc, String xq, String currXq) {
        String[] jcArray = kjscxInfo.getJc();
        String[] jcs = jc.split("-");
        for (int i = 0; i < baseArray.length; i++) {
            String[] baseJcs = baseArray[i].split("-");
            for (int j = 0; j < baseJcs.length; j++) {
                for (int k = 0; k < jcs.length; k++) {
                    if (baseJcs[j].equals(jcs[k])) {
                        if (xq.equals(currXq)) {
                            jcArray[i] = "1";
                        }
                    }
                }
            }
        }
        kjscxInfo.setJc(jcArray);
    }
}
