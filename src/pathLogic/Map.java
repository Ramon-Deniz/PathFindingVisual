package pathLogic;

public class Map {

	// dimensions of map
	public int ROWS;
	public int COLUMNS;

	// current position
	public static int row;
	public static int col;
	
	public int startRow;
	public int startCol;

	// map as 2D array
	private int[][] map;

	// portion of map filled with blocks
	private double blocksPercent = 0.25;
	
	// minimum distance from start
	private final int DISTANCE=40;

	// total number of blocks
	private int amountOfBlocks;

	// ending point
	public int endRow;
	public int endCol;

	public Map(int rows, int columns) {
		this.ROWS = rows;
		this.COLUMNS = columns;

		map = new int[rows][columns];

		amountOfBlocks = (int) (rows * columns * blocksPercent);

		fillMap();
		
		setPoints();
	}

	private void fillMap() {
		for (int i = 0; i < amountOfBlocks; i++) {
			int randRow = (int) (Math.random() * (ROWS - 2)) + 1;
			int randCol = (int) (Math.random() * (COLUMNS - 2)) + 1;
			while (map[randRow][randCol] == -1) {
				randRow = (int) (Math.random() * (ROWS - 2)) + 1;
				randCol = (int) (int) (Math.random() * (COLUMNS - 2)) + 1;
			}
			map[randRow][randCol] = 1;
		}
	}

	// sets the starting and end points
	private void setPoints() {
		int randRow = -1;
		int randCol = -1;

		while (!validPoint(randRow, randCol)) {
			randRow = (int) (Math.random() * ROWS);
			randCol = (int) (Math.random() * COLUMNS);
		}

		// set starting position
		startRow = randRow;
		startCol = randCol;

		randRow = -1;
		randCol = -1;

		while (!validPoint(randRow, randCol) || isNear(randRow, randCol)) {
			randRow = (int) (Math.random() * ROWS);
			randCol = (int) (Math.random() * COLUMNS);
		}

		// set ending position
		endRow = randRow;
		endCol = randCol;

	}

	// determines whether or not the start/end point is valid
	private boolean validPoint(int tempRow, int tempCol) {
		if (tempRow < 1 || tempCol < 1 || tempRow >= ROWS - 1 || tempCol >= COLUMNS - 1
				|| map[tempRow][tempCol] == 1) {
			return false;
		}

		// amount of blocks allowed around the point
		int count = 0;

		// checks every element around the point
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (map[tempRow + i][tempCol + j] == -1) {
					count++;
				}
			}
		}

		return (count < 4);
	}

	// determines whether or not the tile is a block or is within the map
	public boolean validTile(int row, int col) {
		if (row < 0 || col < 0 || row >= ROWS || col >= COLUMNS) {
			return false;
		}
		return !(map[row][col] == -1);
	}

	public String toString() {
		String mapString = "";
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				mapString += map[i][j];
			}
			mapString += "\n";
		}
		return mapString;
	}

	public int[][] getMap() {
		return map;
	}

	private boolean isNear(int tempRow, int tempCol) {
		tempRow = Math.abs(tempRow - row);
		tempCol = Math.abs(tempCol - col);

		// distance calculation
		tempRow *= tempRow;
		tempCol *= tempCol;
		return (Math.sqrt(tempRow + tempCol) <= DISTANCE);
	}

}
