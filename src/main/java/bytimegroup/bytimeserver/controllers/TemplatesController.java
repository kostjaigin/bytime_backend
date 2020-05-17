package bytimegroup.bytimeserver.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	
}
