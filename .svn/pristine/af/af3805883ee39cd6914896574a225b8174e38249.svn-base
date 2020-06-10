package com.ouver.o2o.service;

import com.ouver.o2o.domain.HeadLine;
import com.ouver.o2o.domain.ImageHolder;
import com.ouver.o2o.exceptions.ProductOperationException;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HeadLineService {

    public static final String KEY="headlinelist";

    List<HeadLine> queryHeadLineList(@Param("headLineCondition") HeadLine headLineCondition);

    /**
     * 店铺查询自己店铺下的申请的头条
     */
    List<HeadLine> queryHeadLineListByShopId(@Param("headLineCondition")HeadLine headLineCondition);

    /**
     * 添加头条
     * @param headLineCondition
     * @param thumbnail
     * @return
     * @throws ProductOperationException
     */
    int addProduct(HeadLine headLineCondition, ImageHolder thumbnail) throws ProductOperationException;
        }
