package com.ouver.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ouver.o2o.domain.*;
import com.ouver.o2o.dto.ShopExecution;
import com.ouver.o2o.enums.ShopStateEnum;
import com.ouver.o2o.service.AreaService;
import com.ouver.o2o.service.HeadLineService;
import com.ouver.o2o.service.ShopCategoryService;
import com.ouver.o2o.service.ShopService;
import com.ouver.o2o.utils.CodeUtil;
import com.ouver.o2o.utils.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {

    @Autowired
    private ShopService service;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private HeadLineService headLineService;
    @Autowired
    private AreaService areaService;


    @RequestMapping(value = "/getshoplist",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getShopList(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<String,Object>();
//        PersonInfo personInfo = new PersonInfo();
//        personInfo.setUserId(1L);
//        personInfo.setName("测试用户名1");
//        request.getSession().setAttribute("user",personInfo);
        PersonInfo personInfo = (PersonInfo) request.getSession().getAttribute("user");
        try{
            Shop shopCondition = new Shop();
            shopCondition.setOwner(personInfo);
            ShopExecution se = service.queryShopList(shopCondition, 0, 100);
            modelMap.put("shopList",se.getShopList());
            modelMap.put("user",personInfo);
            modelMap.put("success",true);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
        }
        return modelMap;
    }

    @RequestMapping(value = "/getshopmanagementinfo" )
    @ResponseBody
    private Map<String,Object> getShopManagementInfo(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<String,Object>();
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        if(shopId <= 0){
            Object currentShopObj = request.getSession().getAttribute("currentShop");
            if(currentShopObj == null){
                modelMap.put("redirect",true);
                modelMap.put("url","/shopAdmin/shopList");
            }else {
                Shop currentShop = (Shop)currentShopObj;
                modelMap.put("redirect",false);
                modelMap.put("shopId",currentShop.getShopId());
            }
        }else {
            Shop currentShop = new Shop();
            currentShop.setShopId(shopId);
            request.getSession().setAttribute("currentShop",currentShop);
            modelMap.put("redirect",false);
        }
        return modelMap;
    }

    @RequestMapping(value = "/getshopbyid",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getShopById(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        if(shopId > -1){
            try{
                Shop shop = service.queryShopByShopId(shopId);
                List<Area> areas = areaService.queryListArea();
                modelMap.put("shop",shop);
                modelMap.put("areaList",areas);
                modelMap.put("success",true);
            }catch (Exception e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.toString());
            }
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","empty shopId");
        }
        return modelMap;
    }

    @RequestMapping(value = "/getshopinitinfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getshopinitinfo(){
        Map<String,Object> modelMap = new HashMap<String ,Object>();
        List<ShopCategory> shopCategoryList = new ArrayList<>();
        List<Area> areaList = new ArrayList<>();
        try{
            shopCategoryList = shopCategoryService.queryShopCategory(new ShopCategory());
            areaList = areaService.queryListArea();
            modelMap.put("shopCategoryList",shopCategoryList);
            modelMap.put("areaList",areaList);
            modelMap.put("success",true);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
        }
        return modelMap;
    }

    @RequestMapping(value = "/registershop",method = RequestMethod.POST)
    @ResponseBody
    private Map<String , Object> registerShop(HttpServletRequest request){

        //1.接受并转化相应的参数，包括店铺信息以及图片信息
        Map<String,Object> modelMap = new HashMap<String,Object>();

        if(!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","输入了错误的验证码");
            return modelMap;
        }

        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
//        try {
//            shopStr= URLDecoder.decode(shopStr,"utf-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        try {
//            request.setCharacterEncoding("UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try{
            shop = mapper.readValue(shopStr,Shop.class);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if(commonsMultipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
            shopImg = (CommonsMultipartFile)multipartHttpServletRequest.getFile("shopImg");
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","上传图片不能为空！");
            return modelMap;
        }
        //2.注册店铺
        if(shop!= null && shopImg !=null){
            PersonInfo owner = new PersonInfo();
            owner.setUserId(1L);
            shop.setOwner(owner);
            ShopExecution se = null;//ImageUtil.transferCommonsMultipartFileFile(shopImg)
            try {
                ImageHolder thumbnail = new ImageHolder(shopImg.getOriginalFilename(),shopImg.getInputStream());
                se = service.addsShop(shop,thumbnail);
            } catch (IOException e) {
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
                return modelMap;
            }

            if(se.getState() == ShopStateEnum.CHECK.getState()){
                modelMap.put("success",true);
            }else {
                modelMap.put("success",false);
                modelMap.put("errMsg",se.getStateInfo());
            }
            return modelMap;
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","请输入店铺信息！");
            return modelMap;
        }
    }

    @RequestMapping(value = "/modifyshop",method = RequestMethod.POST)
    @ResponseBody
    private Map<String , Object> modifyShop(HttpServletRequest request){

        //1.接受并转化相应的参数，包括店铺信息以及图片信息
        Map<String,Object> modelMap = new HashMap<String,Object>();

        if(!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","输入了错误的验证码");
            return modelMap;
        }

        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
//        try {
//            shopStr= URLDecoder.decode(shopStr,"utf-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        try {
//            request.setCharacterEncoding("UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try{
            shop = mapper.readValue(shopStr,Shop.class);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if(commonsMultipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
            shopImg = (CommonsMultipartFile)multipartHttpServletRequest.getFile("shopImg");
        }
        //2.更新店铺
        if(shop!= null && shop.getShopId() !=null){
            PersonInfo owner = (PersonInfo)request.getSession().getAttribute("user");
            shop.setOwner(owner);
            ShopExecution se = null;//ImageUtil.transferCommonsMultipartFileFile(shopImg)
            try {
                ImageHolder thumbnail = new ImageHolder(shopImg.getOriginalFilename(),shopImg.getInputStream());
                if(shopImg == null){
                    se = service.modifyShop(shop,null);
                }else {
                    se = service.modifyShop(shop, thumbnail);
                }
                if(se.getState() == ShopStateEnum.SUCCESS.getState()){
                    modelMap.put("success",true);
                    //用户可操作的店铺列表
                    @SuppressWarnings("unchecked")
                    List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
                    if(shopList == null || shopList.size() == 0){
                        shopList = new ArrayList<Shop>();
                    }
                    shopList.add(se.getShop());
                    request.getSession().setAttribute("shopList",shopList);
                }else {
                    modelMap.put("success",false);
                    modelMap.put("errMsg",se.getStateInfo());
                }
            } catch (IOException e) {
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
                return modelMap;
            }

            return modelMap;
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","请输入店铺ID！");
            return modelMap;
        }
    }

    /**
     * 店铺头条
     * @param ins
     * @param file
     */
    @RequestMapping("/getshopheadline")
    @ResponseBody
    private Map<String,Object> getshopheadline(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        Shop currentShopObj = (Shop)request.getSession().getAttribute("currentShop");
        if(currentShopObj != null && currentShopObj.getShopId() > -1) {
            HeadLine headLineCondition = new HeadLine();
            headLineCondition.setShopId(Math.toIntExact(currentShopObj.getShopId()));
            List<HeadLine> headLineList = headLineService.queryHeadLineListByShopId(headLineCondition);
            if (headLineList != null && headLineList.size() > -1) {
                modelMap.put("success", true);
                modelMap.put("headLineList", headLineList);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "错误了....");
            }
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","错误的操作，无店铺登录！");
        }
        return modelMap;
    }

    private static void inputStreamToFile(InputStream ins, File file){
        FileOutputStream os = null;
        try{
            os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while((bytesRead = ins.read(buffer)) != -1){
                os.write(buffer,0,bytesRead);
            }
        }catch (Exception e){
            throw new RuntimeException("调用inputStreamToFile产生异常"+e.getMessage());
        }finally {
            try {
                if (os != null) {
                    os.close();
                }
                if(ins != null){
                    ins.close();
                }
            }catch (Exception e){
                throw new RuntimeException("inputStreamToFile关闭io流产生异常"+e.getMessage());
            }
        }
    }
}
