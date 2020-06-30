package gka.demo;

import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import gka.dzfp.SendDzfp;
import gka.dzfp.ThreadPoolUtil;

/**
 * Created by Administrator on 2020/5/31 0031.
 */
@ControllerBind(controllerKey = "/test1")
public class Test extends Controller {
    public void test() {
        ThreadPoolUtil.execute(new SendDzfp("1590909008009nrtargb"));
    }
}
