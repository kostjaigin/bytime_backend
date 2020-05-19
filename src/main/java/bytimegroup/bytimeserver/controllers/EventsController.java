package bytimegroup.bytimeserver.controllers;

import java.util.List;
import java.util.NoSuchElementException;
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

import bytimegroup.bytimeserver.models.Delta;
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
	 * @param event json formed event entity without id (global id has to be assigned on the server side)
	 */
	@PostMapping("/events")
	public Events createEvent(@RequestBody Events event) {
		return eventsService.storeEvent(event, usersService.getCurrentUser());
	}
	
	/**
	 * Edit event.
	 * @param entityId 
	 * @param changed datetime when crud operation was performed on the client side (passed to resolve possible conflicts)
	 * @param changes
	 * @return HTTP response
	 */
	@PutMapping("/events")
	public ResponseEntity<String> updateEvent(@RequestParam(value = "changed", defaultValue = "") String changed, 
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
				if (eventsService.updateEvent(entityId, change, usersService.getCurrentUser())) {
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
