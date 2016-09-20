import java.util.PriorityQueue;

public class Pathfinder {
	
	// This class was made following a tutorial at 
	// http://www.codebytes.in/2015/02/a-shortest-path-finding-algorithm.html
	
	public static final int COST = 10;
	
	private Cell[][] map;
	private int startX, startY, endX, endY;
	private PriorityQueue<Cell> open;
	private boolean[][] closed;
	private int mapWidth, mapHeight;
	
	public Pathfinder(int[][] intMap, int w, int h) {

		Cell[][] map;
		map = new Cell[w][h];
		init(map, intMap, w, h);
		mapWidth = w;
		mapHeight = h;
	}
	
	public void start(int sX, int sY) {
		startX = sX;
		startY = sY;
		
		open.add(map[startX][startY]);
		Cell current;
		current = open.poll();
		
		while(current != null) {

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
	
	private void init(Cell[][] map, int[][] intMap, int w, int h) {

		for(int i = 0; i < w; i++) {
			for(int j = 0; j < h; j++) {
				if(intMap[i][j] == DrawPanel.NONE) {
					map[i][j] = new Cell(i,j);
				}  else if(intMap[i][j] == DrawPanel.EXIT) {
					endX = i;
					endY = j;
				}
			}
		}
		open = new PriorityQueue<Cell>();
	}
	

}
