package com.lhhs.loan.core.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableRedisHttpSession
public class SessionConfig {
	@Autowired
	private RedisSentinelConfiguration redisSentinelConfiguration;
	@Autowired
	private JedisPoolConfig jedisPoolConfig;
	
	@Value("${redis.pool.database}")
	private int database;
	
	@Bean
	public JedisConnectionFactory connectionFactory(){
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisSentinelConfiguration,jedisPoolConfig);
		//指定存储数据库
		jedisConnectionFactory.setDatabase(database);
		return jedisConnectionFactory;
	}
}
