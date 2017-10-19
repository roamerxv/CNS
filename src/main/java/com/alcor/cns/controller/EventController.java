package com.alcor.cns.controller;

import com.alcor.cns.entity.EventEntity;
import com.alcor.cns.service.EventService;
import com.alcor.cns.service.ServiceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pers.roamer.boracay.aspect.businesslogger.BusinessMethod;
import pers.roamer.boracay.aspect.httprequest.SessionCheckKeyword;
import pers.roamer.boracay.helper.HttpResponseHelper;
import pers.roamer.boracay.helper.JsonUtilsHelper;

import java.util.HashMap;
import java.util.UUID;

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
//    @BusinessMethod(value = "查看提醒事件列表", isLogged = true)
    @GetMapping(value = "/events/getDataWithoutPaged")
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

    /**
     * 显示一个事件详情
     *
     * @param id
     *
     * @return
     *
     * @throws ControllerException
     */
    @GetMapping(value = "/events/{id}")
    public ModelAndView edit(@PathVariable String id) throws ControllerException {
        log.debug("显示一个需要编辑的记录");
        ModelAndView modelAndView = new ModelAndView("/event/edit");
        try {
            EventEntity eventEntity = eventService.findById(id);
            if (eventEntity == null) {
                String error_message = "exception.system.event.not_found";
                log.error(error_message);
                throw new ControllerException(error_message);
            }
            modelAndView.addObject("event", eventEntity);
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage());
        }
        log.debug("显示结束");
        return modelAndView;
    }


    @BusinessMethod(value = "更新一个事件信息")
    @PostMapping("/events/{id}")
    @ResponseBody
    public String update(@RequestBody EventEntity eventEntity) throws ControllerException {
        try {
            log.debug("更新一条记录:" + JsonUtilsHelper.objectToJsonString(eventEntity));
            try {
                EventEntity eventEntityUpdated = eventService.update(eventEntity);

            } catch (ServiceException e) {
                throw new ControllerException(e.getBindingResult());
            }
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        log.debug("更新成功");
        return HttpResponseHelper.successInfoInbox("更新成功");
    }

    @BusinessMethod(value = "新建一个事件信息")
    @GetMapping("/events")
    public ModelAndView create() throws ControllerException {
        log.debug("增加的记录");
        ModelAndView modelAndView = new ModelAndView("/event/new");
        EventEntity eventEntity = new EventEntity();
        eventEntity.setId(UUID.randomUUID().toString());
        modelAndView.addObject("event", eventEntity);
        log.debug("增加结束");
        return modelAndView;
    }

    @BusinessMethod(value = "删除一个事件信息")
    @DeleteMapping("/events/{id}")
    @ResponseBody
    public String delete(@PathVariable String id) throws ControllerException {
        log.debug("删除一条记录:" + id);
        try {
            eventService.delete(id);
        } catch (ServiceException e) {
            throw new ControllerException(e.getBindingResult());
        }
        log.debug("删除成功");
        return HttpResponseHelper.successInfoInbox("更新成功");
    }
}
