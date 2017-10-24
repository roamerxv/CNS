package com.alcor.cns.controller;

import com.alcor.cns.entity.CustomerEntity;
import com.alcor.cns.service.CustomerService;
import com.alcor.cns.service.ServiceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
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
@RestController("com.alcor.cns.controller.CustomerController")
@SessionCheckKeyword(checkIt = true)
public class CustomerController extends BaseController {

    @Autowired
    CustomerService customerService;


    /**
     * 跳转到显示所有客户信息的界面
     *
     * @return
     */
    @GetMapping("/customers/index")
    public ModelAndView showCustomersPage() {
        ModelAndView modelAndView = new ModelAndView("/customer/index");
        return modelAndView;
    }


    /**
     * 跳转到新建一个客户信息的界面
     *
     * @return
     *
     * @throws ControllerException
     */
    @GetMapping("/customers")
    public ModelAndView create() throws ControllerException {
        log.debug("显示增加的客户信息的界面");
        ModelAndView modelAndView = new ModelAndView("/customer/new");
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(UUID.randomUUID().toString());
        modelAndView.addObject("customer", customerEntity);
        log.debug("增加结束");
        return modelAndView;
    }

    /**
     * 列出客户数据，以 json 字符串的形式返回给 dataTables 使用
     *
     * @return
     *
     * @throws ControllerException
     */
    @GetMapping(value = "/customers/getDataWithoutPaged")
    @ResponseBody
    public String getDataWithoutPaged() throws ControllerException {
        log.debug("开始查询全部客户信息");
        try {
            HashMap hashMap = new HashMap();
            String m_rtn = "";
            try {
                hashMap.put("data", customerService.findAll());
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


    @BusinessMethod(value = "保存/更新一条客户信息")
    @PostMapping(value = "/customers/{id}")
    @ResponseBody
    public String update(@RequestBody CustomerEntity customerEntity) throws ControllerException {
        log.debug("更新一条客户信息");
        try {
            customerService.update(customerEntity);
        } catch (ServiceException e) {
            new ControllerException(e.getMessage());
        }
        log.debug("更新一条客户信息完成");
        return HttpResponseHelper.successInfoInbox("更新成功");
    }


    @GetMapping(value = "/customers/{id}")
    public ModelAndView edit(@PathVariable String id) throws ControllerException {
        log.debug("显示一个需要编辑的客户信息记录");
        ModelAndView modelAndView = new ModelAndView("/customer/edit");
        try {
            CustomerEntity customerEntity = customerService.findById(id);
            if (customerEntity == null) {
                String error_message = "exception.system.customer.not_found";
                log.error(error_message);
                throw new ControllerException(error_message);
            }
            modelAndView.addObject("customer", customerEntity);
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage());
        }
        log.debug("显示结束");
        return modelAndView;
    }

}
