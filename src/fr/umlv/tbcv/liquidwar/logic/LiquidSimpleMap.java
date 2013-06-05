package fr.umlv.tbcv.liquidwar.logic;

public class LiquidSimpleMap implements LiquidMap {
	private int w,h ;
	private int map[] ;
	public static final int EMPTY = 0 ;
	public static final int OBSTACLE = -1 ; // Map contains either 0(empty), -1(obstacle), or n > 0 : n being the fighter index in the Fighters[] array
	
	public LiquidSimpleMap (int w, int h) {
		this.w = w ;
		this.h = h ;
		
		// Every element initialized and equals 0 (EMPTY) at the creation
		map = new int[w * h] ;
	}
	
	private void putElement ( Coordinates coord, int element) {
        if( coord.getX() <  0 || coord.getX() >= w || coord.getY() < 0 || coord.getY() >= h ) {
            throw new RuntimeException(); // Out of bounds : Throw exception
        }
		map[ Coordinates.calculateIndex(w, h, coord.getX() , coord.getY() ) ] = element ;
	}

    private int getElement ( Coordinates coord ) {
        if( coord.getX() <  0 || coord.getX() >= w || coord.getY() < 0 || coord.getY() >= h ) {
            return -1 ; // Out of bounds : We say it's s an obstacle
        }
        return map[ Coordinates.calculateIndex(w, h, coord.getX() , coord.getY() ) ] ;
    }

    private Fighter getFighterWithIndex(Fighter[] fighters, int index) {
        if( index <= 0 || index > fighters.length) {
            throw new RuntimeException() ;
        }
        return fighters[index-1] ;
    }

    public Fighter getFighter(Fighter[] fighters, Coordinates c) {
        return getFighterWithIndex( fighters, getElement(c)) ;
    }

    @Override
	public void clear ( Coordinates coord ) {
		putElement( coord,  EMPTY) ;
	}
	
	@Override
	public void putObstacle (Coordinates coord) {
		putElement( coord,  OBSTACLE) ;
	}
	
	@Override
	public void putSoldier ( Coordinates coord,  Fighter f) {
		if (! (f instanceof SimpleFighter) )  {
			throw new RuntimeException() ;
		}
		
		SimpleFighter fighter = (SimpleFighter) f ;
		// Old fighter position gets cleared from the board
		clear( fighter.getPosition() ) ;
		// New position (in coord) is now occupied by the fighter
		putElement( coord, fighter.getIndex() ) ;
	}

    @Override
    public int getWidth() {
        return w ;
    }

    @Override
    public int getHeight() {
        return h ;
    }
	
	@Override
	public CellState checkPosition ( Coordinates pos ) {
		switch(getElement(pos)) {
            case -1 : return CellState.OBSTACLE ;
            case 0 : return CellState.EMPTY ;
            default : return CellState.FIGHTER ;
        }
	}

    @Override
    public boolean isEmpty (Coordinates pos) {
        if (checkPosition(pos) == CellState.EMPTY ) {
            return true ;
        }
        return false ;
    }

    @Override
    public boolean hasFighter(Coordinates pos) {
        if (checkPosition(pos) == CellState.FIGHTER ) {
            return true ;
        }
        return false ;
    }


}
