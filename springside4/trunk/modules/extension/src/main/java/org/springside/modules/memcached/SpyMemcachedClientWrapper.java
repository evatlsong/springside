/**
 * Copyright (c) 2005-2010 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: SpyMemcachedClient.java 1303 2010-11-19 17:04:05Z calvinxiu $
 */
package org.springside.modules.memcached;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import net.spy.memcached.MemcachedClient;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;

/**
 * 对SpyMemcached Client的二次封装,提供常用的Get/GetBulk/Set/Delete/Incr/Decr函数的封装.
 * 
 * 未提供封装的函数可直接调用getClient()取出Spy的原版MemcachedClient来使用.
 * 
 * @author calvin
 */
public class SpyMemcachedClientWrapper implements DisposableBean {

	private static Logger logger = LoggerFactory.getLogger(SpyMemcachedClientWrapper.class);

	private MemcachedClient memcachedClient;

	private long shutdownTimeout = 1000;

	/**
	 * Get方法, 转换结果类型并屏蔽异常, 仅返回Null.
	 */
	public <T> T get(String key) {
		try {
			return (T) memcachedClient.get(key);
		} catch (RuntimeException e) {
			handleException(e, key);
			return null;
		}
	}

	/**
	 * GetBulk方法, 转换结果类型并屏蔽异常.
	 */
	public <T> Map<String, T> getBulk(Collection<String> keys) {
		try {
			return (Map<String, T>) memcachedClient.getBulk(keys);
		} catch (RuntimeException e) {
			handleException(e, StringUtils.join(keys, ","));
			return null;
		}
	}

	/**
	 * Set方法.
	 */
	public Future<Boolean> set(String key, int expiredTime, Object value) {
		return memcachedClient.set(key, expiredTime, value);
	}

	/**
	 * Delete方法.
	 */
	public Future<Boolean> delete(String key) {
		return memcachedClient.delete(key);
	}

	/**
	 * Incr方法.
	 */
	public long incr(String key, int by, long defaultValue) {
		return memcachedClient.incr(key, by, defaultValue);
	}

	/**
	 * Decr方法.
	 */
	public long decr(String key, int by, long defaultValue) {
		return memcachedClient.decr(key, by, defaultValue);
	}

	/**
	 * 异步Incr方法, 不支持默认值, 若key不存在返回-1.
	 */
	public Future<Long> asyncIncr(String key, int by) {
		return memcachedClient.asyncIncr(key, by);
	}

	/**
	 * 异步Decr方法, 不支持默认值, 若key不存在返回-1.
	 */
	public Future<Long> asyncDecr(String key, int by) {
		return memcachedClient.asyncDecr(key, by);
	}

	private RuntimeException handleException(Exception e, String key) {
		logger.warn("spymemcached client receive an exception with key:" + key, e);
		return new RuntimeException(e);
	}

	@Override
	public void destroy() throws Exception {
		if (memcachedClient != null) {
			memcachedClient.shutdown(shutdownTimeout, TimeUnit.MILLISECONDS);
		}
	}

	public MemcachedClient getMemcachedClient() {
		return memcachedClient;
	}

	public void setMemcachedClient(MemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}

	public void setShutdownTimeout(long shutdownTimeout) {
		this.shutdownTimeout = shutdownTimeout;
	}
}