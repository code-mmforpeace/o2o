package com.ouver.o2o.dao;

import com.ouver.o2o.domain.LocalAuth;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * 账号的dao层
 */
public interface LocalAuthDao {
    /**
     *通过账号密码查询用户，用于登陆
     *
     * @param userName
     * @param password
     * @return
     */
    LocalAuth queryLocalByUserNameAndPwd(@Param("userName") String userName,
                                         @Param("password") String password);

    /**
     *
     *
     * @param userId
     * @return
     */
    LocalAuth queryLocalByUserId(@Param("userId") long userId);

    /**
     *添加用户
     *
     * @param localAuth
     * @return
     */
    int insertLocalAuth(LocalAuth localAuth);

    /**
     *通过userid，username，password修改密码
     *
     * @param
     * @return
     */
    int updateLocalAuth(@Param("userId") Long userId,
                        @Param("userName") String userName,
                        @Param("password") String password,
                        @Param("newPassword") String newPassword,
                        @Param("lastEditTime") Date lastEditTime);


}
