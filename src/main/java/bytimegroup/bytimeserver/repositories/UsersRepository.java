package bytimegroup.bytimeserver.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import bytimegroup.bytimeserver.models.Users;


public interface UsersRepository extends MongoRepository<Users, String> {
	
	@Query("{ 'username' : ?0 }")
	Users findByUsername(String username);
	
	@Query("{ 'email' : ?0 }")
	Users findByEmail(String email);
	
	Boolean existsByUsername(String username);
	
	Boolean existsByEmail(String email);
	
}
