package com.alcor.cns.controller;

import com.alcor.cns.service.CustomerService;
import com.alcor.cns.service.ServiceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
@Controller("com.alcor.cns.controller.CustomerController")
@SessionCheckKeyword(checkIt = true)
public class CustomerController extends BaseController {

    @Autowired
    CustomerService customerService;

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
}
