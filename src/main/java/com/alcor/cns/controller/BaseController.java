/*
 * Boracay - Web 项目实用组件框架
 *
 * @author 徐泽宇 roamerxv@gmail.com
 * @version 1.0.0
 * Copyright (c) 2017. 徐泽宇
 *
 */

package com.alcor.cns.controller;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pers.roamer.boracay.configer.ConfigHelper;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

/**
 * 基础控制类
 *
 * @author roamer - 徐泽宇
 * @create 2017-06-2017/6/2  下午4:24
 */
@Log4j2
@Controller("com.alcor.cns.controller.BaseController")
public class BaseController {

    @Autowired
    ServletContext servletContext;

    @Autowired
    protected HttpSession httpSession;

    public String getUserID() throws ControllerException {
        String user_id = (String) httpSession.getAttribute(ConfigHelper.getConfig().getString("System.SessionUserKeyword"));
        log.debug("当前 session 中的user_id 是 {}", user_id);
        if (StringUtils.isEmpty(user_id)) {
            throw new ControllerException("exception.system.need_login");
        }
        return user_id;
    }
}
