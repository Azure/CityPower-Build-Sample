package devCamp.WebApp.services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.PagedResources.PageMetadata;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import devCamp.WebApp.models.IncidentBean;

@JsonIdentityInfo(generator =  ObjectIdGenerators.PropertyGenerator.class, property = "page")
public class PagedIncidents  {

	public static final long serialVersionUID = 12714812665805256L;

	ArrayList<IncidentBean> incidents;
	PageMetadata metadata;
	int page;

	public PagedIncidents() {
		incidents = null;
		metadata = null;
		incidents = null;
	}

	public PagedIncidents(PagedResources<IncidentBean> beanResources,int pg) {
		this.incidents = new ArrayList<IncidentBean>(beanResources.getContent());
		this.metadata = beanResources.getMetadata();
		this.page = pg;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public Collection<IncidentBean> getIncidents() {
		return incidents;
	}

	public void setIncidents(Collection<IncidentBean> incidents) {
		this.incidents = new ArrayList<IncidentBean>(incidents);
	}

	public PageMetadata getMetadata() {
		return metadata;
	}

	public void setMetadata(PageMetadata metadata) {
		this.metadata = metadata;
	}

}
