package fr.umlv.tbcv.liquidwar.logic;

import fr.umlv.tbcv.liquidwar.input.GameInput;

public class LiquidWorld  {
	private static final int gameWidth = 50 ;
	private static final int gameHeight = 50 ;
	private static final int fighterNumber = 500 ;
	
	private static boolean[] fighterMap ;
	
	boolean gameOn = true ;
	
	private Player player ;
	private Fighter[] fighters  ;

	/*                   GETTERS / SETTERS                 */
	public static int getGamewidth() {
		return gameWidth;
	}
	public static int getGameheight() {
		return gameHeight;
	}
	
	/*														*/
	
	
	public LiquidWorld() {
		player = new Player() ;
		fighters = new Fighter [fighterNumber] ;
		for ( int i = 0 ; i < fighterNumber ; i++ ) {
			fighters[i] = new Fighter();
		}
		
		fighterMap = new boolean [ gameWidth * gameHeight ] ;
		for ( int i = 0 ; i < gameWidth * gameHeight ; i++ ) {
			fighterMap[ i ] = false ;
		}
		new GameInput() ;
	}
	
	/**
	 * Realize a game turn (Every fighter moves one pixel at most)
	 */
	public void turn() {
		
		// Update Player position
		player.getPosition().setX( GameInput.getxPlayer() ) ;
		player.getPosition().setY( GameInput.getyPlayer() ) ;
		
		// Every fighter decides its next position and moves
		for ( Fighter f : fighters ) {
			f.move();
		}
	}
	
	
	
	
	

}
