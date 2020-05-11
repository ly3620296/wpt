<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.jfinal.plugin.activerecord.Db" %>
<%@ page import="com.jfinal.plugin.activerecord.Record" %>
<%@ page import="java.io.OutputStream" %>
<%@ page import="gka.xsjfgl.login.WptMaXSUserInfo" %>
<%@ page import="java.io.File" %>
<%@ page import="java.awt.image.BufferedImage" %>
<%@ page import="javax.imageio.ImageIO" %>
<%@ page import="java.io.ByteArrayOutputStream" %>
<%
    try {
        WptMaXSUserInfo userInfo = (WptMaXSUserInfo) session.getAttribute("wptMaXSUserInfo");
        if (userInfo == null) {
            OutputStream os = response.getOutputStream();
            String realPath = request.getServletContext().getRealPath("");
            byte[] bytes = defaultImg(realPath);
            os.write(bytes, 0, bytes.length);
            os.close();
        } else {
            String xh = userInfo.getZh();
            Record first = Db.findFirst("SELECT ZP FROM V_WPT_XSZPB WHERE XH=?", xh);
            if (first != null) {
                byte[] imgs = first.getBytes("ZP");
                if (imgs != null) {
                    OutputStream os = response.getOutputStream();
                    os.write(imgs, 0, imgs.length);
                    os.close();
                } else {
                    OutputStream os = response.getOutputStream();
                    String realPath = request.getServletContext().getRealPath("");
                    byte[] bytes = defaultImg(realPath);
                    os.write(bytes, 0, bytes.length);
                    os.close();
                }
            } else {
                OutputStream os = response.getOutputStream();
                String realPath = request.getServletContext().getRealPath("");
                byte[] bytes = defaultImg(realPath);
                os.write(bytes, 0, bytes.length);
                os.close();
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    out.clear();
    out = pageContext.pushBody();
%>
<%!
    public byte[] defaultImg(String path) {
        File f = new File(path + "img/defaultStu.jpg");
        BufferedImage bi;
        try {
            bi = ImageIO.read(f);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi, "jpg", baos);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
%>