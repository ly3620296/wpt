package gka.dzfp;

/**
 * Created by Administrator on 2020/6/1 0001.
 */
public class FpglBean {
    //业务单号
    private String BUSNO;
    //电子票据代码
    private String EBILLCODE;
    //电子票据号码
    private String EBILLNO;
    //电子校验码
    private String CHECKCODE;
    //开票日期
    private String KPRQ;
    //学号
    private String XH;
    //学年
    private String XN;
    //缴费项目
    private String JFXM;
    //缴费项目ID
    private String JFXMID;
    //缴费金额
    private String JFHJ;
    //入参报文
    private String RCBW;
    //出参报文
    private String CCBW;
    //接口返回码
    private String CODE;
    //接口返回信息
    private String MSG;
    //发票类型 1 通用 2往来
    private String FPLX;

    public String getBUSNO() {
        return BUSNO;
    }

    public void setBUSNO(String BUSNO) {
        this.BUSNO = BUSNO;
    }

    public String getEBILLCODE() {
        return EBILLCODE;
    }

    public void setEBILLCODE(String EBILLCODE) {
        this.EBILLCODE = EBILLCODE;
    }

    public String getEBILLNO() {
        return EBILLNO;
    }

    public void setEBILLNO(String EBILLNO) {
        this.EBILLNO = EBILLNO;
    }

    public String getCHECKCODE() {
        return CHECKCODE;
    }

    public void setCHECKCODE(String CHECKCODE) {
        this.CHECKCODE = CHECKCODE;
    }

    public String getKPRQ() {
        return KPRQ;
    }

    public void setKPRQ(String KPRQ) {
        this.KPRQ = KPRQ;
    }

    public String getXH() {
        return XH;
    }

    public void setXH(String XH) {
        this.XH = XH;
    }

    public String getXN() {
        return XN;
    }

    public void setXN(String XN) {
        this.XN = XN;
    }

    public String getJFXM() {
        return JFXM;
    }

    public void setJFXM(String JFXM) {
        this.JFXM = JFXM;
    }

    public String getJFHJ() {
        return JFHJ;
    }

    public void setJFHJ(String JFHJ) {
        this.JFHJ = JFHJ;
    }

    public String getRCBW() {
        return RCBW;
    }

    public void setRCBW(String RCBW) {
        this.RCBW = RCBW;
    }

    public String getCCBW() {
        return CCBW;
    }

    public void setCCBW(String CCBW) {
        this.CCBW = CCBW;
    }

    public String getCODE() {
        return CODE;
    }

    public void setCODE(String CODE) {
        this.CODE = CODE;
    }

    public String getMSG() {
        return MSG;
    }

    public void setMSG(String MSG) {
        this.MSG = MSG;
    }

    public String getFPLX() {
        return FPLX;
    }

    public void setFPLX(String FPLX) {
        this.FPLX = FPLX;
    }

    public String getJFXMID() {
        return JFXMID;
    }

    public void setJFXMID(String JFXMID) {
        this.JFXMID = JFXMID;
    }
}
