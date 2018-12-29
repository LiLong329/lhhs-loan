/**
 * 
 */
package com.lhhs.loan.core.redis;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

/**
 * @author zhangpenghong
 *
 */
@Configuration
@EnableCaching
@EnableAutoConfiguration
public class CacheConfig extends CachingConfigurerSupport {
	Logger logger = LoggerFactory.getLogger(CacheConfig.class);

	public CacheConfig() {
	}

	public Set<String> getSentinels(String sentinels){
		 return new HashSet<String>(Arrays.asList(sentinels.split(",")));
	}
	
	@Bean
	@ConfigurationProperties(prefix="redis.config")
	public JedisPoolConfig getJedisPoolConfig(){
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        return jedisPoolConfig;
	}
	
	@Bean
	public RedisSentinelConfiguration getRedisSentinelConfiguration(@Value("${redis.pool.masterName}") String masterName,@Value("${redis.pool.sentinels}") String sentinels){
		return new RedisSentinelConfiguration(masterName,getSentinels(sentinels));
	}
	
	@Bean
    public JedisSentinelPool redisPoolFactory(@Value("${redis.pool.masterName}") String masterName,
    		@Value("${redis.pool.database}") int database,
    		@Value("${redis.pool.password}") String password,
    		@Value("${redis.pool.timeout}") int timeout,
    		@Value("${redis.pool.sentinels}") String sentinels) {
        JedisSentinelPool jedisPool = null;
        
        if(password != null && !"".equals(password)){
        	jedisPool = new JedisSentinelPool(masterName,getSentinels(sentinels),getJedisPoolConfig(),timeout, password,database);
        }else{
        	jedisPool = new JedisSentinelPool(masterName,getSentinels(sentinels),getJedisPoolConfig(),timeout,null,database);
        }
        
        return jedisPool;
    }
}
