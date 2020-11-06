package top.zhouy.basiczuul.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import top.zhouy.commonresponse.bean.model.R;

/**
 * @description: 异常处理
 * @author: zhouy
 * @create: 2020-11-05 11:50:00
 */
@RestController
public class GlobalErrorController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @ResponseBody
    @RequestMapping("/error")
    public R error(Exception e) {
        return R.exception(e);
    }
}
