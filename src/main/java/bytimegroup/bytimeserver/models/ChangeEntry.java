package bytimegroup.bytimeserver.models;

import java.time.LocalDateTime;

public class ChangeEntry {
	
	private String entityId;
	private LocalDateTime changed;
	private String entityType;
	private String crudType;
	private String field;
	private String oldValue;
	private String newValue;
	
	public ChangeEntry() {}
	public ChangeEntry(String entityId, int status, LocalDateTime changed, 
			String entityType, String crudType, String field, String oldValue, String newValue) {
		this.entityId = entityId;
		this.changed = changed;
		this.entityType = entityType;
		this.crudType = crudType;
		this.field = field;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}
	
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	public LocalDateTime getChanged() {
		return changed;
	}
	public void setChanged(LocalDateTime changed) {
		this.changed = changed;
	}
	public String getEntityType() {
		return entityType;
	}
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getOldValue() {
		return oldValue;
	}
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	public String getNewValue() {
		return newValue;
	}
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	public String getCrudType() {
		return crudType;
	}
	public void setCrudType(String crudType) {
		this.crudType = crudType;
	}
	
	
}
