package com.ouver.o2o.web.user;

import com.ouver.o2o.domain.Award;
import com.ouver.o2o.domain.PersonInfo;
import com.ouver.o2o.domain.UserAwardMap;
import com.ouver.o2o.dto.UserAwardMapExecution;
import com.ouver.o2o.enums.UserAwardMapStateEnum;
import com.ouver.o2o.service.AwardService;
import com.ouver.o2o.service.PersonInfoService;
import com.ouver.o2o.service.UserAwardMapService;
import com.ouver.o2o.utils.HttpServletRequestUtil;
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
@RequestMapping("/frontend")
public class MyAwardController {
	@Autowired
	private UserAwardMapService userAwardMapService;
	@Autowired
	private AwardService awardService;
	@Autowired
	private PersonInfoService personInfoService;

	@RequestMapping(value = "/listuserawardmapsbycustomer", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listUserAwardMapsByCustomer(
			HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		Long userId = 1L;
		if ((pageIndex > -1) && (pageSize > -1) && (userId != null)) {
			UserAwardMap userAwardMapCondition = new UserAwardMap();
			userAwardMapCondition.setUserId(userId);
			long shopId = HttpServletRequestUtil.getLong(request, "shopId");
			if (shopId > -1) {
				userAwardMapCondition.setShopId(shopId);
			}
			String awardName = HttpServletRequestUtil.getString(request,
					"userName");
			if (awardName != null) {
				userAwardMapCondition.setAwardName(awardName);
			}
			UserAwardMapExecution ue = userAwardMapService.listUserAwardMap(
					userAwardMapCondition, pageIndex, pageSize);
			modelMap.put("userAwardMapList", ue.getUserAwardMapList());
			modelMap.put("count", ue.getCount());
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or pageIndex or userId");
		}
		return modelMap;
	}

	/**
	 * 奖品兑换
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/adduserawardmap", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> addUserAwardMap(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		PersonInfo user = (PersonInfo) request.getSession()
				.getAttribute("user");
		Long awardId = HttpServletRequestUtil.getLong(request, "awardId");
		UserAwardMap userAwardMap = compactUserAwardMap4Add(user, awardId);
		if (userAwardMap != null) {
			try {
				UserAwardMapExecution se = userAwardMapService
						.addUserAwardMap(userAwardMap);
				if (se.getState() == UserAwardMapStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			} catch (RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请选择领取的奖品");
		}
		return modelMap;
	}

	@RequestMapping("/getuserawardlist")
    @ResponseBody
    public Map<String,Object> getuserawardlist(HttpServletRequest request){
	    Map<String,Object> modelMap = new HashMap<>();
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        PersonInfo user = (PersonInfo)request.getSession().getAttribute("user");
        if(user != null && user.getUserId() > -1){
            UserAwardMap userAwardMap = new UserAwardMap();
            userAwardMap.setUserId(user.getUserId());
            UserAwardMapExecution userAwardMapExecution = userAwardMapService.listUserAwardMap(userAwardMap, pageIndex, pageSize);
            if(userAwardMapExecution.getStateInfo().equals(UserAwardMapStateEnum.SUCCESS.getStateInfo())){
                modelMap.put("success",true);
                modelMap.put("awardList",userAwardMapExecution.getUserAwardMapList());
                modelMap.put("count",userAwardMapExecution.getCount());
            }else {
                modelMap.put("success",false);
                modelMap.put("errMsg","查询失败！");
            }
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","错误操作：无用户登陆！！");
        }
	    return modelMap;
    }


	private UserAwardMap compactUserAwardMap4Add(PersonInfo user, Long awardId) {
		UserAwardMap userAwardMap = null;
		if (user != null && user.getUserId() != null && awardId != -1) {
			userAwardMap = new UserAwardMap();
			PersonInfo personInfo = personInfoService.getPersonInfoById(user
					.getUserId());
			Award award = awardService.queryAwardByAwardId(awardId);
			userAwardMap.setUserId(user.getUserId());
			userAwardMap.setAwardId(awardId);
			userAwardMap.setShopId(award.getShopId());
			userAwardMap.setUserName(personInfo.getName());
			userAwardMap.setAwardName(award.getAwardName());
			userAwardMap.setPoint(award.getPoint());
			userAwardMap.setCreateTime(new Date());
			userAwardMap.setUsedStatus(1);
		}
		return userAwardMap;
	}
}
