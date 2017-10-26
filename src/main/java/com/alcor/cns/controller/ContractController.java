package com.alcor.cns.controller;

import com.alcor.cns.entity.ContractEntity;
import com.alcor.cns.service.ContractService;
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
@RestController("com.alcor.cns.controller.ContractController")
@SessionCheckKeyword(checkIt = true)
public class ContractController extends BaseController {

    @Autowired
    ContractService contractService;


    /**
     * 跳转到新建一个合同信息的界面
     *
     * @return
     *
     * @throws ControllerException
     */
    @GetMapping("/customers/{customerId}/contracts")
    public ModelAndView create(@PathVariable String customerId) throws ControllerException {
        log.debug("显示增加的合同信息的界面,所属的客户ID 是：{}",customerId);
        ModelAndView modelAndView = new ModelAndView("/contract/new");
        ContractEntity contractEntity = new ContractEntity();
        contractEntity.setId(UUID.randomUUID().toString());
        contractEntity.setCustomerId(customerId);
        modelAndView.addObject("contract", contractEntity);
        log.debug("增加结束");
        return modelAndView;
    }

    @GetMapping("/contracts/{id}")
    public ModelAndView edit(@PathVariable String id)throws ControllerException{
        log.debug("编辑 id 是:{}的合同信息",id);
        ModelAndView modelAndView = new ModelAndView("/contract/edit");
        ContractEntity contractEntity ;
        try {
            contractEntity = contractService.findById(id);
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage());
        }
        modelAndView.addObject("contract", contractEntity);
        log.debug("增加结束");
        return modelAndView;
    }

    /**
     * 列出合同数据，以 json 字符串的形式返回给 dataTables 使用
     *
     * @return
     *
     * @throws ControllerException
     */
    @GetMapping(value = "/contracts/{customerId}/getDataWithoutPaged")
    @ResponseBody
    public String getDataWithoutPaged(@PathVariable String customerId) throws ControllerException {
        log.debug("开始查询全部合同信息,客户编号是:{}",customerId);
        try {
            HashMap hashMap = new HashMap();
            String m_rtn = "";
            try {
                hashMap.put("data", contractService.findAllByCustomerId(customerId));
                m_rtn = JsonUtilsHelper.objectToJsonString(hashMap);
            } catch (JsonProcessingException e) {
                log.trace(e);
                throw new ControllerException(e.getMessage());
            }
            log.debug("列出合同数据完成,返回数据是: {}", m_rtn);
            return m_rtn;
        } catch (ServiceException e) {
            log.error(e.getMessage());
            throw new ControllerException(e.getMessage());
        }
    }

    @BusinessMethod(value = "保存/更新一份合同信息")
    @PostMapping(value = "/contracts/{id}")
    @ResponseBody
    public String update(@RequestBody ContractEntity contractEntity) throws ControllerException {
        log.debug("更新一份合同信息");
        try {
            contractService.update(contractEntity);
        } catch (ServiceException e) {
            log.error(e.getMessage());
            new ControllerException(e.getMessage());
        }
        log.debug("更新一份合同信息完成");
        return HttpResponseHelper.successInfoInbox("更新成功");
    }

    @BusinessMethod(value = "删除一份合同信息")
    @DeleteMapping("/contracts/{id}")
    @ResponseBody
    public String delete(@PathVariable String id) throws ControllerException {
        log.debug("删除一份合同记录:" + id);
        try {
            contractService.delete(id);
        } catch (Exception e) {
            throw new ControllerException(e.getMessage());
        }
        log.debug("删除成功");
        return HttpResponseHelper.successInfoInbox("删除成功");
    }
}
