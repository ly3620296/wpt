package gka.lsjfgl.login;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import javax.servlet.http.HttpSession;

/**
 * @Auther ly
 * @Date 2019/4/19
 * @Describe
 */
public class LSLoginDao {

    public Record loginValidate(String account, String password) {
        String sql = "SELECT ZH,XM,XB,MZ,ZZMM,ZJLX,ZJHM,JGDM,JGMC,ZYDM,ZYMC,BJDM,BJMC,NJDM,NJMC,XZ,SFZX,XJZT,LXDH,YX,JSDM,JSMC,ZYFXDM,ZYFXMC,OPENID FROM WPT_YH WHERE ZH=? AND KL=? and JSDM='01'";
        Record re = Db.findFirst(sql, account, password);
        return re;
    }

    public static void setSession(Record record, HttpSession session) {
        WptMaLSUserInfo userInfo = new WptMaLSUserInfo();
        userInfo.setZh(record.getStr("ZH") == null ? "" : record.getStr("ZH"));
        userInfo.setXm(record.getStr("XM") == null ? "" : record.getStr("XM"));
        userInfo.setXb(record.getStr("XB") == null ? "" : record.getStr("XB"));
        userInfo.setMz(record.getStr("MZ") == null ? "" : record.getStr("MZ"));
        userInfo.setZzmm(record.getStr("ZZMM") == null ? "" : record.getStr("ZZMM"));
        userInfo.setCsrq(record.getStr("CSRQ") == null ? "" : record.getStr("CSRQ"));
        userInfo.setZjlx(record.getStr("ZJLX") == null ? "" : record.getStr("ZJLX"));
        userInfo.setZjhm(record.getStr("ZJHM") == null ? "" : record.getStr("ZJHM"));
        userInfo.setJgdm(record.getStr("JGDM") == null ? "" : record.getStr("JGDM"));
        userInfo.setJgmc(record.getStr("JGMC") == null ? "" : record.getStr("JGMC"));
        userInfo.setZydm(record.getStr("ZYDM") == null ? "" : record.getStr("ZYDM"));
        userInfo.setZymc(record.getStr("ZYMC") == null ? "" : record.getStr("ZYMC"));
        userInfo.setBjdm(record.getStr("BJDM") == null ? "" : record.getStr("BJDM"));
        userInfo.setBjmc(record.getStr("BJMC") == null ? "" : record.getStr("BJMC"));
        userInfo.setNjdm(record.getStr("NJDM") == null ? "" : record.getStr("NJDM"));
        userInfo.setNjmc(record.getStr("NJMC") == null ? "" : record.getStr("NJMC"));
        userInfo.setXz(record.getStr("XZ") == null ? "" : record.getStr("XZ"));
        userInfo.setSfzx(record.getStr("SFZX") == null ? "" : record.getStr("SFZX"));
        userInfo.setXjzt(record.getStr("XJZT") == null ? "" : record.getStr("XJZT"));
        userInfo.setBdzc(record.getStr("BDZC") == null ? "" : record.getStr("BDZC"));
        userInfo.setLxdh(record.getStr("LXDH") == null ? "" : record.getStr("LXDH"));
        userInfo.setYx(record.getStr("YX") == null ? "" : record.getStr("YX"));
        userInfo.setJsdm(record.getStr("JSDM") == null ? "" : record.getStr("JSDM"));
        userInfo.setJsmc(record.getStr("JSMC") == null ? "" : record.getStr("JSMC"));
        userInfo.setZyfxdm(record.getStr("ZYFXDM") == null ? "" : record.getStr("ZYFXDM"));
        userInfo.setZyfxmc(record.getStr("ZYFXMC") == null ? "" : record.getStr("ZYFXMC"));
        userInfo.setOpenId(record.getStr("OPENID") == null ? "" : record.getStr("OPENID"));
        session.setAttribute("wptMaLSUserInfo", userInfo);
    }
}
