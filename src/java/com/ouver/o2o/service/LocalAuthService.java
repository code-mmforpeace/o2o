package com.ouver.o2o.service;

import com.ouver.o2o.domain.LocalAuth;
import com.ouver.o2o.dto.LocalAuthExecution;
import com.ouver.o2o.exceptions.LocalAuthOperationException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public interface LocalAuthService {
    /**
     *
     * @param userName
     * @return
     */
    LocalAuth getLocalAuthByUserNameAndPwd(String userName, String password);

    /**
     *
     * @param userId
     * @return
     */
    LocalAuth getLocalAuthByUserId(long userId);

    /**
     *
     * @param localAuth
     * @param profileImg
     * @return
     * @throws RuntimeException
     */
    LocalAuthExecution register(LocalAuth localAuth,
                                CommonsMultipartFile profileImg) throws LocalAuthOperationException;

    /**
     *
     * @param localAuth
     * @return
     * @throws RuntimeException
     */
    LocalAuthExecution bindLocalAuth(LocalAuth localAuth)
            throws LocalAuthOperationException;

    /**
     *
     * @param
     * @return
     */
    LocalAuthExecution modifyLocalAuth(Long userId, String userName,
                                       String password, String newPassword);
}
