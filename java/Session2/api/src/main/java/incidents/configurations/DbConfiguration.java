package incidents.configurations;

import java.net.UnknownHostException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.Mongo;

@Configuration
public class DbConfiguration {
	  /*
	   * Use the standard Mongo driver API to create a com.mongodb.Mongo instance.
	   */
	@SuppressWarnings("deprecation")
	public @Bean Mongo mongo() throws UnknownHostException {
	       return new Mongo("localhost");
	   }
}
