package fr.umlv.tbcv.liquidwar.logic;

public class Armies {
	
	public static final int fighterNumber = 500 ;
	private Fighter[] fighters ;
	private int[] fightersPosition ;
	
	
	public Armies () {
		for ( int i = 0 ; i < fighterNumber ; i++ ) {
			fighters[i] = new Fighter();
		}

		// 2 slots ( one for X, the other for Y ) for each fighter
		fightersPosition = new int[fighterNumber * 2] ;
	}

	public void move() {
		for ( Fighter f : fighters ) {
			f.move() ;
		}
	}
	
	/**
	 * Reads every fighters' coordinates and aggregates them in an array
	 * for drawing purposes
	 */
	public void retrieveFightersPosition () {
		int i = 0 ;
		for ( Fighter f : fighters ) {
			fightersPosition[i++] = f.getPosition().getX() ;
			fightersPosition[i++] = f.getPosition().getY() ;
		}
	}
	
	public int[] getFightersPosition () { 
		return fightersPosition ;
	}
}
