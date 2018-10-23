package il.co.freebie.test;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import il.co.freebie.model.*;

/**
 * This class implements a simple test for the model part. 
 * Before running the test make sure your database is empty.
 */
public class ModelTester {	
	/**
	 * This method tests the model part.
	 */
	@Test
	public void testModel() {
		IToDoListDAO dao = HibernateToDoListDAO.getDAO();
		
		try {
			dao.register("Andrey", "123");
			dao.register("Andrey", "1234");//nothing happens here because Andrey is exist
			dao.register("Dani", "12345");
			dao.register("Kate", "2301");
			dao.register("Omer", "2301");
			Assert.assertTrue(dao.login("Andrey", "123"));
			Assert.assertFalse(dao.login("Andrey", "1234"));
		} catch (DAOException e) {
			e.printStackTrace();
		}		
		
		ToDoItem item1 = new ToDoItem("Take dogs to the park", "14/05/18", 1, 2);
		ToDoItem item2 = new ToDoItem("Buy milk", "13/05/18", 2, 2);
		ToDoItem item3 = new ToDoItem("Make project phase 1", "20/05/18", 3, 4);
		ToDoItem item4 = new ToDoItem("Buy a ticket to Asia", "20/05/18", 4, 2);
		List<ToDoItem> itemsList;	
		try {
			dao.add(item1);
			dao.add(item2);
			dao.add(item3);
			dao.add(item4);
		    itemsList = dao.getToDoItems(item2.getUserId());
			Assert.assertArrayEquals(new ToDoItem[]{item1, item2, item4}, itemsList.toArray());
			dao.delete(item2);
			itemsList = dao.getToDoItems(item2.getUserId());
			Assert.assertArrayEquals(new ToDoItem[]{item1, item4}, itemsList.toArray());
			ToDoItem updatedItem3 = new ToDoItem("Make project phase 2", "27/05/18", 3, 4);
			dao.updateToDoItem(updatedItem3);
			itemsList = dao.getToDoItems(updatedItem3.getUserId());
			Assert.assertArrayEquals(new ToDoItem[]{updatedItem3}, itemsList.toArray());
			Assert.assertEquals(updatedItem3.getItemName(), ((ToDoItem) itemsList.toArray()[0]).getItemName());
			Assert.assertEquals(updatedItem3.getLastDate(), ((ToDoItem)itemsList.toArray()[0]).getLastDate());
		} catch (DAOException e1) {
			e1.printStackTrace();
		}		
	}
}
