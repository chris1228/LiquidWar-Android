package fr.umlv.tbcv.liquidwar.logic;

public interface LiquidMap {
	
	public void clear (Coordinates coord) ;
	
	public void putObstacle (Coordinates coord) ;
	
	public void putSoldier (Coordinates coord, Fighter f);

    public int getWidth() ;
    public int getHeight() ;
	
//	public Fighter checkFighter (Coordinates pos) ;
	
	public CellState checkPosition (Coordinates pos) ;
}
