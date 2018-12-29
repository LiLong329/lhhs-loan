package com.lhhs.loan.common.jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lhhs.loan.common.conversion.KyroSerializable;
import com.lhhs.loan.common.utils.StrUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

@Component
@SuppressWarnings("all")
public class JedisComponent {
	private static Logger logger = LoggerFactory.getLogger(JedisComponent.class);

	@Autowired
	private JedisSentinelPool jedisSentinelPool;

	public long expire(String key, int seconds) {
		if ((key == null) || (key.equals(""))) {
			return 0L;
		}
		Jedis jedis = null;
		try {
			jedis = this.jedisSentinelPool.getResource();
			return jedis.expire(key, seconds).longValue();
		} catch (Exception ex) {
			logger.error("EXPIRE error[key=" + key + " seconds=" + seconds + "]" + ex.getMessage(), ex);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		return 0L;
	}

	public long expireAt(String key, int unixTimestamp) {
		if ((key == null) || (key.equals(""))) {
			return 0L;
		}
		Jedis jedis = null;
		try {
			jedis = this.jedisSentinelPool.getResource();
			return jedis.expireAt(key, unixTimestamp).longValue();
		} catch (Exception ex) {
			logger.error("EXPIRE error[key=" + key + " unixTimestamp=" + unixTimestamp + "]" + ex.getMessage(), ex);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		return 0L;
	}

	public String trimList(String key, long start, long end) {
		if ((key == null) || (key.equals(""))) {
			return "-";
		}
		Jedis jedis = null;
		try {
			jedis = this.jedisSentinelPool.getResource();
			return jedis.ltrim(key, start, end);
		} catch (Exception ex) {
			logger.error("LTRIM 出错[key=" + key + " start=" + start + " end=" + end + "]" + ex.getMessage(), ex);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		return "-";
	}

	public long countSet(String key) {
		if (key == null) {
			return 0L;
		}
		Jedis jedis = null;
		try {
			jedis = this.jedisSentinelPool.getResource();
			return jedis.scard(key).longValue();
		} catch (Exception ex) {
			logger.error("countSet error.", ex);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		return 0L;
	}

	public boolean addSet(String key, int seconds, String[] value) {
		boolean result = addSet(key, value);
		if (result) {
			long i = expire(key, seconds);
			return i == 1L;
		}
		return false;
	}

	public boolean addSet(String key, String[] value) {
		if ((key == null) || (value == null)) {
			return false;
		}
		Jedis jedis = null;
		try {
			jedis = this.jedisSentinelPool.getResource();
			jedis.sadd(key, value);
			return true;
		} catch (Exception ex) {
			logger.error("setList error.", ex);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		return false;
	}

	public boolean containsInSet(String key, String value) {
		if ((key == null) || (value == null)) {
			return false;
		}
		Jedis jedis = null;
		try {
			jedis = this.jedisSentinelPool.getResource();
			return jedis.sismember(key, value).booleanValue();
		} catch (Exception ex) {
			logger.error("setList error.", ex);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		return false;
	}

	public Set<String> getSet(String key) {
		Jedis jedis = null;
		try {
			jedis = this.jedisSentinelPool.getResource();
			return jedis.smembers(key);
		} catch (Exception ex) {
			logger.error("getList error.", ex);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		return null;
	}

	public boolean removeSetValue(String key, String[] value) {
		Jedis jedis = null;
		try {
			jedis = this.jedisSentinelPool.getResource();
			jedis.srem(key, value);
			return true;
		} catch (Exception ex) {
			logger.error("getList error.", ex);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		return false;
	}

	public int removeListValue(String key, List<String> values) {
		return removeListValue(key, 1L, values);
	}

	public int removeListValue(String key, long count, List<String> values) {
		int result = 0;
		if ((values != null) && (values.size() > 0)) {
			for (String value : values) {
				if (removeListValue(key, count, value)) {
					result++;
				}
			}
		}
		return result;
	}

	public boolean removeListValue(String key, long count, String value) {
		Jedis jedis = null;
		try {
			jedis = this.jedisSentinelPool.getResource();
			jedis.lrem(key, count, value);
			return true;
		} catch (Exception ex) {
			logger.error("getList error.", ex);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		return false;
	}

	public List<String> rangeList(String key, long start, long end) {
		if ((key == null) || (key.equals(""))) {
			return null;
		}
		Jedis jedis = null;
		try {
			jedis = this.jedisSentinelPool.getResource();
			return jedis.lrange(key, start, end);
		} catch (Exception ex) {
			logger.error("rangeList 出错[key=" + key + " start=" + start + " end=" + end + "]" + ex.getMessage(), ex);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		return null;
	}

	public long countList(String key) {
		if (key == null) {
			return 0L;
		}
		Jedis jedis = null;
		try {
			jedis = this.jedisSentinelPool.getResource();
			return jedis.llen(key).longValue();
		} catch (Exception ex) {
			logger.error("countList error.", ex);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		return 0L;
	}

	public boolean addList(String key, int seconds, String[] value) {
		boolean result = addList(key, value);
		if (result) {
			long i = expire(key, seconds);
			return i == 1L;
		}
		return false;
	}

	public boolean addList(String key, String[] value) {
		if ((key == null) || (value == null)) {
			return false;
		}
		Jedis jedis = null;
		try {
			jedis = this.jedisSentinelPool.getResource();
			jedis.lpush(key, value);
			return true;
		} catch (Exception ex) {
			logger.error("setList error.", ex);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		return false;
	}

	public boolean addList(String key, List<String> list) {
		if ((key == null) || (list == null) || (list.size() == 0)) {
			return false;
		}
		for (String value : list) {
			addList(key, new String[] { value });
		}
		return true;
	}

	public List<String> getList(String key) {
		Jedis jedis = null;
		try {
			jedis = this.jedisSentinelPool.getResource();
			return jedis.lrange(key, 0L, -1L);
		} catch (Exception ex) {
			logger.error("getList error.", ex);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		return null;
	}

	public boolean setHSet(String domain, String key, String value) {
		if (value == null)
			return false;
		Jedis jedis = null;
		try {
			jedis = this.jedisSentinelPool.getResource();
			jedis.hset(domain, key, value);
			return true;
		} catch (Exception ex) {
			logger.error("setHSet error.", ex);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		return false;
	}

	public String getHSet(String domain, String key) {
		Jedis jedis = null;
		try {
			jedis = this.jedisSentinelPool.getResource();
			return jedis.hget(domain, key);
		} catch (Exception ex) {
			logger.error("getHSet error.", ex);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		return null;
	}

	public long delHSet(String domain, String key) {
		Jedis jedis = null;
		long count = 0L;
		try {
			jedis = this.jedisSentinelPool.getResource();
			count = jedis.hdel(domain, new String[] { key }).longValue();
		} catch (Exception ex) {
			logger.error("delHSet error.", ex);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		return count;
	}

	public long delHSet(String domain, String[] key) {
		Jedis jedis = null;
		long count = 0L;
		try {
			jedis = this.jedisSentinelPool.getResource();
			count = jedis.hdel(domain, key).longValue();
		} catch (Exception ex) {
			logger.error("delHSet error.", ex);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		return count;
	}

	public boolean existsHSet(String domain, String key) {
		Jedis jedis = null;
		boolean isExist = false;
		try {
			jedis = this.jedisSentinelPool.getResource();
			isExist = jedis.hexists(domain, key).booleanValue();
		} catch (Exception ex) {
			logger.error("existsHSet error.", ex);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		return isExist;
	}

	public List<Map.Entry<String, String>> scanHSet(String domain, String match) {
		Jedis jedis = null;
		try {
			int cursor = 0;
			jedis = this.jedisSentinelPool.getResource();
			ScanParams scanParams = new ScanParams();
			scanParams.match(match);

			List list = new ArrayList();
			do {
				ScanResult scanResult = jedis.hscan(domain, String.valueOf(cursor), scanParams);
				list.addAll(scanResult.getResult());
				cursor = Integer.parseInt(scanResult.getStringCursor());
			} while (cursor > 0);
			return list;
		} catch (Exception ex) {
			logger.error("scanHSet error.", ex);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		return null;
	}

	public List<String> hvals(String domain) {
		Jedis jedis = null;
		List retList = null;
		try {
			jedis = this.jedisSentinelPool.getResource();
			retList = jedis.hvals(domain);
		} catch (Exception ex) {
			logger.error("hvals error.", ex);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		return retList;
	}

	public Set<String> hkeys(String domain) {
		Jedis jedis = null;
		Set retList = null;
		try {
			jedis = this.jedisSentinelPool.getResource();
			retList = jedis.hkeys(domain);
		} catch (Exception ex) {
			logger.error("hkeys error.", ex);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		return retList;
	}

	public long lenHset(String domain) {
		Jedis jedis = null;
		long retList = 0L;
		try {
			jedis = this.jedisSentinelPool.getResource();
			retList = jedis.hlen(domain).longValue();
		} catch (Exception ex) {
			logger.error("hkeys error.", ex);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		return retList;
	}

	public boolean setSortedSet(String key, long score, String value) {
		Jedis jedis = null;
		try {
			jedis = this.jedisSentinelPool.getResource();
			jedis.zadd(key, score, value);
			return true;
		} catch (Exception ex) {
			logger.error("setSortedSet error.", ex);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		return false;
	}

	public Set<String> getSoredSet(String key, long startScore, long endScore, boolean orderByDesc) {
		Jedis jedis = null;
		try {
			jedis = this.jedisSentinelPool.getResource();
			Set localSet;
			if (orderByDesc) {
				return jedis.zrevrangeByScore(key, endScore, startScore);
			}
			return jedis.zrangeByScore(key, startScore, endScore);
		} catch (Exception ex) {
			logger.error("getSoredSet error.", ex);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		return null;
	}

	public long countSoredSet(String key, long startScore, long endScore) {
		Jedis jedis = null;
		try {
			jedis = this.jedisSentinelPool.getResource();
			Long count = jedis.zcount(key, startScore, endScore);
			return count == null ? 0L : count.longValue();
		} catch (Exception ex) {
			logger.error("countSoredSet error.", ex);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		return 0L;
	}

	public boolean delSortedSet(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = this.jedisSentinelPool.getResource();
			long count = jedis.zrem(key, new String[] { value }).longValue();
			return count > 0L;
		} catch (Exception ex) {
			logger.error("delSortedSet error.", ex);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		return false;
	}

	public Set<String> getSoredSetByRange(String key, int startRange, int endRange, boolean orderByDesc) {
		Jedis jedis = null;
		try {
			jedis = this.jedisSentinelPool.getResource();
			Set localSet;
			if (orderByDesc) {
				return jedis.zrevrange(key, startRange, endRange);
			}
			return jedis.zrange(key, startRange, endRange);
		} catch (Exception ex) {
			logger.error("getSoredSetByRange error.", ex);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		return null;
	}

	public Double getScore(String key, String member) {
		Jedis jedis = null;
		try {
			jedis = this.jedisSentinelPool.getResource();
			return jedis.zscore(key, member);
		} catch (Exception ex) {
			logger.error("getSoredSet error.", ex);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		return null;
	}

	public boolean set(String key, String value, int second) {
		Jedis jedis = null;
		try {
			jedis = this.jedisSentinelPool.getResource();
			jedis.setex(key, second, value);
			return true;
		} catch (Exception ex) {
			logger.error("set error.", ex);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		return false;
	}

	public boolean set(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = this.jedisSentinelPool.getResource();
			jedis.set(key, value);
			return true;
		} catch (Exception ex) {
			logger.error("set error.", ex);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		return false;
	}

	public String get(String key, String defaultValue) {
		Jedis jedis = null;
		try {
			jedis = this.jedisSentinelPool.getResource();
			return jedis.get(key) == null ? defaultValue : jedis.get(key);
		} catch (Exception ex) {
			logger.error("get error.", ex);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		return defaultValue;
	}

	public boolean del(String key) {
		Jedis jedis = null;
		try {
			jedis = this.jedisSentinelPool.getResource();
			jedis.del(key);
			return true;
		} catch (Exception ex) {
			logger.error("del error.", ex);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		return false;
	}

	public long incr(String key) {
		Jedis jedis = null;
		try {
			jedis = this.jedisSentinelPool.getResource();
			return jedis.incr(key).longValue();
		} catch (Exception ex) {
			logger.error("incr error.", ex);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		return 0L;
	}

	public long decr(String key) {
		Jedis jedis = null;
		try {
			jedis = this.jedisSentinelPool.getResource();
			return jedis.decr(key).longValue();
		} catch (Exception ex) {
			logger.error("incr error.", ex);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		return 0L;
	}

	public boolean setValue(String key, String value, int seconds) {
		Jedis jedis = null;
		try {
			jedis = this.jedisSentinelPool.getResource();
			jedis.set(key, value);
			expire(key, seconds);
			return true;
		} catch (Exception e) {
			logger.error("incr error.", e);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		return false;
	}

	public String getValue(String key) {
		Jedis jedis = null;
		try {
			jedis = this.jedisSentinelPool.getResource();
			String val = jedis.get(key);
			return val;
		} catch (Exception e) {
			logger.error("incr error.", e);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		return null;
	}

	/**
	 * put Object
	 * 
	 * @param key
	 * @param value
	 */
	public void putObject(Object key, Object value) {
		Jedis jedis = null;
		if (logger.isDebugEnabled())
			logger.debug("putObject: " + key + " ---|--- " + value);
		if (null == key || null == value)
			return;
		try {
			jedis = jedisSentinelPool.getResource();
			jedis.set(KyroSerializable.obj2Str(key), KyroSerializable.obj2Str(value));
		} catch (Exception e) {
			logger.error("putObject error", e);
		} finally {
			if (jedis != null)
				jedis.close();
		}
	}

	/**
	 * get Object
	 * 
	 * @param key
	 * @return
	 */
	public Object getObject(Object key) {
		if (null == key)
			return key;
		Object o = null;
		String value = null;
		Jedis jedis = null;
		if (logger.isDebugEnabled())
			logger.debug("getObject(" + key + ")");
		try {
			jedis = jedisSentinelPool.getResource();
			value = jedis.get(KyroSerializable.obj2Str(key));
			o = KyroSerializable.str2Obj(value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("getObject(" + key + ") error", e);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		if (logger.isDebugEnabled())
			logger.debug("getObject(" + key + ")---|--- " + o);
		return o;
	}

	/**
	 * removeObject
	 * 
	 * @param key
	 * @return
	 */
	public Object removeObject(Object key) {
		Jedis jedis = null;
		if (null == key)
			return key;
		String value = "";
		try {
			jedis = jedisSentinelPool.getResource();
			value = jedis.get(KyroSerializable.obj2Str(key));
			if (!StrUtils.isNullOrEmpty(value)) {
				jedis.del(KyroSerializable.obj2Str(key));
			}
		} catch (Exception e) {
			logger.error("removeObject(" + key + ") error", e);
			return null;
		} finally {
			if (jedis != null)
				jedis.close();
		}
		return value;
	}
	
	/**
     * 订单加锁
     * @param orderToken   redis key值  ("loan_lock_"+orderNo)
     * @param userToken 用户标识("loan_lock_id_"+empId)
     * @param expire	超时时间
     * @return
     */
    public boolean lockOrder(String orderToken,String userToken,int expire) {
    	Jedis jedis = null;
    	String oldOrderToken = null;
	    try {
	      jedis = jedisSentinelPool.getResource();
	      long time = System.currentTimeMillis() + expire*1000;
	      long status = jedis.setnx(orderToken, time+";"+userToken);
	      if(status == 1) {
	    	  oldOrderToken = jedis.getSet(userToken, orderToken);
	    	  if(oldOrderToken != null && !"".equals(oldOrderToken) && !oldOrderToken.equals(orderToken)){
	    		  this.unLockOrder(oldOrderToken,userToken);
	    	  }
	          return true;
	      }else{
	    	 String value = jedis.get(orderToken);
	    	 if(value != null && !"".equals(value)){
	    		 long oldTime = Long.parseLong(value.split(";")[0]);
	    		 //超时判断
	    		 if(oldTime < System.currentTimeMillis()){
	    			 long newExpireTime = System.currentTimeMillis() + expire*1000;
	    			 String oldValue = jedis.getSet(orderToken, newExpireTime+";"+userToken);
	    			 if(oldValue != null && !"".equals(oldValue)){
	    				 long oldValueTime = Long.parseLong(oldValue.split(";")[0]);
	    				 if(oldValueTime == oldTime){
	    					 oldOrderToken = jedis.getSet(userToken, orderToken);
	    					 //同一用户同一单子因为超时二次获得锁时不用释放
	    					 if(oldOrderToken != null && !"".equals(oldOrderToken) && !oldOrderToken.equals(orderToken)){
	    			    		  this.unLockOrder(oldOrderToken,userToken);
	    			    	  }
	    			          return true;
	    				 }
	    			 }
	    		 }else{
	    			//未超时  判断是否是同一用户操作
		    		 if(userToken.equals(value.split(";")[1])){
		    			 return true;
		    		 }
	    		 }
	    	 }
	      }
	    }catch (Exception ex) {
		      logger.error("加锁失败：" + orderToken);
		} finally {
		  	if (jedis != null)
		  	   jedis.close();
		}
        return false;
    }
    
    /**
     * 释放订单锁
     * @param orderToken  jedis的key
     * @return
     */
    public boolean unLockOrder(String orderToken,String userToken){
    	Jedis jedis = null;
    	try{
    		jedis = jedisSentinelPool.getResource();
    		String value = jedis.get(orderToken);
    		if(value != null && !"".equals(value)){
    			long oldTime = Long.parseLong(value.split(";")[0]);
    			if(oldTime >= System.currentTimeMillis() && userToken.equals(value.split(";")[1])){
    				jedis.del(orderToken);
    				return true;
    			}
    		}
    	}catch(Exception e){
    		  logger.error("解锁失败：" + orderToken);
    	}finally{
    		if(jedis != null){
    			jedis.close();
    		}
    	}
    	return false;
    }
    /**
     * 订单审核时是否超时或审核操作权限判断
     * @param orderToken  jedis的key
     * @param userToken
     * @return
     * 		false  超时/没有审核权限
     * 		true   未超时
     */
    public boolean isOvertime(String orderToken,String userToken){
    	Jedis jedis = null;
    	try{
    		jedis = this.jedisSentinelPool.getResource();
    		String value = jedis.get(orderToken);
    		if(value != null && !"".equals(value)){
    			String temp[] = value.split(";");
    			if(temp[1].equals(userToken) && System.currentTimeMillis() <= Long.parseLong(temp[0])){
    				return true;
    			}
    		}
    	}catch(Exception e){
    		logger.error("超时验证失败"+orderToken);
    	}finally{
    		if(jedis != null){
    			jedis.close();
    		}
    	}
    	return false;
    }
}

