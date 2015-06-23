/**
 * 
 */
package io.redis.demo;

import java.util.UUID;

import io.redis.service.SimpleRedisService;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

/**
 * @author felipeg
 *
 */
public class RedisSpringApplication {

	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("/META-INF/config/spring/redis-context.xml");
		
		SimpleRedisService service = context.getBean(SimpleRedisService.class);
		String uuid = UUID.randomUUID().toString();
		
		service.saveTable("TABLEX:" + uuid, uuid);
		uuid = UUID.randomUUID().toString();
		service.saveTable("TABLEY:" + uuid, uuid);
		
		Assert.isTrue(uuid.equals(service.getValue("TABLEY:"+uuid)));
		
		((ConfigurableApplicationContext)context).close();
	}

}
