package fr.umlv.tbcv.liquidwar.logic;

import android.util.Log;

public class Armies {
	
	public static final int fighterNumber = 600 ;
	private Fighter[] fighters ;
	private int[] fightersPosition ;
	LiquidMap lwmap ;
	
	
	public Armies ( LiquidMap lwmap ) {
		
		this.lwmap = lwmap ;
		
		fighters = new Fighter[ fighterNumber ] ;
		initArmy() ;

		// 2 slots ( one for X, the other for Y ) for each fighter
		fightersPosition = new int[fighterNumber * 2] ;
	}

	private void initArmy() {
		int j  = 3 ;
		int fakeWidth = 20 ;
		
		for ( int i = 0 ; i < fighterNumber ; i++ ) {
			fighters[i] = new Fighter(i);
			fighters[i].getPosition().setX( i% (fakeWidth + 1 )) ;
			fighters[i].getPosition().setY(  j ) ;

//			Log.e("FighterPos", "Fighter init at" + fighters[i].getPosition() ) ;
			
			if ( i >= fakeWidth && i % fakeWidth == 0 ) {
				j++ ;
			}
		}
	}

	public void move( LiquidMap lwmap ) {
		for ( int i = 0 ; i < fighterNumber; i++ ) {
			fighters[i].move( lwmap ) ;
		}
	}
	
	/**
	 * Reads every fighters' coordinates and aggregates them in an array
	 * for drawing purposes
	 */
	public void retrieveFightersPosition () {  
		int i = 0 ;
		
		for ( int f = 0 ; f < fighterNumber ; f++ )
		{
			fightersPosition[i++] = fighters[f].getPosition().getX() ;
			fightersPosition[i++] = fighters[f].getPosition().getY() ;
//			Log.e("FighterPos", fighters[f].getPosition().toString() );
//			Log.e("Array", "ARRAY["+ fightersPosition[i-2] + "," + fightersPosition[i-1] + "]") ;
		}

	}
	
	
	/* GETTER / SETTERS */
	
	public int[] getFightersPosition () { 
		return fightersPosition ;
	}
	
	public Fighter[] getFighters () {
		return fighters ;
	}
}
