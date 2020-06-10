package com.ouver.o2o.service.impl;

import com.ouver.o2o.dao.ShopAuthMapDao;
import com.ouver.o2o.domain.ShopAuthMap;
import com.ouver.o2o.dto.ShopAuthMapExecution;
import com.ouver.o2o.enums.ShopAuthMapStateEnum;
import com.ouver.o2o.service.ShopAuthMapService;
import com.ouver.o2o.utils.PageCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ShopAuthMapServiceImpl implements ShopAuthMapService {

    @Autowired
    private ShopAuthMapDao shopAuthMapDao;

    @Override
    public ShopAuthMapExecution listShopAuthMapByShopId(Long shopId, Integer pageIndex, Integer pageSize) {
        ShopAuthMapExecution se = new ShopAuthMapExecution();
        //空值判断
        if(shopId != null && pageIndex != null && pageSize != null){
            int i = PageCalculator.calculateRowIndex(pageIndex, pageSize);
            List<ShopAuthMap> shopAuthMaps = shopAuthMapDao.queryShopAuthMapListByShopId(shopId, i, pageSize);
            int count = shopAuthMapDao.queryShopAuthCountByShopId(shopId);
            se.setShopAuthMapList(shopAuthMaps);
            se.setCount(count);
            return se;
        }else {
            return null;
        }
    }

    @Override
    @Transactional
    public ShopAuthMapExecution addShopAuthMap(ShopAuthMap shopAuthMap) throws RuntimeException {
        //空值判断
        if (shopAuthMap != null && shopAuthMap.getShopId() != null
                && shopAuthMap.getEmployeeId() != null) {
            shopAuthMap.setCreateTime(new Date());
            shopAuthMap.setLastEditTime(new Date());
            shopAuthMap.setEnableStatus(1);
            try {
                int effectedNum = shopAuthMapDao.insertShopAuthMap(shopAuthMap);
                if (effectedNum <= 0) {
                    throw new RuntimeException("添加授权失败");
                }
                return new ShopAuthMapExecution(ShopAuthMapStateEnum.SUCCESS,
                        shopAuthMap);
            } catch (Exception e) {
                throw new RuntimeException("添加授权失败:" + e.toString());
            }
        } else {
            return new ShopAuthMapExecution(
                    ShopAuthMapStateEnum.NULL_SHOPAUTH_INFO);
        }
    }

    @Override
    public ShopAuthMapExecution modifyShopAuthMap(ShopAuthMap shopAuthMap) throws RuntimeException {
        if (shopAuthMap == null || shopAuthMap.getShopAuthId() == null) {
            return new ShopAuthMapExecution(
                    ShopAuthMapStateEnum.NULL_SHOPAUTH_ID);
        } else {
            try {
                int effectedNum = shopAuthMapDao.updateShopAuthMap(shopAuthMap);
                if (effectedNum <= 0) {
                    return new ShopAuthMapExecution(
                            ShopAuthMapStateEnum.INNER_ERROR);
                } else {// 创建成功
                    return new ShopAuthMapExecution(
                            ShopAuthMapStateEnum.SUCCESS, shopAuthMap);
                }
            } catch (Exception e) {
                throw new RuntimeException("updateShopByOwner error: "
                        + e.getMessage());
            }
        }
    }

    @Override
    public ShopAuthMapExecution removeShopAuthMap(Long shopAuthMapId) throws RuntimeException {
        return null;
    }

    @Override
    public ShopAuthMap getShopAuthMapById(Long shopAuthId) {
        return shopAuthMapDao.queryShopAuthMapById(shopAuthId);
    }
}
