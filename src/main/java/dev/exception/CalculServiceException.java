package dev.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import console.App;

public class CalculServiceException extends RuntimeException {
	
	/** serialVersionUID : long */
	private static final long serialVersionUID = 5232135507194512691L;
	
	private static final Logger LOG = LoggerFactory.getLogger(App.class);
	
	public CalculServiceException() {
		super();
	}

	public CalculServiceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CalculServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public CalculServiceException(Throwable cause) {
		super(cause);
	}
	
	public CalculServiceException(String msg) {
		super(msg);
	}
}
