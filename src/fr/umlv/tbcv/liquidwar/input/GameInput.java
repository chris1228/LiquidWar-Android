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

package fr.umlv.tbcv.liquidwar.input;

import fr.umlv.tbcv.liquidwar.logic.Coordinates;
import fr.umlv.tbcv.liquidwar.logic.LiquidWorld;

/**
 * Input handling module.
 * Every input, whatever its nature should be sent to this class,
 * which in turn, will transmit the informations to the logic backend.
 */
public class GameInput {
    private static int nbPlayers ;
    private static Coordinates[] playersCoordinates ;
	
	public GameInput(int nbPlayers) {
        this.nbPlayers = nbPlayers ;
        playersCoordinates = new Coordinates[nbPlayers] ;
        for(int i = 0 ; i < nbPlayers ; i++) {
            switch(i) {
                default :
                case 0 : playersCoordinates[i] = new Coordinates(0,0); break ;
                case 1 : playersCoordinates[i] = new Coordinates(0,LiquidWorld.gameHeight-1); break ;
                case 2 : playersCoordinates[i] = new Coordinates(LiquidWorld.gameWidth-1,LiquidWorld.gameHeight-1); break ;
                case 3 : playersCoordinates[i] = new Coordinates(LiquidWorld.gameWidth-1,0); break ;
            }
        }
	}

    public static Coordinates getPlayerCoordinate(int player) {
        if(player < 0 ||player >= LiquidWorld.MAXPLAYERS) {
            throw new RuntimeException() ;
        }
        return playersCoordinates[player] ;
    }

    public static void setPlayersCoordinates(int player, Coordinates newCoordinates) {
        if(player < 0 ||player >= LiquidWorld.MAXPLAYERS) {
            throw new RuntimeException() ;
        }
        playersCoordinates[player].copyCoordinates(newCoordinates);
    }

    public static int getNbPlayers () {
        return nbPlayers ;
    }

}
