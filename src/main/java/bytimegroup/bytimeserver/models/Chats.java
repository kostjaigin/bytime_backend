package bytimegroup.bytimeserver.models;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Chats extends EntityBase {

	private String title;
	private List<String> messagesIds;
	
	public Chats() {}
	public Chats(ObjectId _id, String title, List<String> messagesIds, LocalDateTime creationDate) {
		super(_id, creationDate);
		this.title = title;
		this.messagesIds = messagesIds;
	}
	public Chats(String title, List<String> messagesIds, LocalDateTime creationDate) {
		super(creationDate);
		this.title = title;
		this.messagesIds = messagesIds;
	}
	
	public List<String> getMessagesIds() {
		return messagesIds;
	}
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	public void setMessagesId(List<String> messagesIds) {
		this.messagesIds = messagesIds;
	}
	
	public void addMessageId(String messageId) {
		this.messagesIds.add(messageId);
	}
	public void removeMessageId(String messageId) {
		this.messagesIds.removeIf(Id -> Id.equals(messageId));
	}
	
}
