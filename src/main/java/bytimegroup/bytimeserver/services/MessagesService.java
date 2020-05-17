package bytimegroup.bytimeserver.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bytimegroup.bytimeserver.models.Messages;
import bytimegroup.bytimeserver.models.Users;
import bytimegroup.bytimeserver.repositories.MessagesRepository;

@Service
public class MessagesService {

	@Autowired
	private MessagesRepository messagesRepository;
	
	/**
	 * @param sender
	 * @param messageType:
	 * 0:title, 1:startdate, 2:enddate, 
	 * 3:text, 4:photo, 5:todo, 6:file
	 * @param body
	 * @param creationDate
	 * @return
	 */
	public Messages createMessage(Users sender, int messageType, String body, LocalDateTime creationDate) {
		// create entity
		Messages message = new Messages(sender.getID(), messageType, body, creationDate);
		// save entity and provide it with id
		message = messagesRepository.save(message);
		return message;
	}
	
}
