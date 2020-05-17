package bytimegroup.bytimeserver.repositories;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import bytimegroup.bytimeserver.models.Roles;


public interface RolesRepository extends MongoRepository<Roles, String> {
	
	/**
	 * Find role by
	 * @param entityId
	 * @return roles corresponding to entity
	 */
	List<Roles> findAllByEntityId(String entityId);
	/**
	 * Find all roles by
	 * @param userId
	 * @return list of all user roles
	 */
	List<Roles> findAllByUserId(String userId);
	/**
	 * Find all roles by
	 * @param usedId and for a given
	 * @param entityType
	 * @return list of typed user roles
	 */
	@Query("{ 'userId' : ?0, 'entityType' : ?1 }")
	List<Roles> findByUserIdAndType(String usedId, String entityType);
	
	/**
	 * Find all roles by
	 * @param entityId
	 * @param userId
	 * @return
	 */
	@Query("{ 'entityId' : ?0, 'userId' : ?1 }")
	Roles findByEntityIdAndUserId(String entityId, String userId);
	
}
