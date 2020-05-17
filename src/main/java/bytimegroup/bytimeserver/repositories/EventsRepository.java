package bytimegroup.bytimeserver.repositories;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import bytimegroup.bytimeserver.models.Events;

/**
 * Connector between the model and mongodb
 */
public interface EventsRepository extends MongoRepository<Events, String> {

}
