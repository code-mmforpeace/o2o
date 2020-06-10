package com.ouver.o2o.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ouver.o2o.dao.HeadLineDao;
import com.ouver.o2o.domain.HeadLine;
import com.ouver.o2o.domain.ImageHolder;
import com.ouver.o2o.exceptions.HeadLineOperationException;
import com.ouver.o2o.exceptions.ProductOperationException;
import com.ouver.o2o.redis.RedisUtils;
import com.ouver.o2o.service.HeadLineService;
import com.ouver.o2o.utils.ImageUtil;
import com.ouver.o2o.utils.PathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class HeadLineServiceImpl implements HeadLineService {

    @Autowired
    private HeadLineDao headLineDao;
    @Autowired
    private RedisUtils.Keys keys;
    @Autowired
    private RedisUtils.strings strings;

    private static Logger logger = LoggerFactory.getLogger(HeadLineServiceImpl.class);

    @Override
    @Transactional
    public List<HeadLine> queryHeadLineList(HeadLine headLineCondition) {
        //定义key
        String key = KEY;
        //定义接收对象
        List<HeadLine> headLineList = null;
        //定义jackson数据转换操作类
        ObjectMapper objectMapper = new ObjectMapper();
        //拼接key，可用和不可用
        if(headLineCondition != null && headLineCondition.getEnableStatus() != null){
            key = key+"_"+headLineCondition.getEnableStatus();
        }
        //进行判断
        if(!keys.exists(key)){
            //如果不存在，则表示是第一次缓存
            //那么就先由数据库查询返回结果，结果存入redis
            headLineList = headLineDao.queryHeadLineList(headLineCondition);
            //将相关结果集转换成string，存入redis
            String jsonString;
            try {
                jsonString = objectMapper.writeValueAsString(headLineList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new HeadLineOperationException(e.getMessage());
            }
            strings.set(key,jsonString);
        }else {
            //如果存在，直接将redis存储的转换回去返回
            String s = strings.get(key);
            //将string类型转换为对应的集合类型
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, HeadLine.class);

            try {
                headLineList = objectMapper.readValue(s,javaType);
                //如果后面有店铺申请头条
                List<HeadLine> headLineList1 = headLineDao.queryHeadLineList(headLineCondition);
                if(headLineList.size() != headLineList1.size()){
                    headLineList = headLineDao.queryHeadLineList(headLineCondition);
                    //将相关结果集转换成string，存入redis
                    String jsonString;
                    try {
                        jsonString = objectMapper.writeValueAsString(headLineList);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                        logger.error(e.getMessage());
                        throw new HeadLineOperationException(e.getMessage());
                    }
                    strings.set(key,jsonString);
                }
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new HeadLineOperationException(e.getMessage());
            }
        }
        return headLineList;
    }

    @Override
    public List<HeadLine> queryHeadLineListByShopId(HeadLine headLineCondition) {
        return headLineDao.queryHeadLineListByShopId(headLineCondition);
    }

    /**
     * 处理缩略图，获取缩略图的路径并赋值给headline，往tb_head_line写入商品信息，并获取productID
     * @param headLineCondition
     * @param thumbnail
     * @return
     * @throws ProductOperationException
     */
    @Override
    public int addProduct(HeadLine headLineCondition, ImageHolder thumbnail) throws ProductOperationException {
        int result;
        if(headLineCondition != null && thumbnail != null){
            addThumbnail(headLineCondition,thumbnail);
            try {
                result = headLineDao.insertHeadLineByShopId(headLineCondition);
                if(result <= 0){
                    throw new RuntimeException("创建头条失败！");
                }
            }catch (Exception e){
                throw new RuntimeException("创建头条失败！");
            }
        }else {
            result = 0;
        }
        return result;
    }

    /**
     * 添加缩略图
     * @param headLineCondition
     * @param thumbnail
     */
    private void addThumbnail(HeadLine headLineCondition, ImageHolder thumbnail) {
        String dest = PathUtil.getHeadLineImgPath(headLineCondition.getShopId());
        String imageAddr = ImageUtil.generateThumbnail(thumbnail, dest);
        headLineCondition.setLineImg(imageAddr);
    }
}
