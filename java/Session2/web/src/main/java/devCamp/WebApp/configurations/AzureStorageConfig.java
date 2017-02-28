package devCamp.WebApp.configurations;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.microsoft.azure.storage.CloudStorageAccount;

import devCamp.WebApp.properties.AzureStorageAccountProperties;

@Configuration
public class AzureStorageConfig {
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationConfig.class);

    @Autowired
    private AzureStorageAccountProperties azureStorageProperties;

    @PostConstruct
    protected void postConstruct() throws IOException {
        LOG.info(azureStorageProperties.toString());
    }
    
    @Bean
    public CloudStorageAccount getStorageAccount() throws InvalidKeyException, URISyntaxException {
        String cs = String.format("DefaultEndpointsProtocol=http;AccountName=%s;AccountKey=%s",
                azureStorageProperties.getName(),
                azureStorageProperties.getKey());
        return CloudStorageAccount.parse(cs);
    }

}
