package bytimegroup.bytimeserver.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import bytimegroup.bytimeserver.models.Templates;

public interface TemplatesRepository extends MongoRepository<Templates, String> {

}
