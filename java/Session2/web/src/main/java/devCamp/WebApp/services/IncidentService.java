package devCamp.WebApp.services;

import devCamp.WebApp.models.IncidentBean;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.PagedResources;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public interface IncidentService {

	@Cacheable("incidents")
	List<IncidentBean> getAllIncidents();

	@Cacheable("incidents")
	PagedResources<IncidentBean> getIncidentsPaged(int page,int pagesize);

	@CacheEvict(cacheNames="incidents", allEntries=true)
	IncidentBean createIncident(IncidentBean incident);

	@CacheEvict(cacheNames="incidents", allEntries=true)
	IncidentBean updateIncident(IncidentBean newIncident);

	@Cacheable("incidents")
	IncidentBean getById(String incidentId);

	@Async
	CompletableFuture<List<IncidentBean>> getAllIncidentsAsync();

	@Async
	CompletableFuture<IncidentBean> createIncidentAsync(IncidentBean incident);

	@Async
	CompletableFuture<IncidentBean> updateIncidentAsync(IncidentBean newIncident);

	@Async
	CompletableFuture<IncidentBean> getByIdAsync(String incidentId);

	@CacheEvict(cacheNames="incidents", allEntries=true)
	void clearCache();

}
