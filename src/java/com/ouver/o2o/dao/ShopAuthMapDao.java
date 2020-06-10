package com.ouver.o2o.dao;

import com.ouver.o2o.domain.ShopAuthMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 店家授权dao层
 */
public interface ShopAuthMapDao {
    /**
     * 分页展示授权信息
     */
    List<ShopAuthMap> queryShopAuthMapListByShopId(@Param("shopId") long shopId, @Param("rowIndex") int rowIndex,
                                                 @Param("pageSize") int pageSize);

    /**
     * 获取授权总数
     *
     * @param shopId
     * @return
     */
    int queryShopAuthCountByShopId(@Param("shopId") long shopId);

    /**
     * 新增一条店铺与店员的授权关系
     *
     * @param shopAuthMap
     * @return effectedNum
     */
    int insertShopAuthMap(ShopAuthMap shopAuthMap);

    /**
     * 更新授权信息
     *
     * @param shopAuthMap
     * @return
     */
    int updateShopAuthMap(ShopAuthMap shopAuthMap);

    /**
     * 对某员工除权
     *
     * @param employeeId
     * @param shopId
     * @return effectedNum
     */
    int deleteShopAuthMap(@Param("employeeId") long employeeId,
                          @Param("shopId") long shopId);

    /**
     *
     * @param shopAuthId
     * @return
     */
    ShopAuthMap queryShopAuthMapById(Long shopAuthId);
}
