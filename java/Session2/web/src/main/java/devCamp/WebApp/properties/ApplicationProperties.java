package devCamp.WebApp.properties;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {
    private String incidentApiUrl;

    public String getIncidentApiUrl() {
        return incidentApiUrl;
    }

    public void setIncidentApiUrl(String incidentApiUrl) {
        this.incidentApiUrl = incidentApiUrl;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
