package devCamp.WebApp.configurations;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.cache.CacheManager;

import devCamp.WebApp.properties.ApplicationProperties;
import devCamp.WebApp.properties.CacheProperties;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableConfigurationProperties(value = {
        ApplicationProperties.class,
})
public class CacheConfig {
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationConfig.class);
    
    @Autowired
    private CacheProperties cacheProperties;
    
    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(5);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        JedisConnectionFactory ob = new JedisConnectionFactory(poolConfig);
        ob.setUsePool(true);
        String redishost = cacheProperties.getRedisHost(); //System.getenv("REDISCACHE_HOSTNAME");
        LOG.info("REDISCACHE_HOSTNAME={}", redishost);
        ob.setHostName(redishost);
        String redisport = cacheProperties.getRedisPort().toString(); //System.getenv("REDISCACHE_PORT");
        LOG.info("REDISCACHE_PORT= {}", redisport);
        try {
            ob.setPort(Integer.parseInt(  redisport));
        } catch (NumberFormatException e1) {
            // if the port is not in the ENV, use the default
            ob.setPort(6379);
        }
        String rediskey = cacheProperties.getPrimaryKey(); //System.getenv("REDISCACHE_PRIMARY_KEY");
        LOG.info("REDISCACHE_PRIMARY_KEY= {}", rediskey);
        ob.setPassword(rediskey);
        ob.afterPropertiesSet();
        RedisTemplate<Object,Object> tmp = new RedisTemplate<>();
        tmp.setConnectionFactory(ob);

        //make sure redis connection is working
        try {
            String msg = tmp.getConnectionFactory().getConnection().ping();
            LOG.info("redis ping response="+msg);
            //clear the cache before use
            tmp.getConnectionFactory().getConnection().flushAll();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ob;
    }

    @Bean(name="redisTemplate")
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory cf) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
        redisTemplate.setConnectionFactory(cf);
        return redisTemplate;
    }

    @Bean
    public CacheManager cacheManager() {
        RedisCacheManager manager = new RedisCacheManager(redisTemplate(redisConnectionFactory()));
        manager.setDefaultExpiration(300);
        return manager;
    }
    
}
