package bytimegroup.bytimeserver.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import bytimegroup.bytimeserver.models.RegisterForm;
import bytimegroup.bytimeserver.models.Users;
import bytimegroup.bytimeserver.repositories.UsersRepository;

/**
 * Creates and edits users, 
 * provides them with roles
 */
@Service
public class UsersService {

	@Autowired
	private UsersRepository usersRepository;
	
	/**
	 * Registers a new user with registration form.
	 * @param registrationForm JSON encoded user info
	 * @return freshly created instance
	 */
	public Users createUser(RegisterForm registrationForm) {
		// create instance
		Users newuser = new Users(registrationForm);
		// save instance and provide it with id by doing so
		newuser = usersRepository.save(newuser);
		return newuser;
	}
	
	public Boolean usernameUnique(String username) {
		return usersRepository.existsByUsername(username) == false;
	}
	
	public Boolean emailUnique(String email) {
		return usersRepository.existsByEmail(email) == false;
	}
	
	/**
	 * retrieve information about currently logged user
	 * @return current logged user instance
	 */
	public Users getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentName = authentication.getName();
		return usersRepository.findByUsername(currentName);
	}
	
	/**
	 * Returns requested user if there is a permission
	 * @param username
	 * @throws IllegalArgumentException if input empty
	 * @throws TODO AccessDeniedException if no permission to access user
	 * @return
	 */
	public Users getUserByUsername(String username) throws AccessDeniedException, IllegalArgumentException {
		if (username.isEmpty() == false) {
			Users user = usersRepository.findByUsername(username);
			return user;
		} else {
			throw new IllegalArgumentException("username cannot be empty");
		}
	}
	
	/**
	 * Returns requested user if there is a permission
	 * @param email
	 * @throws IllegalArgumentException if input empty
	 * @throws TODO AccessDeniedException if no permission to access user
	 * @return
	 */
	public Users getUserByMail(String email) throws AccessDeniedException, IllegalArgumentException {
		if (email.isEmpty() == false) {
			Users user = usersRepository.findByEmail(email);
			return user;
		} else {
			throw new IllegalArgumentException("email cannot be empty");
		}
	}
	
	/**
	 * @param user
	 * @throws TODO AccessControlException if no permission
	 * @return
	 */
	public List<Users> getSubscribersOf(Users user) {
		List<String> subscribersIds = user.getSubscribers();
		return (List<Users>) usersRepository.findAllById(subscribersIds);
	}
	
	/**
	 * @throws TODO AccessControlException if no permission
	 * @param user
	 * @return
	 */
	public List<Users> getSubscriptionsOf(Users user) {
		List<String> subscriptionsIds = user.getSubscriptions();
		return (List<Users>) usersRepository.findAllById(subscriptionsIds);
	}
	
	/**
	 * Get bytime administrative user
	 * that will be used to send system
	 * messages to the user
	 * @return
	 */
	public Users getSystemUser() {
		return usersRepository.findByUsername("bytime");
	}
	
}
