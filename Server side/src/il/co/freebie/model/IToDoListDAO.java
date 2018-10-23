package il.co.freebie.model;

import java.util.List;

/**
 * This interface specify a behavior of the classes that will implement it.
 * This interface promotes interaction with the database.
 */
public interface IToDoListDAO {
	/**
	 * This method allows to add a new user or a new to-do item to database.
	 * @param obj the object that represents a user or a to-do item
	 * @return the boolean value with which you can know if the method's actions are executed as provided
	 * @throws DAOException 
	 */
	public boolean add(Object obj) throws DAOException;
	
	/**
	 * This method allows a user to update an existing to-do item in database.
	 * @param item the updated to-do item that we want to see in database 
	 * @return the boolean value with which you can know if the method's actions are executed as provided
	 * @throws DAOException
	 */
	public boolean updateToDoItem(ToDoItem item) throws DAOException;
	
	/**
	 * This method allows a user to delete an existing to-do item from database.
	 * @param item the to-do item to be removed
	 * @return the boolean value with which you can know if the method's actions are executed as provided
	 * @throws DAOException
	 */
	public boolean delete(ToDoItem item) throws DAOException;
	
	/**
	 * This method allows a user to get his personal to-do items.
	 * @param userId the to-do items owner id 
	 * @return the list with the user's to-do items
	 * @throws DAOException
	 */
	public List<ToDoItem> getToDoItems(int userId) throws DAOException;
	
	/**
	 * This method allows a user to login.
	 * @param userName the user name
	 * @param password the user password
	 * @return the boolean value with which you can know if the user name and the password are matching 
	 * to each other
	 * @throws DAOException
	 */
	public boolean login(String userName, String password) throws DAOException;
	
	/**
	 * This method allows a user to register.
	 * @param userName the user name
	 * @param password the user password
	 * @return the boolean value with which you can know if the user is registered 
	 * @throws DAOException
	 */
	public boolean register(String userName, String password) throws DAOException;
	
	/**
	 * This method allows to check by user name if a user exists in database
	 * @param userName the user name
	 * @return the boolean value with which you can know if the user already exists
	 * @throws DAOException
	 */
	public boolean checkIfUserExists(String userName) throws DAOException;
	
	/**
	 * This method returns a user id by a user name.
	 * @param username the user name
	 * @return the requested user id
	 * @throws DAOException
	 */
	public int getUserId(String username) throws DAOException;
	
	/**
	 * This method returns a to-do item by it's id.
	 * @param itemId the to-do item id
	 * @return the requested to-do item
	 * @throws DAOException
	 */
	public ToDoItem getItemById(int itemId) throws DAOException;
}
