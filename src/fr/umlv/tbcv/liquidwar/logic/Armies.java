package fr.umlv.tbcv.liquidwar.logic;

public interface Armies {
	public void move(LiquidMap map) ;
	public int getFightersNumber() ;
	public int[] getFightersPosition () ;
}
