package com.alcor.cns.service;

import com.alcor.cns.entity.GatherInfoEntity;
import com.alcor.cns.repository.IGatherInfoRepository;
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
@Service("com.alcor.cns.service.GatherInfoService")
public class GatherInfoService {

    @Qualifier("com.alcor.cns.repository.IGatherInfoRepository")
    @Autowired
    IGatherInfoRepository iGatherInfoRepository;

    @Transactional(readOnly = true)
    public List<GatherInfoEntity> findAllByContractId(String contractId) throws ServiceException {
        return iGatherInfoRepository.findAllByContractId(contractId);
    }

    public GatherInfoEntity findById(String id)throws ServiceException{
        return iGatherInfoRepository.findOne(id) ;
    }

    public void update(GatherInfoEntity gatherInfoEntity) throws ServiceException {
        iGatherInfoRepository.save(gatherInfoEntity);
    }

    public void delete(String id) throws ServiceException {
        iGatherInfoRepository.delete(id);
    }


}
