package bytimegroup.bytimeserver.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bytimegroup.bytimeserver.models.Chats;
import bytimegroup.bytimeserver.models.Messages;
import bytimegroup.bytimeserver.models.Users;
import bytimegroup.bytimeserver.repositories.ChatsRepository;
import bytimegroup.bytimeserver.repositories.MessagesRepository;

/**
 * Creates and edits chats,
 * manages messages coupling.
 * For now chats include no roles.
 */
@Service
public class ChatsService {

	@Autowired
	private ChatsRepository chatsRepository;
	@Autowired
	private MessagesRepository messagesRepository;
	 
	/**
	 * @param title
	 * @param messagesIds
	 * @param creationDate
	 * @return freshly created instance
	 */
	public Chats createChat(String title, List<String> messagesIds, LocalDateTime creationDate) {
		// create instance
		Chats chat = new Chats(title, messagesIds, creationDate);
		// save instance and provide it with id by doing so
		chat = chatsRepository.save(chat);
		return chat;
	}
	
	public Chats addMessage(Chats chat, Messages msg) {
		chat.addMessageId(msg.get_id());
		chat = chatsRepository.save(chat);
		return chat;
	}
	
}
