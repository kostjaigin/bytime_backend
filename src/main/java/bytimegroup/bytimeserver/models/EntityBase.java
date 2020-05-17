package bytimegroup.bytimeserver.models;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

/**
 * Common properties for entities in system.
 */
public class EntityBase {

	@Id protected ObjectId _id;
	protected LocalDateTime creationDate;
	
	public EntityBase() {}
	
	public EntityBase(ObjectId _id, LocalDateTime creationDate) {
		this._id = _id;
		this.creationDate = creationDate;
	}
	
	public EntityBase(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public String get_id() {
		return _id.toHexString();
	}
	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	public LocalDateTime getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}
	
	
}
