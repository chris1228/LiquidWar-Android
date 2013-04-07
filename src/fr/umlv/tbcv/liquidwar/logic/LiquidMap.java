package fr.umlv.tbcv.liquidwar.logic;

public class LiquidMap {
	private int w,h,d ;
	private int map[] ;
	
	private int nbPlaces, nbZones, nbSlots, nbUsableSlots, nbRoomForArmies, maxZoneSize ;
	
	// places , zones , slots 
	
	public LiquidMap () {
		w = h = d = nbPlaces = nbZones = nbSlots = nbUsableSlots = nbRoomForArmies = maxZoneSize = 0 ;
	}
}
