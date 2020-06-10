package com.ouver.o2o.service.impl;

import com.ouver.o2o.dao.LocalAuthDao;
import com.ouver.o2o.domain.LocalAuth;
import com.ouver.o2o.dto.LocalAuthExecution;
import com.ouver.o2o.enums.LocalAuthStateEnum;
import com.ouver.o2o.exceptions.LocalAuthOperationException;
import com.ouver.o2o.service.LocalAuthService;
import com.ouver.o2o.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.Date;

@Service
public class LocalAuthServiceImpl implements LocalAuthService {

    @Autowired
    private LocalAuthDao localAuthDao;

    @Override
    public LocalAuth getLocalAuthByUserNameAndPwd(String userName, String password) {
        return localAuthDao.queryLocalByUserNameAndPwd(userName, password);
    }

    @Override
    public LocalAuth getLocalAuthByUserId(long userId) {
        return localAuthDao.queryLocalByUserId(userId);
    }

    /**
     * 注册用户（用户或店家）
     * @param localAuth
     * @param profileImg
     * @return
     * @throws LocalAuthOperationException
     */
    @Override
    public LocalAuthExecution register(LocalAuth localAuth, CommonsMultipartFile profileImg) throws LocalAuthOperationException {
        //空值判断
        if(localAuth != null && profileImg != null){}
        //TODO
        return null;
    }

    @Override
    public LocalAuthExecution bindLocalAuth(LocalAuth localAuth) throws LocalAuthOperationException {
        if(localAuth != null){
            String passwordForMD5 = MD5Util.getMd5(localAuth.getPassword());
            localAuth.setPassword(passwordForMD5);
            int i = localAuthDao.insertLocalAuth(localAuth);
            if(i > 0){
                return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS);
            }else {
                return new LocalAuthExecution(LocalAuthStateEnum.FAILURE);
            }
        }else {
            return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
        }
    }

    @Override
    public LocalAuthExecution modifyLocalAuth(Long userId, String userName, String password, String newPassword) {
        if (userId != null && userName != null && password != null
                && newPassword != null && !password.equals(newPassword)) {
            try {
                int effectedNum = localAuthDao.updateLocalAuth(userId,
                        userName, MD5Util.getMd5(password),
                        MD5Util.getMd5(newPassword), new Date());
                if (effectedNum <= 0) {
                    throw new RuntimeException("更新密码失败");
                }
                return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS);
            } catch (Exception e) {
                throw new RuntimeException("更新密码失败:" + e.toString());
            }
        } else {
            return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
        }
    }
}
