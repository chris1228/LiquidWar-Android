package fr.umlv.tbcv.liquidwar.logic;

public class LiquidMap {
	private int w,h,d ;
	private int map[] ;
	
	public static final int EMPTY = 0 ;
	public static final int OBSTACLE = -1 ; 
	
	private int nbPlaces, nbZones, nbSlots, nbUsableSlots, nbRoomForArmies, maxZoneSize ;
	// + lw6ker_place_struct_t
	// + lw6ker_zone_struct_t
	// + lw6ker_slot_struct_t
	
	// places , zones , slots 
	
	public LiquidMap (int w, int h, int d ) {
		nbPlaces = nbZones = nbSlots = nbUsableSlots = nbRoomForArmies = maxZoneSize = 0 ;
		this.w = w ;
		this.h = h ;
		this.d = d ;
		
		// Every element initialized and equals 0 (EMPTY) at the creation
		map = new int[w * h] ;
	}
	
	void putElement ( Coordinates coord, int element) {
		map[ Coordinates.calculateIndex(w, h, coord.getX() , coord.getY() ) ] = element ;
	}
	
	void clear ( Coordinates coord ) {
		putElement( coord,  EMPTY) ;
	}
	
	void putObstacle (Coordinates coord) {
		putElement( coord,  OBSTACLE) ;
	}
	
	void putSoldier ( Coordinates coord,  Fighter f) {
		// Old fighter position gets cleared from the board
		clear( f.getPosition() ) ;
		// New position (in coord) is now occupied by the fighter
		putElement( coord, f.getIndex() ) ;
	}
	
	int checkPosition ( Coordinates pos ) {
		if  ( pos.getX() < 0 || pos.getX() >= w || pos.getY() < 0 || pos.getY() >= h ) {
			return OBSTACLE ;
		}
		
		return map[ Coordinates.calculateIndex(w, h, pos.getX(), pos.getY() )] ;
	}
		
}
