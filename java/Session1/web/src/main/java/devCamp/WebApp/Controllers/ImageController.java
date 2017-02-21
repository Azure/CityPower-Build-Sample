package devCamp.WebApp.Controllers;

import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import devCamp.WebApp.models.IncidentBean;
import devCamp.WebApp.services.ImageStorageService;
import devCamp.WebApp.services.IncidentService;

@Controller
public class ImageController {

	@Autowired
	private ImageStorageService storageService;
	
	@Autowired
    private IncidentService incidentService;

	@GetMapping("/Image/{id}")
	public ResponseEntity<byte[]> getImageArray(@PathVariable("id") String id, HttpServletResponse response) {
		IncidentBean incident = incidentService.getById(id);   
	    byte[] image = storageService.getImageAsArray(incident.getImageUri());  //this just gets the data from a database
	    return ResponseEntity.ok(image);	
	}
	
}