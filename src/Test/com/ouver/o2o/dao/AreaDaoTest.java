package com.ouver.o2o.dao;

import com.ouver.o2o.BaseTest;
import com.ouver.o2o.domain.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AreaDaoTest extends BaseTest {

    @Autowired
    private AreaDao areaDao;

    @Test
    public void queryListArea(){
        List<Area> areas = areaDao.queryArea();
        System.out.println(areas);
    }

    @Test
    public void testAddNum(){
        int i = areaDao.addNum(50);
        System.out.println(i);
    }


}
