package bytimegroup.bytimeserver.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import bytimegroup.bytimeserver.models.Calendars;
import bytimegroup.bytimeserver.models.ChangeEntry;
import bytimegroup.bytimeserver.models.Chats;
import bytimegroup.bytimeserver.models.Events;
import bytimegroup.bytimeserver.models.Roles;
import bytimegroup.bytimeserver.models.Users;
import bytimegroup.bytimeserver.repositories.EventsRepository;
import bytimegroup.bytimeserver.repositories.RolesRepository;
import bytimegroup.bytimeserver.repositories.UsersRepository;
import bytimegroup.bytimeserver.repositories.CalendarsRepository;
import bytimegroup.bytimeserver.repositories.ChatsRepository;

/**
 * Creates and edits events, 
 * provides roles and couples
 * them with users and chats
 * TODO use interface as service declaration instead
 */
@Service
public class EventsService {
	  
	  @Autowired
	  private EventsRepository eventsRepository;
	  @Autowired
	  private RolesRepository rolesRepository;
	  @Autowired
	  private CalendarsRepository calendarsRepository;
	  
	  /**
	   * @param start date of new event
	   * @param end date of new event
	   * @param chat of new event
	   * @param creationDate
	   * @param user owner of new event
	   * @param calendar in which new event will be created
	   * @return freshly created instance
	   */
	  public Events createEvent(LocalDateTime start, LocalDateTime end, Chats chat, LocalDateTime creationDate, Users user, Calendars calendar) {
		  // create event itself
		  Events event = new Events(start, end, creationDate, calendar.get_id(), chat.get_id());
		  // save instance and provide it with id by doing so
		  return storeEvent(event, user);
	  }
	  
	  /**
	   * Stores event in DB and
	   * provides initiator (user) with owner role.
	   * @param event
	   * @param user
	   * @return stored 
	   */
	  public Events storeEvent(Events event, Users user) {
		  // save instance and provide it with id by doing so
		  event = eventsRepository.save(event);
		  // create owner role for given user
		  Roles role = new Roles(user.getID(), event.get_id(), "event", "owner", event.getCreationDate());
		  // save the role and provide it with id by doing so
		  role = rolesRepository.save(role);
		  return event;
	  }
	  
	  /**
	   * Returns all events associated with given user.
	   * @param user
	   * @return
	   */
	  public List<Events> getEvents(Users user) {
		  List<Roles> userEventRoles = rolesRepository.findByUserIdAndType(user.getID(), "event");
		  List<String> eventIds = userEventRoles.stream().map(Roles::getEntityId).collect(Collectors.toList());
		  return (List<Events>) eventsRepository.findAllById(eventIds);
	  }
	  
	  /**
	   * if owner, event will be marked to be moved to bin
	   * as well as corresponding roles.
	   * @param eventId
	   * @return true if successful
	   */
	  public Boolean deleteEvent(String eventId, Users user) throws AccessDeniedException {
		  // check if event there
		  Events event;
		  try {
			  event = eventsRepository.findById(eventId).get();
		  } catch (NoSuchElementException nsee) {
			  return false;
		  }
		  // check if owner
		  Roles userEventRole = rolesRepository.findByEntityIdAndUserId(eventId, user.getID());
		  if (userEventRole == null) {
			  throw new AccessDeniedException();
		  }
		  if (userEventRole.getAccessLevel().equals("owner")) {
			  // delete entity
			  eventsRepository.deleteById(eventId);
			  // remember user ids for notification later
			  List<String> otherUserIds = rolesRepository.findAllByEntityId(eventId).stream().map(Roles::getUserId).collect(Collectors.toList());
			  // except the user that performed deletion
			  try {
				  otherUserIds.remove(user.getID());
			  } catch (Exception e) {
				  e.printStackTrace();
				  // TODO log it
			  }
			  // TODO notify other participants
		  }
		  // in any case delete user role:
		  rolesRepository.deleteById(userEventRole.get_id());
		  return true;
	  }
	  
	  /**
	   * Update existing event
	   * TODO in some cases do not just return false, throw some meaningful exception
	   * TODO to prevent logical errors from happening because of networking,
	   * check if provided startdate > current endate -> update enddate with old intervall too!
	   * TODO same story as above with provided enddate
	   * @param entry
	   * @param user
	   * @return
	   * @throws AccessDeniedException
	   */
	  public Boolean updateEvent(ChangeEntry entry, Users user) throws AccessDeniedException {
		  // check if entry is valid
		  Events event;
		  try {
			  event = eventsRepository.findById(entry.getEntityId()).get();
		  } catch (NoSuchElementException nsee) {
			  System.out.println("Event user wanted to update does not exist in DB...");
			  return false;
		  }
		  // check if permission is sufficient to perform an update
		  Roles userEventRole = rolesRepository.findByEntityIdAndUserId(entry.getEntityId(), user.getID());
		  if (userEventRole == null || userEventRole.getAccessLevelCode() < 70) {
			  throw new AccessDeniedException();
		  }
		  // try to perform an update
		  switch (entry.getField().toLowerCase()) {
			  case "startdate":
					try {
						LocalDateTime newStart = LocalDateTime.parse(entry.getNewValue());
						event.setStartDate(newStart);
					} catch (DateTimeParseException dtpe) {
						System.out.println("Cannot read input start date...");
						return false;
					}
					break;
			  case "enddate":
				  try {
					  LocalDateTime newEnd = LocalDateTime.parse(entry.getNewValue());
					  event.setEndDate(newEnd);
				  } catch (DateTimeParseException dtpe) {
					  return false;
				  }
				  break;
			  case "calendarid":
				  if (calendarsRepository.existsById(entry.getNewValue())) {
					  event.setCalendarId(entry.getNewValue());
				  } else {
					  return false;
				  }
				  break;
			  default:
				  return false;
		  }
		  // save updated entity
		  event = eventsRepository.save(event);
		  // TODO notify other participiants
		  return true;
	  }
	  
}
