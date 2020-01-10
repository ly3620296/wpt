package gka.controller.lsjfgl.tjcx.ijfxx;

import com.alibaba.druid.util.StringUtils;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import java.util.ArrayList;
import java.util.List;

public class IjfxxDao {

    public Page<Record> getOrderInfo(int page, int limit, IjfxxSearch search) {

        String selectSql = "SELECT decode(o.pay_type,'CASH','现金','CARD','刷卡','JSAPI','APP微信','NATIVE','微信扫码') PAY_TYPE,o.xh,o.sfxn,o.ids,o.ORDER_NO,y.xm,y.xb,y.xymc,y.zymc,y.bjmc,o.TOTAL_FEE,to_char(to_date(o.TIME_START,'yyyymmddhh24miss'),'yyyy-mm-dd hh24:mi:ss') TIME_START";
        String fromSql = " from wpt_wxzf_special_order o left join yhsjb y on y.xh=o.xh and y.xn=o.sfxn where o.return_code='success' ";
        if (!StringUtils.isEmpty(search.getSfxn())) {
            fromSql += " AND o.sfxn='" + search.getSfxn() + "'";
        }
        if (!StringUtils.isEmpty(search.getXh())) {
            fromSql += " AND o.xh='" + search.getXh() + "'";
        }
        if (!StringUtils.isEmpty(search.getXm())) {
            fromSql += " AND y.XM like '%" + search.getXm() + "%'";
        }
        if (!StringUtils.isEmpty(search.getXymc())) {
            fromSql += " AND y.xymc like '%" + search.getXymc() + "%'";
        }
        if (!StringUtils.isEmpty(search.getZymc())) {
            fromSql += " AND y.zymc like '%" + search.getZymc() + "%'";
        }
        if (!StringUtils.isEmpty(search.getBjmc())) {
            fromSql += " AND y.bjmc='" + search.getBjmc() + "'";
        }
        if (!StringUtils.isEmpty(search.getDateStart())) {
            fromSql += " AND  to_date(o.TIME_START,'yyyymmddhh24miss')>=to_date('" + search.getDateStart() + "','yyyy-mm-dd') ";
        }
        if (!StringUtils.isEmpty(search.getDateEnd())) {
            fromSql += " AND  to_date(o.TIME_START,'yyyymmddhh24miss')<=to_date('" + search.getDateEnd() + "','yyyy-mm-dd')+1 ";
        }
        if (!StringUtils.isEmpty(search.getPay_type())) {
            fromSql += " AND o.pay_type='" + search.getPay_type() + "'";
        }
        if (!StringUtils.isEmpty(search.getNj())) {
            fromSql += " AND y.nj='" + search.getNj() + "'";
        }
        fromSql += "order by to_number(o.TIME_START) desc";
        Page<Record> paginate = Db.paginate(page, limit, selectSql, fromSql);
        return paginate;
    }
//    select o.*, y.* from wpt_wxzf_special_order o,yhsjb y where y.xh=o.xh and y.xn=o.sfxn and o.return_code='success'

    public List<Record> getList(List<Record> out_list, List<Record> titles) {
        List<Record> list = new ArrayList<Record>();
        for (int i = 0; i < out_list.size(); i++) {
            String ids = out_list.get(i).get("IDS") == null ? "" : out_list.get(i).get("IDS").toString();
            String xh = out_list.get(i).get("XH") == null ? "" : out_list.get(i).get("XH").toString();            //学号
            String xn = out_list.get(i).get("SFXN") == null ? "" : out_list.get(i).get("SFXN").toString();       //学年
            Record this_re = Db.findFirst("select * from yhsjb where xh=? and xn=?", xh, xn);
            Record re = out_list.get(i);
            for (int t = 0; t < titles.size(); t++) {
                String jfje = "0";
                String title_xmid = titles.get(t).getStr("JFXMID");
                String[] xmid = ids.split(",");
                for (int a = 0; a < xmid.length; a++) {
                    String one_xmid = xmid[a];
                    if (title_xmid.equals(one_xmid)) {
                        jfje = this_re.getStr(title_xmid);
                        break;
                    }
                }
                re.set(title_xmid, jfje);
            }
            list.add(re);
        }
        return list;
    }
}
