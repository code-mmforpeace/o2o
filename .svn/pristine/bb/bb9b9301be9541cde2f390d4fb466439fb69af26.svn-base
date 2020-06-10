package com.ouver.o2o.dao;

import com.ouver.o2o.BaseTest;
import com.ouver.o2o.domain.*;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class ShopDaoTest extends BaseTest {

    @Autowired
    private shopDao dao;

    @Test
    @Ignore
    public void testInsertShop() {
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId(1L);

        area.setAreaId(2);
        shopCategory.setShopCategoryId(1L);
        shop.setOwner(owner)    ;
        shop.setShopName("mytest1");
        shop.setShopDesc("mytest1");
        shop.setShopAddr("testaddr1");
        shop.setPhone("13810524526");
        shop.setShopImg("test1");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(1);
        shop.setAdvice("审核中");
        shop.setShopCategory(shopCategory);
        shop.setArea(area);
        int effectedNum = dao.insertShop(shop);
        System.out.println(effectedNum);
    }

    @Test
    @Ignore
    public void testUpdateShop(){
        Shop shop = new Shop();
        shop.setShopId(11L);

        shop.setShopName("测试更新");
        shop.setShopDesc("测试更新1");
        shop.setLastEditTime(new Date());
        int effectedNum = dao.updateShop(shop);
        System.out.println(effectedNum);
    }

    @Test
    @Ignore
    public void testQueryByShopId(){
        Shop shop = dao.queryByShopId(11L);
        System.out.println(shop.getArea().getAreaId());
        System.out.println(shop.getArea().getAreaName());
    }

    @Test
    @Ignore
    public void testQueryShopList(){
        Shop shopCondition = new Shop();
        PersonInfo owner = new PersonInfo();
        owner.setUserId(1l);
        shopCondition.setOwner(owner);
        List<Shop> shopList = dao.queryShopList(shopCondition, 0, 5);
        System.out.println(shopList.size());
    }

    @Test
    @Ignore
    public void testQueryShopCount(){
        Shop shopCondition = new Shop();
        PersonInfo owner = new PersonInfo();
        owner.setUserId(1l);
        shopCondition.setOwner(owner);
        int i = dao.queryShopCount(shopCondition);
        System.out.println(i);
    }

    @Test
    public void testCount(){
        Shop shop = new Shop();
        ProductCategory parent = new ProductCategory();
        parent.setProductCategoryId(13L);
        ProductCategory child = new ProductCategory();

    }
}
