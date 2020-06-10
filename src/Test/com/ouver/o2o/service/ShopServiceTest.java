package com.ouver.o2o.service;

import com.ouver.o2o.BaseTest;
import com.ouver.o2o.domain.Area;
import com.ouver.o2o.domain.PersonInfo;
import com.ouver.o2o.domain.Shop;
import com.ouver.o2o.domain.ShopCategory;
import com.ouver.o2o.domain.ImageHolder;
import com.ouver.o2o.dto.ShopExecution;
import com.ouver.o2o.enums.ShopStateEnum;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

public class ShopServiceTest extends BaseTest {

    @Autowired
    private ShopService service;

    @Test
    @Ignore
    public void testAddShop() throws FileNotFoundException {
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId(1L);

        area.setAreaId(2);
        shopCategory.setShopCategoryId(1L);
        shop.setOwner(owner)    ;
        shop.setShopName("测试店铺5");
        shop.setShopDesc("测试店铺5");
        shop.setShopAddr("testaddr1");
        shop.setPhone("13810524526");
        shop.setShopImg("test1");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("审核中");
        shop.setShopCategory(shopCategory);
        shop.setArea(area);
        File shopImg = new File("C:\\Users\\DELL\\Pictures\\Saved Pictures\\2-7.3.jpg");
        FileInputStream is = new FileInputStream(shopImg);
        ImageHolder thumbnail = new ImageHolder(shopImg.getName(),is);
        ShopExecution shopExecution = service.addsShop(shop,thumbnail);
        System.out.println(shopExecution);
    }

    @Test
    @Ignore
    public void testModifyShop() throws FileNotFoundException {
        //Shop shop = service.queryShopByShopId(11L);
        Shop shop = new Shop();
        shop.setShopId(32L);
        shop.setShopName("修改后的店铺名字");
        File shopImg = new File("C:\\Users\\DELL\\Pictures\\Saved Pictures\\2-7.4.jpg");
        InputStream is = new FileInputStream(shopImg);
        ImageHolder thumbnail = new ImageHolder("2-7.4.jpg",is);
        ShopExecution shopExecution = service.modifyShop(shop, thumbnail);
        System.out.println("状态："+shopExecution.getStateInfo());
    }

    @Test
    public void testQueryShopListAndCount(){
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(1L);
        shop.setShopCategory(shopCategory);
        ShopExecution shopExecution = service.queryShopList(shop, 1, 6);
        System.out.println(shopExecution.getShopList().get(1).getShopName());
    }
}
