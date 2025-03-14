package icoding.springboot.cardetect.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {
    private static  final JedisPool jedisPool;

    static {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(5);
        jedisPool = new JedisPool(poolConfig, "localhost", 6379);
    }

    public static Jedis getJedis() {
        return jedisPool.getResource();
    }

}