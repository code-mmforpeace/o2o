package com.ouver.o2o.service.impl;

import com.ouver.o2o.dao.ProductDao;
import com.ouver.o2o.dao.ProductImgDao;
import com.ouver.o2o.domain.Product;
import com.ouver.o2o.domain.ProductImg;
import com.ouver.o2o.domain.ImageHolder;
import com.ouver.o2o.dto.ProductExecution;
import com.ouver.o2o.enums.ProductStateEnum;
import com.ouver.o2o.exceptions.ProductOperationException;
import com.ouver.o2o.service.ProductService;
import com.ouver.o2o.utils.ImageUtil;
import com.ouver.o2o.utils.PageCalculator;
import com.ouver.o2o.utils.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;

    //1.处理缩略图，获取缩略图性对路径并赋值给product
    //2.往tb_product写入商品信息，获取productId
    //3.结合productId批量处理商品详情图
    //4.将商品详情图列表批量插入tb_product_img
    @Override
    @Transactional
    public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList) throws ProductOperationException {
        if(product != null && product.getShop() != null && product.getShop().getShopId() != null){
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            product.setEnableStatus(1);
            if(thumbnail != null){
                addThumbnail(product,thumbnail);
            }
            try{
                //
                int effectedNum = productDao.insertProduct(product);
                if(effectedNum <= 0){
                    throw new ProductOperationException("创建商品失败");
                }
            }catch (Exception e){
                throw new ProductOperationException("创建商品失败:"+e.toString());
            }
            //
            if(productImgList != null && productImgList.size() >0 ){
                addProductImgList(product,productImgList);
            }
            return new ProductExecution(ProductStateEnum.SUCCESS, product);
        }else {
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    @Override
    public Product queryProductById(long productId) {
        return productDao.queryProductById(productId);
    }

    @Override
    @Transactional
    public ProductExecution updateProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList) throws ProductOperationException {
        //1.若存在缩略图，先处理缩略图（先删除旧的缩略图，添加新的缩略图，获取相对路径并赋值给product）
        //2.如详情图也存在，需要修改就进行跟缩略图一样的操作
        //3.将tb_product_img下的详情图记录清除
        //4.更新tb_product信息

        //空值判断
        if(product != null && product.getShop() != null && product.getShop().getShopId() != null){
            product.setLastEditTime(new Date());
            //缩略图处理
            if(thumbnail != null){
                Product product1 = productDao.queryProductById(product.getProductId());
                if(product1.getImgAddr() != null){
                    ImageUtil.deleteFileOrPath(product1.getImgAddr());
                }
                addThumbnail(product,thumbnail);
            }
            //详情图处理
            if(productImgList != null && productImgList.size() >0){
                deleteProductImgList(product.getProductId());
                addProductImgList(product,productImgList);
            }
            try{
                //更新商品信息
                int i = productDao.updateProduct(product);
                if(i <= 0){
                    throw new ProductOperationException("更新商品信息失败！");
                }
                return new ProductExecution(ProductStateEnum.SUCCESS,product);
            }catch (Exception e){
                throw new ProductOperationException("更新商品信息失败！"+ e.toString());
            }
        }else {
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    @Override
    public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        List<Product> products = productDao.queryProductList(productCondition, rowIndex, pageSize);
        int count = productDao.queryProductCount(productCondition);
        ProductExecution productExecution = new ProductExecution();
        productExecution.setCount(count);
        productExecution.setProductList(products);
        return productExecution;
    }


    /**
     * 批量删除详情图
     * @param productId
     */
    private void deleteProductImgList(Long productId) {
        List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);
        for (ProductImg p : productImgList) {
            ImageUtil.deleteFileOrPath(p.getImgAddr());
        }
        productImgDao.deleteProductImgByProductId(productId);
    }


    /**
     * 批量添加图片
     * @param product
     * @param productImgHolderList
     */
    private void addProductImgList(Product product, List<ImageHolder> productImgHolderList) {
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        List<ProductImg> productImgList = new ArrayList<ProductImg>();
        //遍历图片一次去处理，并添加进productImg实体类中
        for (ImageHolder productImgHolder : productImgHolderList) {
            String imgAddr = ImageUtil.generateNormalThumbnail(productImgHolder, dest);
            ProductImg productImg = new ProductImg();
            productImg.setImgAddr(imgAddr);
            productImg.setProductId(product.getProductId());
            productImg.setCreateTime(new Date());
            productImgList.add(productImg);
        }
        //如果确实是有图片需要添加的，就执行批量添加操作
        if(productImgList.size() > 0){
            try{
                int effectedNum = productImgDao.batchInsertProductImg(productImgList);
                if(effectedNum <= 0){
                    throw new ProductOperationException("创建商品类别失败");
                }
            }catch (Exception e){
                throw new ProductOperationException("创建商品详情图片失败："+e.toString());
            }
        }
    }

    /**
     * 添加缩略图
     * @param product
     * @param thumbnail
     */
    private void addThumbnail(Product product, ImageHolder thumbnail) {
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail,dest);
        product.setImgAddr(thumbnailAddr);
    }
}
