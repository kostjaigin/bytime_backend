package bytimegroup.bytimeserver.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import bytimegroup.bytimeserver.models.Messages;

public interface MessagesRepository extends MongoRepository<Messages, String> {

}
