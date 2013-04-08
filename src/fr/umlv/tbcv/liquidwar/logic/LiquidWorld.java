package fr.umlv.tbcv.liquidwar.logic;

import fr.umlv.tbcv.liquidwar.input.GameInput;

public class LiquidWorld  {
	private static final int gameWidth = 50 ;
	private static final int gameHeight = 50 ;
	private static final int gameDepth = 1 ;
	private LiquidMap lwmap ;
	
	boolean gameOn = true ;
	private Player player ;
	private Armies armies ;


	/*                   GETTERS / SETTERS                 */
	public static int getGamewidth() {
		return gameWidth;
	}
	public static int getGameheight() {
		return gameHeight;
	}
	
	/*														*/
	
	
	public LiquidWorld() {
		lwmap = new LiquidMap( gameWidth , gameHeight , gameDepth ) ;
		player = new Player() ;
		armies = new Armies(lwmap) ;
		
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
		armies.move( lwmap ) ;
	}
	
	
	
	
	

}
