package com.alcor.cns.controller;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

/**
 * 提醒事件的 controller 类
 *
 * @author roamer - 徐泽宇
 * @create 2017-10-2017/10/11  下午12:13
 */
@Log4j2
@RestController("com.alcor.cns.controller.TestController")

public class TestController extends BaseController {


    @GetMapping("/tester/user")
    public ModelAndView getUser() throws ControllerException {
        ModelAndView modelAndView = new ModelAndView("/test/user");

        User user = new User();
        user.setName("徐泽宇");
        user.setSex("男");
        user.setAge(44);

        modelAndView.addObject("user", user);
        return modelAndView;
    }


    @GetMapping("/tester/users")
    public ModelAndView getUsers() throws ControllerException {
        ModelAndView modelAndView = new ModelAndView("/test/users");
        ArrayList<User> userArrayList = new ArrayList();
        for (int i = 7 ; i<=20 ; i++) {
            User user = new User();
            user.setName("徐泽宇"+i);
            user.setSex("男");
            user.setAge(44+i);
            userArrayList.add(user);
        }
        modelAndView.addObject("users", userArrayList);
        return modelAndView;
    }

    @GetMapping("/test/500Error")
    @ResponseBody
    public String testError() throws ControllerException{
        throw new ControllerException("一个测试用的错误！");
    }


   @Data
    public class User {
        public String name ;
        public String sex ;
        public int age ;
    }

}

