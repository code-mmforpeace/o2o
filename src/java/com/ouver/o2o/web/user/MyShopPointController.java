package com.ouver.o2o.web.user;

import com.ouver.o2o.domain.PersonInfo;
import com.ouver.o2o.domain.UserShopMap;
import com.ouver.o2o.dto.UserShopMapExecution;
import com.ouver.o2o.service.UserShopMapService;
import com.ouver.o2o.utils.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/frontend")
public class MyShopPointController {

	@Autowired
	private UserShopMapService userShopMapService;

	@RequestMapping(value = "/listusershopmapsbycustomer", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listUserShopMapsByCustomer(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
			if ((pageIndex > -1) && (pageSize > -1)) {
				if(user != null){
				UserShopMap userShopMapCondition = new UserShopMap();
				userShopMapCondition.setUserId(user.getUserId());
				//long shopId = HttpServletRequestUtil.getLong(request, "shopId");
//			if (shopId > -1) {
//				userShopMapCondition.setShopId(shopId);
//			}
				UserShopMapExecution ue = userShopMapService.listUserShopMap(userShopMapCondition, pageIndex, pageSize);
				modelMap.put("userShopMapList", ue.getUserShopMapList());
				modelMap.put("count", ue.getCount());
				modelMap.put("success", true);
				}else{
					modelMap.put("success",false);
					modelMap.put("errMsg","请先登录！");
				}
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
			}
		return modelMap;
	}
}
