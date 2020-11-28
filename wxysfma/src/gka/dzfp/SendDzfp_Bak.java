package gka.dzfp;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import gka.controller.xsjfgl.fpgl.FpglBean;
import gka.controller.xsjfgl.wyjf.WyjfDao;
import gka.dzfp.bean.InvoiceEBillByCollegeBean;
import gka.dzfp.bean.ItemDetail;

import java.util.List;


public class SendDzfp_Bak implements Runnable {
    private String sendObj;
    private FpglBean fpglBean;


    public SendDzfp_Bak(String sendObj, FpglBean fpglBean) {
        this.sendObj = sendObj;
        this.fpglBean = fpglBean;
    }

    @Override
    public void run() {
        ElectronicInvoiceApi_bak.invoiceEBillByCollege(sendObj, fpglBean);
    }

    /**
     * 发票种类
     *
     * @return
     */
    private Record sfxm(String zydm, String sfxm) {
        String sql = "";
        Record record = null;
        if (sfxm.equals("SFXM01")) {
            sql = "SELECT XMBM,XMMC||'（'||SFBZMC||'）' XMMC ,SFBZM,SFSX,XMMC MC FROM WPTMA_FPXX_SFXM WHERE ZYDM=?";
            record = Db.findFirst(sql, zydm);
        } else {
            sql = "SELECT XMBM,XMMC||'（'||SFBZMC||'）' XMMC ,SFBZM,SFSX,XMMC MC FROM WPTMA_FPXX_SFXM WHERE SFXMID=?";
            record = Db.findFirst(sql, sfxm);
        }
        return record;
    }


    /**
     * 发票种类
     *
     * @return
     */
    private List<Record> fpList() {
        String sql = "SELECT ID,FPMC,FPLX,QYZT,XMID,XMMC FROM WPTMA_FPXX";
        List<Record> records = Db.find(sql);
        return records;
    }


    private ItemDetail[] parseItemDet(ItemDetail[] itemDetails) {
        int len = 0;
        for (ItemDetail id : itemDetails) {
            if (id != null) {
                len++;
            }
        }
        ItemDetail[] newId = new ItemDetail[len];
        for (int i = 0; i < len; i++) {
            newId[i] = itemDetails[i];
        }
        return newId;
    }
}
