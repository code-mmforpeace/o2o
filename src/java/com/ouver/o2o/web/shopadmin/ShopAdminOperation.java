package com.ouver.o2o.web.shopadmin;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/shopAdmin",method = {RequestMethod.GET})
public class ShopAdminOperation {

    @RequestMapping("/shopOperation")
    public String ShopAdminController(){
        return "/shop/shopoperation";
    }

    @RequestMapping("/shopList")
    public String ShopList(){
        return "/shop/shoplist";
    }

    @RequestMapping("/shopManagement")
    public String shopManagement(){
        return "/shop/shopmanagement";
    }

    @RequestMapping("/shopAuthManagement")
    public String shopAuthManagement(){
        return "/shop/shopauthmanage";
    }

    @RequestMapping("/shopAuthEdit")
    public String shopAuthEdit(){
        return "/shop/shopauthedit";
    }

    @RequestMapping("/productCategoryManagement")
    public String productCategoryManagement(){
        return "/shop/productcategorymanage";
    }

    @RequestMapping("/productOperation")
    public String productOperation(){
        return "/shop/productoperation";
    }

    @RequestMapping("/productManagement")
    public String productManagement(){
        return "/shop/productmanagement";
    }

    @RequestMapping("/headLine")
    public String headLineManagement(){
        return "/shop/shopheadline";
    }

    @RequestMapping("/createHeadLine")
    public String createHeadLine(){
        return "/shop/shopcreateheadline";
    }

    @RequestMapping("/awardList")
    public String AwardManagement(){
        return "/shop/shopaward";
    }

    @RequestMapping("/createAward")
    public String createAward(){
        return "/shop/createaward";
    }

    @RequestMapping("/userShopMap")
    public String userShopMap(){
        return "/shop/usershopcheck";
    }

    @RequestMapping("/shopAwardCheck")
    public String shopAwardCheck(){
        return "/shop/shopawarddelivercheck";
    }

}
