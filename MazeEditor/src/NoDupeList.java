import java.util.Collection;
import java.util.Vector;

public class NoDupeList<Cell> extends Vector<Cell> {
	
	@Override 
	public boolean add(Cell c) {
		if(!contains(c)) {
			super.add(c);
		}
		return true;
	}
	
	@Override
	public boolean addAll(Collection<? extends Cell> other) {
		for(Cell c : other) {
			add(c);
		}
		return true;
	}
}
