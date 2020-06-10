package com.ouver.o2o.dao;

import com.ouver.o2o.domain.HeadLine;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeadLineDao {

    /**
     * 获取首页头条轮播图列表，无条件查询
     * @return
     */
    List<HeadLine> queryHeadLineList(@Param("headLineCondition") HeadLine headLineCondition);

    /**
     * 店铺查询自己店铺下的申请的头条
     */
    List<HeadLine> queryHeadLineListByShopId(@Param("headLineCondition")HeadLine headLineCondition);

    /**
     * 店铺新增头条活动轮播图
     */
    int insertHeadLineByShopId(HeadLine headLineCondition);

}
