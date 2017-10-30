package com.alcor.cns.controller;

import com.alcor.cns.entity.CustomerTypeEntity;
import com.alcor.cns.service.CustomerTypeService;
import com.alcor.cns.service.ServiceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pers.roamer.boracay.aspect.httprequest.SessionCheckKeyword;
import pers.roamer.boracay.helper.HttpResponseHelper;
import pers.roamer.boracay.helper.JsonUtilsHelper;

/**
 * 提醒事件的 controller 类
 *
 * @author roamer - 徐泽宇
 * @create 2017-10-2017/10/11  下午12:13
 */
@Log4j2
@RestController("com.alcor.cns.controller.CustomerTypeController")
@SessionCheckKeyword(checkIt = true)
public class CustomerTypeController extends BaseController {


    @Autowired
    CustomerTypeService customerTypeService;

    /**
     * 跳转到客户分类信息维护的界面
     *
     * @return
     *
     * @throws ControllerException
     */
    @GetMapping("/customer_type/show")
    public ModelAndView create() throws ControllerException {
        log.debug("显示客户分类维护的界面");
        ModelAndView modelAndView = new ModelAndView("/customer_type/show");
        log.debug("显示客户分类维护的界面 end");
        return modelAndView;
    }

    @PostMapping("/customer_type/create")
    @ResponseBody
    public String create(@RequestBody CustomerTypeEntity customerTypeEntity) throws ControllerException{
        log.debug("开始更新或者增加一个 customerType : begin");
        try {
            log.debug(JsonUtilsHelper.objectToJsonString(customerTypeEntity));
            customerTypeService.create(customerTypeEntity);
        } catch (ServiceException | JsonProcessingException e) {
            log.trace(e);
            throw new ControllerException(e.getMessage());
        }
        log.debug("开始更新或者增加一个 customerType : end");
        return HttpResponseHelper.successInfoInbox("增加成功");
    }
}
