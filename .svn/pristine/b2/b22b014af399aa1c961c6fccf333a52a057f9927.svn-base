package com.ouver.o2o.dao;

import com.ouver.o2o.domain.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商家的持久层接口
 */
public interface shopDao {
    /**
     * 分页查询店铺，可输入的条件有店铺名（支持模糊搜索），店铺状态，店铺类别，区域id，owner
     * @param shopCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition, @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);
    /**
     * 返回queryShopList总数
     */
    int queryShopCount(@Param("shopCondition")Shop shopCondition);
    /**
     * 新增店铺信息
     * @param shop
     * @return
     */
    int insertShop(Shop shop);

    /**
     * 更新店铺信息
     * @param shop
     * @return
     */
    int updateShop(Shop shop);
    /**
     * 查询店铺信息
     * @param shopId
     * @return shop
     */
    Shop queryByShopId (long shopId);
}
