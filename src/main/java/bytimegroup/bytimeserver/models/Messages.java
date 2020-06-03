package bytimegroup.bytimeserver.models;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Messages extends EntityBase {

	private String senderId;
	/**
	 * 0:title, 1:startdate, 2:enddate, 
	 * 3:text, 4:photo, 5:todo, 6:file
	 */
	private int messageType;
	private String messageBody;
	
	public Messages() {}
	public Messages(ObjectId _id, String senderId, int messageType, String messageBody, LocalDateTime creationDate) {
		super(_id, creationDate);
		this.senderId = senderId;
		this.messageType = messageType;
		this.messageBody = messageBody;
	}
	public Messages(String senderId, int messageType, String messageBody, LocalDateTime creationDate) {
		super(creationDate);
		this.senderId = senderId;
		this.messageType = messageType;
		this.messageBody = messageBody;
	}
	
	public String getSenderId() {
		return senderId;
	}
	public int getMessageType() {
		return messageType;
	}
	public String getMessageBody() {
		return messageBody;
	}
	
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}
	
}
