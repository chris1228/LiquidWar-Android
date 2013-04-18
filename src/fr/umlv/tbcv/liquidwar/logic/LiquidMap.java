package fr.umlv.tbcv.liquidwar.logic;

public interface LiquidMap {
	
	public void clear (Coordinates coord) ;
	
	public void putObstacle (Coordinates coord) ;
	
	public void putSoldier (Coordinates coord, Fighter f);
	
//	public Fighter checkFighter (Coordinates pos) ;
	
	public CellState checkPosition (Coordinates pos) ;
}
