package com.ouver.o2o.dao;

import com.ouver.o2o.BaseTest;
import com.ouver.o2o.domain.HeadLine;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class HeadLineTest extends BaseTest {

    @Autowired
    private HeadLineDao headLineDao;

    @Test

    public void testQueryHeadLine(){
        List<HeadLine> headLines = headLineDao.queryHeadLineList(new HeadLine());
        Assert.assertEquals(5,headLines.size());
    }

    @Test
    @Ignore
    public void testQueryHeadLineByShopId(){
        HeadLine headLine = new HeadLine();
        headLine.setShopId(37);
        List<HeadLine> headLineList = headLineDao.queryHeadLineListByShopId(headLine);
        System.out.println(headLineList.size());
    }

    @Test
    @Ignore
    public void testInsertHeadLineByShopId(){
        HeadLine headLine = new HeadLine();
        //headLine.setLineId(5l);
        headLine.setShopId(35);
        headLine.setEnableStatus(0);
        headLine.setCreateTime(new Date());
        headLine.setLastEditTime(new Date());
        headLine.setLineImg("测试2");
        headLine.setLineName("测试2");
        headLine.setLineLink("test2");
        int i = headLineDao.insertHeadLineByShopId(headLine);
        System.out.println(i);
    }
}
