package com.ouver.o2o.web.user;

import com.ouver.o2o.dao.LocalAuthDao;
import com.ouver.o2o.domain.LocalAuth;
import com.ouver.o2o.domain.PersonInfo;
import com.ouver.o2o.dto.LocalAuthExecution;
import com.ouver.o2o.enums.LocalAuthStateEnum;
import com.ouver.o2o.service.LocalAuthService;
import com.ouver.o2o.service.PersonInfoService;
import com.ouver.o2o.utils.CodeUtil;
import com.ouver.o2o.utils.HttpServletRequestUtil;
import com.ouver.o2o.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/userController")
public class userController {

    @Autowired
    private LocalAuthService localAuthService;
    @Autowired
    private PersonInfoService personInfoService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> login(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        /**
         * 验证码判断
         */
        boolean needVerify = HttpServletRequestUtil.getBoolean(request,
                "needVerify");
        if (needVerify && !CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        /**
         * 获取前端输入的账号密码进行验证
         */
        String userName = HttpServletRequestUtil.getString(request, "userName");
        String password = HttpServletRequestUtil.getString(request, "password");
        if(userName !=null && password != null){
            password = MD5Util.getMd5(password);
            LocalAuth user = localAuthService.getLocalAuthByUserNameAndPwd(userName, password);
            if(user != null){
                //对用户登录进行判断，为1是店家，为0是用户
                if(user.getPersonInfo().getShopOwnerFlag()==1){
                    modelMap.put("success", true);
                    request.getSession().setAttribute("user",
                            user.getPersonInfo());
                    modelMap.put("shopOwner",true);
                }else {
                    modelMap.put("success", true);
                    request.getSession().setAttribute("user",
                            user.getPersonInfo());
                    modelMap.put("shopOwner",false);
                }
            }else {
                modelMap.put("success",false);
                modelMap.put("errMsg","账号密码有误");
            }
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","账号或密码不能为空");
        }
        return modelMap;
    }

    /**
     * 获取账号的个人资料
     */
    @RequestMapping("/getPersonInfo")
    @ResponseBody
    public Map<String,Object> getPersonInfo(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
        if(user != null){
            Long userId = user.getUserId();
            PersonInfo personInfoById = personInfoService.getPersonInfoById(userId);
            if(personInfoById != null){
                modelMap.put("success",true);
                modelMap.put("personinfo",personInfoById);
            }else {
                modelMap.put("success",false);
                modelMap.put("errMsg","非法用户");
            }
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","请先登录");
        }
        return modelMap;
    }

    @RequestMapping(value = "/changePW",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> changePW(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        /**
         * 验证码判断
         */
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        //获取前端传过来的数据
        String username = HttpServletRequestUtil.getString(request, "username");
        String oldpsw = HttpServletRequestUtil.getString(request, "oldpsw");
        String newpsw = HttpServletRequestUtil.getString(request, "newpsw");
        String newDpsw = HttpServletRequestUtil.getString(request, "newDpsw");
        PersonInfo personInfo = (PersonInfo) request.getSession().getAttribute("user");
        //先判断新密码两次是否一致
        if(newpsw.equals(newDpsw)){
            //判断用户名跟旧密码是否能有用户对应得上
            LocalAuth localAuth = localAuthService.getLocalAuthByUserNameAndPwd(username, MD5Util.getMd5(oldpsw));
            //判断用户是否存在
            if(localAuth != null){
                LocalAuthExecution localAuthExecution = localAuthService.modifyLocalAuth(personInfo.getUserId(), username, oldpsw, newDpsw);
                if(localAuthExecution.getState() == LocalAuthStateEnum.SUCCESS.getState()){
                    modelMap.put("success",true);
                }else {
                    modelMap.put("success",false);
                    modelMap.put("errMsg",localAuthExecution.getStateInfo());
                }
            }
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","两次密码不一致!");
        }
        return modelMap;
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> register(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        /**
         * 验证码判断
         */
        boolean needVerify = HttpServletRequestUtil.getBoolean(request,
                "needVerify");
        if (needVerify && !CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        /**
         * 根据前端传入的信息进行注册
         */
        String username = HttpServletRequestUtil.getString(request,"username");
        String password = HttpServletRequestUtil.getString(request,"password");
        String birthday = HttpServletRequestUtil.getString(request, "birthday");
        String phone = HttpServletRequestUtil.getString(request, "phone");
        String email = HttpServletRequestUtil.getString(request, "email");
        String name = HttpServletRequestUtil.getString(request, "name");
        //空值判断
        if(username != null && password != null && birthday != null && phone != null && email != null && name != null){
            PersonInfo personInfo = new PersonInfo();
            personInfo.setBirthday(new Date());
            personInfo.setPhone(phone);
            personInfo.setEmail(email);
            personInfo.setName(name);
            personInfo.setEnableStatus(1);
            personInfo.setShopOwnerFlag(0);
            personInfo.setCustomerFlag(1);
            personInfo.setAdminFlag(1);
            personInfoService.addPersonInfo(personInfo);
            Long userId = personInfo.getUserId();
            if(userId > -1){
                LocalAuth localAuth = new LocalAuth();
                localAuth.setUserName(username);
                localAuth.setPassword(password);
                localAuth.setUserId(userId);
                LocalAuthExecution localAuthExecution = localAuthService.bindLocalAuth(localAuth);
                if(localAuthExecution.getState() == LocalAuthStateEnum.SUCCESS.getState()){
                    modelMap.put("success",true);
                }else {
                    modelMap.put("success",false);
                    modelMap.put("errMsg",localAuthExecution.getStateInfo());
                }
            }else {
                modelMap.put("success",false);
            }
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","所填信息不能为空");
        }
        return modelMap;
    }

//    @RequestMapping()
//    @ResponseBody
//    public Map<String,Object> loginByUser(){
//
//    }

    @RequestMapping(value = "/loginout",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> loginout(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();
        request.getSession().setAttribute("user", null);
        request.getSession().setAttribute("shopList", null);
        request.getSession().setAttribute("currentShop", null);
        modelMap.put("success", true);
        return modelMap;
    }

}
