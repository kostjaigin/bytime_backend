package bytimegroup.bytimeserver.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bytimegroup.bytimeserver.models.Calendars;
import bytimegroup.bytimeserver.models.Roles;
import bytimegroup.bytimeserver.models.Users;
import bytimegroup.bytimeserver.repositories.CalendarsRepository;
import bytimegroup.bytimeserver.repositories.RolesRepository;
import bytimegroup.bytimeserver.repositories.UsersRepository;

/**
 * Creates and edits calendars, 
 * provides roles and couples
 * them with users
 * TODO use interface as service declaration instead
 */
@Service
public class CalendarsService {
	
	@Autowired
	private RolesRepository rolesRepository;
	@Autowired
	private CalendarsRepository calendarsRepository;
	
	/**
	 * @param title of calendar
	 * @param selected - whether created calendar selected by default
	 * @param creationDate
	 * @param user for which calendar will be created
	 * @return freshly created instance
	 */
	public Calendars createCalendar(String title, Boolean selected, LocalDateTime creationDate, Users user) {
		// create instance
		Calendars calendar = new Calendars(title, selected, creationDate, user.getID());
		// save instance and provide it with id by doing so
		calendar = calendarsRepository.save(calendar);
		// create owner role for given user
		Roles calRole = new Roles(user.getID(), calendar.get_id(), "calendar", "owner", creationDate);
		// save role and provide it with id by doing so
		calRole = rolesRepository.save(calRole);
		return calendar;
	}
	
	/**
	 * Returns all calendars associated with given user.
	 * @param user
	 * @return
	 * TODO AccessControlException if no permission
	 */
	public List<Calendars> getCalendars(Users user) {
		List<Roles> userCalendarRoles = rolesRepository.findByUserIdAndType(user.getID(), "calendar");
		List<String> calendarIds = userCalendarRoles.stream().map(Roles::getEntityId).collect(Collectors.toList());
		return (List<Calendars>) calendarsRepository.findAllById(calendarIds);
	}
	
}
