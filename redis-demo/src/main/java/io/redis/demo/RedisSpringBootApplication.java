package io.redis.demo;

import io.redis.service.SimpleRedisService;

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
		uuid = UUID.randomUUID().toString();
		this.service.saveTable("TABLEY:" + uuid, uuid);
		
		Assert.isTrue(uuid.equals(service.getValue("TABLEY:"+uuid)));
		
		//Sending Multiple
		String[] rows = { 
				"TABLEX:" + UUID.randomUUID().toString() + "," + UUID.randomUUID().toString(),
				"TABLEY:" + UUID.randomUUID().toString() + "," + UUID.randomUUID().toString(),
				"TABLEZ:" + UUID.randomUUID().toString() + "," + UUID.randomUUID().toString()
				};		
		this.service.saveMulti(rows);
		
	}
	
    public static void main(String[] args) {
        SpringApplication.run(RedisSpringBootApplication.class, args).close();
    }
}
