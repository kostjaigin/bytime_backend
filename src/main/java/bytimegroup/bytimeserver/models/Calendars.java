package bytimegroup.bytimeserver.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;

public class Calendars extends EntityBase {

	private String title;
	private Boolean selected;
	private String ownerId;
	
	public Calendars() {}
	public Calendars(ObjectId _id, String title, Boolean selected,
						LocalDateTime creationDate, String ownerId) {
		super(_id, creationDate);
		this.title = title;
		this.selected = selected;
		this.creationDate = creationDate;
		this.ownerId = ownerId; 
	}
	public Calendars(String title, Boolean selected,
			LocalDateTime creationDate, String ownerId) {
		super(creationDate);
		this.title = title;
		this.selected = selected;
		this.ownerId = ownerId; 
	}
	
	public String getTitle() { return title; }
	public Boolean getSelected() { return selected; }
	public String getOwnerId() { return ownerId; }
	
	public void setTitle(String title) {
		this.title = title;
	}
	public void setSelected(Boolean selected) {
		this.selected = selected;
	}	
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	
}
