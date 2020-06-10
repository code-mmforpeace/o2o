package com.ouver.o2o.service;

import com.ouver.o2o.domain.Shop;
import com.ouver.o2o.domain.ImageHolder;
import com.ouver.o2o.dto.ShopExecution;
import com.ouver.o2o.exceptions.ShopOperationException;
import org.apache.ibatis.annotations.Param;

public interface ShopService {

    /**
     * 注册店铺信息，包括对图片的处理
     * @param shop
     * @param thumbnail
     * @return
     */
    ShopExecution addsShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;

    /**
     * 通过店铺id获取店铺信息
     * @param shopId
     * @return
     */
    Shop queryShopByShopId(long shopId);

    /**
     *  分页查询店铺，可输入的条件有店铺名（支持模糊搜索），店铺状态，店铺类别，区域id，owner
     */
    ShopExecution queryShopList(@Param("shopCondition") Shop shopCondition, @Param("rowIndex") int pageIndex, @Param("pageSize") int pageSize);

    /**
     * 更新店铺信息，包括对图片的处理
     */
    ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;
}
