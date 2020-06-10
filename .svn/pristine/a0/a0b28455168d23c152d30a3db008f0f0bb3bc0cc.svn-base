package com.ouver.o2o.service;


import com.ouver.o2o.domain.ProductCategory;
import com.ouver.o2o.dto.ProductCategoryExecution;
import com.ouver.o2o.exceptions.ProductCategoryOperationExecption;

import java.util.List;

public interface ProductCategoryService {
    /**
     * 通过shopid查询店铺商品类别
     * @param shopId
     * @return
     */
    List<ProductCategory> queryProductCategoryList(long shopId);

    ProductCategoryExecution batchInsertProductCategory(List<ProductCategory> productCategoryList)throws ProductCategoryOperationExecption;

    /**
     * 将此类别下的商品里的类别id置为空，再删除掉该商品类别
     * @param productCategoryId
     * @param shopId
     * @return
     * @throws ProductCategoryOperationExecption
     */
    ProductCategoryExecution deleteProductCategory(long productCategoryId,long shopId)throws ProductCategoryOperationExecption;
}

