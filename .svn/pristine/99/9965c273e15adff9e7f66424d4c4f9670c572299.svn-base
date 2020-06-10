package com.ouver.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ouver.o2o.domain.Award;
import com.ouver.o2o.domain.ImageHolder;
import com.ouver.o2o.domain.Shop;
import com.ouver.o2o.dto.AwardExecution;
import com.ouver.o2o.enums.AwardStateEnum;
import com.ouver.o2o.service.AwardService;
import com.ouver.o2o.utils.CodeUtil;
import com.ouver.o2o.utils.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 店铺的积分兑换
 */
@Controller
@RequestMapping("/award")
public class ShopAwardController {

    @Autowired
    private AwardService awardService;

    @RequestMapping("/awardlist")
    @ResponseBody
    public Map<String,Object> getawardlist(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
        Award awardCondition = new Award();
        awardCondition.setShopId(currentShop.getShopId());
        AwardExecution awardExecution = awardService.queryAwardList(awardCondition, 0, 7);
        if(awardExecution.getAwardList()!=null && awardExecution.getAwardList().size() >= 1){
            modelMap.put("success",true);
            modelMap.put("awardList",awardExecution.getAwardList());
        }else {
            modelMap.put("success",false);
        }
        return modelMap;
    }

    @RequestMapping(value = "/addaward", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addAward(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        ObjectMapper mapper = new ObjectMapper();
        Award award = null;
        String awardStr = HttpServletRequestUtil.getString(request, "awardStr");
        CommonsMultipartFile awardImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if(commonsMultipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
            awardImg = (CommonsMultipartFile)multipartHttpServletRequest.getFile("awardImg");
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","上传图片不能为空！");
            return modelMap;
        }
        try {
            award = mapper.readValue(awardStr, Award.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        if (award != null && awardImg != null) {
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute(
                        "currentShop");
                award.setShopId(currentShop.getShopId());
                ImageHolder thumbnail = new ImageHolder(awardImg.getOriginalFilename(),awardImg.getInputStream());
                AwardExecution ae = awardService.insertAward(award, thumbnail);
                if (ae.getState() == AwardStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", ae.getStateInfo());
                }
            } catch (RuntimeException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            } catch (IOException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息");
        }
        return modelMap;
    }

    @RequestMapping(value = "/modifyaward", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyAward(HttpServletRequest request) {
        boolean statusChange = HttpServletRequestUtil.getBoolean(request,
                "statusChange");
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        ObjectMapper mapper = new ObjectMapper();
        Award award = null;
        AwardExecution pe = null;
        String awardStr = HttpServletRequestUtil.getString(request, "awardStr");
        MultipartHttpServletRequest multipartRequest = null;
        CommonsMultipartFile awardImg = null;
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            multipartRequest = (MultipartHttpServletRequest) request;
            awardImg = (CommonsMultipartFile) multipartRequest
                    .getFile("awardImg");
        }
        try {
            award = mapper.readValue(awardStr, Award.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        if (award != null) {
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute(
                        "currentShop");
                award.setShopId(currentShop.getShopId());
                ImageHolder thumbnail = null;
                if (awardImg != null) {
                    thumbnail = new ImageHolder(awardImg.getOriginalFilename(),awardImg.getInputStream());
                    pe = awardService.updateAward(award, thumbnail);
                }else {
                    pe = awardService.updateAward(award,null);
                }
                if (pe.getState() == AwardStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (RuntimeException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息");
        }
        return modelMap;
    }

    /**
     * 获取当前奖品得到信息
     */
    @RequestMapping("/getawardbyid")
    @ResponseBody
    public Map<String,Object> getAward(@RequestParam long awardId){
        Map<String,Object> modelMap = new HashMap<>();
        if(awardId > -1){
            Award award = awardService.queryAwardByAwardId(awardId);
            modelMap.put("success",true);
            modelMap.put("award",award);
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","empty award");
        }
        return modelMap;
    }

}
