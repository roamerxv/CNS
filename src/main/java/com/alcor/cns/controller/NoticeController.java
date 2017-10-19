package com.alcor.cns.controller;

import com.alcor.cns.service.NoticeService;
import com.alcor.cns.service.ServiceException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.roamer.boracay.aspect.httprequest.SessionCheckKeyword;
import pers.roamer.boracay.helper.HttpResponseHelper;

/**
 * @author roamer - 徐泽宇
 * @create 2017-10-2017/10/16  下午6:41
 */
@Log4j2
@RestController("com.alcor.cns.controller.NoticeController")
@SessionCheckKeyword
public class NoticeController {

    @Autowired
    private NoticeService noticeService;



    @PostMapping(value = "/notice/test/{id}")
    public String sendSimpleMail(@PathVariable String id) throws  ControllerException{
        log.debug("noticing");
        try {
            noticeService.noticeEvent(id);
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage());
        }
        return HttpResponseHelper.successInfoInbox("提醒测试成功");
    }
}
