package io.redis.demo;

import io.redis.service.SimpleRedisService;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.Assert;

@SpringBootApplication
public class RedisSpringBootApplication implements CommandLineRunner {
	
	@Autowired
	private SimpleRedisService service;
	
	@Override
	public void run(String... arg0) throws Exception {
		String uuid = UUID.randomUUID().toString();
		this.service.saveTable("TABLEX:" + uuid, uuid);
		this.service.deleteValue("TABLEX:" + uuid);
		
		uuid = UUID.randomUUID().toString();
		this.service.saveTable("TABLEY:" + uuid, uuid);
		Assert.isTrue(uuid.equals(service.getValue("TABLEY:"+uuid)));
		this.service.deleteValue("TABLEY:" + uuid);
		
		//Sending Multiple
		String[] rows = { 
				"TABLEX:X" + "," + UUID.randomUUID().toString(),
				"TABLEY:Y" + "," + UUID.randomUUID().toString(),
				"TABLEZ:Z" + "," + UUID.randomUUID().toString()
				};		
		this.service.saveMulti(rows);
		
		//Getting Get/Delete Multiple
		String[] keys = { "TABLEX:X", "TABLEY:Y", "TABLEZ:Z"};
		Map<String,String> map = this.service.getAndDeleteMulti(keys);
		
		Assert.isTrue(map!=null);
		Assert.isTrue(map.containsKey("TABLEY:Y"));
		Assert.isTrue(map.get("TABLEZ:Z")!=null,"The Value for Key: 'TABLEZ:Z' must exists");
		Assert.isTrue(map.size() == 3,"MUst have 3 key/value pairs");
		
		//Del Multiple
		//this.service.deleteMulti(keys);
		
	}
	
    public static void main(String[] args) {
        SpringApplication.run(RedisSpringBootApplication.class, args).close();
    }
}
