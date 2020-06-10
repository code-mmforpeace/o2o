package com.ouver.o2o.dao;

import com.ouver.o2o.BaseTest;
import com.ouver.o2o.domain.UserShopMap;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserShopMapDaoTest extends BaseTest {
    @Autowired
    private UserShopMapDao userShopMapDao;

    @Test
    public void testQueryUserShopMap(){
        List<UserShopMap> userShopMaps = userShopMapDao.queryUserShopMapList(new UserShopMap(), 0, 7);
        System.out.println(userShopMaps.size());
    }
}
