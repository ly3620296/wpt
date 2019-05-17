package gka.controller.module.jwl.xkqkcx;

/**
 * Created by Administrator on 2019/5/6 0006.
 */
public class RqInfo {
    private String rq;
    private String xq;
    private String xq_num;

    public RqInfo() {
    }

    public RqInfo(String rq, String xq, String xq_num) {
        this.rq = rq;
        this.xq = xq;
        this.xq_num = xq_num;
    }

    public String getRq() {
        return rq;
    }

    public void setRq(String rq) {
        this.rq = rq;
    }

    public String getXq() {
        return xq;
    }

    public void setXq(String xq) {
        this.xq = xq;
    }

    public String getXq_num() {
        return xq_num;
    }

    public void setXq_num(String xq_num) {
        this.xq_num = xq_num;
    }
}
