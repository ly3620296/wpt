package gka.controller.module.jwl.xskb;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import gka.controller.CommonUtil;
import gka.kit.ArrayUtil;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther ly
 * @Date 2019/4/3
 * @Describe
 */
public class XskbDao {
    public List<Record> xskb(String xh, String xnxq, HttpSession session) {
        List<Record> xskbList = (List<Record>) session.getAttribute("xskbList");
        if (xskbList == null) {
            String sql = "SELECT KCMC,SKDD,RKJSXM,KHFS,ZC,XQ,JC FROM V_WPT_XSKB  WHERE XNXQ=? AND XH=?";
            xskbList = Db.find(sql, xnxq, xh);
            session.setAttribute("xskbList", xskbList);
        }
        return xskbList;
    }

    /**
     * 获得二位数组课表 插件需要
     *
     * @param xskbList
     * @param currZc
     * @return
     */
    public String[][] getKbArray(List<Record> xskbList, String currZc) {
        String[][] xskb = new String[7][12];
        CommonUtil.initArray(xskb);
        //循环7天
        for (int i = 1; i <= 7; i++) {
            for (int j = 0; j < xskbList.size(); j++) {
                Record re = xskbList.get(j);
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
                            xskb[i - 1][k - 1] = kcmc + "@" + skdd + "_n_" + re.getStr("RKJSXM") + "_n_" + re.getStr("KHFS") + "_n_" + CommonUtil.formatZc(re.getStr("ZC"));
                        }
                    }
                }
            }
        }
        return xskb;
    }


}
