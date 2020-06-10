package com.ouver.o2o.service;

import com.ouver.o2o.BaseTest;
import com.ouver.o2o.domain.Award;
import com.ouver.o2o.domain.ImageHolder;
import com.ouver.o2o.dto.AwardExecution;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class AwardServiceTest extends BaseTest {

    @Autowired
    private AwardService awardService;

    @Test
    @Ignore
    public void testQueryAwardList(){
        //List<Award> awards = awardService.queryAwardList(new Award(),0,7);
        //System.out.println(awards.size());
    }

    @Test
    @Ignore
    public void testInsertAward(){
        Award award = new Award();
        award.setAwardDesc("测试添加2");
        award.setAwardImg("测试添加2的图片");
        award.setAwardName("测试添加2的奖品");
        award.setCreateTime(new Date());
        award.setLastEditTime(new Date());
        award.setPoint(5);
        award.setPriority(0);
        award.setEnableStatus(1);
        award.setShopId(35l);
        //AwardExecution awardExecution = awardService.insertAward(award,new ImageHolder());
        //System.out.println(awardExecution.getStateInfo());
    }


}
