package com.ouver.o2o.service;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.ouver.o2o.domain.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ShopCategoryServiceTest extends Serializers.Base {

    @Autowired
    private ShopCategoryService shopCategoryService;

    @Test
    public void testQuery(){
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(10l);
        List<ShopCategory> shopCategoryList = shopCategoryService.queryShopCategory(shopCategory);
        System.out.println(shopCategoryList.size());
    }
}
