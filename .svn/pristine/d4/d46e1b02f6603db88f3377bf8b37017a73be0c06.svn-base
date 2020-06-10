package com.ouver.o2o.service.impl;

import com.ouver.o2o.dao.shopDao;
import com.ouver.o2o.domain.Shop;
import com.ouver.o2o.domain.ImageHolder;
import com.ouver.o2o.dto.ShopExecution;
import com.ouver.o2o.enums.ShopStateEnum;
import com.ouver.o2o.exceptions.ShopOperationException;
import com.ouver.o2o.service.ShopService;
import com.ouver.o2o.utils.ImageUtil;
import com.ouver.o2o.utils.PageCalculator;
import com.ouver.o2o.utils.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private shopDao shopdao;

    @Override
    @Transactional
    public ShopExecution addsShop(Shop shop, ImageHolder thumbnail) {
        if(shop == null){
            return new ShopExecution(ShopStateEnum.NULL_SHOP_INFO);
        }
        try{
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            int effectedNum = shopdao.insertShop(shop);
            if(effectedNum <= 0){
                throw new ShopOperationException("店铺创建失败");
            }else {
                if(thumbnail.getImage()!= null){
                    try {
                        addShopImg(shop, thumbnail);
                    }catch (Exception e){
                        throw new ShopOperationException("addShopImg ERROR:"+e.getMessage());
                    }
                    effectedNum = shopdao.updateShop(shop);
                    if(effectedNum <= 0){
                        throw new ShopOperationException("更新图片地址失败");
                    }
                }
            }
        }catch (Exception e){
                throw new ShopOperationException("addShop error:"+e.getMessage());
        }
        return new ShopExecution(ShopStateEnum.CHECK,shop);
    }

    private void addShopImg(Shop shop, ImageHolder thumbnail) {
        //
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr = ImageUtil.generateThumbnail(thumbnail,dest);
        shop.setShopImg(shopImgAddr);
    }


    public Shop queryShopByShopId(long shopId){
        Shop shop = shopdao.queryByShopId(shopId);
        return shop;
    }

    @Override
    public ShopExecution queryShopList(Shop shopCondition, int pageIndex, int pageSize) {
        //
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        //
        List<Shop> shopList = shopdao.queryShopList(shopCondition, rowIndex, pageSize);
        //
        int count = shopdao.queryShopCount(shopCondition);
        ShopExecution se = new ShopExecution();
        if(shopList != null){
            se.setShopList(shopList);
            se.setCount(count);
        }else {
            se.setState(ShopStateEnum.INNER_ERROR.getState());
        }
        return se;
    }


    @Override
    public ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException {

        if(shop == null || shop.getShopId() == null){
            return new ShopExecution(ShopStateEnum.NULL_SHOPID);
        }else {
            //1.判断是否需要处理图片
            try {
                if (thumbnail.getImage() != null && thumbnail.getImageName() != null && !"".equals(thumbnail.getImageName())) {
                    Shop tempShop = shopdao.queryByShopId(shop.getShopId());
                    System.out.println(tempShop.getShopImg());
                    if (tempShop.getShopImg() != null) {
                        ImageUtil.deleteFileOrPath(tempShop.getShopImg());
                    }
                    addShopImg(shop, thumbnail);
                }
                //2.更新店铺信息
                shop.setLastEditTime(new Date());
                int effectedNum = shopdao.updateShop(shop);
                if (effectedNum <= 0) {
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);
                } else {
                    shop = shopdao.queryByShopId(shop.getShopId());
                    return new ShopExecution(ShopStateEnum.SUCCESS, shop);
                }
            } catch (Exception e) {
                throw new ShopOperationException("modifyShop error:" + e.getMessage());
            }
        }
    }
}
