/**
 * This class represents the shortestPath from a starting cell to a destination cell if such a path exists 
 * @author rishidhir
 *
 */
public class ShortestPath {

	CityMap cityMap; //Declaring an instance variable that refers to the object representing the Map where you find a path
	
	/**
	 * This is the constructor that initializes the instance variable with the parameter passed.
	 * @param theMap
	 */
	public ShortestPath(CityMap theMap) {
		cityMap = theMap;
	}
	
	/**
	 * This method represents the algorithm for the shortestPath from a starting cell to a destination cell if such path exists
	 */
	public void findShortestPath() {
		OrderedCircularArray circularArray = new OrderedCircularArray(); //Initializing an ordered circular array
		MapCell cell = cityMap.getStart(); //Cell refers to the starting place on the Map
		
		//Inserting the cell into the circularArray and marking it in the list
		circularArray.insert(cell, 0);
		cell.markInList();
		
		int distanceLength = 0; //This represents the length of the shortest path if such a path exists 
		
		//Using a while loop to find the shortestPath if it exists
		while(!circularArray.isEmpty() && !cell.isDestination()) {
			try {
				cell = (MapCell) circularArray.getSmallest(); //Storing the cell with the smallest value
				cell.markOutList(); 
			}
			catch (EmptyListException e) {
				System.out.println(e);
			}
			
			if(cell.isDestination()) break; //Checking to see if the current cell is the destination cell
			else {
				MapCell nextCell = nextCell(cell); //using the nextCell method to find the next cell to go to 
				
				while (nextCell != null) {
					int distance = 1 + cell.getDistanceToStart(); //distance between current cell and starting cell
					
					//Checking to see if the distance from start is greater from the nextCell or the current cell
					if(nextCell.getDistanceToStart() > distance) {
						nextCell.setDistanceToStart(distance);
						nextCell.setPredecessor(cell);
					}
					
					int nextCellDistance = nextCell.getDistanceToStart(); //distance between nextCell and starting cell
					
					
					if(nextCell.isMarkedInList() && nextCellDistance < circularArray.getValue(nextCell)) {
						try{
							circularArray.changeValue(nextCell, nextCellDistance);
						}
						catch (InvalidDataItemException e) {
							System.out.println(e);
						}
					}
					else {
						circularArray.insert(nextCell, nextCellDistance);
						nextCell.markInList();
					}
					nextCell = nextCell(cell); //Finding the nextCell 
				}
			}
		}

		if(cell.isDestination()) distanceLength = cell.getDistanceToStart()+1; //updating distanceLength if path is found
		
		if (distanceLength == 0) System.out.println("No solution");
		else System.out.println("Path found of length " + distanceLength);
	}
	
	/**
	 * This method finds the nextCell to go to based on the current cell and the given conditions
	 * @param cell - the current cell
	 * @return targetCell 
	 */
	private MapCell nextCell(MapCell cell) {
		MapCell targetCell = null;
		
		//Using an if-else  block to ensure that if you are on a direction road, you cannot cross over to another cell unless it follows the conditions. 
		if(cell.isNorthRoad()) {
			if (cell.getNeighbour(0) != null && !cell.getNeighbour(0).isMarked() && !cell.getNeighbour(0).isBlock()) //Checking the necessary conditions
				return targetCell = cell.getNeighbour(0);
			else return null;
		}
		else if(cell.isEastRoad()) {
			if (cell.getNeighbour(1) != null && !cell.getNeighbour(1).isMarked() && !cell.getNeighbour(1).isBlock())
				return targetCell = cell.getNeighbour(1);
			else return null;
		}	
		else if(cell.isSouthRoad()) {
			if (cell.getNeighbour(2) != null && !cell.getNeighbour(2).isMarked() && !cell.getNeighbour(2).isBlock())
				return targetCell = cell.getNeighbour(2);
			else return null;
		}
		else if(cell.isWestRoad()) {
			if (cell.getNeighbour(3) != null && !cell.getNeighbour(3).isMarked() && !cell.getNeighbour(3).isBlock())
				return targetCell = cell.getNeighbour(3);
			else return null;
		}
		
		//If the current cell is not a direction road cell, it will find another cell based on given conditions
		else { 
			
			//This loop checks to see if the next cell is the destination cell and if it follows the conditions. 
			for(int i = 0; i < 4; i++) {
				if(cell.getNeighbour(i) != null && !cell.getNeighbour(i).isMarked() && !cell.getNeighbour(i).isBlock() && cell.getNeighbour(i).isDestination()) 
					return targetCell = cell.getNeighbour(i);	
			}
			
			//This loop checks to see if the next cell is the intersection cell and if it follows the conditions. 
			for(int i = 0; i < 4; i++) {
				if(cell.getNeighbour(i) != null && !cell.getNeighbour(i).isMarked() && !cell.getNeighbour(i).isBlock() && cell.getNeighbour(i).isIntersection())
					return targetCell = cell.getNeighbour(i);
			}
			
			//Using if-else statements to see if the next cell is a direction cell and if it follows the conditions. 
			if (cell.getNeighbour(0) != null && !cell.getNeighbour(0).isMarked() && !cell.getNeighbour(0).isBlock() && cell.getNeighbour(0).isNorthRoad())
				return targetCell = cell.getNeighbour(0);
			else if (cell.getNeighbour(1) != null && !cell.getNeighbour(1).isMarked() && !cell.getNeighbour(1).isBlock() && cell.getNeighbour(1).isEastRoad())
				return targetCell = cell.getNeighbour(1);
			else if (cell.getNeighbour(2) != null && !cell.getNeighbour(2).isMarked() && !cell.getNeighbour(2).isBlock() && cell.getNeighbour(2).isSouthRoad())
				return targetCell = cell.getNeighbour(2);
			else if (cell.getNeighbour(3) != null && !cell.getNeighbour(3).isMarked() && !cell.getNeighbour(3).isBlock() && cell.getNeighbour(3).isWestRoad())
				return targetCell = cell.getNeighbour(3);					
			
			return targetCell;
		}
	}
}
