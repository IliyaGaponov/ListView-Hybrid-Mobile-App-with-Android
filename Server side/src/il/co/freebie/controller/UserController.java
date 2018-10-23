package il.co.freebie.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;


import il.co.freebie.model.DAOException;
import il.co.freebie.model.HibernateToDoListDAO;
import il.co.freebie.model.IToDoListDAO;
import il.co.freebie.model.ToDoItem;


/**
 * This class functions as a RESTful web service. This allows interaction between 
 * the view part and the model part .
 */
@Path("/user")
public class UserController {		
	private IToDoListDAO dao = HibernateToDoListDAO.getDAO();
	
	/**
	 * This method allows a user to get his personal to-do items using data access object.
	 * @param username the to-do items owner name
	 * @param header the http header
	 * @param response the http response
	 * @return the user's to-do items represented as list in Json format
	 */
	@GET
	@Path("/itemslist")
	@Produces(MediaType.APPLICATION_JSON)
	public String getToDoItems(@QueryParam("username") String username,
			@Context HttpHeaders header, @Context HttpServletResponse response) {
		List<ToDoItem> itemsList = null;
		int userId;
		
		try {
			userId = dao.getUserId(username);
			itemsList = dao.getToDoItems(userId);			
		} catch (DAOException e) {
			e.printStackTrace();
		}		
		
		//response.setHeader("Access-Control-Allow-Origin", "http://localhost:8888");		
		response.setHeader("Access-Control-Allow-Origin", "null");	
		
		return itemsList.toString();
	}
	
	/**
	 * This method creates a new to-do item using received parameters 
	 * and using data access object adds it into database. 
	 * @param itemName the name of the to-do item being created
	 * @param lastDate the date of deadline of the to-do item being created
	 * @param username the the to-do item owners name being created
	 * @param header the http header
	 * @param response the http response
	 * @return the boolean value packed as Json with which you can know if the method's 
	 * actions are executed as provided
	 */
	@GET
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	public String addToDoItem(@QueryParam("name") String itemName, 
			@QueryParam("date") String lastDate, @QueryParam("username") String username,
			@Context HttpHeaders header, @Context HttpServletResponse response) {		
		Boolean ifCompleted = false;
		
		try {
			int userId = dao.getUserId(username);
			if(userId != -1) {
				ToDoItem item = new ToDoItem(itemName, lastDate, 1, userId);
				ifCompleted = dao.add(item);
			}		
		} catch (DAOException e) {
			e.printStackTrace();
		}	
		
		response.setHeader("Access-Control-Allow-Origin", "null");	
		
		return ifCompleted.toString();
	}
	
	/**
	 * This method deletes an existing to-do item from database using data access object.
	 * @param itemId the item id being deleted
	 * @param header the http header
	 * @param response the http response
	 * @return the boolean value packed as Json with which you can know if the method's 
	 * actions are executed as provided
	 */
	@GET
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteToDoItem(@QueryParam("id") int itemId,
			@Context HttpHeaders header, @Context HttpServletResponse response) {
		Boolean ifCompleted = false;
		
		try {
			ToDoItem item = dao.getItemById(itemId);
			if(item != null) {
				ifCompleted = dao.delete(item);
			}
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		response.setHeader("Access-Control-Allow-Origin", "null");	
		
		return ifCompleted.toString();
	}	
	
	/**
	 * This method updates an existing to-do item in database using data access object.
	 * @param itemId the to-do item id
	 * @param itemName the to-do item name
	 * @param lastDate the date of deadline of the to-do item being updated
	 * @param header the http header 
	 * @param response the http response
	 * @return the boolean value packed as Json with which you can know if the method's 
	 * actions are executed as provided
	 */
	@GET
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	public String updateToDoItem(@QueryParam("id") int itemId, 
			@QueryParam("name") String itemName, @QueryParam("date") String lastDate, 
			@Context HttpHeaders header, @Context HttpServletResponse response) {
		Boolean ifCompleted = false;
		
		try {
			ToDoItem item = dao.getItemById(itemId);
			if(item != null) {
				item.setItemName(itemName);
				item.setLastDate(lastDate);
				ifCompleted = dao.updateToDoItem(item);
			}
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		response.setHeader("Access-Control-Allow-Origin", "null");	
		
		return ifCompleted.toString();
	}
	
	/**
	 * This method checks by user name if a user exists in database using data access object. 
	 * @param username the user name
	 * @param header the http header
	 * @param response the http response
	 * @return the boolean value as Json with which you can know if the user already exists
	 */
	@GET
	@Path("/checkuser")
	@Produces(MediaType.APPLICATION_JSON)
	public String checkUser(@QueryParam("username") String username,
			@Context HttpHeaders header, @Context HttpServletResponse response) {
		Boolean exists = false;
		
		try {
			exists = dao.checkIfUserExists(username);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		response.setHeader("Access-Control-Allow-Origin", "null");	
		
		return exists.toString();
	}
	
	/**
	 * This method uses data access object in order to login a user.
	 * @param username the user name
	 * @param password the user password
	 * @param header the http header
	 * @param response the http response
	 * @return the boolean value as Json with which you can know if the user is logged in
	 */
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public String login(@QueryParam("username") String username, @QueryParam("password") String password,
			@Context HttpHeaders header, @Context HttpServletResponse response)
	{
		Boolean ifMatches = false;
		
		try {
			ifMatches = dao.login(username, password);
		} catch (DAOException e) {
			e.printStackTrace();
		}		
		
		response.setHeader("Access-Control-Allow-Origin", "null");		
		
		return ifMatches.toString();
	}	
	
	/**
	 * This method uses data access object in order to register a user.
	 * @param username the user name
	 * @param password the user password
	 * @param header the http header
	 * @param response the http response
	 * @return the boolean value as Json with which you can know if the user is registered
	 */
	@POST
	@Path("/registration")
	@Produces(MediaType.APPLICATION_JSON)
	public String register(@QueryParam("username") String username, @QueryParam("password") String password,
			@Context HttpHeaders header, @Context HttpServletResponse response)
	{
		Boolean ifCompleted = false;
		
		try {
			ifCompleted = dao.register(username, password);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		response.setHeader("Access-Control-Allow-Origin", "null");	
		
		return ifCompleted.toString();
	}		
}
