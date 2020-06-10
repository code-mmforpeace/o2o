package com.ouver.o2o.service;

import com.ouver.o2o.domain.UserShopMap;
import com.ouver.o2o.dto.UserShopMapExecution;

public interface UserShopMapService {

	UserShopMapExecution listUserShopMap(UserShopMap userShopMapCondition,
										 int pageIndex, int pageSize);

	UserShopMap getUserShopMap(long userId,long shopId);

}
