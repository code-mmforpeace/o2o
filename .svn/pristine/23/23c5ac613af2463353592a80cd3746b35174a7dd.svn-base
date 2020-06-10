package com.ouver.o2o.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ouver.o2o.dao.ShopCategoryDao;
import com.ouver.o2o.domain.ShopCategory;
import com.ouver.o2o.exceptions.ShopCategoryOperationException;
import com.ouver.o2o.redis.RedisUtils;
import com.ouver.o2o.service.ShopCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {

    @Autowired
    private ShopCategoryDao shopCategoryDao;
    @Autowired
    private RedisUtils.Keys keys;
    @Autowired
    private RedisUtils.strings strings;

    private static Logger logger = LoggerFactory.getLogger(ShopCategoryServiceImpl.class);

    @Override
    @Transactional
    public List<ShopCategory> queryShopCategory(ShopCategory shopCategoryCondition) {
        //定义key
        String key = KEY;
        //定义接受对象
        List<ShopCategory> shopCategoryList = null;
        //定义jackson数据转换操作类
        ObjectMapper objectMapper = new ObjectMapper();
        //拼接出redis的key
        if(shopCategoryCondition == null){
            //若查询条件为空，则查询一级类别,即parentid为空的店铺类别
            key = key +"_allFirstLevel";
        }else if(shopCategoryCondition != null && shopCategoryCondition.getParent() != null && shopCategoryCondition.getParent().getShopCategoryId() != null){
            //若parentid不为空，则列出该parentid下所有子类别
            key = key+"_parent"+shopCategoryCondition.getParent().getShopCategoryId();
        }else if(shopCategoryCondition !=null){
            //列出所有二级店铺类别
            key = key + "_allSecondLevel";
        }
        if(!keys.exists(key)){
            //不存在的话，则先查询后把结果存入redis
            shopCategoryList = shopCategoryDao.queryShopCategory(shopCategoryCondition);
            //将相关实体类集合转换成string，存入redis对应的key中
            String jsonString;
            try {
                jsonString = objectMapper.writeValueAsString(shopCategoryList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new ShopCategoryOperationException(e.getMessage());
            }
            strings.set(key,jsonString);
        }else {
            String s = strings.get(key);
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, ShopCategory.class);

            try {
                shopCategoryList = objectMapper.readValue(s,javaType);
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new ShopCategoryOperationException(e.getMessage());
            }
        }
        return shopCategoryList;
    }
}
