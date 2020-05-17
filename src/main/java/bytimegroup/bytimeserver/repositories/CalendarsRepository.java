package bytimegroup.bytimeserver.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import bytimegroup.bytimeserver.models.Calendars;

public interface CalendarsRepository extends MongoRepository<Calendars, String> {
	
}
