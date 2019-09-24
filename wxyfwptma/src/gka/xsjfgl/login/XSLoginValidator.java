package gka.xsjfgl.login;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

/**
 * Created with IntelliJ IDEA.
 * User: 杨亮
 * Date: 2017/8/11
 * Time: 4:46
 * DESCRIPTION:
 * To change this template use File | Settings | File Templates.;
 */
public class XSLoginValidator extends Validator {
    @Override
    protected void validate(Controller c) {
        setShortCircuit(true);
        validateCaptcha("vercode", "vercode", "验证码不正确");
    }

    @Override
    protected void handleError(Controller c) {
        c.renderJson();
    }
}
