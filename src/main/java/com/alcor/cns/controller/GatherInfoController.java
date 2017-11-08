package com.alcor.cns.controller;

import com.alcor.cns.entity.GatherInfoEntity;
import com.alcor.cns.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pers.roamer.boracay.aspect.businesslogger.BusinessMethod;
import pers.roamer.boracay.aspect.httprequest.SessionCheckKeyword;
import pers.roamer.boracay.helper.HttpResponseHelper;
import pers.roamer.boracay.helper.JsonUtilsHelper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

    @Autowired
    ContractService contractService;

    @Autowired
    CustomerService customerService;

    @Autowired
    SystemConfigureService systemConfigureService;


    /**
     * 跳转到新建一个收款信息的界面
     *
     * @return
     *
     * @throws ControllerException
     */
    @GetMapping("/contracts/{contractId}/gatherInfos")
    public ModelAndView create(@PathVariable String contractId) throws ControllerException {
        log.debug("显示增加的收款信息的界面,所属的合同ID 是：{}", contractId);
        ModelAndView modelAndView = new ModelAndView("/gatherInfo/new");
        GatherInfoEntity gatherInfoEntity = new GatherInfoEntity();
        gatherInfoEntity.setId(UUID.randomUUID().toString());
        try {
            gatherInfoEntity.setNoticeTo(systemConfigureService.findByName("cns_mail_to").getValue());
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage());
        }
        gatherInfoEntity.setAmount(0.00);
        gatherInfoEntity.setContractId(contractId);
        modelAndView.addObject("gatherInfo", gatherInfoEntity);
        log.debug("增加结束");
        return modelAndView;
    }

    /**
     * 跳转到显示一个编辑界面
     *
     * @param id
     *
     * @return
     *
     * @throws ControllerException
     */
    @GetMapping("/gatherInfos/{id}")
    public ModelAndView edit(@PathVariable String id) throws ControllerException {
        log.debug("编辑 id 是:{}的付款信息", id);
        ModelAndView modelAndView = new ModelAndView("/gatherInfo/edit");
        GatherInfoEntity gatherInfoEntity;
        try {
            gatherInfoEntity = gatherInfoService.findById(id);
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage());
        }
        modelAndView.addObject("gatherInfo", gatherInfoEntity);
        log.debug("编辑结束");
        return modelAndView;
    }

    /**
     * 生成一个收款信息的提示内容
     *
     * @param id
     *
     * @return
     *
     * @throws ControllerException
     */
    @GetMapping("gatherInfos/gen_notic_ontent/{id}")
    public String getNoticContent(@PathVariable String id) throws ControllerException {
        try {
            Map map = new HashMap<String, String>();
            map.put("content", gatherInfoService.genNoticContent(id));
            return JsonUtilsHelper.objectToJsonString(map);
        } catch (ServiceException | JsonProcessingException e) {
            throw new ControllerException(e.getMessage());
        }
    }


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
        log.debug("开始查询合同对应的收款信息,合同编号是:{}", contractId);
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
            throw new ControllerException(e.getMessage());
        }
    }


    @BusinessMethod(value = "保存/更新一条收款信息")
    @PostMapping(value = "/gatherInfos/{id}")
    @ResponseBody
    public String update(@RequestBody GatherInfoEntity gatherInfoEntity) throws ControllerException {
        try {
            log.debug("更新一条收款信息,内容是{}", JsonUtilsHelper.objectToJsonString(gatherInfoEntity));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            gatherInfoService.update(gatherInfoEntity);
        } catch (ServiceException e) {
            log.error(e.getMessage());
            new ControllerException(e.getMessage());
        }
        log.debug("更新一条收款信息完成");
        return HttpResponseHelper.successInfoInbox("更新成功");
    }

    @BusinessMethod(value = "删除一条收款信息")
    @DeleteMapping("/gatherInfos/{id}")
    @ResponseBody
    public String delete(@PathVariable String id) throws ControllerException {
        log.debug("删除一条收款记录:" + id);
        try {
            gatherInfoService.delete(id);
        } catch (Exception e) {
            throw new ControllerException(e.getMessage());
        }
        log.debug("删除成功");
        return HttpResponseHelper.successInfoInbox("删除成功");
    }

    /**
     * 测试发送收款提示信息
     *
     * @param id
     *
     * @return
     *
     * @throws ControllerException
     */
    @PostMapping("/gatherInfos/notice/{id}")
    @ResponseBody
    public String noticeTest(@PathVariable String id) throws ControllerException {
        log.debug("测试发送一条收款记录:" + id);
        try {
            GatherInfoEntity gatherInfoEntity = gatherInfoService.findById(id);
            gatherInfoService.sentNotice(gatherInfoEntity);
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage());
        }
        return HttpResponseHelper.successInfoInbox("测试完成！");
    }

    /**
     * 获取当前一周内的收款计划笔数
     *
     * @return
     *
     * @throws ControllerException
     */
    @PostMapping("/gatherInfos/count/thisWeek")
    @ResponseBody
    public String countThisWeek() throws ControllerException {
        Date today = new Date();
        DateTime start = new DateTime(today).withDayOfWeek(DateTimeConstants.MONDAY);
        DateTime end = new DateTime(today).withDayOfWeek(DateTimeConstants.SUNDAY);
        log.debug("开始统计从{}到{}的待收款笔数！", start, end);
        String m_rtn = null;
        try {
            int count = this.countBetweenDate(start, end);
            Map<String, Object> map = new HashMap<>();
            map.put("data", count);
            m_rtn = JsonUtilsHelper.objectToJsonString(map);
        } catch (JsonProcessingException e) {
            new ControllerException(e.getMessage());
        }
        return m_rtn;
    }

    /**
     * 获取当前月内的收款计划笔数
     *
     * @return
     *
     * @throws ControllerException
     */
    @PostMapping("/gatherInfos/count/thisMonth")
    @ResponseBody
    public String countThisMonth() throws ControllerException {
        Date today = new Date();
        DateTime start = new DateTime(today).dayOfMonth().withMinimumValue();
        DateTime end = new DateTime(today).dayOfMonth().withMaximumValue();
        log.debug("开始统计从{}到{}的待收款笔数！", start, end);
        String m_rtn = null;
        try {
            int count = this.countBetweenDate(start, end);
            Map<String, Object> map = new HashMap<>();
            map.put("data", count);
            m_rtn = JsonUtilsHelper.objectToJsonString(map);
        } catch (JsonProcessingException e) {
            new ControllerException(e.getMessage());
        }
        return m_rtn;
    }

    /**
     * 获取当前一周内的收款计划总金额
     *
     * @return
     *
     * @throws ControllerException
     */
    @PostMapping("/gatherInfos/sum/thisWeek")
    @ResponseBody
    public String sumThisWeek() throws ControllerException {
        Date today = new Date();
        DateTime start = new DateTime(today).withDayOfWeek(DateTimeConstants.MONDAY);
        DateTime end = new DateTime(today).withDayOfWeek(DateTimeConstants.SUNDAY);
        log.debug("开始统计从{}到{}的待收款总金额！", start, end);
        String m_rtn = null;
        try {
            float sum = this.sumAmountBetweenDate(start, end);
            Map<String, Object> map = new HashMap<>();
            map.put("data", sum);
            m_rtn = JsonUtilsHelper.objectToJsonString(map);
        } catch (JsonProcessingException | ControllerException e) {
            log.trace(e);
            new ControllerException(e.getMessage());
        }
        return m_rtn;
    }


    /**
     * 获取当前一个月内的收款计划总金额
     *
     * @return
     *
     * @throws ControllerException
     */
    @PostMapping("/gatherInfos/sum/thisMonth")
    @ResponseBody
    public String sumThisMonth() throws ControllerException {
        Date today = new Date();
        DateTime start = new DateTime(today).dayOfMonth().withMinimumValue();
        DateTime end = new DateTime(today).dayOfMonth().withMaximumValue();
        log.debug("开始统计从{}到{}的待收款笔数！", start, end);
        String m_rtn = null;
        try {
            float sum = this.sumAmountBetweenDate(start, end);
            Map<String, Object> map = new HashMap<>();
            map.put("data", sum);
            m_rtn = JsonUtilsHelper.objectToJsonString(map);
        } catch (JsonProcessingException  e) {
            new ControllerException(e.getMessage());
        }
        return m_rtn;
    }

    /**
     * 统计一段时间内的待收款笔数
     * @param start
     * @param end
     * @return
     * @throws ControllerException
     */
    private int countBetweenDate(DateTime start, DateTime end) throws ControllerException {
        int count = 0;
        try {
            count = gatherInfoService.countBetweenDate(start.toDate(), end.toDate());
        } catch (ServiceException e) {
            new ControllerException(e.getMessage());
        }
        return count;
    }


    /**
     * 统计一段时间内的待收款金额
     * @param start
     * @param end
     * @return
     * @throws ControllerException
     */
    private float sumAmountBetweenDate(DateTime start, DateTime end) throws ControllerException {
        float sum = 0;
        try {
            sum = gatherInfoService.sumAmountByGatherDateBetween(start.toDate(), end.toDate());
        } catch (ServiceException e) {
            new ControllerException(e.getMessage());
        }
        return sum;
    }
}
