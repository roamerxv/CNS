package com.alcor.cns.service;

import com.alcor.cns.entity.GatherInfoEntity;
import com.alcor.cns.repository.IGatherInfoRepository;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author roamer - 徐泽宇
 * @create 2017-08-2017/8/8  下午5:57
 */
@Log4j2
@Data
@Service("com.alcor.cns.service.GatherInfoService")
public class GatherInfoService {

    @Qualifier("com.alcor.cns.repository.IGatherInfoRepository")
    @Autowired
    IGatherInfoRepository iGatherInfoRepository;

    @Autowired
    private SystemConfigureService systemConfigureService;

    @Autowired
    private MailService mailService;



    @Transactional(readOnly = true)
    public List<GatherInfoEntity> findAllByContractId(String contractId) throws ServiceException {
        return iGatherInfoRepository.findAllByContractId(contractId);
    }

    public GatherInfoEntity findById(String id) throws ServiceException {
        return iGatherInfoRepository.findOne(id);
    }

    public void update(GatherInfoEntity gatherInfoEntity) throws ServiceException {
        iGatherInfoRepository.save(gatherInfoEntity);
    }

    public void delete(String id) throws ServiceException {
        iGatherInfoRepository.delete(id);
    }


    public void sentNotice(GatherInfoEntity gatherInfoEntity) throws ServiceException {
        String mailListString = gatherInfoEntity.getNoticeTo();
        try{
            if (!StringUtils.isEmpty(mailListString)) {
                String[] to = mailListString.split(",");
                String subject = String.format(systemConfigureService.findByName("cns_subject").getValue(), gatherInfoEntity.getName());
                Future<String> mailSendFuture = mailService.sendSimpleMail(to, subject, gatherInfoEntity.getNoticeContent());
                log.debug("noticing sended ? = " + mailSendFuture.isDone());
            }
        }catch (Exception e){
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * 列出所有的需要指定日期提醒的收款计划
     * @return
     * @throws ServiceException
     */

    public List<GatherInfoEntity> needToNotice(Date noticeDate) throws  ServiceException{
        return iGatherInfoRepository.findAllByNoticeDateAndGatheredAndNotice(noticeDate,false,true);
    }


}
