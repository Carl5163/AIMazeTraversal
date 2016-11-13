import java.util.PriorityQueue;

public class Pathfinder {
	
	// This class was made following a tutorial at 
	// http://www.codebytes.in/2015/02/a-shortest-path-finding-algorithm.html
	
	public static final int COST = 10;
	
	private Cell[][] map;
	private int endX, endY;
	private PriorityQueue<Cell> open;
	private boolean[][] closed;
	private int mapWidth, mapHeight;
	
	public Pathfinder(int[][] intMap, int w, int h) {

		map = new Cell[w][h];
		//System.out.printf("Map array created with dimensions: %dx%d.\n", w, h);
		mapWidth = w;
		mapHeight = h;
		init(intMap);
		
	}
	
	private void start(int startX, int startY) {
		
		open.add(map[startX][startY]);
		Cell current;
		current = open.poll();
		
		while(current != null) {

			
			closed[current.x][current.y] = true;
			
			if(current.x == endX && current.y == endY) {
				// Done				
				return;				
			}
			
			Cell neighbor;
			
			if(current.x-1 >= 0) {
				neighbor = map[current.x-1][current.y];
				updateCell(current, neighbor, current.fCost+COST);				
			}
			
			if(current.y-1 >= 0){
				neighbor = map[current.x][current.y-1];
				updateCell(current, neighbor, current.fCost+COST); 
            }
			
			if(current.x+1 < mapWidth){
				neighbor = map[current.x+1][current.y];
				updateCell(current, neighbor, current.fCost+COST); 
            }
			
			if(current.y+1 < mapHeight){
				neighbor = map[current.x][current.y+1];
				updateCell(current, neighbor, current.fCost+COST); 
            }

			
			current = open.poll();
		} 
		
		
	}
	
	public int getPathLength(int startX, int startY) {
		
		int ret = -1;
		resetMap();
		
		closed = new boolean[mapWidth][mapHeight];
		open = new PriorityQueue<Cell>();
		
		map[startX][startY].fCost = 0;
				
		start(startX, startY);
		
		if(closed[endX][endY]){
			ret++;
			Cell current = map[endX][endY];
			while(current.parent!=null){
				current = current.parent;
				ret++;
            } 
        }else System.out.println("No possible path");
		
		
		return ret;
	}

	private void updateCell(Cell c, Cell n, int cost){
		
        if(n == null || closed[n.x][n.y]) {
        	return;
        }
        int fCost = n.hCost+cost;
        
        if(!open.contains(n) || fCost < n.fCost){
            n.fCost = fCost;
            n.parent = c;
            if(!open.contains(n)) {
            	open.add(n);
            }
        }
    }
	
	private void init(int[][] intMap) {

		for(int i = 0; i < mapWidth; i++) {
			for(int j = 0; j < mapHeight; j++) {
				if(intMap[i][j] == DrawPanel.NONE) {
					map[i][j] = new Cell(i,j);
					map[i][j].x = i;
					map[i][j].y = j;
				} else if(intMap[i][j] == DrawPanel.ENTRANCE) {
					map[i][j] = new Cell(i,j);	
					map[i][j].x = i;
					map[i][j].y = j;	
					//System.out.printf("Created a cell at the entrance: (%d,%d).\n", i,j);			
				} else if(intMap[i][j] == DrawPanel.EXIT) {
					map[i][j] = new Cell(i,j);
					map[i][j].x = i;
					map[i][j].y = j;	
					endX = i;
					endY = j;
				}
			}
		}
		
		for(int i = 0; i < mapWidth; i++) {
			for(int j = 0; j < mapHeight; j++) {
				if(map[i][j] != null) {
					map[i][j].hCost = Math.abs(i-endX)+Math.abs(j-endY);
				}
			}
		}
		//System.out.println("A* initialized.");
	}
	
	private void resetMap() {
		for(int i = 0; i < mapWidth; i++) {
			for(int j = 0; j < mapHeight; j++) {
				if(map[i][j] != null) {
					map[i][j].parent = null;
				}
			}
		}
	}
	
	

}
