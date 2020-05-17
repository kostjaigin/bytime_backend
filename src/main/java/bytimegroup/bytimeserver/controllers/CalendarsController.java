package bytimegroup.bytimeserver.controllers;

import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bytimegroup.bytimeserver.models.Calendars;
import bytimegroup.bytimeserver.models.Users;
import bytimegroup.bytimeserver.repositories.CalendarsRepository;
import bytimegroup.bytimeserver.services.AccessDeniedException;
import bytimegroup.bytimeserver.services.CalendarsService;
import bytimegroup.bytimeserver.services.UsersService;

@RestController
public class CalendarsController {

	@Autowired
	private UsersService usersService;
	@Autowired
	private CalendarsService calendarsService;
	
	@GetMapping("/calendars")
	public List<Calendars> getCalendars(@RequestParam(value = "username", defaultValue = "") String username) throws AccessDeniedException {
		Users user = username.isEmpty() ? usersService.getCurrentUser() : usersService.getUserByUsername(username);
		return calendarsService.getCalendars(user);
	}

}
