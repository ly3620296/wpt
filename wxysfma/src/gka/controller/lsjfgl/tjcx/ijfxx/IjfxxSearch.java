package gka.controller.lsjfgl.tjcx.ijfxx;

/**
 * Created by Administrator on 2019/11/11 0011.
 */
public class IjfxxSearch {
    String sfxn; //缴费学年
    String xh; //学号
    String xm; //姓名
    String xymc;  //学院名称
    String zymc;  //专业名称
    String bjmc;  //班级名称
    String dateStart;  //开始时间
    String dateEnd;  //结束时间
    String pay_type;  //缴费类型
    String nj;  //入学年级

    public IjfxxSearch() {
    }

    public IjfxxSearch(String sfxn, String xh, String xm, String xymc, String zymc, String bjmc, String dateStart, String dateEnd, String pay_type, String nj) {
        this.sfxn = sfxn;
        this.xh = xh;
        this.xm = xm;
        this.xymc = xymc;
        this.zymc = zymc;
        this.bjmc = bjmc;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.pay_type = pay_type;
        this.nj = nj;
    }

    public String getSfxn() {
        return sfxn;
    }

    public void setSfxn(String sfxn) {
        this.sfxn = sfxn;
    }

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getXymc() {
        return xymc;
    }

    public void setXymc(String xymc) {
        this.xymc = xymc;
    }

    public String getZymc() {
        return zymc;
    }

    public void setZymc(String zymc) {
        this.zymc = zymc;
    }

    public String getBjmc() {
        return bjmc;
    }

    public void setBjmc(String bjmc) {
        this.bjmc = bjmc;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getNj() {
        return nj;
    }

    public void setNj(String nj) {
        this.nj = nj;
    }
}
