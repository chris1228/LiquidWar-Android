package fr.umlv.tbcv.liquidwar.logic;

public class SimpleArmies implements Armies {
	
	public static final int fighterNumber = 300 ;
	private Fighter[] fighters ;
	private int[] fightersPosition ;
    private int nbArmies = 1 ;
	LiquidSimpleMap lwmap ;
	
	
	public SimpleArmies ( LiquidSimpleMap lwmap , int nbArmies ) {
		this.nbArmies = nbArmies ;
		this.lwmap = lwmap ;

		fighters = new SimpleFighter[ fighterNumber ] ;
		initArmy() ;

		// 2 slots ( one for X, the other for Y ) for each fighter
		fightersPosition = new int[fighterNumber * 2] ;
	}

    /**
     * Initializes the position of every fighter
     * Every fighter should be regrouped with its friends
     * from the same Army in a corner of the map.
     */
	private void initArmy() {
		int j  = 3 ;
		int fakeWidth = 20 ;

		for ( int i = 0 ; i < fighterNumber/2 ; i++ ) {
			fighters[i] = new SimpleFighter(i+1,0);
			fighters[i].getPosition().setX( i% (fakeWidth + 1 )) ;
			fighters[i].getPosition().setY(  j ) ;

//			Log.e("FighterPos", "Fighter init at" + fighters[i].getPosition() ) ;

			if ( i >= fakeWidth && i % fakeWidth == 0 ) {
				j++ ;
			}
		}

        j = lwmap.getHeight() - 3 ;
        for ( int i = fighterNumber/2 ; i < fighterNumber ; i++ ) {
            fighters[i] = new SimpleFighter(i+1,1);
            fighters[i].getPosition().setX( i% (fakeWidth + 1 )) ;
            fighters[i].getPosition().setY( j ) ;

//			Log.e("FighterPos", "Fighter init at" + fighters[i].getPosition() ) ;

            if ( i >= fakeWidth && i % fakeWidth == 0 ) {
                j-- ;
            }
        }
	}

	@Override
	public void move() {
		for ( int i = 0 ; i < fighterNumber; i++ ) {
			fighters[i].move( lwmap, fighters ) ;
		}
	}

	
	/* GETTER / SETTERS */

    @Override
	public int[] getFightersPosition (int team) {
		int i = 0 ;
		
		for ( int f = 0 ; f < fighterNumber ; f++ )
		{
            if(fighters[f].team == team || team == -1) {
                fightersPosition[i++] = fighters[f].getPosition().getX() ;
                fightersPosition[i++] = fighters[f].getPosition().getY() ;
            }
		}
		return fightersPosition ;
	}
	
	public Fighter[] getFighters () {
		return fighters ;
	}


	@Override
	public int getFightersNumber(int team) {
        int i = 0 ;
        for ( int f = 0 ; f < fighterNumber ; f++ )
        {
            if(fighters[f].team == team) {
                i++ ;
            }
        }
        return i ;
	}

    @Override
    public int getFightersNumber() {
        return fighterNumber ;
    }

    @Override
    public int getArmiesNumber() {
        return nbArmies ;
    }
}
