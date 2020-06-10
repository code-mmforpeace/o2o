package com.ouver.o2o.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ouver.o2o.dao.AreaDao;
import com.ouver.o2o.domain.Area;
import com.ouver.o2o.exceptions.AreaOperationException;
import com.ouver.o2o.redis.RedisUtils;
import com.ouver.o2o.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {


    @Autowired
    private AreaDao areaDao;
    @Autowired
    private RedisUtils.Keys redisKeys;
    @Autowired
    private RedisUtils.strings strings;

    private static Logger logger = LoggerFactory.getLogger(AreaServiceImpl.class);

    @Override
    @Transactional
    public List<Area> queryListArea() {
        String key = AREALISTKEY;
        List<Area> areaList = null;
        ObjectMapper objectMapper = new ObjectMapper();
        //判断key是不是存在的
        if(!redisKeys.exists(key)){
            //如果不存在，就去数据库取出对应数据
            areaList = areaDao.queryArea();
            //
            String jsonMapper;
            try {
                jsonMapper = objectMapper.writeValueAsString(areaList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new AreaOperationException(e.getMessage());
            }
            strings.set(key,jsonMapper);
        }else {
            String jsonString = strings.get(key);
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class,Area.class);
            try {
                areaList = objectMapper.readValue(jsonString,javaType);
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new AreaOperationException(e.getMessage());
            }
        }
        return areaList;
    }
}
