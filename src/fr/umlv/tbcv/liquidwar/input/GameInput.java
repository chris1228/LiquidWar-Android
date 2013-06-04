package fr.umlv.tbcv.liquidwar.input;

import fr.umlv.tbcv.liquidwar.logic.Coordinates;
import fr.umlv.tbcv.liquidwar.logic.LiquidWorld;

public class GameInput {
    private static Coordinates[] playersCoordinates = new Coordinates[LiquidWorld.MAXPLAYERS] ;
	private static volatile int xPlayer ;
	private static volatile int yPlayer ;
	
	public GameInput() {
        for(Coordinates c : playersCoordinates) {
            c = new Coordinates() ;
        }
		xPlayer = yPlayer = 0 ;
	}

    public static Coordinates getPlayerCoordinate(int player) {
        if(player < 1 ||player > LiquidWorld.MAXPLAYERS) {
            throw new RuntimeException() ;
        }
        return playersCoordinates[player-1] ;
    }

    public static void setPlayersCoordinates(int player, Coordinates newCoordinates) {
        if(player < 1 ||player > LiquidWorld.MAXPLAYERS) {
            throw new RuntimeException() ;
        }
        playersCoordinates[player-1].copyCoordinates(newCoordinates);
    }

}
