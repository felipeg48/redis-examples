package io.redis.demo;

import io.redis.service.SimpleRedisService;

import java.util.UUID;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;

/**
 * @author felipeg
 *
 */
public class RedisSpringConfigApplication {

	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(RedisSpringConfig.class);
		
		SimpleRedisService service = context.getBean(SimpleRedisService.class);
		String uuid = UUID.randomUUID().toString();
		
		service.saveTable("TABLEX:" + uuid, uuid);
		uuid = UUID.randomUUID().toString();
		service.saveTable("TABLEY:" + uuid, uuid);
		
		
		Assert.isTrue(uuid.equals(service.getValue("TABLEY:"+uuid)));
		
		((ConfigurableApplicationContext)context).close();
	}

}

@Configuration
@ComponentScan("io.redis.service")
class RedisSpringConfig{
	
	@Bean
	public JedisConnectionFactory jedisConnectionFactory(){
		return new JedisConnectionFactory();
	}
	
	@Bean 
	StringRedisTemplate redisTemplate(){
		return new StringRedisTemplate(jedisConnectionFactory());
	}
}


//@Configuration
//@ComponentScan("io.redis.service")
class RedisSentinelSpringConfig{
	
	@Bean
	public JedisConnectionFactory jedisConnectionFactory(){
		RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration() 
				.master("mymaster")
				.sentinel("127.0.0.1", 26379) 
				.sentinel("127.0.0.1", 26380);
		return new JedisConnectionFactory(sentinelConfig);
	}
	
	@Bean 
	StringRedisTemplate redisTemplate(){
		return new StringRedisTemplate(jedisConnectionFactory());
	}
}