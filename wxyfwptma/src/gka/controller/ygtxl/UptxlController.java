package gka.controller.ygtxl;

import com.jfinal.aop.Clear;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Db;
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

@ControllerBind(controllerKey = "/uptxl")
public class UptxlController extends ServiceController {
    @Clear
    public void upload() {
        try {
            WptMaUserInfo userInfo = (WptMaUserInfo) getSession().getAttribute("wptMaUserInfo");
            if (userInfo == null) {
                renderJson(ReturnKit.retOk("用户登录超时请，请返回重新登陆!"));
            } else {
                UploadFile file = this.getFile();
                String basePath = getSession().getServletContext().getRealPath("/");
                String path = "uploadygtxl/";
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
                    for (int i = 1; i < list.size(); i++) {
                        try {
                            Map<Integer, String> map = list.get(i);
                            System.out.println("zh==========" + map.get(0));
                            String sql = "insert into wptma_ygtxl (id,xm, dh, xyid) values(SEQ_WPTMA_YGTXL.NEXTVAL,?,?,?)";
                            int a = Db.update(sql, map.get(0), map.get(1), map.get(2));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    renderJson(ReturnKit.retOk("录入完成!共计" + (list.size() - 1) + "条!"));
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