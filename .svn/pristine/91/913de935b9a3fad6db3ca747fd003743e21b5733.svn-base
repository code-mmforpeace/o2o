package com.ouver.o2o.service;

import com.ouver.o2o.domain.Product;
import com.ouver.o2o.domain.ImageHolder;
import com.ouver.o2o.dto.ProductExecution;
import com.ouver.o2o.exceptions.ProductOperationException;

import java.util.List;

public interface ProductService {

    /**
     * 添加商品信息以及图片处理
     * @param product
     * @param thumbnail
     * @param productImgList
     * @return
     */
    ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList) throws ProductOperationException;

    /**
     * 根据productId查询对应的商品信息
     * @param productId
     * @return
     */
    Product queryProductById(long productId);

    /**
     * 根据传入的信息更新商品信息
     * @param product
     * @return
     */
    ProductExecution updateProduct(Product product,ImageHolder thumbnail,List<ImageHolder> productImgList) throws ProductOperationException;

    /**
     * 查询商品列表并分页，支持商品名模糊查询
     * @param productCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    ProductExecution getProductList(Product productCondition,int rowIndex,int pageSize);


}
