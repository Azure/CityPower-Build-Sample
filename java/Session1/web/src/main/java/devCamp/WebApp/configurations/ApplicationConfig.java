package devCamp.WebApp.configurations;

import com.microsoft.azure.storage.CloudStorageAccount;
import devCamp.WebApp.properties.ApplicationProperties;
import devCamp.WebApp.properties.ImageStorageProperties;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;

@Configuration
@EnableConfigurationProperties(value = {
        ApplicationProperties.class,
        ImageStorageProperties.class
})
@EnableCaching
@ComponentScan
public class ApplicationConfig {
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationConfig.class);

    @Autowired
    private ImageStorageProperties azureStorageAccountProperties;

    @PostConstruct
    protected void postConstruct() throws IOException {
        LOG.info(applicationProperties.toString());
        LOG.info(azureStorageAccountProperties.toString());
    }

    @Autowired
    private ApplicationProperties applicationProperties;

//    @Bean
//    public CloudStorageAccount getStorageAccount() throws InvalidKeyException, URISyntaxException {
//        String cs = String.format("DefaultEndpointsProtocol=http;AccountName=%s;AccountKey=%s",
//                azureStorageAccountProperties.getName(),
//                azureStorageAccountProperties.getKey());
//        return CloudStorageAccount.parse(cs);
//    }


    
    
}
