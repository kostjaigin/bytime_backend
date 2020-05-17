package bytimegroup.bytimeserver.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bytimegroup.bytimeserver.models.ChangeEntry;
import bytimegroup.bytimeserver.models.Events;
import bytimegroup.bytimeserver.models.Templates;
import bytimegroup.bytimeserver.models.Users;
import bytimegroup.bytimeserver.services.AccessDeniedException;
import bytimegroup.bytimeserver.services.TemplatesService;
import bytimegroup.bytimeserver.services.UsersService;

@RestController
public class TemplatesController {

	@Autowired
	private TemplatesService templatesService;
	@Autowired
	private UsersService usersService;
	
	/**
	 * @param username
	 * @return
	 * @throws AccessDeniedException
	 */
	@GetMapping("/templates")
	public List<Templates> getTemplates(@RequestParam(value = "username", defaultValue = "") String username) throws AccessDeniedException {
		Users user = username.isEmpty() ? usersService.getCurrentUser() : usersService.getUserByUsername(username);
		return templatesService.getTemplates(user);
	}
	
	/**
	 * Create new template request.
	 * Without id since not stored yet.
	 * @return stored template with id provided by saving.
	 */
	@PostMapping("/templates")
	public Templates createEvent(@RequestBody Templates template) {
		return templatesService.storeTemplate(template, usersService.getCurrentUser());
	}
	
	@PutMapping("/templates")
	public ResponseEntity<String> updateTemplate(@RequestBody ChangeEntry entry) {
		// CRUD type should be update, otherwise it is a wrong way to request
		if (entry.getCrudType().equalsIgnoreCase("U") == false) {
			return new ResponseEntity<String>("Wrong usage of REST API", HttpStatus.BAD_REQUEST);
		}
		// we handle only events here
		if (entry.getEntityType().equalsIgnoreCase("template") == false) {
			return new ResponseEntity<String>("Wrong http path", HttpStatus.BAD_REQUEST);
		}
		// try to update
		if (templatesService.updateTemplate(entry, usersService.getCurrentUser())) {
			return new ResponseEntity<String>("OK", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Bad bad request!", HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/templates")
	public ResponseEntity<String> deleteTemplate(@RequestParam(value = "entityId", defaultValue = "") String entityId) {
		Users user = usersService.getCurrentUser();
		return new ResponseEntity<String>("Removed successfully", HttpStatus.OK);
	}
	
}
