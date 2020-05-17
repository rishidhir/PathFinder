/**
 * This class represents the data items that will be stored in the circular array 
 * @author rishidhir
 *
 * @param <T>
 */
public class CellData<T> {
	
	//Declaring instance variables
	private T id; //A reference to the identifier stored in this object
	private int value; //Value of the data to be stored in the object
	
	/**
	 * This is the constructor for the class which initializes the instance variables with the parameters.  
	 * @param theId
	 * @param theValue
	 */
	public CellData(T theId, int theValue) {
		id = theId;
		value = theValue;
	}
	
	/**
	 * Getter method 
	 * @return value 
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Getter method
	 * @return id
	 */
	public T getId() {
		return id;
	}
	
	/**
	 * Setter method
	 * @param newValue
	 */
	public void setValue(int newValue) {
		value = newValue;
	}
	
	/**
	 * Setter method 
	 * @param newId
	 */
	public void setId(T newId) {
		id = newId;
	}
}
