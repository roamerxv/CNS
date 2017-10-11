package com.alcor.cns.controller;

import com.alcor.cns.service.EventService;
import com.alcor.cns.service.ServiceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pers.roamer.boracay.aspect.businesslogger.BusinessMethod;
import pers.roamer.boracay.aspect.httprequest.SessionCheckKeyword;
import pers.roamer.boracay.helper.JsonUtilsHelper;

import java.util.HashMap;

/**
 * 提醒事件的 controller 类
 *
 * @author roamer - 徐泽宇
 * @create 2017-10-2017/10/11  下午12:13
 */
@Log4j2
@Controller("com.alcor.cns.controller.EventController")
@SessionCheckKeyword(checkIt = true)
public class EventController extends BaseController {

    @Autowired
    EventService eventService;

    /**
     * 列出所有满足记录的 提醒事件数据，以 json 字符串的形式返回给 dataTables 使用
     *
     * @return
     *
     * @throws ControllerException
     */
    @BusinessMethod(value = "查看提醒事件列表", isLogged = true)
    @GetMapping(value = "/event/getDataWithoutPaged")
    @ResponseBody
    public String getDataWithoutPaged() throws ControllerException {
        log.debug("开始查询");
        try {
            HashMap hashMap = new HashMap();
            String m_rtn = "";
            try {
                hashMap.put("data", eventService.findAll());
                m_rtn = JsonUtilsHelper.objectToJsonString(hashMap);
            } catch (JsonProcessingException e) {
                log.error(e.getStackTrace());
            }
            log.debug("查询完成,返回数据是: {}", m_rtn);
            return m_rtn;
        } catch (ServiceException e) {
            log.error(e.getMessage());
            throw new ControllerException(e.getMessage());
        }
    }

    @GetMapping(value = "/event/{id}")
    public ModelAndView edit() throws  ControllerException{
        log.debug("显示一个需要编辑的记录");
        ModelAndView modelAndView = new ModelAndView("/event/edit");
        log.debug("显示结束");
        return modelAndView;
    }
}
