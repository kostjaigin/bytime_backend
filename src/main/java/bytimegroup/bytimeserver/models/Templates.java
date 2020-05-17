package bytimegroup.bytimeserver.models;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Templates extends EntityBase {
	
	private String chatId;
	
	public Templates() {}
	public Templates(ObjectId _id, LocalDateTime creationDate, String chatId) {
		super(_id, creationDate);
		this.chatId = chatId;
	}
	public Templates(LocalDateTime creationDate, String chatId) {
		super(creationDate);
		this.chatId = chatId;
	}
	
	public String getChatId() {
		return chatId;
	}

	public void setChatid(String chatId) {
		this.chatId = chatId;
	}
}
