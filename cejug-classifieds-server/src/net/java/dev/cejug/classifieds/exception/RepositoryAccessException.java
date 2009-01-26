package net.java.dev.cejug.classifieds.exception;

import java.io.IOException;

/**
 * All attachments (images, videos, etc.) are stored in a content repository (by
 * default a File System). This exception is thrown in case of problems trying
 * to access the repository.
 * 
 * @author $Author$
 * @version $Rev$ ($Date$)
 * 
 */
public class RepositoryAccessException extends RuntimeException {
	/**
	 * default uid.
	 */
	private static final long serialVersionUID = 1L;

	/** just call the super constructor. */
	public RepositoryAccessException(IOException e) {
		super(e);
	}

	/** just call the super constructor. */
	public RepositoryAccessException(String string) {
		super(string);
	}
}
