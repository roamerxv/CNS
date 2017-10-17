package com.alcor.cns.controller;

import com.alcor.cns.entity.SystemConfigureEntity;
import com.alcor.cns.service.ServiceException;
import com.alcor.cns.service.SystemConfigureService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pers.roamer.boracay.aspect.businesslogger.BusinessMethod;
import pers.roamer.boracay.aspect.httprequest.SessionCheckKeyword;
import pers.roamer.boracay.helper.HttpResponseHelper;

import java.util.List;

/**
 * 系统参数Controller 类
 *
 * @author roamer - 徐泽宇
 * @create 2017-10-2017/10/16  上午10:56
 */
@Log4j2
@RestController("com.alcor.cns.controller.SystemConfigureController")
@SessionCheckKeyword(checkIt = true)
public class SystemConfigureController extends BaseController {

    @Autowired
    SystemConfigureService systemConfigureService;


    @GetMapping(value = "/system_config")
    public ModelAndView showSystemConfigure() throws ControllerException{
        log.debug("开始显示系统参数页面");
        ModelAndView modelAndView = new ModelAndView("/systemConfigure/edit");
        try {
            List<SystemConfigureEntity>  systemConfigureEntityList =systemConfigureService.findAll();
            log.debug("总共发现{}条系统参数记录", systemConfigureEntityList.size());
            modelAndView.addObject("systemConfigures", systemConfigureEntityList);
        } catch (ServiceException e) {
            throw new ControllerException(e.getBindingResult());
        }
        log.debug("显示完成");

        return modelAndView;
    }

    @PostMapping(value = "/systemConfigs/{name}")
    @BusinessMethod(value = "新建一个配置项")
    public String update(@RequestBody SystemConfigureEntity systemConfigureEntity) throws ControllerException{
        log.debug("更新一个配置项目");
        try {
            SystemConfigureEntity systemConfigureEntity2Update = systemConfigureService.findByName(systemConfigureEntity.getName());
            systemConfigureEntity2Update.setValue(systemConfigureEntity.getValue());
            systemConfigureService.update(systemConfigureEntity2Update);
        } catch (ServiceException e) {
            throw new ControllerException(e.getBindingResult());
        }
        log.debug("更新完成");
        return HttpResponseHelper.successInfoInbox("更新成功");
    }
}
