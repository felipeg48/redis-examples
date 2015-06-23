package io.redis.demo;

import io.redis.exception.RedisNotFoundException;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.util.Assert;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;


/**
 * @author felipeg
 *
 */
public class RedisJedisApplication {

	Jedis jedis = new Jedis("localhost");
	
	public static void main(String[] args) {
		RedisJedisApplication app = new RedisJedisApplication();
		String uuid = UUID.randomUUID().toString();
		
		app.saveTable("TABLEX:" + uuid, uuid);
		uuid = UUID.randomUUID().toString();
		app.saveTable("TABLEY:" + uuid, uuid);
		
		
		Assert.isTrue(uuid.equals(app.getValue("TABLEY:"+uuid)));	
	}

	public void saveTable(String key,String value){
		jedis.set(key,value);
	}
	
	public String getValue(String key) throws RedisNotFoundException{
		String value = jedis.get(key);
		if(null!=value)
			return value;
		throw new RedisNotFoundException("Key: " + key + " not found.");
	}
}

class RedisJedisSentinel {
	
	private JedisSentinelPool pool = null;
	private Set<String> sentinels = new HashSet<String>();
	private Jedis jedis= null;
	
	public RedisJedisSentinel(){
		this.sentinels.add("127.0.0.1:26379");
		this.sentinels.add("127.0.0.1:26380");
		
		this.pool = new JedisSentinelPool("mymaster", this.sentinels);
		this.jedis = this.pool.getResource();
	}
	
	public Jedis getResource(){
		return this.jedis;
	}
	
	public void close(){
		if(null!= this.jedis)
			jedis.close();
		
		if(null!=this.pool)
			this.pool.destroy();
	}
}


class RedisJedisCluster {
	
	private Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
	private JedisCluster jc = null;
	public RedisJedisCluster(){
		this.jedisClusterNodes.add(new HostAndPort("127.0.0.1", 6379));
		this.jc = new JedisCluster(jedisClusterNodes);
	}
	
	public JedisCluster getResource(){
		return this.jc;
	}
}