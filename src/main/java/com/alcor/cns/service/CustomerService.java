package com.alcor.cns.service;

import com.alcor.cns.entity.CustomerEntity;
import com.alcor.cns.repository.ICustomerRepository;
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
@Service("com.alcor.cns.service.CustomerService")
public class CustomerService {

    @Qualifier("com.alcor.cns.repository.ICustomerRepository")
    @Autowired
    ICustomerRepository iCustomerRepository;

    @Transactional(readOnly = true)
    public List<CustomerEntity> findAll() throws ServiceException {
        return iCustomerRepository.findAll();
    }


}
