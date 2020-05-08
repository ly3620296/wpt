package gka.common.kit;

import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import gka.controller.lsjfgl.tjcx.qftj.QftjDao;
import gka.controller.lsjfgl.tjcx.qftj.SearchBean;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelExportUtil {
    private static QftjDao qftjDao = new QftjDao();
    private static final String FILEPATH = PathKit.getWebRootPath() + File.separator + "upload" + File.separator;

    public static String getTitle(String title) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String this_title = FILEPATH + dateFormat.format(date) + "_" + title + ".xls";
        return this_title;
    }


    public static File saveFile(Map<String, String> headData, String sql, File file) {
        // 创建工作薄
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        // sheet:一张表的简称
        // row:表里的行
        // 创建工作薄中的工作表
        HSSFSheet hssfSheet = hssfWorkbook.createSheet();
        // 创建行
        HSSFRow row = hssfSheet.createRow(0);
        // 创建单元格，设置表头 创建列
        HSSFCell cell = null;
        // 初始化索引
        int rowIndex = 0;
        int cellIndex = 0;

        // 创建标题行
        row = hssfSheet.createRow(rowIndex);
        rowIndex++;
        // 遍历标题
        for (String h : headData.keySet()) {
            //创建列
            cell = row.createCell(cellIndex);
            //索引递增
            cellIndex++;
            //逐列插入标题
            cell.setCellValue(headData.get(h));
        }

        // 得到所有记录 行：列
        List<Record> list = Db.find(sql);
        Record record = null;

        if (list != null) {
            // 获取所有的记录 有多少条记录就创建多少行
            for (int i = 0; i < list.size(); i++) {
                row = hssfSheet.createRow(rowIndex);
                // 得到所有的行 一个record就代表 一行
                record = list.get(i);
                //下一行索引
                rowIndex++;
                //刷新新行索引
                cellIndex = 0;
                // 在有所有的记录基础之上，便利传入进来的表头,再创建N行
                for (String h : headData.keySet()) {
                    cell = row.createCell(cellIndex);
                    cellIndex++;
                    //按照每条记录匹配数据
                    cell.setCellValue(record.get(h) == null ? "" : record.get(h).toString());
                }
            }
        }
        try {
            FileOutputStream fileOutputStreane = new FileOutputStream(file);
            hssfWorkbook.write(fileOutputStreane);
            fileOutputStreane.flush();
            fileOutputStreane.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static File saveFilepQftj(File file,  SearchBean searchBean) {
        Map<String, String> headData = new LinkedHashMap<String, String>();//标题，后面用到
        headData.put("XN", "学年");
        headData.put("XH", "学号");
        headData.put("XM", "姓名");
        headData.put("SFZH", "身份证号");
        headData.put("XYMC", "学院名称");
        headData.put("ZYMC", "专业名称");
        headData.put("BJMC", "班级名称");
        headData.put("PC_TOTAL", "欠费金额");
        List<Record> titleList = qftjDao.queryTitle();
        for (Record re : titleList) {
            headData.put(re.getStr("JFXMID"), re.getStr("JFXMMC"));
        }
        // 创建工作薄
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        // sheet:一张表的简称
        // row:表里的行
        // 创建工作薄中的工作表
        HSSFSheet hssfSheet = hssfWorkbook.createSheet();
        // 创建行
        HSSFRow row = hssfSheet.createRow(0);
        // 创建单元格，设置表头 创建列
        HSSFCell cell = null;
        // 初始化索引
        int rowIndex = 0;
        int cellIndex = 0;

        // 创建标题行
        row = hssfSheet.createRow(rowIndex);
        rowIndex++;
        // 遍历标题
        for (String h : headData.keySet()) {
            //创建列
            cell = row.createCell(cellIndex);
            //索引递增
            cellIndex++;
            //逐列插入标题
            cell.setCellValue(headData.get(h));
        }

        // 得到所有记录 行：列
        List<Record> list = qftjDao.getOrderInfo(searchBean);
        Record record = null;
        if (list != null) {
            // 获取所有的记录 有多少条记录就创建多少行
            for (int i = 0; i < list.size(); i++) {
                row = hssfSheet.createRow(rowIndex);
                // 得到所有的行 一个record就代表 一行
                record = list.get(i);
                //下一行索引
                rowIndex++;
                //刷新新行索引
                cellIndex = 0;
                // 在有所有的记录基础之上，便利传入进来的表头,再创建N行
                for (String h : headData.keySet()) {
                    cell = row.createCell(cellIndex);
                    cellIndex++;
                    //按照每条记录匹配数据
                    cell.setCellValue(record.get(h) == null ? "" : record.get(h).toString());
                }
            }
        }
        try {
            FileOutputStream fileOutputStreane = new FileOutputStream(file);
            hssfWorkbook.write(fileOutputStreane);
            fileOutputStreane.flush();
            fileOutputStreane.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}