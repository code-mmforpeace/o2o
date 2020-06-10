package com.ouver.o2o.service;

import com.ouver.o2o.BaseTest;
import com.ouver.o2o.domain.HeadLine;
import com.ouver.o2o.domain.ImageHolder;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

public class HeadLineServiceTest extends BaseTest {

    @Autowired
    private HeadLineService headLineService;

    @Test
    public void testQueryHeadLine(){
        List<HeadLine> headLineList = headLineService.queryHeadLineList(new HeadLine());
        System.out.println(headLineList.size());
    }

    @Test
    @Ignore
    public void testAddHeadLine() throws FileNotFoundException {
        //尝试创建shopId为35的头条
        HeadLine headLineCondition = new HeadLine();
        headLineCondition.setShopId(35);
        headLineCondition.setLineLink("尝试添加1");
        headLineCondition.setLineName("尝试添加1");
        headLineCondition.setLastEditTime(new Date());
        headLineCondition.setCreateTime(new Date());
        headLineCondition.setEnableStatus(1);
        headLineCondition.setPriority(0);
        //添加图片
        File thumbnailFile = new File("E:\\projectLib\\202003191143.jpg");
        InputStream is = new FileInputStream(thumbnailFile);
        ImageHolder imageHolder = new ImageHolder(thumbnailFile.getName(), is);
        headLineService.addProduct(headLineCondition,imageHolder);
    }

}
