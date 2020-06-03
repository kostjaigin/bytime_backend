package bytimegroup.bytimeserver.models;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Roles extends EntityBase {
	
	private String userId;
	private String entityId;
	/**
	 * event, template, etc. lowercased
	 */
	private String entityType;
	/**
	 * read, readwrite, etc. lowercased
	 */
	private String accessLevel;
	
	public Roles() {}
	public Roles(ObjectId _id, String userId, String entityId, String entityType, String accessLevel, LocalDateTime creationDate) {
		super(_id, creationDate);
		this.userId = userId;
		this.entityId = entityId;
		this.entityType = entityType;
		this.accessLevel = accessLevel;
	}
	public Roles(String userId, String entityId, String entityType, String accessLevel, LocalDateTime creationDate) {
		super(creationDate);
		this.userId = userId;
		this.entityId = entityId;
		this.entityType = entityType;
		this.accessLevel = accessLevel;
	}
	
	public String getUserId() { return userId; }
	public String getEntityId() { return entityId; }
	public String getEntityType() { return entityType; }
	public String getAccessLevel() { return accessLevel; }
	
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	
	public void setAccessLevel(String accessLevel) {
		this.accessLevel = accessLevel;
	}
	
	// application specific methods
	public int getAccessLevelCode() {
		switch (this.accessLevel) {
			case "owner":
				return 100;
			case "guest":
				return 80;
			case "reader":
				return 30;
			default:
				return 0;
		}
	}
}
