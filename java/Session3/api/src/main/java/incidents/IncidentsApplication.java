package incidents;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IncidentsApplication {

	private static final Log log = LogFactory.getLog(IncidentsApplication.class);

	public static void main(String[] args) {
		log.debug("Incidents Application Main");
		SpringApplication.run(IncidentsApplication.class, args);
	}

}
