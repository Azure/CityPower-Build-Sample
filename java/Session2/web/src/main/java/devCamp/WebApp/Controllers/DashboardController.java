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
import devCamp.WebApp.services.PagedIncidents;

@Controller
public class DashboardController {
	private static final Logger LOG = LoggerFactory.getLogger(DashboardController.class);

	@Autowired
	IncidentService service;

	@RequestMapping("/dashboard")
	public String dashboard(@RequestParam(defaultValue = "0") int page,Model model) {
		LOG.debug("entering dashboard");
//		PagedResources<IncidentBean> incidents = service.getIncidentsPaged(page,9);
		PagedIncidents pi = service.getIncidentsPaged(page,9);
//		PagedResources<IncidentBean> incidents = pi.getIncidents();
		model.addAttribute("allIncidents", pi.getIncidents());
		model.addAttribute("pageInfo", pi.getMetadata());		
		return "Dashboard/index";
	}	
}
