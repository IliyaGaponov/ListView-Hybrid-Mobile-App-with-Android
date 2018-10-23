package il.co.freebie.model;

/**
 * This class implements to-do item.
 */
public class ToDoItem {
	private String itemName;
	private String lastDate;
	private int itemId;
	private int userId;	
	
	/**
	 * This constructs a to-do item.
	 */
	public ToDoItem() {
		super();
	}	

	/**
	 * This constructs a to-do item with a specified name, last date, id and user's id.
	 * @param itemName the name of the to-do item
	 * @param lastDate the date of deadline
	 * @param itemId the to-do item id
	 * @param userId the id of the user to which the to-do item relates
	 */
	public ToDoItem(String itemName, String lastDate, int itemId, int userId) {
		setItemName(itemName);
		setLastDate(lastDate);
		setItemId(itemId);
		setUserId(userId);
	}
	
	/**
	 * This returns the id of the user to which the to-do item relates.
	 * @return to-do item's user id
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * This sets the id of the user to which the to-do item relates.
	 * @param userId the id of the user to which the to-do item relates
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * This returns the item name.
	 * @return the name of this item
	 */
	public String getItemName() {
		return itemName;
	}
	
	/**
	 * This sets the name of this item.
	 * @param itemName the item name
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	/**
	 * This returns the date of deadline.
	 * @return the date of deadline
	 */
	public String getLastDate() {
		return lastDate;
	}
	
	/**
	 * This sets the date of deadline.
	 * @param lastDate
	 */
	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}
	
	/**
	 * This returns the to-do item id.
	 * @return the to-do item id
	 */
	public int getItemId() {
		return itemId;
	}
	
	/**
	 * This sets the to-do item id.
	 * @param the to-do item id
	 */
	public void setItemId(int id) {
		this.itemId = id;
	}	
	
	@Override
	public String toString() {		
		return "{\"itemName\":" + "\"" + this.itemName + "\",\"lastDate\":" + "\"" +
				this.lastDate + "\",\"itemId\":" + this.getItemId() + "}";
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
		return this.itemId;
	}
}
