package gka.controller.module.jwl.kjscx;


public class KjscxInfo {
    //上课地点
    private String skdd;
    //课程名称
    private String kcmc;
    //节次
    private String[] jc = new String[]{"0", "0", "0", "0", "0", "0"};

    public KjscxInfo() {
    }

    public KjscxInfo(String skdd, String kcmc) {
        this.skdd = skdd;
        this.kcmc = kcmc;
    }

    public String getSkdd() {
        return skdd;
    }

    public void setSkdd(String skdd) {
        this.skdd = skdd;
    }

    public String getKcmc() {
        return kcmc;
    }

    public void setKcmc(String kcmc) {
        this.kcmc = kcmc;
    }

    public String[] getJc() {
        return jc;
    }

    public void setJc(String[] jc) {
        this.jc = jc;
    }

}
