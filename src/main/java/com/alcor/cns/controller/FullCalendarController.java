package com.alcor.cns.controller;

import com.alcor.cns.entity.GatherInfoEntity;
import com.alcor.cns.service.GatherInfoService;
import com.alcor.cns.service.ServiceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.roamer.boracay.aspect.httprequest.SessionCheckKeyword;
import pers.roamer.boracay.helper.JsonUtilsHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 提醒事件的 controller 类
 *
 * @author roamer - 徐泽宇
 * @create 2017-10-2017/10/11  下午12:13
 */
@Log4j2
@Controller("com.alcor.cns.controller.FullCalendarController")
@SessionCheckKeyword(checkIt = true)
public class FullCalendarController extends BaseController {

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private GatherInfoService gatherInfoService;

    @GetMapping("/fullcalendar/events")
    @ResponseBody
    public String listEvents(@RequestParam String beginDate, @RequestParam String endDate) throws ControllerException {
        log.debug("传入的日期是从{}到{}",beginDate,endDate);
        List<FullCalendarEvent> fullCalendarEventList = new ArrayList<>();
        EventsDateCondition eventsDateCondition = new EventsDateCondition();
        try {
            eventsDateCondition.setBeginDate(dateFormat.parse(beginDate));
            eventsDateCondition.setEndDate(dateFormat.parse(endDate));
            log.debug("开始的日期{}，结束的日期{}", eventsDateCondition.getBeginDate(), eventsDateCondition.getEndDate());
            List<GatherInfoEntity> gatherInfoEntityList = gatherInfoService.findAllBetweenDates(eventsDateCondition.getBeginDate(),eventsDateCondition.getEndDate());
            gatherInfoEntityList.forEach(item ->{
                FullCalendarEvent fullCalendarEvent = new FullCalendarEvent();
                fullCalendarEvent.title = item.getName();
                fullCalendarEvent.start = dateFormat.format(item.getGatherDate());
                fullCalendarEvent.allDay = true;
                fullCalendarEventList.add(fullCalendarEvent);
            });
            String m_rtn;
            m_rtn = JsonUtilsHelper.objectToJsonString(fullCalendarEventList);
            log.debug("返回的fullCalendar 的 events 数据是:{}", m_rtn);
            return m_rtn;
        } catch (ServiceException | JsonProcessingException | ParseException e) {
            e.printStackTrace();
            throw new ControllerException(e.getMessage());
        }
    }
}

@Data
class EventsDateCondition {
    Date beginDate;
    Date endDate;
}

@Data
class FullCalendarEvent {
    String id;
    String title;
    boolean allDay;
    String start;
    String end;
}
