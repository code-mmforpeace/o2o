package com.ouver.o2o.web.frontend;

import com.ouver.o2o.domain.Area;
import com.ouver.o2o.domain.PersonInfo;
import com.ouver.o2o.domain.Shop;
import com.ouver.o2o.domain.ShopCategory;
import com.ouver.o2o.dto.ShopExecution;
import com.ouver.o2o.service.AreaService;
import com.ouver.o2o.service.ShopCategoryService;
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
@RequestMapping("/frontend")
public class ShopListController {

    @Autowired
    private AreaService areaService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private ShopService service;


    @RequestMapping(value = "/listshopspageinfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> listShopsPageInfo(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        //
        long parentId = HttpServletRequestUtil.getLong(request, "parentId");
        List<ShopCategory> shopCategoryList = null;
        if(parentId != -1){
            //
            try{
                ShopCategory shopCategoryCondition = new ShopCategory();
                ShopCategory parent = new ShopCategory();
                parent.setShopCategoryId(parentId);
                shopCategoryCondition.setParent(parent);
                shopCategoryList = shopCategoryService.queryShopCategory(shopCategoryCondition);
            }catch (Exception e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
            }
        }else {
            try{
                shopCategoryList = shopCategoryService.queryShopCategory(null);
            }catch (Exception e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
            }
        }
        modelMap.put("shopCategoryList",shopCategoryList);
        List<Area> areaList = null;
        try{
            areaList = areaService.queryListArea();
            modelMap.put("areaList",areaList);
            modelMap.put("success",true);
            return modelMap;
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
        }
        return modelMap;
    }

    @RequestMapping(value = "/shoplists",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> shopLists(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        PersonInfo user = (PersonInfo)request.getSession().getAttribute("user");
        if(user != null && user.getUserId() > -1){
            modelMap.put("user",true);
        }else {
            modelMap.put("user",false);
        }
        //获取页码
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        //获取每一页显示的条数
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        //非空判断
        if((pageIndex > -1) && (pageSize > -1)){
            //获取一级类别
            long parentId = HttpServletRequestUtil.getLong(request, "parentId");
            //获取特定二级类别
            long shopCategoryId = HttpServletRequestUtil.getLong(request, "shopCategoryId");
            //获取区域id
            int areaId = HttpServletRequestUtil.getInt(request, "areaId");
            //获取模糊查询的店铺名字
            String shopName = HttpServletRequestUtil.getString(request, "shopName");
            //获取组合完毕的查询条件
            Shop shopCondition = composeShopConditionForSearch(parentId,shopCategoryId,areaId,shopName);
            //根据查询条件，页码，条数进行查询得到结果
            ShopExecution se = service.queryShopList(shopCondition, pageIndex, pageSize);
            modelMap.put("shopList",se.getShopList());
            modelMap.put("count",se.getCount());
            modelMap.put("success",true);
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","empty data!!");
        }
        return modelMap;
    }

    /**
     * 组合生成查询条件
     * @param parentId
     * @param shopCategoryId
     * @param areaId
     * @param shopName
     * @return
     */
    private Shop composeShopConditionForSearch(long parentId, long shopCategoryId, int areaId, String shopName) {
        Shop shop = new Shop();
        //非空判断
        if(parentId != -1){
            //如果不为空，就是查询某一个一级shopCategory下的二级类别
            ShopCategory parent = new ShopCategory();
            ShopCategory child = new ShopCategory();
            parent.setShopCategoryId(parentId);
            child.setParent(parent);
            shop.setShopCategory(child);
        }
        if(shopCategoryId != -1){
            //如果不为空，就是查询某一个二级店铺类别下的所有店铺
            ShopCategory shopCategory = new ShopCategory();
            shopCategory.setShopCategoryId(shopCategoryId);
            shop.setShopCategory(shopCategory);
        }
        if(areaId != -1){
            //如果不为空，就是查询位于某个区域下的店铺列表
            Area area = new Area();
            area.setAreaId(areaId);
            shop.setArea(area);
        }
        if(shopName != null){
            //如果不为空，就查询包括这个名字的店铺列表
            shop.setShopName(shopName);
        }
        //前端要显示的应该是enable_status为1的，也就是审核通过的店铺
        shop.setEnableStatus(1);
        return shop;
    }
}
