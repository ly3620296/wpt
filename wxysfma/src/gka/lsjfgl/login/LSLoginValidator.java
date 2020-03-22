package gka.lsjfgl.login;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class LSLoginValidator extends Validator {
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
