package fr.umlv.tbcv.liquidwar.logic;

import fr.umlv.tbcv.liquidwar.input.GameInput;

public class LiquidWorld  {
    public static final int MAXPLAYERS = 4 ;
	public static final int gameWidth = 45 ;
	public static final int gameHeight = 80 ;
    private int nbPlayers = 1 ;
    private LiquidSimpleMap lwmap ;
	
	boolean gameOn = true ;
	private Player[] players ;
	private Armies armies ;


    public LiquidWorld(int nbPlayers) {
        this.nbPlayers = nbPlayers ;
        lwmap = new LiquidSimpleMap( gameWidth , gameHeight) ;
        armies = new SimpleArmies(lwmap) ;
        players = new Player[nbPlayers] ;
        for(int i = 0 ; i < nbPlayers ; i++) {
            players[i] = new Player();
        }

        new GameInput(nbPlayers) ;
    }

	/**
	 * Realize a game turn (Every fighter moves one pixel at most)
	 */
	public void turn() {
		// Update Player position
        for(int i = 0 ; i < nbPlayers ; i++) {
            players[i].setPosition(GameInput.getPlayerCoordinate(i));
        }
		
		// Every fighter decides its next position and moves
		armies.move( lwmap ) ;
	}
	
	
	/*                   GETTERS / SETTERS                 */
	public static int getGamewidth() {
		return gameWidth;
	}
	public static int getGameheight() {
		return gameHeight;
	}
	
	public Player[] getPlayers() {
		return players;
	}

    public int getNbPlayers () {
        return nbPlayers ;
    }
	
	public Armies getArmies() {
		return armies;
	}
	
	
	/*														*/
	
}
