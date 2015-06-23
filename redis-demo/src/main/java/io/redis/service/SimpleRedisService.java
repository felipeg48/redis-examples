package io.redis.service;

import io.redis.exception.RedisNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
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
	
	public void deleteValue(String key){
		ValueOperations<String, String> ops = this.redisTemplate.opsForValue();
		ops.getOperations().delete(key);
	}
	
	/**
	 * Example of executing pipelined (Multiple instructions in a row)
	 * @param rows an array of Strings (key/value) Example: "table:uid,myvalue"
	 */
	public void saveMulti(String ...rows){
		this.redisTemplate.executePipelined(new RedisCallback<Object>(){

			@Override
			public Object doInRedis(RedisConnection connection)
					throws DataAccessException {
				StringRedisConnection stringRedisConn = (StringRedisConnection)connection;
				String[] data = null;
				for(String element: rows){
					data = element.split(",");
					stringRedisConn.set(data[0], data[1]);
				}
				return null;
			}
			
		});
	}
}
