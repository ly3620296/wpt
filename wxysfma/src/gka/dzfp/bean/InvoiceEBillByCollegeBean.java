package gka.dzfp.bean;


import gka.dzfp.anno.ElType;

import java.util.List;

public class InvoiceEBillByCollegeBean {
    //业务种类	即分配的appid
    @ElType(require = true)
    private String busType = "";
    //业务单号,全局唯一，也叫业务流水号
    @ElType(require = true)
    private String busNo = "";
    //开票点编码	String	001
    @ElType(require = true)
    private String placeCode = "001";
    //票据种类编码	String	32	是	需要业务系统和平台约定对照关系
    @ElType(require = true)
    private String billCode = "";
    //收费渠道		需要业务系统和平台约定对照关系 直接填写业务系统内部编码值：收费渠道
    @ElType(require = true)
    private String channel = "";
    //收费期间格式：yyyy，如2017
    private String chargePeriod = "";
    //收费日期	格式：yyyy-MM-dd
    private String chargeDate = "";
    //交款人类型：1 个人 2 单位，默认为1
    @ElType(require = true)
    private String payerType = "1";
    //单位机构编码	String	50	否	交款人类型=2,填写 值如：统一社会信用代码
    private String creditCode = "";
    //收款人  财务老师
    @ElType(require = true)
    private String recer = "";
    //开票日期 格式：yyyy-MM-dd
    @ElType(require = true)
    private String ivcDate = "";
    //学生姓名(缴款人)
    @ElType(require = true)
    private String stuName = "";
    //默认开票点维护的老师
    @ElType(require = true)
    private String operator = "";
    //合计金额	Number	18,6	是
    @ElType(require = true)
    private String totalAmt;
    //备注	String	200	否
    private String memo = "";
    //学生学号
    private String stuNo = "";
    //学院编码
    private String collegeCode = "";
    //学院名称
    private String collegeName = "";
    //系别编码
    private String deptCode = "";
    //系别名称
    private String deptName = "";
    //专业编码
    private String proCode = "";
    //专业名称
    private String proName = "";
    //班级编码
    private String classCode = "";
    //班级名称
    private String className = "";
    //学生身份证
    private String stuidCard = "";
    //入学年度 格式：yyyy，如2017
    private String inYear = "";
    //离校年度 格式：yyyy，如2017
    private String outYear = "";
    //手机号码
    private String phone = "";
    //邮箱地址
    private String email = "";
    //通知方式	0无需通知，1手机通知，2邮箱通知，3两者都通知
    private String noticeMode = "";
    @ElType(require = true)
    private ItemDetail[] itemDetail;

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getBusNo() {
        return busNo;
    }

    public void setBusNo(String busNo) {
        this.busNo = busNo;
    }

    public String getPlaceCode() {
        return placeCode;
    }

    public void setPlaceCode(String placeCode) {
        this.placeCode = placeCode;
    }

    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getChargePeriod() {
        return chargePeriod;
    }

    public void setChargePeriod(String chargePeriod) {
        this.chargePeriod = chargePeriod;
    }

    public String getChargeDate() {
        return chargeDate;
    }

    public void setChargeDate(String chargeDate) {
        this.chargeDate = chargeDate;
    }

    public String getPayerType() {
        return payerType;
    }

    public void setPayerType(String payerType) {
        this.payerType = payerType;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public String getRecer() {
        return recer;
    }

    public void setRecer(String recer) {
        this.recer = recer;
    }

    public String getIvcDate() {
        return ivcDate;
    }

    public void setIvcDate(String ivcDate) {
        this.ivcDate = ivcDate;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(String totalAmt) {
        this.totalAmt = totalAmt;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getStuNo() {
        return stuNo;
    }

    public void setStuNo(String stuNo) {
        this.stuNo = stuNo;
    }

    public String getCollegeCode() {
        return collegeCode;
    }

    public void setCollegeCode(String collegeCode) {
        this.collegeCode = collegeCode;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getProCode() {
        return proCode;
    }

    public void setProCode(String proCode) {
        this.proCode = proCode;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getStuidCard() {
        return stuidCard;
    }

    public void setStuidCard(String stuidCard) {
        this.stuidCard = stuidCard;
    }

    public String getInYear() {
        return inYear;
    }

    public void setInYear(String inYear) {
        this.inYear = inYear;
    }

    public String getOutYear() {
        return outYear;
    }

    public void setOutYear(String outYear) {
        this.outYear = outYear;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNoticeMode() {
        return noticeMode;
    }

    public void setNoticeMode(String noticeMode) {
        this.noticeMode = noticeMode;
    }

    public ItemDetail[] getItemDetail() {
        return itemDetail;
    }

    public void setItemDetail(ItemDetail[] itemDetail) {
        this.itemDetail = itemDetail;
    }
}
