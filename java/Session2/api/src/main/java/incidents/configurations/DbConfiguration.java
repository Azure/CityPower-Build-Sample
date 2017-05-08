package incidents.configurations;

import java.net.UnknownHostException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

@Configuration
public class DbConfiguration {
	@Autowired
	private DbConfigurationProperties dbConfigProperties;

	public @Bean MongoClient mongo() throws UnknownHostException {
		String connectString = dbConfigProperties.getConnectString();
		if (connectString.equals("${DB_CONNECT_STRING}")){
			connectString = "mongodb://localhost";
		}
		return new MongoClient(new MongoClientURI(connectString));
	}
}
