package bytimegroup.bytimeserver.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception used to inform about forbidden access
 * @ResponseStatus helps to translate exception to a ResponseEntity
 */
@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class AccessDeniedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

}
