package fr.umlv.tbcv.liquidwar.logic;

public class DraftZones {
	private Coordinates shape ;
	private int max_zone_size ; 
	private DraftZone[] zones ;
	
	public class DraftZone {
		Coordinates pos ;
		boolean used ;
		int size ;
		int[] link ; 
		int corres ;
		
		public DraftZone(int x, int y) {
			pos = new Coordinates(x,y) ;
			used = false ;
			size = 1 ;
			link = new int[ LiquidDirections.numberOfDir ] ;
			corres = 0 ;
		}
	}
	
	
	public void draft_zones_new (MapLevel level) {
		
	}
	
	
	
	
}
