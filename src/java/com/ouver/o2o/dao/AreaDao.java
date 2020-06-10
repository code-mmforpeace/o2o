package com.ouver.o2o.dao;

import com.ouver.o2o.domain.Area;

import java.util.List;

/**
 * 区域的持久层接口
 */
public interface AreaDao {
    /**
     * 查询所有
     * @return
     */
    List<Area> queryArea();

    /**
     * 测试存储过程
     */
    int addNum(int num);
}
