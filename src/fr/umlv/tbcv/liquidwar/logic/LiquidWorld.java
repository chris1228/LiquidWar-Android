/*
 Copyright (C) 2013 Thomas Bardoux, Christophe Venevongsos

 This file is part of Liquid Wars Android

 Liquid Wars Android is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 Liquid Wars Android is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.

 */

package fr.umlv.tbcv.liquidwar.logic;

import android.util.Log;

import fr.umlv.tbcv.liquidwar.input.GameInput;

/**
 * Main class describing the game state.
 * There should be only one instance of LiquidWorld for a running Liquid Wars game.
 */
public class LiquidWorld  {
    public static final int MAXPLAYERS = 4 ;
	public static final int gameWidth = 45 ;
	public static final int gameHeight = 80 ;
    private int nbPlayers  ;
    private LiquidMap lwmap ;
	
	boolean gameOn = true ;
	private Player[] players ;
	private Armies armies ;


    public LiquidWorld(int nbPlayers) {
        if( nbPlayers < 1 || nbPlayers > MAXPLAYERS ) {
            throw new RuntimeException() ;
        }
        this.nbPlayers = nbPlayers ;
        new GameInput(nbPlayers) ;
        players = new Player[nbPlayers] ;

        // Initialize players players position at the start of the game (every player at one corner of the map)
        for(int i = 0 ; i < nbPlayers ; i++) {
            switch(i) {
                default :
                case 0 : players[i] = new Player(0,0); break ;
                case 1 : players[i] = new Player(0,gameHeight-1); break ;
                case 2 : players[i] = new Player(gameWidth-1,gameHeight-1); break ;
                case 3 : players[i] = new Player(gameWidth-1,0); break ;
            }
        }

//        lwmap = new LiquidSimpleMap(gameWidth,gameHeight) ;
//        lwmap.loadMap(); // Important : have to load the map before creating the armies
//        armies = new SimpleArmies((LiquidSimpleMap)lwmap, nbPlayers) ;
        lwmap = new LiquidNodeMap(gameWidth,gameHeight) ;
        lwmap.loadMap();
        armies = new NodeArmies(lwmap,nbPlayers);

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

    public LiquidMap getLiquidMap() {
        return lwmap ;
    }
	
	
	/*														*/
	
}
