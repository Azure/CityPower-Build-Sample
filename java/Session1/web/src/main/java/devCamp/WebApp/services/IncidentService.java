package devCamp.WebApp.services;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.hateoas.PagedResources;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import devCamp.WebApp.models.IncidentBean;

@Service
public interface IncidentService {

	List<IncidentBean> getAllIncidents();

	PagedResources<IncidentBean> getIncidentsPaged(int page,int pagesize);

	IncidentBean createIncident(IncidentBean incident);

	IncidentBean updateIncident(IncidentBean newIncident);

	IncidentBean getById(String incidentId);

	void clearCache();

}
