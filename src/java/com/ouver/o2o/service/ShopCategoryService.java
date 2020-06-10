package com.ouver.o2o.service;

import com.ouver.o2o.domain.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopCategoryService {

    public static final String KEY="shopcategorylist";

    List<ShopCategory> queryShopCategory(@Param("shopCategoryCondition")ShopCategory shopCategoryCondition);
}
