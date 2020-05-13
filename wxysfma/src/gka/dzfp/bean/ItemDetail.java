package gka.dzfp.bean;

import gka.dzfp.anno.ElType;

public class ItemDetail {
    //收费项目编码 需要业务系统和平台约定对照关系
    @ElType(require = true)
    private String itemCode = "";
    //收费项目名称
    @ElType(require = true)
    private String itemName = "";
    //收费标准编码	String	20	否	需要业务系统和平台约定对照关系
    private String itemStdCode = "";
    //数量	Number	18,6	是
    @ElType(require = true)
    private String count = "1";
    //收费标准	Number	18,6	是	如有传入收费标准编码，收费标准必须在规定的范围内
    @ElType(require = true)
    private String standard = "";
    //金额	Number	18,6	是
    private String amt = "";
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

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
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
