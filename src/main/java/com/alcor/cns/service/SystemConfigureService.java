package com.alcor.cns.service;

import com.alcor.cns.entity.SystemConfigureEntity;
import com.alcor.cns.repository.ISystemConfigureRepository;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author roamer - 徐泽宇
 * @create 2017-08-2017/8/8  下午5:57
 */
@Log4j2
@Data
@Service("com.alcor.cns.service.SystemConfigureService")
public class SystemConfigureService {
    @Qualifier("com.alcor.cns.repository.ISystemConfigureRepository")
    @Autowired
    ISystemConfigureRepository iSystemConfigureRepository;

    public List<SystemConfigureEntity> findAll() throws ServiceException {
        return iSystemConfigureRepository.findAll(new Sort(Sort.Direction.ASC, "SortNo"));
    }

    public SystemConfigureEntity findByName(String name) throws  ServiceException{
        return iSystemConfigureRepository.findOne(name);
    }

    public SystemConfigureEntity update( SystemConfigureEntity systemConfigureEntity) throws  ServiceException{
        return iSystemConfigureRepository.save(systemConfigureEntity);
    }

    public void delete( String id) throws  ServiceException{
        iSystemConfigureRepository.delete(id);
    }

}
