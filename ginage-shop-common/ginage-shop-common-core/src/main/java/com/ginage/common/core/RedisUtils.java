
package com.ginage.common.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ser.std.StringSerializer;

import lombok.extern.slf4j.Slf4j;

/**
 * @date:2020年4月2日
 * @description:
 * @Copyright: ginage.com
 *
 */
@Component
@Slf4j
public class RedisUtils {
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private StringRedisTemplate stringRedisTemplateForTransaction;
	
	public void set(String key, String value, long timeout) {
		stringRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
	}

	public String get(String key) {
		// TODO Auto-generated method stub
		return stringRedisTemplate.opsForValue().get(key);
	}
	public Object get(byte[] key) {
		// TODO Auto-generated method stub
		return redisTemplate.opsForValue().get(key);
	}
	public void setObject(String key,Object object) {
		redisTemplate.opsForValue().set(key,object);
	}
	public Object getObject(String key) {
		// TODO Auto-generated method stub
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new RedisSerializerUtils());
		Object object = redisTemplate.opsForValue().get(key);
		return object;
	}
	public String hget(String key,String field) {
		// TODO Auto-generated method stub
		return (String) stringRedisTemplate.opsForHash().get(key,field);
	}
	public boolean del(String key) {
		Boolean delete = stringRedisTemplate.delete(key);
		
		return delete;
		
	}
	
	
	public void mutil() {
		stringRedisTemplateForTransaction.setEnableTransactionSupport(true);
		stringRedisTemplateForTransaction.multi();
	}
	public void exec() {
		stringRedisTemplateForTransaction.exec();
	}
	
	public void discard() {
		stringRedisTemplateForTransaction.discard();
	}
	
	public void setForTransaction(String key, String value, long timeout,TimeUnit timeUnit) {
		stringRedisTemplateForTransaction.opsForValue().set(key, value, timeout, timeUnit);
	}
	
	
	
	 // ------------------------ serialize and unserialize ------------------------

    /**
     * 将对象-->byte[] (由于jedis中不支持直接存储object所以转换成byte[]存入)
     *
     * @param object
     * @return
     */
    public byte[] serialize(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            // 序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                oos.close();
                baos.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        return null;
    }

    /**
     * 将byte[] -->Object
     *
     * @param bytes
     * @return
     */
    public Object unserialize(byte[] bytes) {
        ByteArrayInputStream bais = null;
        try {
            // 反序列化
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                bais.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        return null;
    }
	
	
}
