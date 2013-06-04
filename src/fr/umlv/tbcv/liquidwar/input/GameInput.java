package fr.umlv.tbcv.liquidwar.input;

import fr.umlv.tbcv.liquidwar.logic.Coordinates;
import fr.umlv.tbcv.liquidwar.logic.LiquidWorld;

public class GameInput {
    private static int nbPlayers ;
    private static Coordinates[] playersCoordinates ;
	
	public GameInput(int nbPlayers) {
        this.nbPlayers = nbPlayers ;
        playersCoordinates = new Coordinates[nbPlayers] ;
        for(int i = 0 ; i < nbPlayers ; i++) {
            playersCoordinates[i] = new Coordinates() ;
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
