package gka.common.kit.controller;


import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import gka.interceptor.LoginInterceptor;

@Before({LoginInterceptor.class})
public class LController extends Controller {
}