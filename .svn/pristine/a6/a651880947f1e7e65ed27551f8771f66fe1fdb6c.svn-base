package com.ouver.o2o.service.impl;

import com.ouver.o2o.dao.ProductCategoryDao;
import com.ouver.o2o.dao.ProductDao;
import com.ouver.o2o.domain.ProductCategory;
import com.ouver.o2o.dto.ProductCategoryExecution;
import com.ouver.o2o.enums.ProductCategoryStateEnum;
import com.ouver.o2o.exceptions.ProductCategoryOperationExecption;
import com.ouver.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryDao productCategoryDao;
    @Autowired
    private ProductDao productDao;

    @Override
    public List<ProductCategory> queryProductCategoryList(long shopId) {
        return productCategoryDao.queryProductCategoryList(shopId);
    }

    @Override
    @Transactional
    public ProductCategoryExecution batchInsertProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationExecption {
        if(productCategoryList != null && productCategoryList.size()>0) {
            try {
                int i = productCategoryDao.batchInsertProductCategory(productCategoryList);
                if (i <= 0) {
                    throw new ProductCategoryOperationExecption("店铺类别创建失败");
                } else {
                    return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
                }
            }catch (Exception e){
                throw new ProductCategoryOperationExecption("batchInsertProductCategory error:"+e.getMessage());
            }
        }else {
            return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
        }
    }

    @Override
    public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId) throws ProductCategoryOperationExecption {
        try{
            int i = productDao.updateProductCategoryToNull(productCategoryId);
            if(i < 0){
                throw new RuntimeException("商品类别更新失败");
            }
        }catch (Exception e){
            throw new RuntimeException("删除商品类别失败："+e.getMessage());
        }
       try{
           int i = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
           if(i <= 0){
               throw new ProductCategoryOperationExecption("商品类别删除失败");
           }else {
               return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
           }
       }catch (ProductCategoryOperationExecption e){
           throw new ProductCategoryOperationExecption("deleteProductCategory error :"+e.getMessage());
       }
    }
}
