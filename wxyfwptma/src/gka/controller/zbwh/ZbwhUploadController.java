package gka.controller.zbwh;

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
import java.text.SimpleDateFormat;
import java.util.*;

@ControllerBind(controllerKey = "/zbwhUpload")
public class ZbwhUploadController extends ServiceController {
    @Clear
    public void upload() {
        try {
            WptMaUserInfo userInfo = (WptMaUserInfo) getSession().getAttribute("wptMaUserInfo");
            if (userInfo == null) {
                renderJson(ReturnKit.retOk("用户登录超时请，请返回重新登陆!"));
            } else {
                UploadFile file = this.getFile();
                String basePath = getSession().getServletContext().getRealPath("/");
                String path = "uploadyhzb/";
                String fin_path = basePath + path;
                File imgFile = new File(fin_path);
                if (!imgFile.exists()) {
                    imgFile.mkdirs();
                }
                String fileName = file.getOriginalFileName();
                System.out.println("fileName=======" + fileName);
                String now = System.currentTimeMillis() + "";
                path += now + "_" + fileName;
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
                    String id = now + new SimpleDateFormat("yyyyMMdd").format(new Date());
                    String bt = fileName.substring(0, fileName.indexOf("."));
                    int resule = Db.update("insert into wptma_zbbtb (id,bt) values(?,?)", id, bt);
                    if (resule > 0) {
                        List<Record> list_zd = null;
                        for (int i = 0; i < list.size(); i++) {
                            Map map = list.get(i);
                            if (i == 0) {
                                int sum = Integer.parseInt(Db.findFirst("select count(1) from wptma_zbsyzd").getStr("count(1)"));
                                for (int a = 1; a < map.size(); a++) {
                                    String ms = map.get(a).toString();
                                    Record record = Db.findFirst("select ZD from wptma_zbsyzd where ms=?", ms);
                                    if (record != null) {
                                        Db.update("insert into wptma_zbzdb (ID,ZD,ZDMS,SX) values(?,?,?,?)", id, record.getStr("ZD"), ms, (a - 1));
                                    } else {
                                        sum++;
                                        String zd = "zd" + sum;
                                        Db.update("insert into wptma_zbsyzd (ZD,MS) values(?,?)", zd, ms);
                                        Db.update("insert into wptma_zbzdb (ID,ZD,ZDMS,SX) values(?,?,?,?)", id, zd, ms, (a - 1));
                                        Db.update("alter table wptma_zbsjb add " + zd + " varchar(50)");
                                    }
                                }
                            } else {
                                if (list_zd == null) {
                                    list_zd = Db.find("select * from wptma_zbzdb t where id=? order by sx", id);
                                }
                                String sql = "insert into wptma_zbsjb (id,yhm [aaaaa]) values(?,? [bbbbb])";
                                String aaaaa = "";
                                String bbbbb = "";
                                for (int c = 0; c < list_zd.size(); c++) {
                                    Record record = list_zd.get(c);
                                    aaaaa += "," + (record.get("zd") == null ? "" : record.get("zd").toString() + "");
                                    bbbbb += ",'" + (map.get(c + 1) == null ? "" : map.get(c + 1).toString()) + "'";
                                }
                                sql = sql.replace("[aaaaa]", aaaaa);
                                sql = sql.replace("[bbbbb]", bbbbb);
                                Db.update(sql, id, map.get(0));
                                System.out.println("sql===========" + sql);
                            }
                        }
                        renderJson(ReturnKit.retOk("导入成功！"));
                    } else {
                        renderJson(ReturnKit.retOk("记录工资标题表失败!"));
                    }
                } else {
                    renderJson(ReturnKit.retOk("文件中暂无数据"));
                }
            }
        } catch (Exception e) {
            renderJson(ReturnKit.retOk("导入失败！错误信息为" + (e.toString().length() > 50 ? e.toString().substring(0, 50) : e.toString())));
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