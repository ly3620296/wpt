package gka.controller.lsjfgl.tjcx.yjfxx;

/**
 * Created by Administrator on 2019/11/11 0011.
 */
public class YjfxxSearch {
    String nj;
    String xh;
    String xm;
    String xymc;
    String zymc;
    String bjmc;

    public YjfxxSearch() {
    }

    public YjfxxSearch(String nj, String xh, String xm, String xymc, String zymc, String bjmc) {
        this.nj = nj;
        this.xh = xh;
        this.xm = xm;
        this.xymc = xymc;
        this.zymc = zymc;
        this.bjmc = bjmc;
    }

    public String getNj() {
        return nj;
    }

    public void setNj(String nj) {
        this.nj = nj;
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
}
