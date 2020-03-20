package gka.controller.lsjfgl.tjcx.dnkp;

import com.alibaba.druid.util.StringUtils;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import gka.controller.lsjfgl.tjcx.ijfxx.IjfxxSearch;
import gka.pay.wxpay.WXPayUtil;
import gka.pay.wxpay.controller.MyWxpayConstant;
import gka.pay.wxpay.controller.WxPayOrder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DnkpDao {

    public static List<Record> getOrderInfo(String sfxn, String xm, String xh, String sfzh) {
        String selectSql = "select XM,XH,XN ";
        String fromSql = " from xssfb where 1=1";
        if (!StringUtils.isEmpty(sfxn)) {
            fromSql += " AND xn='" + sfxn + "'";
        }
        if (!StringUtils.isEmpty(xm)) {
            fromSql += " AND XM like '%" + xm + "%'";
        }
        if (!StringUtils.isEmpty(xh)) {
            fromSql += " AND xh='" + xh + "'";
        }
        if (!StringUtils.isEmpty(sfzh)) {
            fromSql += " AND sfzh='" + sfzh + "'";
        }
        List<Record> list = Db.find(selectSql + fromSql);
        return list;
    }

    //APPID  MCH_ID OPENID PREPAY_ID
    public static void insertOrder(String xh, String OUT_TRADE_NO, String ids, String PAY_TYPE, String TOTAL_FEE, String ip, String xn, String orderNo) {
        String sql = "INSERT INTO WPT_WXZF_SPECIAL_ORDER (XH,OUT_TRADE_NO,IDS,PAY_TYPE,TOTAL_FEE,APPID,MCH_ID,OPENID,PAYIP,TIME_START,ORDER_STATE," +
                "PREPAY_ID,SFXN,ORDER_NO,CODE_URL,RETURN_CODE,result_code) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Db.update(sql, xh, OUT_TRADE_NO, ids, PAY_TYPE, TOTAL_FEE, "", "", "", ip, getTime(), "2", "", xn, orderNo, "", "success", "SUCCESS");
    }

    public static String getTime() {
        Date date = new Date();
        String str = "yyyMMddHHmmss";
        SimpleDateFormat sdf = new SimpleDateFormat(str);
        System.out.println(sdf.format(date));
        return sdf.format(date);
    }

    public Record getUserInfo(String xh, String xn) {
        String sql = "SELECT XH,XM,SFZH,XYMC,ZYMC,BJMC,NJ FROM XSSFB WHERE XH=? AND XN=?";
        Record userInfo = Db.findFirst(sql, xh, xn);
        return userInfo;
    }

    public List<Record> queryYjfList(String xh,String xn) {
        List<Record> list = Db.find("");
        return list;
    }
}
