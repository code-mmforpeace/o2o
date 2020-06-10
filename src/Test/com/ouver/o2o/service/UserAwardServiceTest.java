package com.ouver.o2o.service;

import com.ouver.o2o.BaseTest;
import com.ouver.o2o.domain.UserAwardMap;
import com.ouver.o2o.dto.UserAwardMapExecution;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserAwardServiceTest extends BaseTest {

    @Autowired
    private UserAwardMapService userAwardMapService;

    @Test
    public void testQueryUserAwardList(){
        UserAwardMapExecution userAwardMapExecution = userAwardMapService.listUserAwardMap(new UserAwardMap(), 0, 7);
        List<UserAwardMap> userAwardMapList = userAwardMapExecution.getUserAwardMapList();
        UserAwardMap userAwardMap = userAwardMapList.get(0);
        System.out.println(userAwardMap.getUserAwardId());
    }

}
