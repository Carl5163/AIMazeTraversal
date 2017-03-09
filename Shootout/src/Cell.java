
public class Cell implements Comparable{
	
	public int hCost;
	public int fCost;
	public int x, y;
	public Cell parent;
	
	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int compareTo(Object o) {
		if(fCost < ((Cell)o).fCost) {
			return -1;
		}
		if(fCost > ((Cell)o).fCost) {
			return 1;
		}
		return 0;
	}
	
	@Override
	public String toString() {
		return String.format("(%d, %d)",x, y);
	}

}
