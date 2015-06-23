package io.redis.service;

import io.redis.exception.RedisNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class SimpleRedisService {

	@Autowired
	private StringRedisTemplate redisTemplate;
	
	public void saveTable(String key,String value){
		ValueOperations<String, String> ops = this.redisTemplate.opsForValue();
		if (!this.redisTemplate.hasKey(key)) {
			ops.set(key, value);
		}
	}
	
	public String getValue(String key) throws RedisNotFoundException{
		ValueOperations<String, String> ops = this.redisTemplate.opsForValue();
		if(this.redisTemplate.hasKey(key))
			return ops.get(key);
		
		throw new RedisNotFoundException("Key: " + key + " not found.");
	}
}
