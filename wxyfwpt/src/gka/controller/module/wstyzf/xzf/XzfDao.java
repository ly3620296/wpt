package gka.controller.module.wstyzf.xzf;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;
import gka.pay.wxpay.controller.MyWxpayConstant;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Auther ly
 * @Date 2019/9/4
 * @Describe
 */
public class XzfDao {

    public List<Record> xzf(String xh) {
        String sql = "SELECT T1.ID,T1.XH,T1.SFJF,T1.XNXQ,T2.XMMC,T2.XMJE,T2.SFBX FROM WPT_YSFY T1 LEFT JOIN WPT_JFXM T2 " +
                "ON T1.XMID = T2.XMID  WHERE T1.XH=? AND T2.XMLXID=? ORDER BY T1.XNXQ DESC";
        List<Record> sfzhList = Db.find(sql, xh, MyWxpayConstant.XMLXID_XZF);
        return sfzhList;
    }

    public List<Record> xzfXnxq(String xh) {
        String sql = "SELECT DISTINCT(T1.XNXQ) FROM WPT_YSFY T1 LEFT JOIN WPT_JFXM T2 " +
                "ON T1.XMID = T2.XMID  WHERE T1.XH=? AND T2.XMLXID=? ORDER BY T1.XNXQ DESC";
        List<Record> xzfXnxq = Db.find(sql, xh, MyWxpayConstant.XMLXID_XZF);
        return xzfXnxq;
    }


    public static String preMoney(String orderNo) {
        String preMoney = "";
        String sql = "SELECT TOTAL_FEE FROM  WPT_WXZF_ORDER WHERE OUT_TRADE_NO=?";
        Record re = Db.findFirst(sql, orderNo);
        if (re != null) {
            preMoney = re.getStr("TOTAL_FEE");
        }
        return preMoney;
    }

    public static void updateIllegalMoneyOrder(final Map<String, String> repData, String ids) {
        Db.tx(new IAtom() {
            @Override
            public boolean run() throws SQLException {
                String sql = "UPDATE WPT_WXZF_ORDER SET TIME_END=?,ORDER_STATE=?,RETURN_CODE=?,RESULT_CODE=?,TRANSACTION_ID=? WHERE OUT_TRADE_NO=?";
                int upOrder = Db.update(sql, repData.get("time_end"),  MyWxpayConstant.ORDER_STATE_ILLEGALMONEY, "", "", repData.get("transaction_id"), repData.get("out_trade_no"));
                return true;
            }
        });


    }
}
