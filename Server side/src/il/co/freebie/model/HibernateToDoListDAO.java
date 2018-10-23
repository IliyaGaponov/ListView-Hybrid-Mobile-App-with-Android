package il.co.freebie.model;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * This class implements IToDoListDAO interface. 
 * This class represents data access object and allows management of database.
 */
public class HibernateToDoListDAO implements IToDoListDAO {
	private SessionFactory factory;
	private static HibernateToDoListDAO dao;
	
	/**
	 * This constructs data access object and creates new session factory.
	 */
	private HibernateToDoListDAO() {
		factory = new AnnotationConfiguration().configure().buildSessionFactory();
	}
	
	/**
	 * This method allows to create single data access object or to get an existing data access object.
	 * @return the single data access object
	 */
	public static HibernateToDoListDAO getDAO() {
		if(dao == null) {
			dao = new HibernateToDoListDAO();
		}
		
		return dao;
	}
	
	@Override
	public boolean add(Object obj) throws DAOException {
		boolean ifCompleted = false;
		Session session = factory.openSession();
		
		try {
			session.beginTransaction();	
			if(obj instanceof ToDoItem)	{
				session.save("ToDoItem", obj);	  	
			}
			else if(obj instanceof User) {				
				session.save("User", obj);					
			}
			
			session.getTransaction().commit();
			ifCompleted = true;
		}
		catch(HibernateException e) {
			if(session.getTransaction() != null)
			{
				session.getTransaction().rollback();
			}			
		}
		finally {
			session.close();
		}	
		
		return ifCompleted;
	}

	@Override
	public boolean delete(ToDoItem item) throws DAOException {
		boolean ifCompleted = false;
		Session session = factory.openSession();
		
		try {
			session.beginTransaction();
			session.delete("ToDoItem", item);			
			session.getTransaction().commit();
			ifCompleted = true;
		}
		catch(HibernateException e) {
			if(session.getTransaction() != null)
			{
				session.getTransaction().rollback();
			}			
		}
		finally {
			session.close();
		}	

		return ifCompleted;	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ToDoItem> getToDoItems(int userId) throws DAOException {		
		List<ToDoItem> itemsList = null;
		Session session = factory.openSession();
		
		try {
			session.beginTransaction();
			Query query = session.createQuery("select i from ToDoItem i where i.userId = :id");
			query.setInteger("id", userId);
			itemsList = query.list();			
			session.getTransaction().commit();
		}
		catch(HibernateException e) {
			System.out.println(e.getMessage());
			if(session.getTransaction() != null)
			{
				session.getTransaction().rollback();
			}			
		}
		finally {
			session.close();
		}	
		
		return itemsList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean login(String userName, String password) throws DAOException {
		boolean ifMatches = false;
		
		if(userName != null && password != null)
		{
			Session session = factory.openSession();
			
			try {
				session.beginTransaction();
				Query query = session.createQuery("select u from User u where u.userName = :key");
				query.setString("key", userName);
				if(query.uniqueResult() != null) {
					User user = (User) query.uniqueResult();					
					if(user.getPassword().equals(password))
					{
						ifMatches = true;
					}
				}
				
				session.getTransaction().commit();
			}
			catch(HibernateException e) {
				System.out.println("Login method dao: " + e.getMessage());
				if(session.getTransaction() != null)
				{
					session.getTransaction().rollback();
				}			
			}
			finally {
				session.close();
			}		
		}

		return ifMatches;	
	}		
	
	@Override
	public boolean register(String userName, String password) throws DAOException {		
		boolean ifCompleted = false;

		if(userName != null && password != null)
		{
			if(!checkIfUserExists(userName))
			{
				User user = new User(userName, password, 1);
				add(user);
				ifCompleted = true;
			}
		}		
		
		return ifCompleted;				
	}
	
	public boolean checkIfUserExists(String userName) throws DAOException
	{	
		boolean ifExists = false;
		Session session = factory.openSession();
		
		try {
			session.beginTransaction();
			Query query = session.createQuery("select 1 from User u where u.userName = :key");
			query.setString("key", userName);
			if(query.uniqueResult() != null) {
				ifExists = true;
			}
			
			session.getTransaction().commit();
		}
		catch(HibernateException e) {
			if(session.getTransaction() != null)
			{
				session.getTransaction().rollback();
			}			
		}
		finally {
			session.close();
		}		

		return ifExists;
	}

	@Override
	public boolean updateToDoItem(ToDoItem item) throws DAOException {
		boolean ifCompleted = false;		
		Session session = factory.openSession();		
		
		try {
			session.beginTransaction();
			Query query = session.createQuery("update ToDoItem i set i.itemName = :newItemName, i.lastDate = :newLastDate where i.itemId = :id");
			query.setString("newItemName", item.getItemName());	
			query.setString("newLastDate", item.getLastDate());
			query.setInteger("id", item.getItemId());
			query.executeUpdate();	
			session.getTransaction().commit();
			ifCompleted = true;
		}
		catch(HibernateException e) {
			System.out.println(e.getMessage());
			if(session.getTransaction() != null)
			{
				session.getTransaction().rollback();
			}			
		}
		finally {
			session.close();
		}		
		
		return ifCompleted;
	}	
	
	@Override
	public int getUserId(String username) {
		int userId = -1;
		
		Session session = factory.openSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery("select u from User u where u.userName = :key");
			query.setString("key", username);
			if(query.uniqueResult() != null) {
				User user = (User) query.uniqueResult();					
				userId = user.getId();
			}
			
			session.getTransaction().commit();
		}
		catch(HibernateException e) {
			if(session.getTransaction() != null)
			{
				session.getTransaction().rollback();
			}			
		}
		finally {
			session.close();
		}		
		
		return userId;
	}

	@Override
	public ToDoItem getItemById(int itemId) throws DAOException {
		ToDoItem item = null;
		
		Session session = factory.openSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery("select i from ToDoItem i where i.itemId = :key");
			query.setInteger("key", itemId);
			if(query.uniqueResult() != null) {
				item = (ToDoItem) query.uniqueResult();						
			}
			
			session.getTransaction().commit();
		}
		catch(HibernateException e) {
			if(session.getTransaction() != null)
			{
				session.getTransaction().rollback();
			}			
		}
		finally {
			session.close();
		}
		
		return item;
	}
}
