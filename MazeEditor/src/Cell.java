
public class Cell {
	private boolean visited;
	private int x,y;
	
	public Cell(int x, int y) {
		visited = false;
		this.x = x;
		this.y = y;
	}
	
	public static int[][] convertCellMapToIntMap(Cell[][] cellMap, int width, int height) {
		int[][] intMap;
		intMap = new int[width][height];
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				intMap[i][j] = (cellMap[i][j].isVisited()) ? 0 : 1;
			}
		}
		return intMap;
	}


	public void setVisited() {
		visited = true;
	}
	public int getX() {return x;}
	public int getY() {return y;}

	public boolean isVisited() {
		return visited;
	}

	
	public String toString() {
		return (visited) ? "1": "0";
	}
}
