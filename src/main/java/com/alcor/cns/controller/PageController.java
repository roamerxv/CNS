package com.alcor.cns.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pers.roamer.boracay.aspect.httprequest.SessionCheckKeyword;

/**
 * 项目所有的页面跳转控制类
 *
 * @author roamer - 徐泽宇
 * @create 2017-09-2017/9/29  下午1:29
 */

@Controller("com.alcor.ril.controller.PageController")
@Log4j2
@SessionCheckKeyword
public class PageController extends BaseController {

    @RequestMapping("/")
    @SessionCheckKeyword(checkIt = false)
    public ModelAndView index() {
        ModelAndView modelAndView;
        try {
            if (super.getUserID() != null) {
                modelAndView = new ModelAndView("/main");
            } else {
                modelAndView = new ModelAndView("/admin/login");
            }
        } catch (ControllerException e) {
            modelAndView = new ModelAndView("/admin/login");
        }

        return modelAndView;
    }

    @RequestMapping("/system_logs_index")
    public ModelAndView systemLogIndex() {
        ModelAndView modelAndView = new ModelAndView("/systemLogs/systemLoggerIndex");
        return modelAndView;
    }

    @RequestMapping("/testPage")
    public ModelAndView showTestPage() {
        ModelAndView modelAndView = new ModelAndView("/test/test-page");
        return modelAndView;
    }

    @GetMapping("/customers")
    public ModelAndView showCustomersPage() {
        ModelAndView modelAndView = new ModelAndView("/customer/index");
        return modelAndView;
    }
}
