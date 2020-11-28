<%@ page import="com.jfinal.plugin.activerecord.Db" %>
<%@ page import="com.jfinal.plugin.activerecord.Record" %>
<%@ page import="java.util.List" %>
<%@ page import="gka.controller.xsjfgl.fpgl.FpglBean" %>
<%@ page import="gka.dzfp.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
////    J19603235  F1830105
//    String sql = "select * from wptma_ckdzfp1 where xh not in ('F1830105','J19603235')";
//    List<Record> records = Db.find(sql);
//    for (Record re : records) {
//        FpglBean fpglBean = new FpglBean();
//        String sendObj = re.getStr("RCBW");
//        fpglBean.setBUSNO(EleUtil.genBusNo());
//        fpglBean.setXH(re.getStr("XH"));
//        fpglBean.setXN(re.getStr("XN"));
//        fpglBean.setJFHJ(re.getStr("JFHJ"));
//        fpglBean.setJFXM(re.getStr("JFXM"));
//        fpglBean.setJFXMID(re.getStr("JFXMID"));
//        fpglBean.setFPLX(re.getStr("FPLX"));
//        ThreadPoolUtil.execute(new SendDzfp_Bak(sendObj, fpglBean));
//    }

%>
<html>
<head>
    <title></title>
</head>
<body>

</body>
</html>
