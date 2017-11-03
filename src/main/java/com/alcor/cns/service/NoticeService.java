package com.alcor.cns.service;

import com.alcor.cns.entity.EventEntity;
import com.alcor.cns.entity.GatherInfoEntity;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

/**
 * 提醒的 service 类
 *
 * @author roamer - 徐泽宇
 * @create 2017-10-2017/10/18  下午5:29
 */
@Service(value = "com.alcor.cns.service.NoticeService")
@Log4j2
public class NoticeService {

    @Autowired
    private SystemConfigureService systemConfigureService;


    @Autowired
    private EventService eventService;

    @Autowired
    private MailService mailService;

    @Autowired
    private GatherInfoService gatherInfoService;

    /**
     * 对配置的所有提醒时间进行扫描。符合条件的进行提醒 mail 发送
     */
    @Async
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public void notice() throws ServiceException {
        // 发送提示事件中需要提醒的内容
        this.noticeEvent();
        // 发送收款记录中需要提醒的内容
        this.noticeEvent();

    }

    /**
     * 发送所有需要提醒的收款计划
     * @throws ServiceException
     */
    @Async
    public void noticeGatherInfo() throws ServiceException{
        List<GatherInfoEntity> gatherInfoEntityList = gatherInfoService.needToNotice(new Date());
        log.debug("总共发现{}条需要发送提醒的收款计划", gatherInfoEntityList.size());
        gatherInfoEntityList.forEach(item ->{
            try {
                gatherInfoService.sentNotice(item);
            } catch (ServiceException e) {
                log.error(e.getMessage());
            }
        });
    }

    /**
     * 发送提醒事件表中的需要提醒的事件
     */
    private void noticeEvent() throws  ServiceException {
        //1 获取所有需要提醒的事件
        List<EventEntity> eventEntities = eventService.findAllByNotice(true);
        log.debug("总共发现{}条记录需要提示", eventEntities.size());
        eventEntities.forEach(eventEntity -> {
            log.debug("正在处理【{}】的事件数据", eventEntity.getName());
            Calendar now = Calendar.getInstance();
            Calendar actDate = Calendar.getInstance();
            actDate.setTime(eventEntity.getActDate());
            switch (eventEntity.getRepeatType()) {
                case 0:
                    log.debug("\t类型是：只在发生日当天提醒");
                    if (now.get(Calendar.DATE) == actDate.get(Calendar.DATE)) {
                        log.debug("\t\t匹配到当前日期就是需要提醒的日期,需要进行提醒");
                        try {
                            this.noticeEvent(eventEntity);
                        } catch (ServiceException e) {
                            log.error(e.getMessage());
                        }
                    } else {
                        log.debug("\t\t没符合提醒条件");
                    }
                    break;
                case 1:
                    log.debug("\t类型是：每月当日进行提醒");
                    if (now.get(Calendar.DAY_OF_MONTH) == actDate.get(Calendar.DAY_OF_MONTH)) {
                        log.debug("\t\t匹配到当前日就是需要的提醒日,需要每月此日进行提醒");
                        try {
                            this.noticeEvent(eventEntity);
                        } catch (ServiceException e) {
                            log.error(e.getMessage());
                        }
                    } else {
                        log.debug("\t\t没符合提醒条件");
                    }
                    break;
                case 2:
                    log.debug("\t类型是：每周相同的星期数进行提醒");
                    if (now.get(Calendar.DAY_OF_WEEK) == actDate.get(Calendar.DAY_OF_WEEK)) {
                        log.debug("\t\t匹配到当前日就是需要提醒的DAY_OF_WEEK,需要每周的此DAY_OF_WEEK进行提醒");
                        try {
                            this.noticeEvent(eventEntity);
                        } catch (ServiceException e) {
                            log.error(e.getMessage());
                        }
                    } else {
                        log.debug("\t\t没符合提醒条件");
                    }
                    break;
                default:
                    log.error("事件[{}]错误的提醒重复类型:{}", eventEntity.getName(), eventEntity.getRepeatType());
            }
        });
    }

    /**
     * 发送 提醒
     *
     * @param id
     *
     * @throws ServiceException
     */
    public void noticeEvent(String id) throws ServiceException {
        EventEntity eventEntity = eventService.findById(id);
        this.noticeEvent(eventEntity);
    }


    /**
     * 发送提醒
     *
     * @param eventEntity
     *
     * @throws ServiceException
     */
    private void noticeEvent(EventEntity eventEntity) throws ServiceException {
        String mailListString = eventEntity.getNoticeMail();
        if (!StringUtils.isEmpty(mailListString)) {
            String[] to = eventEntity.getNoticeMail().split(",");
            String subject = String.format(systemConfigureService.findByName("cns_subject").getValue(), eventEntity.getName());
            Future<String> mailSendFuture = mailService.sendSimpleMail(to, subject, eventEntity.getNoticeContent());
            log.debug("noticing sended ? = " + mailSendFuture.isDone());
        }
    }
}
