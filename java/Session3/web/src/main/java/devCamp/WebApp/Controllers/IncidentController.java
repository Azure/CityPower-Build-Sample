package devCamp.WebApp.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import devCamp.WebApp.models.IncidentBean;

import devCamp.WebApp.services.ImageStorageService;
import devCamp.WebApp.services.IncidentService;

@Controller
public class IncidentController {
	private static final Logger LOG = LoggerFactory.getLogger(IncidentController.class);

	@Autowired
	private IncidentService incidentService;

	@Autowired
	private ImageStorageService storageService;

	@GetMapping("/details/{id}")
	public String Details( @PathVariable("id") String id, Model model) {
		//get the incident from the REST service
		IncidentBean incident = incidentService.getById(id);    	
		//plug incident into the Model
		model.addAttribute("incident", incident);
		return "Incident/details";
	}


	@GetMapping("/new")
	public String newIncidentForm( Model model) {
		model.addAttribute("incident", new IncidentBean());
		return "Incident/new";
	}

	/*
	@PostMapping("/new")
	public String Create(@ModelAttribute IncidentBean incident,@RequestParam("file") MultipartFile imageFile) {
		LOG.info("creating incident");
		return "redirect:/dashboard";
	}
	 */



	@PostMapping("/new")
	public String Create(@ModelAttribute IncidentBean incident, @RequestParam("file") MultipartFile imageFile) {
		LOG.info("creating incident");
		//graphService.sendMail(OAuth2TokenUtils.getGivenName(),OAuth2TokenUtils.getMail());
		IncidentBean result = incidentService.createIncident(incident);
		String incidentID = result.getId();

		if (imageFile != null) {
			try {
				String fileName = imageFile.getOriginalFilename();
				if (fileName != null) {
					//save the file
					//now upload the file to blob storage

					LOG.info("Uploading to blob");
					String imageFileName = storageService.storeImage(incidentID, fileName, imageFile.getContentType(), imageFile.getBytes());
					result.setImageUri(imageFileName);
					incidentService.updateIncident( result);
				}
			} catch (Exception e) {
				return "Incident/details";
			}
			return "redirect:/dashboard";
		}
		return "redirect:/dashboard";
	}
}
