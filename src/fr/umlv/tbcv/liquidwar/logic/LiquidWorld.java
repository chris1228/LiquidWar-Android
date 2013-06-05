package fr.umlv.tbcv.liquidwar.logic;

import android.util.Log;

import fr.umlv.tbcv.liquidwar.input.GameInput;

public class LiquidWorld  {
    public static final int MAXPLAYERS = 4 ;
	public static final int gameWidth = 45 ;
	public static final int gameHeight = 80 ;
    private int nbPlayers  ;
    private LiquidSimpleMap lwmap ;
	
	boolean gameOn = true ;
	private Player[] players ;
	private Armies armies ;


    public LiquidWorld(int nbPlayers) {
        this.nbPlayers = nbPlayers ;
        new GameInput(nbPlayers) ;
        lwmap = new LiquidSimpleMap(gameWidth , gameHeight) ;
        armies = new SimpleArmies(lwmap, nbPlayers) ;
        players = new Player[nbPlayers] ;
        for(int i = 0 ; i < nbPlayers ; i++) {
            switch(i) {
                default :
                case 0 : players[i] = new Player(0,0); break ;
                case 1 : players[i] = new Player(0,gameHeight); break ;
                case 2 : players[i] = new Player(gameWidth,gameHeight); break ;
                case 3 : players[i] = new Player(gameWidth,0); break ;
            }
        }
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
		armies.move() ;
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
