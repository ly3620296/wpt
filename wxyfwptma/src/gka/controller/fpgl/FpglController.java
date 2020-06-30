package gka.controller.fpgl;

import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import gka.common.kit.ReKit;
import gka.common.kit.ReturnKit;
import gka.common.kit.controller.LController;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerBind(controllerKey = "/fpgl")
public class FpglController extends LController {
    private FpglDao fpglDao = new FpglDao();

    public void list() {
        try {
            int page = Integer.parseInt(getPara("page"));
            int limit = Integer.parseInt(getPara("limit"));
            String fpmc = getPara("fpmc");
            int count = fpglDao.searchCount(fpmc);    // 查找数据条数
            int start = limit * page - limit + 1;
            int end = limit * page;
            List<Record> list = fpglDao.list(start, end, fpmc);
            Map map = ReKit.toMap(count, list);
            renderJson(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sfxmList() {
        renderJson(fpglDao.sfxm());
    }

    public void add() {
        try {
            String SFXM = getPara("SFXM");
            String FPMC = getPara("FPMC");
            String FPLX = getPara("FPLX");
            String XMMC = URLDecoder.decode(getPara("XMMC"), "UTF-8");
            int result = fpglDao.add(FPMC, FPLX, SFXM, XMMC);
            if (result > 0) {
                renderJson(ReturnKit.retOk());
            } else {
                renderJson(ReturnKit.retFail("添加失败，缴费项目代码不可重复!"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void edit() {
        try {
            String id = getPara("ID");
            String SFXM = getPara("SFXM");
            String FPMC = getPara("FPMC");
            String FPLX = getPara("FPLX");
            String XMMC = URLDecoder.decode(getPara("XMMC"), "UTF-8");
            int result = fpglDao.edit(id, FPMC, FPLX, SFXM, XMMC);
            if (result > 0) {
                renderJson(ReturnKit.retOk());
            } else {
                renderJson(ReturnKit.retFail("添加失败，请稍后再试!"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void del() {
        try {
            String[] array = getParaValues("id[]");
            if (array.length > 0) {
                for (int i = 0; i < array.length; i++) {
                    Db.update("DELETE FROM WPTMA_FPXX WHERE ID=?", array[i]);
                }
                renderJson(ReturnKit.retOk());
            } else {
                renderJson(ReturnKit.retFail("请选择需要删除的数据!"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void updateStatus() {
        try {
            String my_id = getPara("my_id");
            String my_type = getPara("my_type");
            String my_status = getPara("my_status");
            int result = fpglDao.updateStatus(my_id, my_type, my_status);
            if (result > 0) {
                renderJson(ReturnKit.retOk());
            } else {
                renderJson(ReturnKit.retFail("修改失败，请稍后再试!"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void query() {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            String id = getPara("id");
            Record re = Db.findFirst("SELECT ID,FPMC,FPLX,XGSJ,QYZT,XMID,XMMC,CJSJ FROM WPTMA_FPXX WHERE ID=?", id);
            List<Record> sfxm = fpglDao.sfxm();
            map.put("re", re);
            map.put("sfxm", sfxm);
            if (re != null) {
                renderJson(ReturnKit.retOk(map));
            } else {
                renderJson(ReturnKit.retFail());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
