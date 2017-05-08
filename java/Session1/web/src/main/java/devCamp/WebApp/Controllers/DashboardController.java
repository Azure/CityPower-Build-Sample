package devCamp.WebApp.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedResources;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import devCamp.WebApp.models.IncidentBean;
import devCamp.WebApp.services.IncidentService;

//import devCamp.WebApp.IncidentAPIClient.IncidentService;
//import devCamp.WebApp.IncidentAPIClient.IncidentService;
//import devCamp.WebApp.models.IncidentBean;

@Controller
public class DashboardController {
	private static final Logger LOG = LoggerFactory.getLogger(DashboardController.class);

	@Autowired
	IncidentService service;

	@RequestMapping("/dashboard")
	public String dashboard(@RequestParam(defaultValue = "0") int page,Model model) {
		LOG.debug("Dashboard");
		PagedResources<IncidentBean> incidents = service.getIncidentsPaged(page,9);
		model.addAttribute("allIncidents", incidents.getContent());
		model.addAttribute("pageInfo", incidents.getMetadata());		
		return "Dashboard/index";
	}	
}
