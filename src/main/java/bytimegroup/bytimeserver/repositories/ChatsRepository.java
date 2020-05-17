package bytimegroup.bytimeserver.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import bytimegroup.bytimeserver.models.Chats;

public interface ChatsRepository extends MongoRepository<Chats, String> {

}
