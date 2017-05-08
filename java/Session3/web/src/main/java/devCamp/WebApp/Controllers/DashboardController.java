package devCamp.WebApp.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedResources;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import devCamp.WebApp.models.IncidentBean;
import devCamp.WebApp.services.IncidentService;

@Controller
public class DashboardController {

	@Autowired
	IncidentService service;

	@RequestMapping("/dashboard")
	public String dashboard(@RequestParam(defaultValue = "0") int page,Model model) {

		PagedResources<IncidentBean> incidents = service.getIncidentsPaged(page,9);
		model.addAttribute("allIncidents", incidents.getContent());
		model.addAttribute("pageInfo", incidents.getMetadata());		
		return "Dashboard/index";
	}	
}
