package fr.umlv.tbcv.liquidwar.logic;

public class Armies {
	
	public static final int fighterNumber = 100 ;
	private Fighter[] fighters ;
	private int[] fightersPosition ;
	LiquidMap lwmap ;
	
	
	public Armies ( LiquidMap lwmap ) {
		
		this.lwmap = lwmap ;
		
		fighters = new Fighter[ fighterNumber ] ;
		
		Coordinates tempPosition = new Coordinates() ;
		int j  = 0 ;
		int fakeWidth = 20 ;
		for ( int i = 0 ; i < fighterNumber ; i++ ) {
			fighters[i] = new Fighter(i);
			// TEMP position initialization
			tempPosition.setX( i % (fakeWidth + 1) ) ;
			tempPosition.setY( j ) ;
			fighters[i].setPosition( tempPosition ) ;
			
			if ( i % fakeWidth == 0 ) {
				j++ ;
			}
			// TEMP end of position initialization 
		}

		// 2 slots ( one for X, the other for Y ) for each fighter
		fightersPosition = new int[fighterNumber * 2] ;
	}

	public void move( LiquidMap lwmap ) {
		for ( Fighter f : fighters ) {
			f.move( lwmap ) ;
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
	
	public Fighter[] getFighters () {
		return fighters ;
	}
}
