package com.ouver.o2o.service.impl;

import com.ouver.o2o.redis.RedisUtils;
import com.ouver.o2o.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisUtils.Keys keys;

    /**
     * 更新的时候需要对旧信息进行删除
     * @param key
     */
    @Override
    public void removeFromRedis(String key) {
        Set<String> keySet = keys.keys(key + "*");
        for (String key1: keySet) {
            keys.del(key1);
        }
    }
}
