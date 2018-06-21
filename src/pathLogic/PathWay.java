package pathLogic;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class PathWay {
	// allow diagonals to be considered for shortest path
	public static boolean allowDiagonals = false;

	private Path current;
	private Path end;

	private int[][] map;

	private Set<Path> open;
	private Set<Path> closed;
	
	public static int count=0;

	@SuppressWarnings("static-access")
	public PathWay(int[][] map, int startRow, int startCol, int endRow, int endCol, boolean allowDiagonals) throws PathNotFoundException {
		this.map = map;
		this.allowDiagonals = allowDiagonals;
		Path.endRow = endRow;
		Path.endCol = endCol;
		
		open = new HashSet<>();
		closed = new HashSet<>();

		current = new Path(startRow, startCol, 0);
		end = new Path(endRow, endCol, 0);
		
		open.add(current);
		
		explore();
		
	}

	private void explore() throws PathNotFoundException {
		count++;
		
		if (open.isEmpty()) {
			return;
		}
		
		current = getShortestPath();

		if (current.equals(end)) {
			return;
		}

		open.remove(current);
		closed.add(current);

		// check every tile around current path
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				// calculate gCost
				int gCost = (i != 0 && j != 0) ? 14 : 10;

				// skips if 'i' and 'j' is zero or diagonal
				if (i == 0 && j == 0 || (gCost==14 && !allowDiagonals)) {
					continue;
				}
				
				int tempRow = current.getRow()+i;
				int tempCol = current.getColumn()+j;
				
				// skip if invalid tile
				if(!validTile(tempRow,tempCol)) {
					continue;
				}
				
				// skips if diagonal is invalid
				if(gCost==14 && (map[tempRow-i][tempCol]==1 || map[tempRow][tempCol-j]==1)) {
					continue;
				}
				
				// add up total gCost
				gCost+=current.getGCost();

				Path tempPath = new Path(tempRow, tempCol, gCost);
				
				if(!closed.contains(tempPath) && isShorterPath(tempPath)) {
					tempPath.previous = current;
					open.add(tempPath);
				}
			}
		}
		
		explore();
	}

	private Path getShortestPath() {
		Iterator<Path> iterator = open.iterator();
		Path shortestPath = iterator.next();
		
		while (iterator.hasNext()) {
			Path compare = iterator.next();
			if (compare.compareTo(shortestPath) < 0) {
				shortestPath = compare;
			}
		}
		
		return shortestPath;
	}
	
	private boolean isShorterPath(Path potentialPath) {
		if(!open.contains(potentialPath)) {
			return true;
		}
		
		Iterator<Path> iterator = open.iterator();
		
		Path tempPath=null;
		
		while(!potentialPath.equals(tempPath)) {
			tempPath = iterator.next();
		}
		
		if(potentialPath.compareTo(tempPath)<0) {
			open.remove(tempPath);
			return true;
		}
		
		return false;
	}
	
	// determines whether or not the tile is a block or is within the map
	private boolean validTile(int row, int col) {
		if (row < 0 || col < 0 || row >= map.length || col >= map[0].length) {
			return false;
		}
		
		return !(map[row][col] == 1);
	}
	
	public Path getFastestPath() {
		return current;
	}

}
