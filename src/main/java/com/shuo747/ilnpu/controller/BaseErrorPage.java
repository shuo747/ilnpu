package com.shuo747.ilnpu.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/error")
public class BaseErrorPage implements ErrorController {

    //Logger logger = LoggerFactory.getLogger(BaseErrorPage.class);

    @Override
    public String getErrorPath() {
        //logger.info("进入自定义错误页面");
        return "error";
    }

    @RequestMapping
    public String error() {
        return getErrorPath();
    }

}
