package gka.common.kit.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import gka.interceptor.LoginInterceptor;

/**
 * Created with IntelliJ IDEA.
 * User: 杨亮
 * Date: 2017/7/25
 * Time: 3:54
 * DESCRIPTION: 服务级控制器父类
 * To change this template use File | Settings | File Templates.;
 */
public abstract class ServiceController extends Controller {
    public abstract void index();

    public abstract void add();

    public abstract void save();

    public abstract void edit();

    public abstract void update();

    public abstract void delete();

    public abstract void list();
}
