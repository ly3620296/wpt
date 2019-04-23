package gka.controller.module.jwl.jskb;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import gka.controller.CommonUtil;
import gka.controller.module.jwl.xskb.XskbDao;
import gka.kit.ArrayUtil;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Auther ly
 * @Date 2019/4/3
 * @Describe
 */
public class JskbDao {
    public List<Record> jskb(String jgh, String xnxq, HttpSession session) {
        List<Record> jskbList = (List<Record>) session.getAttribute("jskbList");
        if (jskbList == null) {
            String sql = "SELECT KCMC,SKDD,JXBMC,BJZC,KHFS,ZC,JXBRS,XQ,JC FROM V_WPT_JSKB  WHERE XNXQ=? AND JGH=?";
            jskbList = Db.find(sql, xnxq, jgh);
            session.setAttribute("jskbList", jskbList);
        }
        return jskbList;
    }

    /**
     * 获得二位数组课表 插件需要
     *
     * @param currZc
     * @return
     */
    public String[][] getKbArray(List<Record> jskbList, String currZc) {
        String[][] jskb = new String[7][12];
        CommonUtil.initArray(jskb);
        //循环7天
        for (int i = 1; i <= 7; i++) {
            for (int j = 0; j < jskbList.size(); j++) {
                Record re = jskbList.get(j);
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
                            jskb[i - 1][k - 1] = kcmc + "@" + skdd + "_n_" + re.getStr("JXBMC") +"_n_" + re.getStr("BJZC")+ "_n_" + re.getStr("KHFS") + "_n_" + CommonUtil.formatZc(re.getStr("ZC"))+ "_n_"+re.getStr("JXBRS");
                        }
                    }
                }
            }
        }
        return jskb;
    }


}
