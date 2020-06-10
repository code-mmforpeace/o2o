package com.ouver.o2o.service;

import com.ouver.o2o.domain.UserProductMap;
import com.ouver.o2o.dto.UserProductMapExecution;

public interface UserProductMapService {
	/**
	 * 
	 * @param userProductCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	UserProductMapExecution listUserProductMap(
			UserProductMap userProductCondition, Integer pageIndex,
			Integer pageSize);

	/**
	 * 
	 * @param userProductMap
	 * @return
	 * @throws RuntimeException
	 */
	UserProductMapExecution addUserProductMap(UserProductMap userProductMap)
			throws RuntimeException;

}
