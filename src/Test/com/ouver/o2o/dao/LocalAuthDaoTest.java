package com.ouver.o2o.dao;

import com.ouver.o2o.BaseTest;
import com.ouver.o2o.domain.LocalAuth;
import com.ouver.o2o.domain.PersonInfo;
import com.ouver.o2o.utils.MD5Util;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;


public class LocalAuthDaoTest extends BaseTest {

    @Autowired
    private LocalAuthDao localAuthDao;

    public static final String username ="root";
    public static final String password ="root";

    @Test
    @Ignore
    public void testInsertLocalAuth(){
        //创建一个userid为2的新用户
        LocalAuth localAuth = new LocalAuth();
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(2L);
        localAuth.setPersonInfo(personInfo);
        localAuth.setUserName(username);
        localAuth.setPassword(password);
        localAuth.setCreateTime(new Date());
        int i = localAuthDao.insertLocalAuth(localAuth);
        System.out.println(i);
    }

    @Test
    public void testQuery(){
        LocalAuth localAuth = localAuthDao.queryLocalByUserNameAndPwd("test", MD5Util.getMd5("test"));
        System.out.println(localAuth.getPersonInfo().getName());
    }
}
