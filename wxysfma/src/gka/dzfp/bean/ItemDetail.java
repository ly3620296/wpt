package gka.dzfp.bean;

import gka.dzfp.anno.ElType;

public class ItemDetail {
    //收费项目编码	String	32	是	需要业务系统和平台约定对照关系
    @ElType(require = true)
    private String itemCode = "";
    //收费项目名称	String	200	是
    @ElType(require = true)
    private String itemName = "";
    //收费标准编码	String	20	否	需要业务系统和平台约定对照关系
    private String itemStdCode = "";
    //数量	Number	18,6	是
    @ElType(require = true)
    private int count;
    //收费标准	Number	18,6	是	如有传入收费标准编码，收费标准必须在规定的范围内
    @ElType(require = true)
    private int standard;
    //金额	Number	18,6	是
    private int amt;
    //计量单位	String	10	否
    private String units = "";
    //备注1	String	200	否
    private String memo1 = "";
    //备注2	String	200	否
    private String memo2 = "";

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemStdCode() {
        return itemStdCode;
    }

    public void setItemStdCode(String itemStdCode) {
        this.itemStdCode = itemStdCode;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStandard() {
        return standard;
    }

    public void setStandard(int standard) {
        this.standard = standard;
    }

    public int getAmt() {
        return amt;
    }

    public void setAmt(int amt) {
        this.amt = amt;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getMemo1() {
        return memo1;
    }

    public void setMemo1(String memo1) {
        this.memo1 = memo1;
    }

    public String getMemo2() {
        return memo2;
    }

    public void setMemo2(String memo2) {
        this.memo2 = memo2;
    }

    @Override
    public String toString() {
        return "ItemDetail{" +
                "itemCode='" + itemCode + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemStdCode='" + itemStdCode + '\'' +
                ", count=" + count +
                ", standard=" + standard +
                ", amt=" + amt +
                ", units='" + units + '\'' +
                ", memo1='" + memo1 + '\'' +
                ", memo2='" + memo2 + '\'' +
                '}';
    }
}
