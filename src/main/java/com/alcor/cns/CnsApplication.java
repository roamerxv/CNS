package com.alcor.cns;

import com.alcor.cns.service.NoticeService;
import com.alcor.cns.service.ServiceException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication(scanBasePackages = {"pers.roamer.boracay", "com.alcor.cns"})
@ImportResource(locations = {"classpath:boracay-config.xml"})
@EnableJpaRepositories({"pers.roamer.boracay", "com.alcor.cns"})
@EntityScan({"pers.roamer.boracay.entity", "com.alcor.cns.entity"})
//@EnableAsync()
@EnableScheduling
@Log4j2
public class CnsApplication extends SpringBootServletInitializer {
    @Autowired
    NoticeService noticeService;

    public static void main(String[] args) {
        SpringApplication.run(CnsApplication.class, args);
    }


    //秒 分 时 日 月 年
    @Scheduled(cron = "0 0 8 * * *")
    public void scheduler() {
        log.debug("开始调用提醒任务");
        try {
            noticeService.notice();
        } catch (ServiceException e) {
            log.error(e.getMessage());
        }
        log.debug("提醒任务完成");
    }
}
