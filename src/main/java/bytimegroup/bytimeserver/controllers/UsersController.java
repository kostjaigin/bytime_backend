package bytimegroup.bytimeserver.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bytimegroup.bytimeserver.models.Users;
import bytimegroup.bytimeserver.services.AccessDeniedException;
import bytimegroup.bytimeserver.services.UsersService;

@RestController
public class UsersController {

	@Autowired
	private UsersService usersService;
	
	/**
	 * @return json encoded subscribers of
	 * @param param passed user
	 * and access denied to its subscribers
	 */
	@GetMapping("/subscribers")
	public List<Users> getSubscribers(@RequestParam(value = "username", defaultValue = "") String username) throws AccessDeniedException {
		Users requestedUser = username.isEmpty() ? usersService.getCurrentUser() : usersService.getUserByUsername(username);
		return usersService.getSubscribersOf(requestedUser);
	}
	
	/**
	 * @param username
	 * @return json encoded subscriptions of passed user
	 */
	@GetMapping("/subscriptions")
	public List<Users> getSubscriptions(@RequestParam(value = "username", defaultValue = "") String username) throws AccessDeniedException {
		Users requestedUser = username.isEmpty() ? usersService.getCurrentUser() : usersService.getUserByUsername(username);
		return usersService.getSubscriptionsOf(requestedUser);
	}
	
	@GetMapping("/currentuser")
	public Users getUser() {
		return usersService.getCurrentUser();
	}
	
}
