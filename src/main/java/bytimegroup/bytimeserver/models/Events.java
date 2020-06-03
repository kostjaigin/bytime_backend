package bytimegroup.bytimeserver.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * todo: insert @AliasFor spring annotation
 * Represents event document from mongo
 */
@Document
public class Events extends EntityBase {
	
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private String calendarId;
	private String chatId;
	
	public Events() {}
	public Events(ObjectId _id, LocalDateTime startDate, LocalDateTime endDate, LocalDateTime creationDate,
					String calendarId, String chatId) {
		super(_id, creationDate);
		this.startDate = startDate;
		this.endDate = endDate;
		this.calendarId = calendarId;
		this.chatId = chatId;
	}
	public Events(LocalDateTime startDate, LocalDateTime endDate, LocalDateTime creationDate,
			String calendarId, String chatId) {
		super(creationDate);
		this.startDate = startDate;
		this.endDate = endDate;
		this.calendarId = calendarId;
		this.chatId = chatId;
	}
	
	public LocalDateTime getStartDate() {
		return startDate;
	}
	public LocalDateTime getEndDate() {
		return endDate;
	}
	public String getCalendarId() {
		return calendarId;
	}
	public String getChatId() {
		return chatId;
	}
	
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}
	public void setCalendarId(String calendarId) {
		this.calendarId = calendarId;
	}
	public void setChatid(String chatId) {
		this.chatId = chatId;
	}
	
}
