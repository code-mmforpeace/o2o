package com.ouver.o2o.dao;

import com.ouver.o2o.BaseTest;
import com.ouver.o2o.domain.Award;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AwardDaoTest extends BaseTest {

    @Autowired
    private AwardDao awardDao;

    @Test
    public void testInsertAward(){
        Award award = new Award();
        award.setShopId(35l);
        award.setPoint(1);
        award.setEnableStatus(1);
        award.setAwardName("测试商品添加1");
        awardDao.insertAward(award);
    }

    @Test
    public void testQueryAward(){
        Award award = new Award();
        List<Award> awards = awardDao.queryAwardList(award,0,7);
        System.out.println(awards.size());
    }

    @Test
    public void testQueryAwardById(){
        Award award = awardDao.queryAwardByAwardId(1l);
        System.out.println(award.getAwardName());
    }
}
