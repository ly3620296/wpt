package gka.controller.lsjfgl.tjcx.qftj;

public class SearchBean {
    private String xn = "";
    private String xh = "";
    private String xm = "";
    private String xymc = "";
    private String zymc = "";
    private String bjmc = "";
    private String nj = "";

    public SearchBean() {
    }

    public SearchBean(String xn, String xh, String xm, String xymc, String zymc, String bjmc, String nj) {
        this.xn = xn;
        this.xh = xh;
        this.xm = xm;
        this.xymc = xymc;
        this.zymc = zymc;
        this.bjmc = bjmc;
        this.nj = nj;
    }

    public String getXn() {
        return xn;
    }

    public void setXn(String xn) {
        this.xn = xn;
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

    public String getNj() {
        return nj;
    }

    public void setNj(String nj) {
        this.nj = nj;
    }
}
