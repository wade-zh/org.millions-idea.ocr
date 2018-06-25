package org.millions.idea.ocr.web.common.utility.jedis;

import org.springframework.data.redis.core.script.DefaultRedisScript;
import redis.clients.jedis.Jedis;

public class JedisUtil{

    private RedisConfig redisConfig;

    public RedisConfig getRedisConfig() {
        return redisConfig;
    }

    public void setRedisConfig(RedisConfig redisConfig) {
        this.redisConfig = redisConfig;
        RedisUtil.setRedisConfig(redisConfig);
    }

    public boolean put(String ticket, Integer second, String vcode) {
        Jedis jedis = RedisUtil.getJedis();
        String result = jedis.setex(ticket.toString(), second, vcode);
        return result != null;
    }

    public String get(String key){
        Jedis jedis = RedisUtil.getJedis();
        String result = jedis.get(key);
        return result;
    }

    public boolean incrBy(String key, Integer num){
        Jedis jedis = RedisUtil.getJedis();
        Long result = jedis.incrBy(key,num);
        return result != 0;
    }
}
