package com.alcor.cns.controller;

import com.alcor.cns.service.GatherInfoService;
import com.alcor.cns.service.ServiceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
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
@RestController("com.alcor.cns.controller.GatherInfoController")
@SessionCheckKeyword(checkIt = true)
public class GatherInfoController extends BaseController {

    @Autowired
    GatherInfoService gatherInfoService;




    /**
     * 列出收款数据，以 json 字符串的形式返回给 dataTables 使用
     *
     * @return
     *
     * @throws ControllerException
     */
    @GetMapping(value = "/gatherInfos/{contractId}/getDataWithoutPaged")
    @ResponseBody
    public String getDataWithoutPaged(@PathVariable String contractId) throws ControllerException {
        log.debug("开始查询合同对应的收款信息,合同编号是:{}",contractId);
        try {
            HashMap hashMap = new HashMap();
            String m_rtn = "";
            try {
                hashMap.put("data", gatherInfoService.findAllByContractId(contractId));
                m_rtn = JsonUtilsHelper.objectToJsonString(hashMap);
            } catch (JsonProcessingException e) {
                log.trace(e);
                throw new ControllerException(e.getMessage());
            }
            log.debug("列出合同对应的收款数据完成,返回数据是: {}", m_rtn);
            return m_rtn;
        } catch (ServiceException e) {
            log.error(e.getMessage());
            throw new ControllerException(e.getMessage());
        }
    }


}
