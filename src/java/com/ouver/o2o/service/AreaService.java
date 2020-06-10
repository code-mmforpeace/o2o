package com.ouver.o2o.service;


import com.ouver.o2o.domain.Area;

import java.util.List;

/**
 * 区域的业务层接口
 */
public interface AreaService {

    public static final String AREALISTKEY="arealist";

    List<Area> queryListArea();

}
