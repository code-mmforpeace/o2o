package com.ouver.o2o.dao;

import com.ouver.o2o.domain.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductDao {

    /**
     * 插入商品
     * @param product
     * @return
     */
     int insertProduct(Product product);

    /**
     * 根据id查询商品
     * @param productId
     * @return
     */
     Product queryProductById(long productId);

    /**
     * 根据传入的信息更新商品信息
     * @param product
     * @return
     */
    int updateProduct(Product product);

    /**
     * 分页查询商品
     * @param productCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<Product> queryProductList(@Param("productCondition") Product productCondition,@Param("rowIndex")int rowIndex,@Param("pageSize")int pageSize);

    /**
     * 返回商品总数
     * @return
     */
    int queryProductCount(@Param("productCondition")Product productCondition);


    /**
     * 删除商品类别之前，将商品类别ID置为空
     * @param productCategoryId
     * @return
     */
    int updateProductCategoryToNull(long productCategoryId);

}
