package fr.umlv.tbcv.liquidwar.input;

import fr.umlv.tbcv.liquidwar.logic.Coordinates;

public class GameInput {
	private static volatile int xPlayer ;
	private static volatile int yPlayer ;
	
	public GameInput() {
		xPlayer = yPlayer = 0 ;
	}
	
	
	public static int getxPlayer() {
		return xPlayer;
	}
	public static void setxPlayer(int xPlayer) {
		GameInput.xPlayer = xPlayer;
	}
	public static int getyPlayer() {
		return yPlayer;
	}
	public static void setyPlayer(int yPlayer) {
		GameInput.yPlayer = yPlayer;
	}
	
	public static Coordinates getPosition () {
		return new Coordinates( xPlayer, yPlayer ) ;
	}

}
