package gka.controller.lsjfgl.tjcx.xsyjxx;

/**
 * Created by Administrator on 2019/11/11 0011.
 */
public class XsyjxxSearch {
    String ddbh;
    String xn;
    String xm;
    String ddzt;
    String xh;
    String sfzh;
    String dateStart;
    String dateEnd;

    public XsyjxxSearch() {
    }

    public XsyjxxSearch(String ddbh, String xn, String xm, String ddzt, String xh, String sfzh, String dateStart, String dateEnd) {
        this.ddbh = ddbh;
        this.xn = xn;
        this.xm = xm;
        this.ddzt = ddzt;
        this.xh = xh;
        this.sfzh = sfzh;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public String getDdbh() {
        return ddbh;
    }

    public void setDdbh(String ddbh) {
        this.ddbh = ddbh;
    }

    public String getXn() {
        return xn;
    }

    public void setXn(String xn) {
        this.xn = xn;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getDdzt() {
        return ddzt;
    }

    public void setDdzt(String ddzt) {
        this.ddzt = ddzt;
    }

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }

    public String getSfzh() {
        return sfzh;
    }

    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
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
}
