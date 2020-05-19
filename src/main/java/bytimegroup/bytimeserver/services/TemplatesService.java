package bytimegroup.bytimeserver.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bytimegroup.bytimeserver.models.Chats;
import bytimegroup.bytimeserver.models.Delta;
import bytimegroup.bytimeserver.models.Roles;
import bytimegroup.bytimeserver.models.Templates;
import bytimegroup.bytimeserver.models.Users;
import bytimegroup.bytimeserver.repositories.RolesRepository;
import bytimegroup.bytimeserver.repositories.TemplatesRepository;

/**
 * Creates and edits templates, 
 * provides roles and couples
 * them with users 
 * TODO use interface as service declaration
 */
@Service
public class TemplatesService {
	
	@Autowired
	private TemplatesRepository templatesRepository;
	@Autowired
	private RolesRepository rolesRepository;
	
	/**
	 * @param creationDate
	 * @param chat
	 * @param user
	 * @return
	 */
	public Templates createTemplate(LocalDateTime creationDate, Chats chat, Users user) {
		// create entity
		Templates template = new Templates(creationDate, chat.get_id());
		// save entity and provide it with id by doing so
		return storeTemplate(template, user);
	}
	
	/**
	 * @param template
	 * @param user
	 * @return
	 */
	public Templates storeTemplate(Templates template, Users user) {
		// save instance and provide it with by id doing so
		template = templatesRepository.save(template);
		// create owner role for given user
		Roles role = new Roles(user.getID(), template.get_id(), "template", "owner", template.getCreationDate());
		// save the role and provide it with id by doing so
		role = rolesRepository.save(role);
		return template;
	}
	
	/**
	 * @param user
	 * @return
	 */
	public List<Templates> getTemplates(Users user) {
		List<Roles> userTemplateRoles = rolesRepository.findByUserIdAndType(user.getID(), "template");
		List<String> templateIds = userTemplateRoles.stream().map(Roles::getEntityId).collect(Collectors.toList());
		return (List<Templates>) templatesRepository.findAllById(templateIds);
	}
	
//	public Boolean deleteTemplate(String templateId, Users user) throws AccessDeniedException {
//		// check if template there
//		Templates template;
//		try {
//			template = templatesRepository.findById(templateId).get();
//		} catch (NoSuchElementException nsee) {
//			return false;
//		}
//		// check if owner
//		Roles userTemplateRole = rolesRepository.findByEntityIdAndUserId(templateId, user.getID());
//		if (userTemplateRole == null) {
//			throw new AccessDeniedException();
//		}
//		if (userTemplateRole.getAccessLevel().equals("owner")) {
//			// delete entity
//			templatesRepository.deleteById(templateId);
//			// remember user ids for notification
//		}
//	}
	
	/**
	 * @param entityId
	 * @param change
	 * @param user
	 * @return
	 * @throws AccessDeniedException
	 * @throws NoSuchElementException if entityId unvalid
	 */
	public Boolean updateTemplate(String entityId, Delta change, Users user) throws AccessDeniedException, NoSuchElementException  {
		// check if entry is valid
		Templates template = templatesRepository.findById(entityId).get();
		// check if permission is sufficient to perform an update
		Roles userTemplateRole = rolesRepository.findByEntityIdAndUserId(entityId, user.getID());
		if (userTemplateRole == null || userTemplateRole.getAccessLevelCode() < 70) {
			throw new AccessDeniedException();
		}
		// try to perform an update
		switch (change.getField().toLowerCase()) {
		}
		
		template = templatesRepository.save(template);
		// TODO notify other participants? 
		return true;
	}
	
}
