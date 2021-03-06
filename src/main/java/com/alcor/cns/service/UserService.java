package com.alcor.cns.service;

import com.alcor.cns.entity.UserEntity;
import com.alcor.cns.repository.IUserRepository;
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
@Service("com.alcor.ril.service.UserService")
public class UserService {
    @Qualifier("com.alcor.cns.repository.IUserRepository")
    @Autowired
    IUserRepository iUserRepository;

    public List<UserEntity> findAll() {
        return iUserRepository.findAll();
    }

    public UserEntity findByID(String id) {
        return iUserRepository.findOne(id);
    }

    @Transactional(readOnly = true)
    public boolean login(UserEntity userEntity) throws ServiceException {
        UserEntity userEntityInDB = iUserRepository.findOne(userEntity.getName());
        if (userEntityInDB == null) {
            throw new ServiceException("exception.user.login.user_not_exit");
        }
        if (userEntityInDB.getPasswd().equalsIgnoreCase(userEntity.getPasswd())) {
            return true;
        } else {
            throw new ServiceException("exception.user.login.password_not_match");
        }
    }

    public boolean modifyPassword(String userName ,String oldPassword ,String newPassword) throws ServiceException{
        UserEntity userEntity = iUserRepository.findOne(userName);
        if (userEntity == null) {
            throw new ServiceException("exception.user.login.user_not_exit");
        }
        if (!userEntity.getPasswd().equals(oldPassword)){
            throw new ServiceException("exception.user.login.password_not_match");
        }
        userEntity.setPasswd(newPassword);
        iUserRepository.save(userEntity);
        return true;
    }

    public void update(UserEntity userEntity)throws ServiceException{
        iUserRepository.save(userEntity);
    }
}
