package devCamp.WebApp.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import devCamp.WebApp.models.IncidentBean;
import devCamp.WebApp.properties.ApplicationProperties;

@Service
public class IncidentServiceImpl implements IncidentService {
	private static final Logger LOG = LoggerFactory.getLogger(IncidentServiceImpl.class);

	@Autowired
	private ApplicationProperties applicationProperties;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public List<IncidentBean> getAllIncidents() {
		LOG.info("Performing get {} web service", applicationProperties.getIncidentApiUrl() +"/incidents");
		final String restUri = applicationProperties.getIncidentApiUrl() +"/incidents";
		ResponseEntity<Resources<IncidentBean>> response = restTemplate.exchange(restUri, HttpMethod.GET, null,
				new ParameterizedTypeReference<Resources<IncidentBean>>() {});
		Resources<IncidentBean> beanResources = response.getBody();
		Collection<IncidentBean> beanCol = beanResources.getContent();
		ArrayList<IncidentBean> beanList= new ArrayList<IncidentBean>(beanCol);
		LOG.info("Total Incidents {}", beanList.size());
		return beanList;
	}

	@Override
	public PagedResources<IncidentBean> getIncidentsPaged(int page,int pagesize) {
		LOG.info("Performing get {} web service", applicationProperties.getIncidentApiUrl() +"/incidents");
		final String restUri = applicationProperties.getIncidentApiUrl() +"/incidents?page="+page+"&size="+pagesize;
		ResponseEntity<PagedResources<IncidentBean>> response = restTemplate.exchange(restUri, HttpMethod.GET, null,
				new ParameterizedTypeReference<PagedResources<IncidentBean>>() {});
		PagedResources<IncidentBean> beanResources = response.getBody();
		return beanResources;
	}    

	@Override
	public IncidentBean createIncident(IncidentBean incident) {
		LOG.info("Creating incident");
		if (incident.getCreated() == null) {
			incident.setCreated(getCurrentDate());
			incident.setLastModified(getCurrentDate());
		}
		final String restUri = applicationProperties.getIncidentApiUrl() +"/incidents";
		IncidentBean createdBean = restTemplate.postForObject(restUri, incident, IncidentBean.class);
		LOG.info("Done creating incident");
		return createdBean;

	}

	@Override
	public IncidentBean updateIncident(IncidentBean newIncident) {
		LOG.info("Updating incident");
		newIncident.setLastModified(getCurrentDate());
		final String restUri = applicationProperties.getIncidentApiUrl() +"/incidents/"+newIncident.getId();
		IncidentBean createdBean = restTemplate.patchForObject(restUri, newIncident, IncidentBean.class);
		LOG.info("Done updating incident");
		return createdBean;
	}

	@Override
	public IncidentBean getById(String incidentId) {
		LOG.info("Getting incident by ID {}", incidentId);
		final String restUri = applicationProperties.getIncidentApiUrl() +"/incidents/"+incidentId;
		IncidentBean result = restTemplate.getForObject(restUri, IncidentBean.class);
		return result;
	}

	@Override
	public void clearCache() {

	}
	private String getCurrentDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		Date parsedDate = new Date();
		String formattedDate = formatter.format(parsedDate);
		return formattedDate;
	}
}
