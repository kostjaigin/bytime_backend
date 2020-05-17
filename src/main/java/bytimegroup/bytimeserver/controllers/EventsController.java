package bytimegroup.bytimeserver.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bytimegroup.bytimeserver.models.ChangeEntry;
import bytimegroup.bytimeserver.models.Events;
import bytimegroup.bytimeserver.models.Roles;
import bytimegroup.bytimeserver.models.Users;
import bytimegroup.bytimeserver.repositories.EventsRepository;
import bytimegroup.bytimeserver.repositories.RolesRepository;
import bytimegroup.bytimeserver.services.AccessDeniedException;
import bytimegroup.bytimeserver.services.EventsService;
import bytimegroup.bytimeserver.services.UsersService;

@RestController
public class EventsController {
	
	@Autowired
	private EventsService eventsService;
	@Autowired
	private UsersService usersService;
	
	/**
	 * @return json encoded events related to
	 * the requesting user
	 */
	@GetMapping("/events")
	public List<Events> getEvents(@RequestParam(value = "username", defaultValue = "") String username) throws AccessDeniedException {
		Users user = username.isEmpty() ? usersService.getCurrentUser() : usersService.getUserByUsername(username);
		return eventsService.getEvents(user);
	}
	
	/**
	 * Create new event request.
	 * Without id since not stored yet.
	 * @return stored event with id provided by saving.
	 */
	@PostMapping("/events")
	public Events createEvent(@RequestBody Events event) {
		return eventsService.storeEvent(event, usersService.getCurrentUser());
	}
	
	/**
	 * Edit event.
	 * @param events
	 * @return
	 */
	@PutMapping("/events")
	public ResponseEntity<String> updateEvent(@RequestBody ChangeEntry entry) {
		// CRUD type should be update, otherwise it is a wrong way to request
		if (entry.getCrudType().equalsIgnoreCase("U") == false) {
			return new ResponseEntity<String>("Wrong usage of REST API", HttpStatus.BAD_REQUEST);
		}
		// we handle only events here
		if (entry.getEntityType().equalsIgnoreCase("event") == false) {
			return new ResponseEntity<String>("Wrong http path", HttpStatus.BAD_REQUEST);
		}
		// try to update
		if (eventsService.updateEvent(entry, usersService.getCurrentUser())) {
			return new ResponseEntity<String>("OK", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Bad bad request!", HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Delete event.
	 * @param entityId
	 * @return
	 */
	@DeleteMapping("/events")
	public ResponseEntity<String> deleteEvent(@RequestParam(value = "entityId", defaultValue = "") String entityId) {
		Users user = usersService.getCurrentUser();
		if (entityId == null || entityId.isEmpty()) {
			return new ResponseEntity<String>("No entity id provided\r\n", HttpStatus.BAD_REQUEST);
		}
		if (eventsService.deleteEvent(entityId, user)) {
			return new ResponseEntity<String>("Removed successfully\r\n", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Was already removed\r\n", HttpStatus.BAD_REQUEST);
		}
	}
}
