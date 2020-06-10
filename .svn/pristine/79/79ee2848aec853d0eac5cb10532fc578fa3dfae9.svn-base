package com.ouver.o2o.web.frontend;

import com.ouver.o2o.domain.Product;
import com.ouver.o2o.domain.ProductCategory;
import com.ouver.o2o.domain.Shop;
import com.ouver.o2o.domain.ShopCategory;
import com.ouver.o2o.dto.ProductExecution;
import com.ouver.o2o.service.AreaService;
import com.ouver.o2o.service.ProductCategoryService;
import com.ouver.o2o.service.ProductService;
import com.ouver.o2o.service.ShopService;
import com.ouver.o2o.utils.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopdetail")
public class ShopDetailController {

    @Autowired
    private ShopService shopService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 获取店铺信息以及该店铺下的商品类别列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/shopdetailpagelist",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> shopDetailPageList(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        //从前端获取店铺id，检索店铺信息以及店铺下的商品信息
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        Shop shop = null;
        List<ProductCategory> productCategoryList = null;
        //空值判断
        if(shopId != -1){
            shop = shopService.queryShopByShopId(shopId);
            productCategoryList = productCategoryService.queryProductCategoryList(shopId);
            modelMap.put("shop",shop);
            modelMap.put("productCategoryList",productCategoryList);
            modelMap.put("success",true);
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","empty shop");
        }
        return modelMap;
    }

    @RequestMapping(value = "/shopSearchProductList",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> shopSearchProductList(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        //先获取前端传来的参数
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        //进行空值判断
        if((shopId > -1)&&(pageIndex > -1)&&(pageSize > -1)){
            //获取检索的商品类别信息以及模糊查询的商品名字
            long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
            String productName = HttpServletRequestUtil.getString(request, "productName");
            Product productCondition = compactProductCondition(shopId,productCategoryId,productName);
            ProductExecution pe = productService.getProductList(productCondition, pageIndex, pageSize);
            modelMap.put("productList",pe.getProductList());
            modelMap.put("count",pe.getCount());
            modelMap.put("success",true);
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","search failure");
        }
        return modelMap;
    }

    /**
     * 组合查询条件，判断空值
     * @param shopId
     * @param productCategoryId
     * @param productName
     * @return
     */
    private Product compactProductCondition(long shopId, long productCategoryId, String productName) {
        Product productCondition = new Product();
        Shop shop = new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);
        if (productCategoryId != -1L) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }
        if (productName != null) {
            productCondition.setProductName(productName);
        }
        //商品信息只能是已上架的状态才可以展示
        productCondition.setEnableStatus(1);
        return productCondition;
    }

}
