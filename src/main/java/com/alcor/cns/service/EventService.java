package com.alcor.cns.service;

import com.alcor.cns.entity.EventEntity;
import com.alcor.cns.repository.IEventRepository;
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
@Service("com.alcor.cns.service.EventService")
public class EventService {
    @Qualifier("com.alcor.cns.repository.IEventRepository")
    @Autowired
    IEventRepository iEventRepository;

    @Transactional(readOnly = true)
    public List<EventEntity> findAll() throws ServiceException {
        return iEventRepository.findAll();
    }

    @Transactional(readOnly = true)
    public EventEntity findById(String id) throws ServiceException {
        return iEventRepository.findOne(id);
    }


    @Transactional(readOnly = true)
    public List<EventEntity> findAllByNotice(boolean notice) throws ServiceException {
        return iEventRepository.findAllByNotice(notice);
    }

    public EventEntity update(EventEntity eventEntity) throws ServiceException {
        return iEventRepository.save(eventEntity);
    }

    public void delete(String id) throws ServiceException {
        iEventRepository.delete(id);
    }

}
