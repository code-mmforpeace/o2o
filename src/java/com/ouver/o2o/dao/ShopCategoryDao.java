package com.ouver.o2o.dao;

import com.ouver.o2o.domain.ShopCategory;
import org.apache.ibatis.annotations.Param;


import java.util.List;

public interface ShopCategoryDao {
    List<ShopCategory> queryShopCategory(@Param("shopCategoryCondition")ShopCategory shopCategoryCondition);
}
