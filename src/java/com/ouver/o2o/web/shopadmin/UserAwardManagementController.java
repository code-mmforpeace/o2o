package com.ouver.o2o.web.shopadmin;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ouver.o2o.domain.PersonInfo;
import com.ouver.o2o.domain.Shop;
import com.ouver.o2o.domain.ShopAuthMap;
import com.ouver.o2o.domain.UserAwardMap;
import com.ouver.o2o.dto.ShopAuthMapExecution;
import com.ouver.o2o.dto.UserAwardMapExecution;
import com.ouver.o2o.enums.UserAwardMapStateEnum;
import com.ouver.o2o.service.AwardService;
import com.ouver.o2o.service.PersonInfoService;
import com.ouver.o2o.service.ShopAuthMapService;
import com.ouver.o2o.service.UserAwardMapService;
import com.ouver.o2o.utils.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
@RequestMapping("/shop")
public class UserAwardManagementController {
	@Autowired
	private UserAwardMapService userAwardMapService;
	@Autowired
	private AwardService awardService;
	@Autowired
	private PersonInfoService personInfoService;
	@Autowired
	private ShopAuthMapService shopAuthMapService;

	@RequestMapping(value = "/listuserawardmapsbyshop", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listUserAwardMapsByShop(
			HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Shop currentShop = (Shop) request.getSession().getAttribute(
                "currentShop");
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		if ((pageIndex > -1) && (pageSize > -1) && (currentShop != null)
				&& (currentShop.getShopId() != null)) {
			UserAwardMap userAwardMap = new UserAwardMap();
			userAwardMap.setShopId(currentShop.getShopId());
			String awardName = HttpServletRequestUtil.getString(request,
					"awardName");
			if (awardName != null) {
				userAwardMap.setAwardName(awardName);
			}
			UserAwardMapExecution ue = userAwardMapService.listUserAwardMap(
					userAwardMap, pageIndex, pageSize);
			modelMap.put("userAwardMapList", ue.getUserAwardMapList());
			modelMap.put("count", ue.getCount());
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
		}
		return modelMap;
	}

    /**
     * 商店兑换商品
     * @return
     */
    @RequestMapping(value = "/changeawardbyshop",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> changeawardbyshop(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        Shop currentShop = (Shop) request.getSession().getAttribute(
                "currentShop");
        Long awardId = HttpServletRequestUtil.getLong(request, "awardId");
        String code = HttpServletRequestUtil.getString(request, "codeToChange");
        if(currentShop.getShopId() >= 1){
            if(awardId >= 1 && code != null){
                UserAwardMap userAwardMapCondition = new UserAwardMap();
                userAwardMapCondition.setUserAwardId(awardId);
                userAwardMapCondition.setShopId(currentShop.getShopId());
                userAwardMapCondition.setExpireTime(new Date());
                userAwardMapCondition.setUsedStatus(0);
                UserAwardMapExecution userAwardMapExecution = userAwardMapService.modifyUserAwardMapByShop(userAwardMapCondition);
                if(userAwardMapExecution.getStateInfo().equals(UserAwardMapStateEnum.SUCCESS.getStateInfo())){
                    modelMap.put("success",true);
                }else {
                    modelMap.put("success",false);
                    modelMap.put("errMsg",userAwardMapExecution.getStateInfo());

                }
            }
        }
        return modelMap;
    }


	private UserAwardMap compactUserAwardMap4Exchange(Long customerId,
			Long userAwardId) {
		UserAwardMap userAwardMap = null;
		if (customerId != null && userAwardId != null) {
			userAwardMap = userAwardMapService.getUserAwardMapById(userAwardId);
			userAwardMap.setUsedStatus(0);
			userAwardMap.setUserId(customerId);
		}
		return userAwardMap;
	}

	private boolean checkShopAuth(long userId, UserAwardMap userAwardMap) {
		ShopAuthMapExecution shopAuthMapExecution = shopAuthMapService
				.listShopAuthMapByShopId(userAwardMap.getShopId(), 1, 1000);
		for (ShopAuthMap shopAuthMap : shopAuthMapExecution
				.getShopAuthMapList()) {
			if (shopAuthMap.getEmployeeId() == userId) {
				return true;
			}
		}
		return false;
	}
}
