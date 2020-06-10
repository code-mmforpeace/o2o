package com.ouver.o2o.dao;

import com.ouver.o2o.BaseTest;
import com.ouver.o2o.domain.UserAwardMap;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class UserAwardMapTest extends BaseTest {

    @Autowired
    private UserAwardMapDao userAwardMapDao;

    @Test
    public void testQuery(){
        UserAwardMap userAwardMap = new UserAwardMap();
        List<UserAwardMap> userAwardMaps = userAwardMapDao.queryUserAwardMapList(userAwardMap, 0, 7);
        System.out.println(userAwardMaps.size());
    }
}
