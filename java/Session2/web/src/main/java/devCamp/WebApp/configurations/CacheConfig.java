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
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.hateoas.core.DefaultRelProvider;
import org.springframework.hateoas.hal.Jackson2HalModule;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.type.TypeFactory;

import org.springframework.cache.CacheManager;

import devCamp.WebApp.properties.ApplicationProperties;
import devCamp.WebApp.properties.CacheProperties;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableConfigurationProperties(value = {
        ApplicationProperties.class,
        CacheProperties.class
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

//    @Bean(name="redisTemplate")
//    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory cf) {
//        RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
//        redisTemplate.setConnectionFactory(cf);
//        return redisTemplate;
//    }
//
//
//    @Bean
//    public CacheManager cacheManager() {
//    	RedisTemplate ops = redisTemplate(redisConnectionFactory());
//        RedisCacheManager manager = new RedisCacheManager(ops);
//        manager.setDefaultExpiration(3);
// 
//        return manager;
//    }    
    
    
//  @Bean(name="redisTemplate")
//  public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory cf) {
//      RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
//      redisTemplate.setConnectionFactory(cf);
//      redisTemplate.setValueSerializer(jsonRedisSerializer(Object.class));
//      return redisTemplate;
//  }
//    

    
    
    
    
    @Bean(name="redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory cf) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(cf);
        redisTemplate.setKeySerializer(null);
        redisTemplate.setHashKeySerializer(null);
        redisTemplate.setValueSerializer(jsonRedisSerializer(Object.class));

        return redisTemplate;    	
    }

    private Jackson2JsonRedisSerializer jsonRedisSerializer(Class type)
    {
        return jsonRedisSerializer(TypeFactory.defaultInstance().constructType(type));
    }
    
    private Jackson2JsonRedisSerializer jsonRedisSerializer(JavaType javaType)
    {
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(javaType);
        ObjectMapper mapper = new ObjectMapper();
        mapper.enableDefaultTyping();
    	mapper.enableDefaultTyping(DefaultTyping.NON_FINAL, As.PROPERTY);

    	mapper.findAndRegisterModules();
        mapper.registerModule(new Jackson2HalModule());
        mapper.setHandlerInstantiator(new Jackson2HalModule.HalHandlerInstantiator(new DefaultRelProvider(), null, null));        
        jackson2JsonRedisSerializer.setObjectMapper(mapper);        
        return jackson2JsonRedisSerializer;
    }    
    
    
    @Bean
    public CacheManager cacheManager() {
    	RedisTemplate ops = redisTemplate(redisConnectionFactory());
//    	RedisSerializer ser = ops.getValueSerializer();
        RedisCacheManager manager = new RedisCacheManager(ops);
        manager.setDefaultExpiration(300);
 
        return manager;
    }
    
}
