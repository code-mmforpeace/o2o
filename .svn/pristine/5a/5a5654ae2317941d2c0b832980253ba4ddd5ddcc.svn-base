package com.ouver.o2o.web.frontend;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ouver.o2o.domain.Award;
import com.ouver.o2o.domain.PersonInfo;
import com.ouver.o2o.domain.UserShopMap;
import com.ouver.o2o.dto.AwardExecution;
import com.ouver.o2o.service.AwardService;
import com.ouver.o2o.service.ShopAuthMapService;
import com.ouver.o2o.service.UserShopMapService;
import com.ouver.o2o.utils.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/frontend")
public class ShopAwardByUserController {
	@Autowired
	private AwardService awardService;
	@Autowired
    private UserShopMapService userShopMapService;

	@RequestMapping(value = "/getawardbyid", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getAwardbyId(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		long awardId = HttpServletRequestUtil.getLong(request, "awardId");
		if (awardId > -1) {
			Award award = awardService.queryAwardByAwardId(awardId);
			modelMap.put("award", award);
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty awardId");
		}
		return modelMap;
	}

	/**
	 * 店铺奖品列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/listawardsbyshop", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listAwardsByShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		if ((pageIndex > -1) && (pageSize > -1) && (shopId > -1)) {
			String awardName = HttpServletRequestUtil.getString(request,
					"awardName");
			Award awardCondition = compactAwardCondition4Search(shopId,
					awardName);
			awardCondition.setEnableStatus(1);
			AwardExecution ae = awardService.queryAwardList(awardCondition,
					pageIndex, pageSize);
            PersonInfo user =(PersonInfo) request.getSession().getAttribute("user");
            //判断是不是未登录
            if(user != null) {
                UserShopMap userShopMap = userShopMapService.getUserShopMap(user.getUserId(), shopId);
                modelMap.put("awardList", ae.getAwardList());
                modelMap.put("count", ae.getCount());
                modelMap.put("totalPoint", userShopMap.getPoint());
                modelMap.put("success", true);
            }else {
                modelMap.put("success",false);
                modelMap.put("errMsg","请先登录！");
            }
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
		}
		return modelMap;
	}

	private Award compactAwardCondition4Search(long shopId, String awardName) {
		Award awardCondition = new Award();
		awardCondition.setShopId(shopId);
		if (awardName != null) {
			awardCondition.setAwardName(awardName);
		}
		return awardCondition;
	}
}
