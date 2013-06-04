package fr.umlv.tbcv.liquidwar.logic;

public class SimpleArmies implements Armies {
	
	public static final int fighterNumber = 300 ;
	private SimpleFighter[] fighters ;
	private int[] fightersPosition ;
	LiquidSimpleMap lwmap ;
	
	
	public SimpleArmies ( LiquidSimpleMap lwmap ) {
		
		this.lwmap = lwmap ;
		
		fighters = new SimpleFighter[ fighterNumber ] ;
		initArmy() ;

		// 2 slots ( one for X, the other for Y ) for each fighter
		fightersPosition = new int[fighterNumber * 2] ;
	}

	private void initArmy() {
		int j  = 3 ;
		int fakeWidth = 20 ;
		
		for ( int i = 0 ; i < fighterNumber ; i++ ) {
			fighters[i] = new SimpleFighter(i+1,1);
			fighters[i].getPosition().setX( i% (fakeWidth + 1 )) ;
			fighters[i].getPosition().setY(  j ) ;

//			Log.e("FighterPos", "Fighter init at" + fighters[i].getPosition() ) ;
			
			if ( i >= fakeWidth && i % fakeWidth == 0 ) {
				j++ ;
			}
		}
	}

	@Override
	public void move( LiquidMap lwmap ) {
		for ( int i = 0 ; i < fighterNumber; i++ ) {
			fighters[i].move( lwmap ) ;
		}
	}
	
	
	/* GETTER / SETTERS */
	
	public int[] getFightersPosition () { 
		int i = 0 ;
		
		for ( int f = 0 ; f < fighterNumber ; f++ )
		{
			fightersPosition[i++] = fighters[f].getPosition().getX() ;
			fightersPosition[i++] = fighters[f].getPosition().getY() ;
		}
		return fightersPosition ;
	}
	
	public SimpleFighter[] getFighters () {
		return fighters ;
	}


	@Override
	public int getFightersNumber() {
		return fighterNumber ;
	}
}
