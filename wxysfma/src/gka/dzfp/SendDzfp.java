package gka.dzfp;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import gka.controller.xsjfgl.fpgl.FpglBean;
import gka.controller.xsjfgl.wyjf.WyjfDao;
import gka.dzfp.bean.InvoiceEBillByCollegeBean;
import gka.dzfp.bean.ItemDetail;

import java.util.List;


public class SendDzfp implements Runnable {
    private String oderId;


    public SendDzfp(String oderId) {
        this.oderId = oderId;
    }

    @Override
    public void run() {
        WyjfDao wyjfDao = new WyjfDao();

        Record sfInfo = wyjfDao.sfInfo(oderId);
        String zydm = sfInfo.getStr("ZYDM");
        String id = sfInfo.getStr("IDS");
        String val = sfInfo.getStr("PAY_VAL");
        String[] ids = id.split(",");
        String[] vals = val.split(",");

        //发票种类
        List<Record> flList = fpList();

        for (int i = 0; i < flList.size(); i++) {
            InvoiceEBillByCollegeBean ie = EleUtil.genIBCBean(oderId);
            ItemDetail[] itemDetails = new ItemDetail[3];
            int count = 0;
            int totalAmt = 0;
            String jfxmid = "";
            String jfxm = "";
            for (int j = 0; j < ids.length; j++) {
                String fpId = flList.get(i).getStr("XMID");
                String sfmc = flList.get(i).getStr("XMMC");
                String fplx = flList.get(i).getStr("FPLX");
                if (fpId.contains(ids[j])) {
                    String[] fpIds = fpId.split(",");
                    String[] sfmcS = sfmc.split("，");
                    for (int k = 0; k < fpIds.length; k++) {
                        if (fpIds[k].equals(ids[j])) {
                            jfxm += sfmcS[k] + "，";
                            break;
                        }
                    }
                    ie.setBillCode(fplx.equals("1") ? DzfpConstant.BILLCODE_FSTY_DZ : DzfpConstant.BILLCODE_ZJWL_DZ);
                    Record sfxm = sfxm(zydm, ids[j]);
                    ItemDetail itemDetail = new ItemDetail();
                    itemDetail.setItemCode(sfxm.getStr("XMBM"));
                    itemDetail.setItemName(sfxm.getStr("XMMC").replace("（）", ""));
                    itemDetail.setItemStdCode(sfxm.getStr("SFBZM") == null ? "" : sfxm.getStr("SFBZM"));
//                    itemDetail.setStandard(sfxm.getStr("SFSX") == null ? "" : sfxm.getStr("SFSX"));
                    itemDetail.setStandard(vals[j]);
                    itemDetail.setAmt(vals[j]);
                    itemDetails[count++] = itemDetail;
                    totalAmt += Integer.parseInt(vals[j]);

                    jfxmid += ids[j] + ",";
                    if (count > 3) {
                        break;
                    }
                }
            }
            if (count > 0) {
                //开票
                ie.setItemDetail(parseItemDet(itemDetails));
                ie.setTotalAmt(String.valueOf(totalAmt));

                //记录
                FpglBean fpglBean = new FpglBean();
                fpglBean.setBUSNO(ie.getBusNo());
                fpglBean.setXH(sfInfo.getStr("XH"));
                fpglBean.setXN(sfInfo.getStr("SFXN"));
                fpglBean.setJFHJ(ie.getTotalAmt());
                fpglBean.setJFXM(jfxm.substring(0, jfxm.lastIndexOf("，")));
                fpglBean.setJFXMID(jfxmid.substring(0, jfxmid.lastIndexOf(",")));
                fpglBean.setFPLX(ie.getBillCode().equals(DzfpConstant.BILLCODE_FSTY_DZ) ? "1" : "2");
                //开票
                ElectronicInvoiceApi.invoiceEBillByCollege(ie, fpglBean);

            }

        }
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
