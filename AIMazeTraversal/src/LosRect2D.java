import java.awt.geom.Rectangle2D;

@SuppressWarnings("serial")
public class LosRect2D extends Rectangle2D.Double {
	int type;
	public LosRect2D(int x, int y, int w, int h, int type) {
		super(x,y,w,h);
		this.type = type;
	}
}
