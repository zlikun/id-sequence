package com.zlikun.tools.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.net.URI;

/**
 * 使用Redis + Lua实现的ID生成器
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018-02-02 17:55
 */
public class JedisIdGenerator {

    private static JedisPool jedisPool ;

    // 配置Jedis数据源
    static {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxIdle(100);
        config.setMaxTotal(100);
        config.setMinIdle(20);
        config.setMaxWaitMillis(3000);
        config.setTestOnCreate(false);
        config.setTestOnBorrow(false);
        config.setTestOnReturn(false);
        config.setTestWhileIdle(true);
        jedisPool = new JedisPool(config, URI.create("redis://192.168.9.205:6379"));
    }

    /**
     * 获取ID，需要传入host，用于分片
     * @param host
     * @return
     */
    public static Long get(String host) {
        if (host == null) {
            throw new IllegalArgumentException("host is required");
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return create(jedis, host);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    /**
     * 生成ID逻辑
     * @param jedis
     * @param host
     * @return
     * @throws Exception
     */
    private static Long create(Jedis jedis, String host) throws Exception {
        // 参考：https://raw.githubusercontent.com/hengyunabc/redis-id-generator/master/redis-script-node1.lua

        // 针对 host 取一个 hash 值(要求至少在一定量，如1024内不能碰撞)

        // 生成一个毫秒数(如果不方便取秒好了)

        // 生成一个自增序列

        // 组装成一个ID

        return null;
    }

}
