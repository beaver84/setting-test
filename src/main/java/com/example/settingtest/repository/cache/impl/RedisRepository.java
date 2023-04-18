package com.example.settingtest.repository.cache.impl;

import com.example.settingtest.repository.cache.CacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Map;
import java.util.Objects;

@Repository
public class RedisRepository implements CacheRepository {

    private RedisTemplate redisTemplate;

    @Autowired
    public RedisRepository(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public String getValue(String key) {
        Object data = redisTemplate.opsForValue().get(key);

        if (Objects.nonNull(data)) {
            return data.toString();
        } else {
            return null;
        }
    }

    @Override
    public void setValue(String key, String value, int minutes) {
        redisTemplate.opsForValue().set(key, value, Duration.ofMinutes(minutes));
    }

    @Override
    public void setValue(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void setValue(String key, String value, Duration duration) {
        redisTemplate.opsForValue().set(key, value, duration);
    }

    @Override
    public void setValue(byte[] key, byte[] value) {
        setValue(new String(key), new String(value));
    }

    @Override
    public void setValue(byte[] key, byte[] value, Duration duration) {
        setValue(new String(key), new String(value), duration);
    }

    @Override
    public String getValue(byte[] key) {
        return getValue(new String(key));
    }

    @Override
    public void remove(byte[] key) {
        remove(new String(key));
    }

    @Override
    public void remove(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public Long setValueHashes(String key, String subKey, String value) {
        redisTemplate.opsForHash().put(key, subKey, value);
        return 1L;
    }

    @Override
    public Long setValueHashes(String key, String subKey, String value, Duration duration) {
        redisTemplate.opsForHash().put(key, subKey, value);
        redisTemplate.expire(key, duration);
        return 1L;
    }

    @Override
    public String getValueHashes(String key, String subKey) {
        Object hashValue = redisTemplate.opsForHash().get(key, subKey);

        if(Objects.nonNull(hashValue)) {
        	return hashValue.toString();
        } else {
        	return null;
        }
    }

    @Override
    public Map<String, String> getAllValueHashes(String key) {
        Map<String, String> result = redisTemplate.opsForHash().entries(key);
        return result;
    }

    @Override
    public Long removeHashes(String key, String subKey) {
        redisTemplate.opsForHash().delete(key, subKey);
        return 1L;
    }

    @Override
    public Long countHashes(String key) {
        return getAllValueHashes(key).keySet().stream().count();
    }
}
