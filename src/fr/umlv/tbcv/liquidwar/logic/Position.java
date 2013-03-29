package fr.umlv.tbcv.liquidwar.logic;

public class Position {
	private int x,y ;
	
	public Position () {
		x = 0 ;
		y = 0 ;
	}
	
	public Position (int x, int y) {
		this.x = x ;
		this.y = y ;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Calculate the square distance between 2 positions
	 * @param a	The first position
	 * @param b The second position
	 * @return	The square distance between the 2 positions
	 */
	public static int getSquareDistance (Position a, Position b) {
		if ( a == null || b == null ) {
			return -1 ;
		}
		
		return ( (b.getX() - a.getX()) * (b.getX() - a.getX()) ) +
				( (b.getY() - a.getY()) * (b.getY() - a.getY()) ) ;

	}
}
