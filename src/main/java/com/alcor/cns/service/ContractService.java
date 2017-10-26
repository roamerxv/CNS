package com.alcor.cns.service;

import com.alcor.cns.entity.ContractEntity;
import com.alcor.cns.repository.IContractRepository;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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


    @Transactional(readOnly = true)
    public List<ContractEntity> findAllByCustomerId(String customerId) throws ServiceException {
        return iContractRepository.findAllByCustomerId(customerId);
    }

    @Transactional()
    public ContractEntity update(ContractEntity contractEntity) throws ServiceException {
        return iContractRepository.save(contractEntity);
    }

    @Transactional(readOnly = true)
    public ContractEntity findById(String id) throws ServiceException {
        return iContractRepository.findOne(id);
    }

    @Transactional
    public void delete(String id) throws ServiceException{
        iContractRepository.delete(id);
    }
}
