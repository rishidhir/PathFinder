/**
 * This class implements an ordered list using a circular array 
 * @author rishidhir
 *
 * @param <T>
 */
public class OrderedCircularArray<T> implements SortedListADT<T> {
	
	//Declaring instance variables 
	private CellData[] list; //Array which stores CellData Objects
	private int front; //Reference to the index of the first, and smallest value Object in the circular Array
	private int rear; //Reference to the index of the last, and greatest value Object in the circular Array
	private int count; //Number of data items in the ordered circular array
	
	/**
	 * This is the class constructor which initializes the instance variables.  
	 */
	public OrderedCircularArray() {
		front = 1;
		rear = 0;
		count = 0;
		list =  (CellData<T>[])(new CellData[5]);
	}
	
	/**
	 * This is the insert method, which inserts a new CellData object into the ordered circular array at the correct index based on the value
	 * @param id, value - the parameters for the new Cell Data object
	 */
	public void insert(T id, int value) {
		CellData temp = new CellData(id, value);
		
		//Checking to see if array is at capacity 
		if(count == list.length) {
			CellData[] largerList = (CellData[])(new CellData[list.length*2]);
			
			//Using a for loop to copy all items from the smaller array to the larger array at the correct index place
			for(int i = 0; i < count; i++) {
				largerList[(front+i) % largerList.length] = list[(front+i) % list.length];
			}
			rear = ((front+count) -1) % largerList.length; //Updating rear to correctly index the last value in the list
			list = largerList;
		}
		
		//The case when the first item is being added to the list
		if(count == 0) {
			list[front] = temp;
			rear = front; 
		}	
		//Finding the correct index place to insert the new CellData object
		else {
			//The case when the value of the new CellData object is greater than the value of the last item in the list
			if(value > list[rear].getValue()) {
				rear = (rear+1) % list.length;
				list[rear] = temp;
			}
			//The case when we need to find the correct index to insert the new CellData object
			else {
				int current = front;
				//Using a while loop to find the correct index place to insert the new Cell Data object
				while(current != rear) {
					if(value > list[current].getValue())
						current = (current+1) % list.length;
					else break;
				}
				int i = rear;
				//Using a while loop to shift all data items rightwards in order to correctly place the new Object in the ordered list
				while(i != current %list.length) {
					list[(i+1) % list.length] = list[i];
					
					//Using if-else to correctly decrement the value of i in order to keep the integrity of the circular array
					if (i == 0) i = list.length-1; 
					else i = (i-1) % list.length;
				}
				list[(current+1)%list.length] = list[current]; //Shifting the element at the index position where the new element is being stored 
				rear = (rear+1) % list.length;
				list[current] = temp;
			}
		}
		count++;
	}
	
	/**
	 * This method returns the integer value of the CellData object with the specified id
	 * @param id
	 * @throws InvalidDataItemException - if the CellData object is not in the list 
	 */
	public int getValue(T id) throws InvalidDataItemException {
		boolean found = false;
		int current = front;
		//Using a while loop to find if the specified id is in the ordered circular array
		while(found == false) {
			if(list[current].getId().equals(id)) {
				found = true;
				break;
			}
			if(current == rear) break; //To ensure the loop isn't infinite 
			current = (current+1) % list.length;
		}
		//Throwing an exception if the id of the object is not in the list 
		if(found == false) {
			throw new InvalidDataItemException("Object with specified id is not in the list");
		}
		return list[current].getValue();
	}
	
	/**
	 * This method removes from the ordered list the CellData object with the specified id
	 * @param id
	 * @throws InvalidDataItemException - if the CellData object is not in the list 
	 */
	public void remove(T id) throws InvalidDataItemException {
		if(count > 0) {
			//The case when it the last item in the ordered circular array
			if(list[rear].getId().equals(id)) {
				list[rear] = null;
				rear = (rear-1) % list.length;
			}
			else {
				boolean found = false;
				int current = front;
				//using a while loop to find the index of the CellData object to be removed
				while(current != rear) {
					if(list[current].getId().equals(id)) {
						found = true;
						break;
					}
					current = (current+1) % list.length;
				}
				//Throwing an exception if the id of the object is not in the list 
				if(found == false)
					throw new InvalidDataItemException("Object with specified id is not in the list");
				else {
					//Using a for loop to correctly shift the elements leftwards 
					for(int i = current; i!=rear; i = (i+1) % list.length) {
						list[i] = list[(i+1) % list.length];
					}
					//Using if-else to correctly index the value of rear
					if (rear == 0) rear = list.length-1;
					else rear = (rear-1) % list.length;
				}
			}
			count--;
		}
	}
	
	/**
	 * This method changes the attribute of the CellData object at the specified id 
	 * @param id, newValue 
	 * @throws InvalidDataItemException - if the CellData object is not in the list 
	 */
	public void changeValue(T id, int newValue) throws InvalidDataItemException {
		if(count == 0) throw new InvalidDataItemException("There is nothing in the list"); 
		else {
			boolean found = false;
			int current = front;
			//Using a while loop to find the CellData object at the specified id 
			while(found == false) {
				if(list[current].getId().equals(id)) {
					found = true;
					break;
				}
				if(current == rear) break; 
				current = (current+1) % list.length;
			}
			//Throwing an exception if the id of the object is not in the list 
			if(found == false) {
				throw new InvalidDataItemException("Object with specified id is not in the list");
			}
			else {
				remove(id);
				insert(id, newValue);
			}
		}
	}
	
	/**
	 * This method removes and returns the CellData object with the smallest value 
	 * @throws EmptyListException 
	 */
	public T getSmallest() throws EmptyListException {
		//Throwing an exception if there is nothing in the list
		if(count==0) {
			throw new EmptyListException("There is nothing in the list");
		}
		
		T temp = (T) list[front].getId();
		list[front] = null;
		front = (front+1) % list.length; //Incrementing front correctly 
		count--; 
		return temp;
	}
	
	/**
	 * Returns true if the ordered list is empty, else false
	 */
	public boolean isEmpty() {
		if(count==0) return true;
		else return false;
	}
	
	/**
	 * Returns the size of the orderedCircularArray
	 * @return count
	 */
	public int size() {
		return count;
	}
	
	/**
	 * Getter method 
	 * @return front
	 */
	public int getFront() {
		return front;
	}
	
	/**
	 * Getter method
	 * @return rear
	 */
	public int getRear() {
		return rear;
	}
}
