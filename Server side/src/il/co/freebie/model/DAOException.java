package il.co.freebie.model;

/**
 * This class implements a data access object exception.
 *
 */
public class DAOException extends Exception {
	/**
	 * This constructs a dao exception with a specified arguments. 
	 */
	public DAOException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * This constructs a dao exception with a specified argument. 
	 * @param message the message of the exception
	 */
	public DAOException(String message) {
		super(message);
	}



}
