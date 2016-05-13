
public class Cell {
	private boolean visited;
	private boolean wall;
	
	public Cell() {
		visited = false;
		wall = true;
	}
	
	public static int[][] convertCellMapToIntMap(Cell[][] cellMap, int width, int height) {
		int[][] intMap;
		intMap = new int[width][height];
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				intMap[i][j] = (cellMap[i][j].isWall()) ? 1 : 0;
			}
		}
		return intMap;
	}

	private boolean isWall() {
		return wall;
	}

	public void setVisited() {
		visited = true;
	}
}
