package pathLogic;

public class Path implements Comparable<Path> {
	// position of path
	private int row;
	private int col;

	// distance from starting node
	private int gCost;

	// distance from end node
	private int hCost;

	// parent node/path
	public Path previous;

	// position of end node
	public static int endRow;
	public static int endCol;

	public Path(int row, int col, int gCost) {
		this.row = row;
		this.col = col;
		this.gCost = gCost;
		
		setHCost();
	}

	// fastest pathway from current path to endpath
	private void setHCost() {
		int cost = 0;
		int tempRow = row;
		int tempCol = col;

		// diagonal
		if (PathWay.allowDiagonals) {
			while (tempRow != endRow && tempCol != endCol) {
				tempRow += (tempRow < endRow) ? 1 : -1;
				tempCol += (tempCol < endCol) ? 1 : -1;
				cost += 14;

			}
		}
		
		// left/right
		while (tempRow != endRow) {
			tempRow += (tempRow < endRow) ? 1 : -1;
			cost += 10;
		}
		
		// up/down
		while (tempCol != endCol) {
			tempCol += (tempCol < endCol) ? 1 : -1;
			cost += 10;
		}
		
		hCost = cost;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null || !(other instanceof Path)) {
			return false;
		}
		
		Path tempPath = (Path) other;
		
		return (tempPath.row == this.row && tempPath.col == this.col);
	}

	@Override
	public int hashCode() {
		return 3517 * (row + 1) + col;
	}

	@Override
	public int compareTo(Path other) {
		// compare total costs
		if ((gCost + hCost) != (other.hCost + other.gCost)) {
			return (gCost + hCost) - (other.hCost + other.gCost);
		}
		
		// compare distances from end node
		else if (hCost != other.hCost) {
			return hCost - other.hCost;
		}
		
		// compare rows
		else if (row != other.row) {
			return row - other.row;
		}
		
		// compare columns
		else if (col != other.col) {
			return col - other.col;
		}
		
		return 0;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return col;
	}

	public int getGCost() {
		return gCost;
	}
	
	public int getTotalCost() {
		return gCost+hCost;
	}

}
