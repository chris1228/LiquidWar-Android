package fr.umlv.tbcv.liquidwar.logic;

public class LiquidMap {
	private int w,h,d ;
	private int map[] ;
	
	public static final int EMPTY = 0 ;
	public static final int OBSTACLE = 1 ;
	public static final int SOLDIER = 2 ;
	
	private int nbPlaces, nbZones, nbSlots, nbUsableSlots, nbRoomForArmies, maxZoneSize ;
	
	
	// places , zones , slots 
	
	public LiquidMap (int w, int h, int d) {
		nbPlaces = nbZones = nbSlots = nbUsableSlots = nbRoomForArmies = maxZoneSize = 0 ;
		this.w = w ;
		this.h = h ;
		this.d = d ;
		// Every element initialized and equals 0 (EMPTY) at the creation
		map = new int[w * h] ;
	}
	
	void putElement (int x, int y, int element) {
		map[ Coordinates.calculateIndex(w, h, x, y) ] = element ;
	}
	
	void clear (int x, int y) {
		putElement(x,y, EMPTY) ;
	}
	
	void putObstacle (int x, int y) {
		putElement(x, y, OBSTACLE) ;
	}
	
	void putSoldier (int x, int y) {
		putElement(x, y, SOLDIER) ;
	}
		
}
