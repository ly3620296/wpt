package gka.controller.xsjfgl.grjfxx;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/10/8 0008.
 */
public class GrjfxxDao {
    public List<Record> grjfxx(String xh) {
        String sql = "SELECT T1.ID,T1.XMID,T1.XH,T1.SFJF,T1.XNXQ,T2.XMMC,T2.XMJE,T2.SFBX FROM WPT_YSFY T1 LEFT JOIN WPT_JFXM T2 " +
                "ON T1.XMID = T2.XMID  WHERE T1.XH=? AND T2.XMLXID=? AND SFJF=? ORDER BY T1.XNXQ DESC";
        List<Record> list = Db.find(sql, xh, MyConstant.xzfid, "1");
        return list;
    }

    public List<HeaderInfo> xzf() {
        String sql = "SELECT XMID,XMMC,SFBX FROM WPT_JFXM T WHERE XMLXID=?";
        List<Record> list = Db.find(sql, MyConstant.xzfid);
        List<HeaderInfo> headerInfos = null;
        if (list != null && list.size() > 0) {
            headerInfos = new ArrayList<HeaderInfo>();
            for (Record re : list) {
                HeaderInfo headerInfo = new HeaderInfo();
                headerInfo.setSfbx(re.getStr("SFBX"));
                headerInfo.setXmid(re.getStr("XMID"));
                headerInfo.setXmmc(re.getStr("XMMC"));
            }
        }
        return headerInfos;
    }
}
