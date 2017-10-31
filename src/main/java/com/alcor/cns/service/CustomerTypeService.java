package com.alcor.cns.service;

import com.alcor.cns.entity.CustomerTypeEntity;
import com.alcor.cns.repository.ICustomerTypeRepository;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * @author roamer - 徐泽宇
 * @create 2017-08-2017/8/8  下午5:57
 */
@Log4j2
@Data
@Service("com.alcor.cns.service.CustomerTypeService")
public class CustomerTypeService {

    @Qualifier("com.alcor.cns.repository.ICustomerTypeRepository")
    @Autowired
    ICustomerTypeRepository iCustomerTypeRepository;


    @Transactional()
    public void update(CustomerTypeEntity customerTypeEntity) throws ServiceException {
        iCustomerTypeRepository.save(customerTypeEntity);
    }

    @Transactional()
    public void create(CustomerTypeEntity customerTypeEntity) throws ServiceException {
        customerTypeEntity.setId(UUID.randomUUID().toString());
        iCustomerTypeRepository.saveAndFlush(customerTypeEntity);
    }

    @Transactional(readOnly = true)
    public List<CustomerTypeEntity> findAll() throws ServiceException{
        return iCustomerTypeRepository.findAll();
    }

    /**
     * 列出所有的顶级分类
     * @return
     * @throws ServiceException
     */
    @Transactional(readOnly = true)
    public List<CustomerTypeEntity> findWithPid(String pid) throws ServiceException{
        return iCustomerTypeRepository.findAllByPId(pid);
    }

}
