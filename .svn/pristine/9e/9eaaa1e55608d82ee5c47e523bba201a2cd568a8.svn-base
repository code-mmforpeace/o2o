package com.ouver.o2o.dao;

import com.ouver.o2o.BaseTest;
import com.ouver.o2o.domain.ProductImg;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductImgDaoTest extends BaseTest {

    @Autowired
    private ProductImgDao productImgDao;

    @Test
    public void testAInsertProductImg(){
        //往productId为6的商品添加两个详情图片
        ProductImg productImg1 = new ProductImg();
        productImg1.setImgAddr("删除图片1");
        productImg1.setImgDesc("测试删除图片1");
        productImg1.setPriority(1);
        productImg1.setCreateTime(new Date());
        productImg1.setProductId(2L);
        ProductImg productImg2 = new ProductImg();
        productImg2.setImgAddr("删除图片1");
        productImg2.setImgDesc("测试查询");
        productImg2.setPriority(1);
        productImg2.setCreateTime(new Date());
        productImg2.setProductId(2L);
        List<ProductImg> productImgList = new ArrayList<ProductImg>();
        productImgList.add(productImg1);
        productImgList.add(productImg2);
        int effectedNum = productImgDao.batchInsertProductImg(productImgList);
        assertEquals(2, effectedNum);
    }

    @Test
    public void testBQueryProductImgList(){
        List<ProductImg> productImgs = productImgDao.queryProductImgList(3L);
//        for (ProductImg p: productImgs) {
//            System.out.println(productImgs.get(0).getImgAddr());
//        }
        assertEquals(2,productImgs.size());
    }

    @Test
    public void testCDeleteProductImgByProductId(){
        int i = productImgDao.deleteProductImgByProductId(2L);
        System.out.println(i);
    }
}
