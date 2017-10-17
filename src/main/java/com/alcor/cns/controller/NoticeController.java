package com.alcor.cns.controller;

import com.alcor.cns.entity.EventEntity;
import com.alcor.cns.service.EventService;
import com.alcor.cns.service.MailService;
import com.alcor.cns.service.ServiceException;
import com.alcor.cns.service.SystemConfigureService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.roamer.boracay.aspect.httprequest.SessionCheckKeyword;
import pers.roamer.boracay.helper.HttpResponseHelper;

import java.util.concurrent.Future;

/**
 * @author roamer - 徐泽宇
 * @create 2017-10-2017/10/16  下午6:41
 */
@Log4j2
@RestController("com.alcor.cns.controller.NoticeController")
@SessionCheckKeyword
public class NoticeController {
    @Autowired
    private MailService mailService;

    @Autowired
    private EventService eventService;

    @Autowired
    private SystemConfigureService systemConfigureService;

    @PostMapping(value = "/notice/test/{id}")
    public String sendSimpleMail(@PathVariable String id) throws  ControllerException{
        log.debug("noticing");
        try {
            EventEntity eventEntity =  eventService.findById(id) ;
            String mailListString = eventEntity.getNoticeMail();

            if (!StringUtils.isEmpty(mailListString))
            {
                String [] to = eventEntity.getNoticeMail().split(",");
                String subject = String.format(systemConfigureService.findByName("cns_subject").getValue(), eventEntity.getName());
                Future<String> mailSendFuture = mailService.sendSimpleMail(to, subject, eventEntity.getNoticeContent());
                log.debug("noticing sended ? = "+mailSendFuture.isDone());
            }

        } catch (ServiceException e) {
            throw  new ControllerException(e.getMessage());
        }
        return HttpResponseHelper.successInfoInbox("提醒测试成功");
    }
}
