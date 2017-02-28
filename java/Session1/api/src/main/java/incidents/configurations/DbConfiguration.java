package incidents.configurations;

import java.net.UnknownHostException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.MongoClient;

@Configuration
public class DbConfiguration {
	/*
	 * Use the standard Mongo driver API to create a com.mongodb.Mongo instance.
	 */
	public @Bean MongoClient mongo() throws UnknownHostException {
		return new MongoClient("localhost");
	}
}
