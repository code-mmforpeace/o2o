package com.ouver.o2o.dao;

import com.ouver.o2o.BaseTest;
import com.ouver.o2o.domain.Shop;
import com.ouver.o2o.domain.ShopAuthMap;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ShopAuthMapDaoTest extends BaseTest {

    @Autowired
    private ShopAuthMapDao shopAuthMapDao;

    @Test
    public void queryShopAuthMapByShopId(){
        Shop shop = new Shop();
        shop.setShopId(37L);
        List<ShopAuthMap> shopAuthMaps = shopAuthMapDao.queryShopAuthMapListByShopId(shop.getShopId(), 0, 7);
        System.out.println(shopAuthMaps.size());
    }
}
