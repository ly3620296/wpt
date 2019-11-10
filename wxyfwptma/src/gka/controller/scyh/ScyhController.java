package gka.controller.scyh;

import com.jfinal.aop.Clear;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import gka.common.kit.ReturnKit;
import gka.common.kit.controller.ServiceController;
import gka.controller.login.WptMaUserInfo;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

@ControllerBind(controllerKey = "/scyh")
public class ScyhController extends ServiceController {
    @Clear
    public void upload() {
        try {
            WptMaUserInfo userInfo = (WptMaUserInfo) getSession().getAttribute("wptMaUserInfo");
            if (userInfo == null) {
                renderJson(ReturnKit.retOk("用户登录超时请，请返回重新登陆!"));
            } else {
                UploadFile file = this.getFile();
                String basePath = getSession().getServletContext().getRealPath("/");
                String path = "uploadscyh/";
                String fin_path = basePath + path;
                File imgFile = new File(fin_path);
                if (!imgFile.exists()) {
                    imgFile.mkdirs();
                }
                String fileName = file.getOriginalFileName();
                path += System.currentTimeMillis() + "_" + fileName;
                String imgPath = basePath + path;
                file.getFile().renameTo(new File(imgPath));
                file.getFile().delete();
                System.out.println("path=======" + imgPath);
                List<Map<Integer, String>> list = null;
                if (path.endsWith(".xls")) {
                    list = dealDataByPath(imgPath);    // 分析EXCEL数据
                }
                if (path.endsWith(".xlsx")) {
                    list = dealDataByPathxlsx(imgPath);    // 分析EXCEL数据
                }
                if (list.size() > 1) {
                    String msg = "本次操作数据一共" + (list.size() - 1) + "条,";
                    String fail = "";
                    int success_sum = 0;
                    int fail_sum = 0;
                    for (int i = 1; i < list.size(); i++) {
                        try {
                            Map<Integer, String> map = list.get(i);
                            System.out.println("zh==========" + map.get(0));
                            String sql = "select xm from wpt_yh where zh=?";
                            Record record = Db.findFirst(sql, map.get(0));
                            if (record == null) {
                                sql = "insert into wpt_yh (ZH,XM,KL,XB,MZ,ZZMM,CSRQ,ZJLX,ZJHM,JGDM,JGMC,ZYDM,ZYMC,BJDM,BJMC,NJDM,NJMC,XZ,SFZX,XJZT,BDZCBJ,LXDH,YX,JSDM,JSMC,ZYFXDM,ZYFXMC,OPENID)" +
                                        " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                                int a = Db.update(sql, map.get(0), map.get(1), map.get(2), map.get(3), map.get(4), map.get(5), map.get(6), map.get(7), map.get(8), map.get(9), map.get(10),
                                        map.get(11), map.get(12), map.get(13), map.get(14), map.get(15), map.get(16), map.get(17), map.get(18), map.get(19), map.get(20),
                                        map.get(21), map.get(22), map.get(23), map.get(24), map.get(25), map.get(26), map.get(27));
                                if (a > 0) {
                                    success_sum++;
                                } else {
                                    fail_sum++;
                                    fail += map.get(0) + "|";
                                }
                            } else {
//                        已存在
                                fail_sum++;
                                fail += map.get(0) + "|";
                            }
                        } catch (Exception e) {
                            fail_sum++;
                            e.printStackTrace();
                        }
                        msg += "其中成功" + success_sum + "条，失败" + fail_sum + "条";
                        if (fail_sum > 0) {
                            msg += "，失败账号对应为[" + fail + "]";
                        }
                        renderJson(ReturnKit.retOk(msg));
                    }
                } else {
                    renderJson(ReturnKit.retOk("文件中暂无数据"));
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }

    }

    private List<Map<Integer, String>> dealDataByPath(String path) {
        System.out.println("HSSFWorkbook---path===" + path);
        List<Map<Integer, String>> list = new ArrayList<Map<Integer, String>>();
        // 工作簿
        HSSFWorkbook hwb = null;
        try {
            hwb = new HSSFWorkbook(new FileInputStream(new File(path)));
            HSSFSheet sheet = hwb.getSheetAt(0);    // 获取到第一个sheet中数据
            for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {// 第二行开始取值，第一行为标题行
                HSSFRow row = sheet.getRow(i);        // 获取到第i列的行数据(表格行)
                Map<Integer, String> map = new HashMap<Integer, String>();
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    HSSFCell cell = row.getCell(j);    // 获取到第j行的数据(单元格)
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    map.put(j, cell.getStringCellValue());
                }
                list.add(map);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private List<Map<Integer, String>> dealDataByPathxlsx(String path) {
        System.out.println("dealDataByPathxlsx---path===" + path);
        List<Map<Integer, String>> list = new ArrayList<Map<Integer, String>>();
        // 工作簿
        XSSFWorkbook hwb = null;
        try {
            hwb = new XSSFWorkbook(new FileInputStream(new File(path)));
            Sheet sheet = hwb.getSheetAt(0);    // 获取到第一个sheet中数据
            for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {// 第二行开始取值，第一行为标题行
                Row row = sheet.getRow(i);        // 获取到第i列的行数据(表格行)
                Map<Integer, String> map = new HashMap<Integer, String>();
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    Cell cell = row.getCell(j);    // 获取到第j行的数据(单元格)
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    map.put(j, cell.getStringCellValue());
                }
                list.add(map);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void index() {

    }

    @Override
    public void add() {


    }

    @Override
    public void save() {


    }

    @Override
    public void edit() {


    }

    @Override
    public void update() {


    }

    @Override
    public void delete() {


    }

    @Override
    public void list() {


    }


}
