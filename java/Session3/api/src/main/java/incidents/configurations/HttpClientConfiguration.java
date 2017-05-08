package incidents.configurations;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;


@Configuration
    public class HttpClientConfiguration {

      private static final Logger log = LoggerFactory.getLogger(HttpClientConfiguration.class);

      @Autowired
      private Environment environment;

      @Bean
      public ClientHttpRequestFactory httpRequestFactory() {
        return new HttpComponentsClientHttpRequestFactory(httpClient());
      }

      @Bean
      public HttpClient httpClient() {
    	  log.debug("creating HttpClient");
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();

        // Get the poolMaxTotal value from our application[-?].yml or default to 10 if not explicitly set
        connectionManager.setMaxTotal(environment.getProperty("poolMaxTotal", Integer.class, 10));

        return HttpClientBuilder
          .create()
          .setConnectionManager(connectionManager)
          .build();
      }    
    
}    
    