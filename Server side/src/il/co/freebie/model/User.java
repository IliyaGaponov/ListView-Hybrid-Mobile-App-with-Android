package il.co.freebie.model;

/**
 * This class implements a to-do list application user. 
 */
public class User {
	private String userName;
	private String password;
	private int id;
	
	/**
	 * This constructs a user.
	 */
	public User()
	{
		super();
	}
	
	/**
	 * This constructs a user with a specified name, password and id.
	 * @param name the user name
	 * @param password the password of this user 
	 * @param id the user id
	 */
	public User(String name, String password, int id) {
		setUserName(name);
		setPassword(password);
		setId(id);		
	}
	
	@Override
	public String toString() {
		return "User [name=" + userName + ", password=" + password + ", id=" + id + "]";
	}
	
	/**
	 * This returns the user name.
	 * @return the name of this user
	 */
	public String getUserName() {
		return userName;
	}
	
	/**
	 * This sets the user name.
	 * @param name the name of this user
	 */
	public void setUserName(String name) {
		this.userName = name;
	}
	
	/**
	 * This returns the user password.
	 * @return the password of this user
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * This sets the user password.
	 * @param password the password of this user
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * This returns the user id.
	 * @return the user id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * This sets the user id.
	 * @param id the user id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean ifEquals = false;		
		
		if(this.hashCode() == obj.hashCode())
		{
			ifEquals = true;
		}
		
		return ifEquals;
	}
	
	@Override
	public int hashCode() {
		return this.id;
	}
}
