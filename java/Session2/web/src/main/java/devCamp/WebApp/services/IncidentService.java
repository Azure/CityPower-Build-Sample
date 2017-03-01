package devCamp.WebApp.services;

import devCamp.WebApp.models.IncidentBean;


import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public interface IncidentService {

	@Cacheable("incidents")
	List<IncidentBean> getAllIncidents();

   
	@Cacheable(value="incidentspaged")	
	PagedIncidents getIncidentsPaged(int pagenum,int pagesize);

//	@CacheEvict(value={"incidents,incidentspaged"}, allEntries=true)
	@Caching(evict = { @CacheEvict(value="incidents",allEntries=true), @CacheEvict(value="incidentspaged",allEntries=true) })
//	@CacheEvict(value="incidentspaged", allEntries=true)
	IncidentBean createIncident(IncidentBean incident);

//	@CacheEvict(value={"incidents,incidentspaged"}, allEntries=true)
	@Caching(evict = { @CacheEvict(value="incidents",allEntries=true), @CacheEvict(value="incidentspaged",allEntries=true) })
//	@CacheEvict(value="incidentspaged", allEntries=true)
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

	@CacheEvict(value={"incidents,incidentspaged"}, allEntries=true)
	void clearCache();

}
