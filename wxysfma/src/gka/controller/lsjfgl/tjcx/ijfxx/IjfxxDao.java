package gka.controller.lsjfgl.tjcx.ijfxx;

import com.alibaba.druid.util.StringUtils;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import gka.controller.lsjfgl.tjcx.yjfxx.YjfxxSearch;

import java.util.List;

public class IjfxxDao {

    public Page<Record> getOrderInfo(List<Record> title, int page, int limit, YjfxxSearch search) {

        String selectSql = "SELECT o.xh,o.sfxn,o.ids";
        String fromSql = " from wpt_wxzf_special_order o join yhsjb y on y.xh=o.xh and y.xn=o.sfxn where o.return_code='success' ";

        Page<Record> paginate = Db.paginate(page, limit, selectSql, fromSql);
        return paginate;
    }
//    select o.*, y.* from wpt_wxzf_special_order o,yhsjb y where y.xh=o.xh and y.xn=o.sfxn and o.return_code='success'

    private String getSql(List<Record> title) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < title.size(); i++) {
            Record re = title.get(i);

            String jfxmId = re.getStr("JFXMID");
            if (i < title.size() - 1) {
                sb.append("NVL(T2." + jfxmId + ",0) " + jfxmId + ",");
            } else {
                sb.append("NVL(T2." + jfxmId + ",0) " + jfxmId);
            }
        }
        return sb.toString();
    }
}
