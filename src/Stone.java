

import java.awt.Point;

public class Stone{
	private int x,y;
	
	public Stone(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Stone(Point p) {
		this.x = (int) p.getX();
		this.y = (int) p.getY();
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}
