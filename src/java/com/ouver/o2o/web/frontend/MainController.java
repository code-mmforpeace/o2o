package com.ouver.o2o.web.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/Page")
@Controller
public class MainController {

    @RequestMapping(value = "/MainPage",method = RequestMethod.GET)
    public String mainPage(){
        return "/frontend/index";
    }

    @RequestMapping(value = "/ListPage",method = RequestMethod.GET)
    public String listPage(){
        return "/frontend/shoplist";
    }

    @RequestMapping(value = "/ShopDetail",method = RequestMethod.GET)
    public String shopPage(){
        return "/frontend/shopdetail";
    }

    @RequestMapping(value = "/ProductDetail",method = RequestMethod.GET)
    public String productPage(){
        return "/frontend/productdetail";
    }

    @RequestMapping("/Register")
    public String register(){
        return "/useroperate/register";
    }

    @RequestMapping("/MyPoint")
    public String myPoint(){
        return "/useroperate/mypoint";
    }

    @RequestMapping("/MyRecord")
    public String myRecord(){
        return "/useroperate/myrecord";
    }

    @RequestMapping("/awardList")
    public String awardList(){
        return "/frontend/awardlist";
    }

    @RequestMapping("/PointRecord")
    public String pointRecord(){
        return "/useroperate/pointrecord";
    }

    @RequestMapping("/user/loginByUser")
    public String Login(){
        return "/useroperate/loginToUser";
    }


}
