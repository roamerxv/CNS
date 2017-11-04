package com.alcor.cns.service;

import com.alcor.cns.entity.ContractEntity;
import com.alcor.cns.entity.GatherInfoEntity;
import com.alcor.cns.repository.IContractRepository;
import com.alcor.cns.repository.IGatherInfoRepository;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * @author roamer - 徐泽宇
 * @create 2017-08-2017/8/8  下午5:57
 */
@Log4j2
@Data
@Service("com.alcor.cns.service.ContractService")
public class ContractService {

    @Qualifier("com.alcor.cns.repository.IContractRepository")
    @Autowired
    IContractRepository iContractRepository;

    @Qualifier("com.alcor.cns.repository.IGatherInfoRepository")
    @Autowired
    IGatherInfoRepository iGatherInfoRepository;

    @Autowired
    SystemConfigureService systemConfigureService;


    @Transactional(readOnly = true)
    public List<ContractEntity> findAllByCustomerId(String customerId) throws ServiceException {
        return iContractRepository.findAllByCustomerId(customerId);
    }

    @Transactional(rollbackFor={Exception.class})
    public ContractEntity update(ContractEntity contractEntity) throws ServiceException {
        if (iContractRepository.findOne(contractEntity.getId()) == null) {
            log.debug("新增一条合同记录，并且生成对应的收款计划 begin");
            log.debug("开始生成收款计划");
            if ( contractEntity.getGatherInterval() == 0){
                log.debug("收款间隔是0，说明是一次性收款合同");
                GatherInfoEntity gatherInfoEntity = new GatherInfoEntity();
                gatherInfoEntity.setId(UUID.randomUUID().toString());
                try {
                    gatherInfoEntity.setNoticeTo(systemConfigureService.findByName("cns_mail_to").getValue());
                } catch (ServiceException e) {
                    throw new ServiceException(e.getMessage());
                }
                gatherInfoEntity.setAmount(contractEntity.getAmount());
                gatherInfoEntity.setContractId(contractEntity.getId());
                gatherInfoEntity.setName("合同["+contractEntity.getName()+"]的一次性付款");
                gatherInfoEntity.setGatherDate(contractEntity.getFirstGatherDate());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(contractEntity.getFirstGatherDate());
                calendar.add(Calendar.DATE, -1);
                Date noticeDate=new Date(calendar.getTime().getTime());
                gatherInfoEntity.setNoticeDate(noticeDate);
                gatherInfoEntity.setGathered(false);
                gatherInfoEntity.setNotice(true);
                gatherInfoEntity.setNoticeTo(systemConfigureService.findByName("cns_mail_to").getValue());
                iGatherInfoRepository.save(gatherInfoEntity);
            }
            log.debug("新增一条合同记录，并且生成对应的付款计划 end");
            return iContractRepository.save(contractEntity);
        } else {
            log.debug("更新一条合同记录，并且生成对应的付款计划 begin");
            ContractEntity contractEntityUpdated = iContractRepository.save(contractEntity);
            log.debug("更新一条合同记录，并且生成对应的付款计划 end");
            return contractEntityUpdated;
        }
    }

    @Transactional(readOnly = true)
    public ContractEntity findById(String id) throws ServiceException {
        return iContractRepository.findOne(id);
    }

    @Transactional
    public void delete(String id) throws ServiceException {
        iContractRepository.delete(id);
    }
}
