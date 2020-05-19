package bytimegroup.bytimeserver.controllers;

import java.util.List;
import java.util.NoSuchElementException;

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

import bytimegroup.bytimeserver.models.Delta;
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
	
	/**
	 * @param changed datetime when crud operation was performed on the client side (passed to resolve possible conflicts)
	 * @param entityId
	 * @param changes
	 * @return
	 */
	@PutMapping("/templates")
	public ResponseEntity<String> updateTemplate(@RequestParam(value = "changed", defaultValue = "") String changed, 
			@RequestParam(value = "entityId", defaultValue = "") String entityId, @RequestBody List<Delta> changes) {
		// check that entity id is provided
		if (entityId.isEmpty()) {
			return new ResponseEntity<String>("No entity id provided\r\n", HttpStatus.BAD_REQUEST);
		}
		// try to perform all updates
		String response = "Performed changes:\n";
		// iterate through deltas, try to perform an update and extend response body
		for (Delta change: changes) {
			try {
				if (templatesService.updateTemplate(entityId, change, usersService.getCurrentUser())) {
					response += change.getField() + " OK\n";
				} else {
					response += change.getField() + " FAILURE\n";
				}
			} catch (NoSuchElementException nsee) {
				return new ResponseEntity<String>(entityId+" item cannot be found in DB\r\n", HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/templates")
	public ResponseEntity<String> deleteTemplate(@RequestParam(value = "entityId", defaultValue = "") String entityId) {
		Users user = usersService.getCurrentUser();
		return new ResponseEntity<String>("Removed successfully", HttpStatus.OK);
	}
	
}
