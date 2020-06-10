package com.ouver.o2o.web.frontend;

import com.ouver.o2o.domain.HeadLine;
import com.ouver.o2o.domain.PersonInfo;
import com.ouver.o2o.domain.ShopCategory;
import com.ouver.o2o.service.HeadLineService;
import com.ouver.o2o.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页
 */
@Controller
@RequestMapping("/frontend")
public class MainPageController {

    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private HeadLineService headLineService;

    /**
     * 初始化前端展示系统，一级店铺类别列表和头条列表
     */
    @RequestMapping(value = "/mainpageinfolist",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> mainPageInfoList(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        //如果是用户登录，域中存有user对象，用来改变首页
        PersonInfo user = (PersonInfo)request.getSession().getAttribute("user");
        List<ShopCategory> shopCategoryList = new ArrayList<>();
        try{
            //获取一级店铺类别列表
            shopCategoryList = shopCategoryService.queryShopCategory(null);
            modelMap.put("shopCategoryList",shopCategoryList);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
        }
        List<HeadLine> headLineList = new ArrayList<>();
        try{
            //获取可用状态为1的头条
            HeadLine headLineCondition = new HeadLine();
            headLineCondition.setEnableStatus(1);
            headLineList = headLineService.queryHeadLineList(headLineCondition);
            modelMap.put("headLineList",headLineList);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
        }
        if(user != null && user.getUserId() > -1){
            modelMap.put("user",true);
        }
        modelMap.put("success",true);
        return modelMap;
    }

//    @RequestMapping("/shoplist")
//    public Map<String,Object> shopCategoryList(){
//        Map<String,Object> modelMap = new HashMap<>();
//    }
}
