package com.livegoods.passport.dao.redis.impl;

import com.livegoods.passport.dao.redis.PassportDao4Redis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class PassportDao4RedisImpl implements PassportDao4Redis {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public String getValidateCode(String key) {
        Object value = redisTemplate.opsForValue().get(key);
        if(value == null){
            return null;
        }
        return value.toString();
    }

    @Override
    public void setValidateCode(String key, String validateCode, long times, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, validateCode, times, unit);
    }

    @Override
    public void removeValidateCode(String key) {
        redisTemplate.delete(key);
    }
}
