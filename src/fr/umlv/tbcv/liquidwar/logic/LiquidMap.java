package fr.umlv.tbcv.liquidwar.logic;

public interface LiquidMap {
	
	public void clear (Coordinates coord) ;
	
	public void putObstacle (Coordinates coord) ;
	
	public void putSoldier (Coordinates coord, Fighter f);

    public int getWidth() ;
    public int getHeight() ;
	
	public CellState checkPosition (Coordinates pos) ;
    public boolean isEmpty (Coordinates pos) ;
    public boolean hasFighter (Coordinates pos) ;
}
