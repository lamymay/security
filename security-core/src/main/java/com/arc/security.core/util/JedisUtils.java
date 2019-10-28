package com.arc.security.core.util;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author 叶超
 * @since 2019/6/10 21:23
 */
@Slf4j
public class JedisUtils {
    private static Jedis getJedis() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(20);
        config.setMaxIdle(10);
        config.setMinIdle(2);
        config.setBlockWhenExhausted(true); //资源耗尽时是否阻塞
        config.setTestOnBorrow(false);
        config.setTestOnReturn(false);
        JedisPool pool = new JedisPool(config, "127.0.0.1", 6379);
        Jedis jedis = pool.getResource();
        log.debug("//////////////////////////////////////////////////");
        log.debug("结果={}", jedis);
        log.debug("//////////////////////////////////////////////////");
        return jedis;
    }
}
