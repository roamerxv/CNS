package com.alcor.cns.service;

import com.alcor.cns.entity.ContractEntity;
import com.alcor.cns.entity.CustomerEntity;
import com.alcor.cns.entity.GatherInfoEntity;
import com.alcor.cns.repository.IContractRepository;
import com.alcor.cns.repository.ICustomerRepository;
import com.alcor.cns.repository.IGatherInfoRepository;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.text.MessageFormat;
import java.util.*;

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

    @Qualifier("com.alcor.cns.repository.ICustomerRepository")
    @Autowired
    ICustomerRepository iCustomerRepository;

    @Autowired
    SystemConfigureService systemConfigureService;


    @Transactional(readOnly = true)
    public List<ContractEntity> findAllByCustomerId(String customerId) throws ServiceException {
        return iContractRepository.findAllByCustomerId(customerId);
    }

    @Transactional(rollbackFor = {Exception.class})
    public ContractEntity update(ContractEntity contractEntity) throws ServiceException {
        if (iContractRepository.findOne(contractEntity.getId()) == null) {
            log.debug("新增一条合同记录，并且生成对应的收款计划 begin");
            contractEntity = iContractRepository.save(contractEntity);
            log.debug("开始生成收款计划");
            if (contractEntity.getGatherCount() == 1) {
                log.debug("收款间隔是0，说明是一次性收款合同");
                this.genGatherInfo(contractEntity, contractEntity.getGatherInterval(), contractEntity.getAmount(), contractEntity.getFirstGatherDate());
            } else if (contractEntity.getGatherCount() > 1) {
                log.debug("收款间隔是{}个月", contractEntity.getGatherCount());
                int count_of_gen_gather_inf = contractEntity.getGatherCount();
                log.debug("要生成{}条收款计划", count_of_gen_gather_inf);
                double amount = contractEntity.getAmount() / count_of_gen_gather_inf;
                DateTime gatherDate = new DateTime(contractEntity.getFirstGatherDate().getTime());
                for (int i = 1; i <= count_of_gen_gather_inf; i++) {
                    this.genGatherInfo(contractEntity, i, amount, new Date(gatherDate.plusMonths((i - 1) * contractEntity.getGatherInterval()).toDate().getTime()));
                }
            }
            log.debug("新增一条合同记录，并且生成对应的付款计划 end");
            return  contractEntity;
        } else {
            log.debug("更新一条合同记录，并且生成对应的付款计划 begin");
            ContractEntity contractEntityUpdated = iContractRepository.save(contractEntity);
            log.debug("更新一条合同记录，并且生成对应的付款计划 end");
            return contractEntityUpdated;
        }
    }

    /**
     * 生成收款计划信息
     *
     * @param contractEntity
     * @param count          要生成的第几条付款计划
     * @param amount         付款金额
     * @param gatherDate     待付款的日期
     *
     * @throws ServiceException
     */
    @Transactional(rollbackFor = {Exception.class})
    public void genGatherInfo(ContractEntity contractEntity, int count, double amount, Date gatherDate) throws ServiceException {
        GatherInfoEntity gatherInfoEntity = new GatherInfoEntity();
        gatherInfoEntity.setId(UUID.randomUUID().toString());
        gatherInfoEntity.setNoticeTo(systemConfigureService.findByName("cns_mail_to").getValue());
        gatherInfoEntity.setAmount(amount);
        gatherInfoEntity.setContractId(contractEntity.getId());
        if (count == 1 && contractEntity.getGatherCount() == 1) {
            gatherInfoEntity.setName("合同[" + contractEntity.getName() + "]的一次性付款");
        } else {
            gatherInfoEntity.setName("合同[" + contractEntity.getName() + "]的第" + (count) + "次付款");
        }

        gatherInfoEntity.setGatherDate(gatherDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(gatherDate);
        int offset = Integer.parseInt(systemConfigureService.findByName("notice_date_offset").getValue().toString());
        calendar.add(Calendar.DATE, -1 * offset);
        Date noticeDate = new Date(calendar.getTime().getTime());
        gatherInfoEntity.setNoticeDate(noticeDate);
        gatherInfoEntity.setGathered(false);
        gatherInfoEntity.setNotice(true);
        gatherInfoEntity.setNoticeTo(systemConfigureService.findByName("cns_mail_to").getValue());
        CustomerEntity customerEntity = iCustomerRepository.findOne(contractEntity.getCustomerId());
        String noticeContent = this.genNoticContent(gatherInfoEntity,contractEntity,customerEntity);
        gatherInfoEntity.setNoticeContent(noticeContent);
        iGatherInfoRepository.save(gatherInfoEntity);
    }

    /**
     * 拼装出需要提示的内容
     * @param gatherInfoEntity
     * @param contractEntity
     * @param customerEntity
     * @return
     * @throws ServiceException
     */
    public String genNoticContent(GatherInfoEntity gatherInfoEntity, ContractEntity contractEntity, CustomerEntity customerEntity) throws ServiceException{
        // 开始拼装提示信息
        String messageTemp = systemConfigureService.findByName("cns_content").getValue();
        Object[] object = new String[]{customerEntity.getName(), contractEntity.getName(), gatherInfoEntity.getName(), gatherInfoEntity.getGatherDate().toString(), gatherInfoEntity.getAmount().toString()};
        String content = MessageFormat.format(messageTemp, object);
        log.debug("生成的收款信息的提示内容{}:end", content);
        return content;
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
