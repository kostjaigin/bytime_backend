package bytimegroup.bytimeserver.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import bytimegroup.bytimeserver.models.Events;
import bytimegroup.bytimeserver.models.Messages;
import bytimegroup.bytimeserver.models.RegisterForm;
import bytimegroup.bytimeserver.models.Roles;
import bytimegroup.bytimeserver.models.Templates;
import bytimegroup.bytimeserver.models.Calendars;
import bytimegroup.bytimeserver.models.Chats;
import bytimegroup.bytimeserver.models.Users;
import bytimegroup.bytimeserver.repositories.CalendarsRepository;
import bytimegroup.bytimeserver.repositories.EventsRepository;
import bytimegroup.bytimeserver.repositories.RolesRepository;
import bytimegroup.bytimeserver.repositories.UsersRepository;
import bytimegroup.bytimeserver.services.CalendarsService;
import bytimegroup.bytimeserver.services.ChatsService;
import bytimegroup.bytimeserver.services.EventsService;
import bytimegroup.bytimeserver.services.MessagesService;
import bytimegroup.bytimeserver.services.TemplatesService;
import bytimegroup.bytimeserver.services.UsersService;
import bytimegroup.bytimeserver.repositories.TemplatesRepository;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles main logic requests.
 */
@RestController
public class MainController {
	
	@Autowired
	protected UsersService usersService;
	@Autowired
	protected CalendarsService calendarsService;
	@Autowired
	protected EventsService eventsService;
	@Autowired
	protected ChatsService chatsService;
	@Autowired
	protected TemplatesService templatesService;
	@Autowired
	protected MessagesService messagesService;
	
	/**
	 * first time login in the system
	 * no need to handle username/pwd because auth required
	 * simply remember the fact that user is in
	 * @return report to user (failure or success)
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ResponseEntity<String> postLoginAnswerMsg() {
		// TODO: log successful login //
		return new ResponseEntity<String>("login successful\nrequest other entities with provided credentials\r\n", HttpStatus.OK);
	}
	
	/**
	 * Checks whether provided registration form is valid,
	 * provided username and email are not already in use,
	 * and registers a new user.
	 * requests initialization of starter-models for new user.
	 * @param registration data
	 * @return report to user (failure or success)
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<String> handleRegistration(@RequestBody RegisterForm registrationForm) {
		
		Boolean userNameUnique = usersService.usernameUnique(registrationForm.getUsername());
		Boolean userEmailUnique = usersService.emailUnique(registrationForm.getEmail());
		Boolean allFieldsProvided = registrationForm.isValid();
		
		if (allFieldsProvided 
				&& userNameUnique
					&& userEmailUnique) {
			Users newuser = usersService.createUser(registrationForm);
			createModelsFor(newuser);
			return new ResponseEntity<String>("registration successful\r\n", HttpStatus.OK);
		} else {
			String errormsg = "\n" + "username unique: " + userNameUnique + "\n" + "email unique: " + userEmailUnique;
			errormsg += "\n" + "all required fields provided: " + allFieldsProvided + "\r\n";
			return new ResponseEntity<String>("failure: "+errormsg, HttpStatus.BAD_REQUEST);
		}
		
	}
	
	/**
	 * Creates start models for
	 * @param newuser
	 * private and public calendars, roles, 
	 * welcome event and welcome template.
	 * TODO try catch blocks to prevent abort of registration
	 * 	delete all possibly created stuff if an abort happens
	 */
	private void createModelsFor(Users newuser) {
		// initial calendars
		LocalDateTime creationDate = LocalDateTime.now();
		Calendars privateCal = calendarsService.createCalendar("private", true, creationDate, newuser);
		Calendars publicCal = calendarsService.createCalendar("public", true, creationDate, newuser);
		// create welcome event
		Chats welcomeEventChat = chatsService.createChat("Welcome to bytime!", new ArrayList<String>(), creationDate);
		LocalDateTime welcomeEventStart = creationDate.plusDays(1).withMinute(0).withSecond(0);
		LocalDateTime welcomeEventEnd = creationDate.plusDays(1).plusHours(2).withMinute(0).withSecond(0);
		Events welcomeEvent = eventsService.createEvent(welcomeEventStart, welcomeEventEnd, welcomeEventChat, creationDate, newuser, privateCal);
		// append welcome message with hints about app
		Messages welcomeMessage = messagesService.createMessage(usersService.getSystemUser(), 3, "this is an event test message everybody gets at start", creationDate);
		welcomeEventChat = chatsService.addMessage(welcomeEventChat, welcomeMessage);
		// create welcome template
		Chats welcomeTemplateChat = chatsService.createChat("Welcome to bytime templates!", new ArrayList<String>(), creationDate);
		Templates welcomeTemplate = templatesService.createTemplate(creationDate, welcomeTemplateChat, newuser);
		// append welcome message with hints about templates
		Messages welcomeMessageTempl = messagesService.createMessage(usersService.getSystemUser(), 3, "this is an template test message everybody gets at start", creationDate);
		welcomeTemplateChat = chatsService.addMessage(welcomeTemplateChat, welcomeMessageTempl);
	}
	
}
