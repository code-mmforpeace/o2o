package com.ouver.o2o.dao;

import com.ouver.o2o.BaseTest;
import com.ouver.o2o.domain.PersonInfo;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class PersonInfoDaoTest extends BaseTest {

    @Autowired
    private PersonInfoDao personInfoDao;

    @Test
    @Ignore
    public void testQueryPersonInfo(){
        List<PersonInfo> personInfos = personInfoDao.queryPersonInfoList(new PersonInfo(), 0, 7);
        System.out.println(personInfos.size());
    }

    @Test
    @Ignore
    public void testQueryPersonInfoById(){
        PersonInfo personInfo = personInfoDao.queryPersonInfoById(1);
        System.out.println(personInfo);
    }

    @Test
    public void testInsert(){
        PersonInfo personInfo = new PersonInfo();
        personInfo.setName("测试添加1");
        personInfo.setBirthday(new Date());
        personInfo.setGender("1");
        personInfo.setPhone("12345678901");
        personInfo.setEmail("test..");
        personInfo.setCustomerFlag(1);
        personInfo.setShopOwnerFlag(0);
        personInfo.setAdminFlag(1);
        personInfo.setEnableStatus(1);
        personInfoDao.insertPersonInfo(personInfo);
        Long userId = personInfo.getUserId();
        System.out.println(userId);
    }
}
