package devCamp.WebApp.configurations;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import devCamp.WebApp.properties.ApplicationProperties;
import devCamp.WebApp.properties.ImageStorageProperties;

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

    
}
