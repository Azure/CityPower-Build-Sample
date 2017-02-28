package devCamp.WebApp.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cache")
public class CacheProperties {
    private String redisHost;
    private Integer redisPort;
    private String primaryKey;
    private Integer redisSslPort;
	public String getRedisHost() {
		return redisHost;
	}
	public void setRedisHost(String redisHost) {
		this.redisHost = redisHost;
	}
	public Integer getRedisPort() {
		return redisPort;
	}
	public void setRedisPort(Integer redisPort) {
		this.redisPort = redisPort;
	}
	public String getPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}
	public Integer getRedisSslPort() {
		return redisSslPort;
	}
	public void setRedisSslPort(Integer redisSslPort) {
		this.redisSslPort = redisSslPort;
	}

}
