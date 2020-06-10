package com.ouver.o2o.web.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 定义用户操作转发的路由
 */
@Controller
@RequestMapping("/user")
public class userMainController {

    /**
     * 商家登录
     * @return
     */
    @RequestMapping("/loginByShop")
    public String shopLogin(){
        return "/useroperate/login";
    }

    /**
     * 用户登录
     */
    @RequestMapping("/loginByUser")
    public String userLogin(){
        return "/useroperate/loginToUser";
    }

    /**
     * 修改密码
     */
    @RequestMapping("/changePW")
    public String shopChangePw(){
        return "/useroperate/changePW";
    }

    @RequestMapping("/register")
    public String register(){
        return "/useroperate/register";
    }

    @RequestMapping("/personinfo")
    public String getpersoninfo(){
        return "/useroperate/personinfo";
    }

}
